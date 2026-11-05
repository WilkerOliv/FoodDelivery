package br.com.fooddelivery.identity;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import java.net.http.HttpClient;
import java.time.Duration;

@Component
public class RecoveryMessageSender {
    @org.springframework.beans.factory.annotation.Autowired
    private br.com.fooddelivery.config.DemoEnvironment demo;
    @Value("${app.demo.mailpit:false}")
    private boolean mailpit;
    private final ObjectProvider<JavaMailSender> mailSender;
    private final String emailFrom;
    private final boolean whatsappEnabled;
    private final String graphVersion;
    private final String phoneNumberId;
    private final String accessToken;
    private final String templateName;
    private final String language;
    private final RestClient http;
    @org.springframework.beans.factory.annotation.Autowired private br.com.fooddelivery.settings.IntegrationSettings settings;
    private String setting(String key,String fallback){return settings==null?fallback:settings.value(key,fallback);}

    public RecoveryMessageSender(ObjectProvider<JavaMailSender> mailSender,
            @Value("${app.recovery.email-from}") String emailFrom,
            @Value("${app.recovery.whatsapp.enabled:false}") boolean whatsappEnabled,
            @Value("${app.recovery.whatsapp.graph-version:v23.0}") String graphVersion,
            @Value("${app.recovery.whatsapp.phone-number-id:}") String phoneNumberId,
            @Value("${app.recovery.whatsapp.access-token:}") String accessToken,
            @Value("${app.recovery.whatsapp.template-name:fooddelivery_password_recovery}") String templateName,
            @Value("${app.recovery.whatsapp.language:pt_BR}") String language) {
        this.mailSender = mailSender; this.emailFrom = emailFrom; this.whatsappEnabled = whatsappEnabled;
        this.graphVersion = graphVersion; this.phoneNumberId = phoneNumberId; this.accessToken = accessToken;
        this.templateName = templateName; this.language = language;
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build());
        factory.setReadTimeout(Duration.ofSeconds(10));
        this.http = RestClient.builder().requestFactory(factory).build();
    }

    public void sendEmail(String recipient, String code) {
        if (demo != null && demo.enabled()) { demo.capture("EMAIL", recipient, code); if(!mailpit)return; }
        JavaMailSender sender = mailSender.getIfAvailable();
        if (sender == null) throw new IllegalStateException("SMTP não configurado.");
        if(sender instanceof org.springframework.mail.javamail.JavaMailSenderImpl original){
            var configured=new org.springframework.mail.javamail.JavaMailSenderImpl();configured.setHost(setting("SMTP_HOST",original.getHost()));configured.setPort(Integer.parseInt(setting("SMTP_PORT",Integer.toString(original.getPort()))));configured.setUsername(setting("SMTP_USERNAME",original.getUsername()));configured.setPassword(setting("SMTP_PASSWORD",original.getPassword()));configured.getJavaMailProperties().putAll(original.getJavaMailProperties());sender=configured;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(setting("RECOVERY_EMAIL_FROM",emailFrom)); message.setTo(recipient); message.setSubject("Código de segurança FoodDelivery");
        message.setText("Seu código de segurança é " + code + ". Ele expira em 10 minutos. Se você não solicitou, ignore este e-mail.");
        sender.send(message);
    }

    public void sendWhatsApp(String phone, String code) {
        if (demo != null && demo.enabled()) { demo.capture("WHATSAPP", phone, code); return; }
        String currentPhone=setting("WHATSAPP_PHONE_NUMBER_ID",phoneNumberId),currentToken=setting("WHATSAPP_ACCESS_TOKEN",accessToken);
        if (!Boolean.parseBoolean(setting("WHATSAPP_CLOUD_ENABLED",Boolean.toString(whatsappEnabled))) || currentPhone.isBlank() || currentToken.isBlank())
            throw new IllegalStateException("WhatsApp Cloud não configurado.");
        String recipient = phone.replaceAll("\\D", "");
        if (recipient.length() == 10 || recipient.length() == 11) recipient = "55" + recipient;
        if (recipient.length() < 12 || recipient.length() > 15) throw new IllegalArgumentException("Telefone inválido.");
        http.post().uri("https://graph.facebook.com/{version}/{phoneId}/messages", setting("WHATSAPP_GRAPH_VERSION",graphVersion), currentPhone)
                .contentType(MediaType.APPLICATION_JSON).headers(h -> h.setBearerAuth(currentToken))
                .body(java.util.Map.of("messaging_product", "whatsapp", "to", recipient, "type", "template",
                        "template", java.util.Map.of("name", setting("WHATSAPP_OTP_TEMPLATE",templateName), "language", java.util.Map.of("code", setting("WHATSAPP_TEMPLATE_LANGUAGE",language)),
                                "components", java.util.List.of(java.util.Map.of("type", "body", "parameters",
                                        java.util.List.of(java.util.Map.of("type", "text", "text", code)))))))
                .retrieve().toBodilessEntity();
    }
}
