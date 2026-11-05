package br.com.fooddelivery.payments;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix="app.payment.mercadopago")
public class MercadoPagoProperties {
 private br.com.fooddelivery.settings.IntegrationSettings settings;
 @org.springframework.beans.factory.annotation.Autowired public void setPrivateSettings(br.com.fooddelivery.settings.IntegrationSettings settings){this.settings=settings;}
 private String clientId;
 private String clientSecret;
 private String redirectUri;
 private String frontendUrl;
 private String tokenEncryptionKey;
 private String webhookSecret;
 private String notificationBaseUrl;
 private boolean sandbox;
 private boolean testWebhookSignature;
 private String currency;
 public MercadoPagoProperties(){}
 public MercadoPagoProperties(String clientId,String clientSecret,String redirectUri,String frontendUrl,String tokenEncryptionKey,String webhookSecret,String notificationBaseUrl,boolean sandbox,boolean testWebhookSignature,String currency){this.clientId=clientId;this.clientSecret=clientSecret;this.redirectUri=redirectUri;this.frontendUrl=frontendUrl;this.tokenEncryptionKey=tokenEncryptionKey;this.webhookSecret=webhookSecret;this.notificationBaseUrl=notificationBaseUrl;this.sandbox=sandbox;this.testWebhookSignature=testWebhookSignature;this.currency=currency;}
 public String clientId(){return settings==null?clientId:settings.value("MP_CLIENT_ID",clientId);}
 public String getClientId(){return clientId;}
 public void setClientId(String value){clientId=value;}
 public String clientSecret(){return settings==null?clientSecret:settings.value("MP_CLIENT_SECRET",clientSecret);}
 public String getClientSecret(){return clientSecret;}
 public void setClientSecret(String value){clientSecret=value;}
 public String redirectUri(){return redirectUri;}
 public String getRedirectUri(){return redirectUri;}
 public void setRedirectUri(String value){redirectUri=value;}
 public String frontendUrl(){return frontendUrl;}
 public String getFrontendUrl(){return frontendUrl;}
 public void setFrontendUrl(String value){frontendUrl=value;}
 public String tokenEncryptionKey(){return tokenEncryptionKey;}
 public String getTokenEncryptionKey(){return tokenEncryptionKey;}
 public void setTokenEncryptionKey(String value){tokenEncryptionKey=value;}
 public String webhookSecret(){return settings==null?webhookSecret:settings.value("MP_WEBHOOK_SECRET",webhookSecret);}
 public String getWebhookSecret(){return webhookSecret;}
 public void setWebhookSecret(String value){webhookSecret=value;}
 public String notificationBaseUrl(){return notificationBaseUrl;}
 public String getNotificationBaseUrl(){return notificationBaseUrl;}
 public void setNotificationBaseUrl(String value){notificationBaseUrl=value;}
 public boolean sandbox(){return sandbox;}
 public boolean getSandbox(){return sandbox;}
 public void setSandbox(boolean value){sandbox=value;}
 public boolean testWebhookSignature(){return testWebhookSignature;}
 public boolean getTestWebhookSignature(){return testWebhookSignature;}
 public void setTestWebhookSignature(boolean value){testWebhookSignature=value;}
 public String currency(){return currency;}
 public String getCurrency(){return currency;}
 public void setCurrency(String value){currency=value;}
 public boolean oauthConfigured(){return present(clientId())&&present(clientSecret())&&present(redirectUri());}
 public boolean checkoutConfigured(){return oauthConfigured()&&present(tokenEncryptionKey());}
 public boolean webhookConfigured(){return present(webhookSecret());}
 public boolean hasNotificationUrl(){return present(notificationBaseUrl());}
 private static boolean present(String value){return value!=null&&!value.isBlank();}
}
