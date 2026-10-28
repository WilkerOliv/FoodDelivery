package br.com.fooddelivery.config;

import br.com.fooddelivery.identity.AccountRepository;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    UserDetailsService userDetailsService(AccountRepository accounts) {
        return email -> accounts.findByEmailIgnoreCase(email)
                .filter(account -> account.isActive())
                .map(account -> User.withUsername(account.getEmail())
                        .password(account.getPasswordHash())
                        .roles(account.getGrantedRoles().stream().map(Enum::name).toArray(String[]::new))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Conta não encontrada"));
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecretKey jwtSecretKey(@Value("${app.security.jwt-secret}") String encodedSecret) {
        byte[] key = Base64.getDecoder().decode(encodedSecret);
        if (key.length < 32) throw new IllegalStateException("A chave JWT deve ter ao menos 256 bits.");
        return new SecretKeySpec(key, "HmacSHA256");
    }

    @Bean
    JwtEncoder jwtEncoder(SecretKey key) { return new NimbusJwtEncoder(new ImmutableSecret<>(key)); }

    @Bean
    JwtDecoder jwtDecoder(SecretKey key, AccountRepository accounts) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
        OAuth2TokenValidator<org.springframework.security.oauth2.jwt.Jwt> activeAccount = jwt -> {
            try {
                java.util.UUID accountId = java.util.UUID.fromString(jwt.getSubject());
                boolean active = accounts.findById(accountId).map(account -> {
                    List<String> granted = account.getGrantedRoles().stream().map(Enum::name).toList();
                    List<String> tokenRoles = jwt.getClaimAsStringList("roles");
                    boolean tokenRequiresPasswordChange = Boolean.TRUE.equals(jwt.getClaim("password_change_required"));
                    Number tokenCredentialVersion = jwt.getClaim("credential_version");
                    return account.isActive() && tokenRoles != null && granted.equals(tokenRoles)
                            && account.isPasswordChangeRequired() == tokenRequiresPasswordChange
                            && tokenCredentialVersion != null && tokenCredentialVersion.intValue() == account.getCredentialVersion();
                }).orElse(false);
                return active
                        ? org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.success()
                        : org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure(
                                new OAuth2Error("invalid_token", "Conta inativa ou não encontrada.", null));
            } catch (RuntimeException invalidSubject) {
                return org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure(
                        new OAuth2Error("invalid_token", "Token de acesso inválido.", null));
            }
        };
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefaultWithIssuer("food-delivery-api"), activeAccount));
        return decoder;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authorities = new JwtGrantedAuthoritiesConverter();
        authorities.setAuthoritiesClaimName("roles");
        authorities.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authorities);
        return converter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:5173", "http://127.0.0.1:5173",
                "http://localhost:5174", "http://127.0.0.1:5174",
                "http://localhost:5175", "http://127.0.0.1:5175"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider provider,
                                            JwtAuthenticationConverter jwtConverter) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(provider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/operator-applications", "/api/v1/auth/password-recovery", "/api/v1/auth/password-recovery/reset").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/payments/mercadopago/callback").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/store-assets/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/store-categories").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/webhooks/mercadopago").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/status", "/actuator/health", "/actuator/health/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/account/password").authenticated()
                        .anyRequest().access((authentication, context) -> {
                            var current = authentication.get();
                            boolean forcedPasswordChange = current instanceof JwtAuthenticationToken token
                                    && Boolean.TRUE.equals(token.getToken().getClaim("password_change_required"));
                            return new AuthorizationDecision(!forcedPasswordChange);
                        }))
                .sessionManagement(session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
                .build();
    }
}
