package br.com.fooddelivery.identity;
import br.com.fooddelivery.settings.PrivateCipher;
import br.com.fooddelivery.admin.AdminAuditService;
import tools.jackson.databind.ObjectMapper;
import java.util.*;
import java.time.Instant;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.AccessDeniedException;
@Service
public class AdminMfaService {
 private final AdminMfaRepository records;private final AccountRepository accounts;private final PrivateCipher cipher;private final PasswordEncoder encoder;private final ObjectMapper mapper;private final AdminAuditService audit;
 public AdminMfaService(AdminMfaRepository records,AccountRepository accounts,PrivateCipher cipher,PasswordEncoder encoder,ObjectMapper mapper,AdminAuditService audit){this.records=records;this.accounts=accounts;this.cipher=cipher;this.encoder=encoder;this.mapper=mapper;this.audit=audit;}
 private Account admin(UUID id,String password){var a=accounts.findByIdForUpdate(id).orElseThrow();if(!a.getGrantedRoles().contains(AccountRole.ADMIN)||!encoder.matches(password,a.getPasswordHash()))throw new AccessDeniedException("Confirmação de identidade inválida.");return a;}
 @Transactional(readOnly=true) public Object status(UUID id){var r=records.findById(id);return Map.of("encryptionReady",cipher.configured(),"enabled",r.map(AdminMfa::isEnabled).orElse(false),"remainingRecoveryCodes",r.filter(AdminMfa::isEnabled).map(v->hashes(v).size()).orElse(0));}
 @Transactional public Object begin(UUID id,String password){var a=admin(id,password);if(records.findById(id).filter(AdminMfa::isEnabled).isPresent())throw new IllegalStateException("O segundo fator já está ativo.");String secret=Totp.secret();records.save(new AdminMfa(id,cipher.encrypt("MFA:"+id,secret)));String label=URLEncoder.encode("FoodDelivery:"+a.getEmail(),StandardCharsets.UTF_8);return Map.of("secret",secret,"otpAuthUri","otpauth://totp/"+label+"?secret="+secret+"&issuer=FoodDelivery&algorithm=SHA1&digits=6&period=30");}
 @Transactional public List<String> enable(UUID id,String password,String code){var a=admin(id,password);var r=records.findById(id).orElseThrow();if(r.isEnabled())throw new IllegalStateException("Segundo fator já ativado.");if(r.getUpdatedAt().isBefore(Instant.now().minusSeconds(600)))throw new IllegalStateException("Configuração expirada. Gere outra chave.");if(!accept(r,id,code,false))return null;List<String> raw=new ArrayList<>(),hashed=new ArrayList<>();for(int i=0;i<8;i++){String value=UUID.randomUUID().toString().replace("-","").substring(0,16);raw.add(value);hashed.add(encoder.encode(value));}try{r.enable(mapper.writeValueAsString(hashed));}catch(Exception impossible){throw new IllegalStateException("Não foi possível guardar a recuperação.");}a.changePassword(a.getPasswordHash());audit.record(id,"ADMIN_MFA_ENABLED","ACCOUNT",id,"Segundo fator ativado; sessões anteriores revogadas.");return raw;}
 @Transactional public boolean disable(UUID id,String password,String code){var a=admin(id,password);var r=records.findById(id).orElseThrow();if(!r.isEnabled())throw new IllegalStateException("Segundo fator não está ativo.");if(!accept(r,id,code,true))return false;records.delete(r);a.changePassword(a.getPasswordHash());audit.record(id,"ADMIN_MFA_DISABLED","ACCOUNT",id,"Segundo fator desativado com confirmação; sessões anteriores revogadas.");return true;}
 @Transactional public boolean authenticate(UUID accountId,String code){accounts.findByIdForUpdate(accountId).orElseThrow();var r=records.findById(accountId);return r.isEmpty()||!r.get().isEnabled()||accept(r.get(),accountId,code,true);}
 private List<String> hashes(AdminMfa r){try{return new ArrayList<>(mapper.readValue(r.getRecoveryHashes(),mapper.getTypeFactory().constructCollectionType(ArrayList.class,String.class)));}catch(Exception error){throw new IllegalStateException("Recuperação indisponível.");}}
 private boolean accept(AdminMfa r,UUID id,String code,boolean recoveryAllowed){if(!r.ready())return false;long counter=Totp.matchingCounter(cipher.decrypt("MFA:"+id,r.getEncryptedSecret()),code,Instant.now().getEpochSecond(),r.getLastCounter());if(counter>=0){r.accepted(counter);return true;}if(recoveryAllowed&&code!=null&&code.matches("[a-f0-9]{16}")){var list=hashes(r);for(var hash:List.copyOf(list))if(encoder.matches(code,hash)){list.remove(hash);try{r.useRecovery(mapper.writeValueAsString(list));return true;}catch(Exception error){throw new IllegalStateException("Não foi possível atualizar a recuperação.");}}}r.failed();return false;}
}
