package br.com.fooddelivery.config;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import static org.junit.jupiter.api.Assertions.*;
class ProductionGuardTest {
 MockEnvironment safe(){return new MockEnvironment().withProperty("app.security.cookie-secure","true").withProperty("app.web.allowed-origins","https://lojas.example.test").withProperty("app.payment.mercadopago.frontend-url","https://lojas.example.test").withProperty("app.payment.mercadopago.sandbox","false");}
 @Test void acceptsHttpsSameOriginConfiguration(){assertDoesNotThrow(()->ProductionGuard.validate(safe()));}
 @Test void rejectsUnsecuredCookie(){assertThrows(IllegalStateException.class,()->ProductionGuard.validate(safe().withProperty("app.security.cookie-secure","false")));}
 @Test void rejectsWildcardAndLocalOrigins(){for(String origin:new String[]{"*","http://example.test","https://localhost","https://user:pass@example.test"})assertThrows(IllegalStateException.class,()->ProductionGuard.validate(safe().withProperty("app.web.allowed-origins",origin)));}
 @Test void rejectsTestSignatureAndMixedProfiles(){assertThrows(IllegalStateException.class,()->ProductionGuard.validate(safe().withProperty("app.payment.mercadopago.test-webhook-signature","true")));MockEnvironment mixed=safe();mixed.setActiveProfiles("prod","homologacao");assertThrows(IllegalStateException.class,()->ProductionGuard.validate(mixed));}
}
