package br.com.fooddelivery.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.servlet.FilterChain;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

class AuthenticationRateLimitFilterTest {
    @AfterEach void clearAuthentication() { SecurityContextHolder.clearContext(); }
    @Test
    void throttlesLoginAttemptsPerRemoteAddressAndReturnsRetryInformation() throws Exception {
        var filter = new AuthenticationRateLimitFilter();
        var passed = new AtomicInteger();
        FilterChain chain = (request, response) -> { passed.incrementAndGet(); ((MockHttpServletResponse) response).setStatus(401); };

        for (int i = 0; i < 10; i++) invoke(filter, "/api/v1/auth/login", "192.0.2.10", chain);
        var limited = invoke(filter, "/api/v1/auth/login", "192.0.2.10", chain);

        assertEquals(10, passed.get());
        assertEquals(429, limited.getStatus());
        assertTrue(Long.parseLong(limited.getHeader("Retry-After")) > 0);
        assertTrue(limited.getContentAsString().contains("Muitas tentativas"));
    }

    @Test
    void limitsDoNotShareBucketsAcrossClientsOrAuthenticationFlows() throws Exception {
        var filter = new AuthenticationRateLimitFilter();
        var passed = new AtomicInteger();
        FilterChain chain = (request, response) -> passed.incrementAndGet();
        FilterChain failure = (request, response) -> { passed.incrementAndGet(); ((MockHttpServletResponse) response).setStatus(401); };
        for (int i = 0; i < 10; i++) invoke(filter, "/api/v1/auth/login", "192.0.2.11", failure);

        assertEquals(200, invoke(filter, "/api/v1/auth/login", "192.0.2.12", chain).getStatus());
        assertEquals(200, invoke(filter, "/api/v1/auth/password-recovery", "192.0.2.11", chain).getStatus());
        assertEquals(12, passed.get());
    }

    @Test void accountsBehindOneGatewayDoNotShareTheStrictLoginBudget() throws Exception {
        var filter = new AuthenticationRateLimitFilter();
        FilterChain failure = (request, response) -> ((MockHttpServletResponse) response).setStatus(401);
        for (int i=0;i<10;i++) assertEquals(401, login(filter,"cliente@example.test","127.0.0.1",failure).getStatus());
        assertEquals(429,login(filter,"cliente@example.test","127.0.0.1",failure).getStatus());
        assertEquals(200,login(filter,"vendedor@example.test","127.0.0.1",(request,response)->{}).getStatus());
    }

    @Test void successfulLoginsDoNotExhaustSharedBudgetsAndBodyStillReachesController() throws Exception {
        var filter = new AuthenticationRateLimitFilter();
        var count = new AtomicInteger();
        FilterChain success = (request,response) -> {
            assertTrue(new String(request.getInputStream().readAllBytes(),StandardCharsets.UTF_8).contains("fake-password"));
            count.incrementAndGet();
        };
        for(int i=0;i<120;i++) assertEquals(200,login(filter,"cliente@example.test","127.0.0.1",success).getStatus());
        assertEquals(120,count.get());
    }

    @Test void changingForwardedHeadersOrNetworkCannotBypassAccountFailures() throws Exception {
        var filter = new AuthenticationRateLimitFilter();
        FilterChain failure=(request,response)->((MockHttpServletResponse)response).setStatus(401);
        for(int i=0;i<10;i++)login(filter," CLIENTE@example.test ","192.0.2.10",failure);
        var request=loginRequest("cliente@example.test","192.0.2.200");
        request.addHeader("X-Forwarded-For","203.0.113.99");
        request.addHeader("X-Real-IP","203.0.113.98");
        var response=new MockHttpServletResponse();filter.doFilter(request,response,failure);
        assertEquals(429,response.getStatus());
    }

    @Test void coarsePeerBudgetLimitsAttemptsAcrossManyInventedAccounts() throws Exception {
        var filter=new AuthenticationRateLimitFilter();
        FilterChain failure=(request,response)->((MockHttpServletResponse)response).setStatus(401);
        for(int i=0;i<100;i++)assertEquals(401,login(filter,"unknown"+i+"@example.test","192.0.2.10",failure).getStatus());
        assertEquals(429,login(filter,"unknown101@example.test","192.0.2.10",failure).getStatus());
        assertEquals(401,login(filter,"unknown102@example.test","192.0.2.11",failure).getStatus());
    }

    @Test void authenticatedFinanceActionsAreLimitedPerVerifiedActor() throws Exception {
        var filter=new AuthenticationRateLimitFilter();
        FilterChain success=(request,response)->{};
        actor("00000000-0000-0000-0000-000000000001");
        for(int i=0;i<20;i++)assertEquals(200,invoke(filter,"/api/v1/courier/payouts","127.0.0.1",success).getStatus());
        assertEquals(429,invoke(filter,"/api/v1/courier/payouts","127.0.0.1",success).getStatus());
        actor("00000000-0000-0000-0000-000000000002");
        assertEquals(200,invoke(filter,"/api/v1/courier/payouts","127.0.0.1",success).getStatus());
    }

    @Test void oversizedAuthBodyIsRejectedBeforeParsing() throws Exception {
        var filter=new AuthenticationRateLimitFilter();
        var request=loginRequest("cliente@example.test","192.0.2.10");request.setContent(new byte[8193]);
        var response=new MockHttpServletResponse();var count=new AtomicInteger();
        filter.doFilter(request,response,(req,res)->count.incrementAndGet());
        assertEquals(413,response.getStatus());assertEquals(0,count.get());
    }

    @Test void sensitivePutAndDeleteAlsoRequireTheirOwnRateBudget() throws Exception {
        actor("00000000-0000-0000-0000-000000000003");
        for(String[] route:List.of(new String[]{"PUT","/api/v1/admin/integration-settings"},new String[]{"DELETE","/api/v1/account/whatsapp-link"})){
            var filter=new AuthenticationRateLimitFilter();
            for(int i=0;i<21;i++){
                var request=new MockHttpServletRequest(route[0],route[1]);request.setRemoteAddr("127.0.0.1");
                var response=new MockHttpServletResponse();filter.doFilter(request,response,(req,res)->{});
                assertEquals(i<20?200:429,response.getStatus());
            }
        }
    }

    @Test void concurrentFailuresCannotExceedTheAccountBudget() throws Exception {
        var filter=new AuthenticationRateLimitFilter();var passed=new AtomicInteger();
        FilterChain failure=(request,response)->{passed.incrementAndGet();((MockHttpServletResponse)response).setStatus(401);};
        try(var executor=Executors.newFixedThreadPool(8)){
            List<Future<Integer>> results=new ArrayList<>();
            for(int i=0;i<20;i++)results.add(executor.submit(()->login(filter,"cliente@example.test","127.0.0.1",failure).getStatus()));
            int allowed=0,blocked=0;
            for(var result:results){if(result.get()==401)allowed++;else if(result.get()==429)blocked++;}
            assertEquals(10,allowed);assertEquals(10,blocked);assertEquals(10,passed.get());
        }
    }

    private static void actor(String id) {
        var jwt=Jwt.withTokenValue("test").header("alg","HS256").subject(id).build();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt,List.of()));
    }

    private static MockHttpServletRequest loginRequest(String email,String address){
        var request=new MockHttpServletRequest("POST","/api/v1/auth/login");request.setRemoteAddr(address);
        request.setContentType("application/json");
        request.setContent(("{\"email\":\""+email+"\",\"password\":\"fake-password\"}").getBytes(StandardCharsets.UTF_8));return request;
    }

    private static MockHttpServletResponse login(AuthenticationRateLimitFilter filter,String email,String address,FilterChain chain)throws Exception{
        var response=new MockHttpServletResponse();filter.doFilter(loginRequest(email,address),response,chain);return response;
    }

    private static MockHttpServletResponse invoke(AuthenticationRateLimitFilter filter, String path, String address,
                                                   FilterChain chain) throws Exception {
        var request = new MockHttpServletRequest("POST", path);
        request.setRemoteAddr(address);
        var response = new MockHttpServletResponse();
        filter.doFilter(request, response, chain);
        if (response.getStatus() == 200) response.setStatus(200);
        return response;
    }
}
