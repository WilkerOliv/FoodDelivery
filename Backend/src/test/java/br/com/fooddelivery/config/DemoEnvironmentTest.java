package br.com.fooddelivery.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import static org.junit.jupiter.api.Assertions.*;

class DemoEnvironmentTest {
    @Test void productionCannotEnableDemoByProperty() {
        var env=new MockEnvironment().withProperty("app.demo.enabled","true");env.setActiveProfiles("prod");
        var demo=new DemoEnvironment(env);assertFalse(demo.enabled());
        assertThrows(IllegalStateException.class,()->demo.capture("EMAIL","test@example.test","123456"));
    }
    @Test void demoRefusesProductionOrAnotherDatabaseProfile() {
        for(String profile:new String[]{"prod","postgres","dev-h2"}){
            var env=new MockEnvironment();env.setActiveProfiles("homologacao",profile);
            assertThrows(IllegalStateException.class,()->new DemoEnvironment(env));
        }
    }
    @Test void inboxIsBoundedAndReturnsNewestMessagesFirst() {
        var env=new MockEnvironment();env.setActiveProfiles("homologacao");var demo=new DemoEnvironment(env);
        for(int i=0;i<105;i++)demo.capture("EMAIL","test@example.test",String.valueOf(i));
        assertTrue(demo.enabled());assertEquals(100,demo.messages().size());assertEquals("104",demo.messages().getFirst().code());
    }
}
