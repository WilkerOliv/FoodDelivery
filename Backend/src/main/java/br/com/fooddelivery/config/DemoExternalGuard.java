package br.com.fooddelivery.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Blocks real provider entry points even if credentials leak into the process environment. */
@Component @Profile("homologacao") @Order(1)
public class DemoExternalGuard extends OncePerRequestFilter {
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain) throws ServletException,IOException {
        String path=request.getRequestURI();
        if(path.startsWith("/api/v1/webhooks/") || path.contains("/mercadopago/callback")
                || path.endsWith("/mercadopago/connect") || path.matches("/api/v1/payments/[^/]+/(checkout|sync)")) {
            response.setStatus(409);response.setCharacterEncoding("UTF-8");response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Homologação local: integração real bloqueada. Use o simulador de pagamento.\"}");return;
        }
        chain.doFilter(request,response);
    }
}
