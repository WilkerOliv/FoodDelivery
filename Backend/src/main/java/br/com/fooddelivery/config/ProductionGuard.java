package br.com.fooddelivery.config;
import java.net.URI;
import java.util.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
/** Fail closed when a production process is accidentally configured as a local simulator. */
@Component @Profile("prod")
public class ProductionGuard implements ApplicationRunner {
 private final Environment environment;
 public ProductionGuard(Environment environment){this.environment=environment;}
 @Override public void run(ApplicationArguments args){validate(environment);}
 public static void validate(Environment env){
  if(!Boolean.parseBoolean(env.getProperty("app.security.cookie-secure","false")))throw new IllegalStateException("Produção exige cookie Secure.");
  String origins=env.getProperty("app.web.allowed-origins","");if(origins.isBlank())throw new IllegalStateException("Configure as origens HTTPS de produção.");
  for(String origin:origins.split(","))httpsOrigin(origin.trim());
  String frontend=env.getProperty("app.payment.mercadopago.frontend-url","");httpsOrigin(frontend);
  if(Arrays.stream(origins.split(",")).map(String::trim).noneMatch(frontend::equals))throw new IllegalStateException("A origem pública do frontend precisa estar na lista permitida.");
  if(Boolean.parseBoolean(env.getProperty("app.payment.mercadopago.sandbox","true"))||Boolean.parseBoolean(env.getProperty("app.payment.mercadopago.test-webhook-signature","false")))throw new IllegalStateException("Simuladores e assinatura de teste são proibidos em produção.");
  if(env.matchesProfiles("homologacao","dev-h2"))throw new IllegalStateException("Perfil de produção não pode ser combinado com demonstração/H2.");
  if(Boolean.parseBoolean(env.getProperty("spring.h2.console.enabled","false")))throw new IllegalStateException("Console H2 não é permitido em produção.");
 }
 private static void httpsOrigin(String input){try{URI uri=URI.create(input);if(!"https".equals(uri.getScheme())||uri.getHost()==null||uri.getUserInfo()!=null||uri.getQuery()!=null||uri.getFragment()!=null||!(uri.getPath().isEmpty()||uri.getPath().equals("/"))||Set.of("localhost","127.0.0.1","::1").contains(uri.getHost()))throw new IllegalArgumentException();}catch(RuntimeException invalid){throw new IllegalStateException("Informe uma origem pública HTTPS válida, sem caminho, credenciais ou curingas.");}}
}
