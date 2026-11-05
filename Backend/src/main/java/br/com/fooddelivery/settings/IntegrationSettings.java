package br.com.fooddelivery.settings;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.admin.AdminAuditService;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.AccessDeniedException;
@Service
public class IntegrationSettings {
 public static final Set<String> KEYS=Set.of("SMTP_HOST","SMTP_PORT","SMTP_USERNAME","SMTP_PASSWORD","RECOVERY_EMAIL_FROM","WHATSAPP_CLOUD_ENABLED","WHATSAPP_COMMERCE_ENABLED","WHATSAPP_GRAPH_VERSION","WHATSAPP_PHONE_NUMBER_ID","WHATSAPP_ACCESS_TOKEN","WHATSAPP_OTP_TEMPLATE","WHATSAPP_TEMPLATE_LANGUAGE","WHATSAPP_VERIFY_TOKEN","WHATSAPP_APP_SECRET","MP_CLIENT_ID","MP_CLIENT_SECRET","MP_WEBHOOK_SECRET");
 private final PrivateSettingRepository settings;private final PrivateCipher cipher;private final AccountRepository accounts;private final PasswordEncoder encoder;private final AdminAuditService audit;
 public IntegrationSettings(PrivateSettingRepository settings,PrivateCipher cipher,AccountRepository accounts,PasswordEncoder encoder,AdminAuditService audit){this.settings=settings;this.cipher=cipher;this.accounts=accounts;this.encoder=encoder;this.audit=audit;}
 @Transactional(readOnly=true) public String value(String key,String fallback){if(!KEYS.contains(key))throw new IllegalArgumentException("Configuração não suportada.");return settings.findById(key).map(s->cipher.decrypt(key,s.getEncryptedValue())).orElse(fallback);}
 @Transactional(readOnly=true) public Object status(){Map<String,Boolean> configured=new TreeMap<>();for(String key:KEYS)configured.put(key,settings.existsById(key));return Map.of("encryptionReady",cipher.configured(),"overrides",configured);}
 @Transactional public void update(UUID actor,String password,Map<String,String> values,List<String> remove){var a=accounts.findByIdForUpdate(actor).orElseThrow();if(!a.getGrantedRoles().contains(br.com.fooddelivery.identity.AccountRole.ADMIN)||!encoder.matches(password,a.getPasswordHash()))throw new AccessDeniedException("Confirme sua identidade.");if(!cipher.configured())throw new IllegalStateException("A chave de configuração privada precisa estar configurada no servidor.");
  if(values.size()+remove.size()>KEYS.size())throw new IllegalArgumentException("Muitas configurações na mesma alteração.");
  for(String key:remove){validateKey(key);settings.deleteById(key);}for(var entry:values.entrySet()){validateKey(entry.getKey());String v=entry.getValue();if(v==null||v.isBlank())continue;validate(entry.getKey(),v);settings.save(new PrivateSetting(entry.getKey(),cipher.encrypt(entry.getKey(),v.trim())));}
  audit.record(actor,"INTEGRATION_SETTINGS_UPDATED","SETTINGS",null,"Configurações privadas atualizadas. Chaves e valores não registrados na auditoria.");
 }
 private static void validateKey(String key){if(!KEYS.contains(key))throw new IllegalArgumentException("Esta configuração exige gestão privada no servidor.");}
 private static void validate(String key,String v){if(v.length()>8192||v.contains("\r")||v.contains("\n"))throw new IllegalArgumentException("Valor de configuração inválido.");
  if(key.equals("SMTP_PORT")){try{int n=Integer.parseInt(v);if(n<1||n>65535)throw new NumberFormatException();}catch(NumberFormatException invalid){throw new IllegalArgumentException("Porta SMTP inválida.");}}
  if(key.equals("SMTP_HOST")&&!v.matches("[A-Za-z0-9.-]{1,253}"))throw new IllegalArgumentException("Informe apenas o nome ou IPv4 do servidor SMTP.");
  if((key.equals("WHATSAPP_CLOUD_ENABLED")||key.equals("WHATSAPP_COMMERCE_ENABLED"))&&!Set.of("true","false").contains(v))throw new IllegalArgumentException("Use true ou false para WhatsApp.");
  if(key.equals("RECOVERY_EMAIL_FROM")&&!v.matches("[^\\s@<>]+@[^\\s@<>]+"))throw new IllegalArgumentException("Remetente de e-mail inválido.");
  if(key.equals("WHATSAPP_GRAPH_VERSION")&&!v.matches("v[0-9]{1,3}\\.[0-9]{1,2}"))throw new IllegalArgumentException("Versão Graph inválida.");
  if(key.equals("WHATSAPP_PHONE_NUMBER_ID")&&!v.matches("[0-9]{1,40}"))throw new IllegalArgumentException("Identificador do número inválido.");
 }
}
