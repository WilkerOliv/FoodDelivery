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
    private final ObjectProvider<JavaMailSender> mailSender;
    private final String emailFrom;
    private final boolean whatsappEnabled;
    private final String graphVersion;
    private final String phoneNumberId;
    private final String accessToken;
    private final String templateName;
    private final String language;
    private final RestClient http;

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
        JavaMailSender sender = mailSender.getIfAvailable();
        if (sender == null) throw new IllegalStateException("SMTP não configurado.");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom); message.setTo(recipient); message.setSubject("Código de segurança FoodDelivery");
        message.setText("Seu código de segurança é " + code + ". Ele expira em 10 minutos. Se você não solicitou, ignore este e-mail.");
        sender.send(message);
    }

    public void sendWhatsApp(String phone, String code) {
        if (!whatsappEnabled || phoneNumberId.isBlank() || accessToken.isBlank())
            throw new IllegalStateException("WhatsApp Cloud não configurado.");
        String recipient = phone.replaceAll("\\D", "");
        if (recipient.length() == 10 || recipient.length() == 11) recipient = "55" + recipient;
        if (recipient.length() < 12 || recipient.length() > 15) throw new IllegalArgumentException("Telefone inválido.");
        http.post().uri("https://graph.facebook.com/{version}/{phoneId}/messages", graphVersion, phoneNumberId)
                .contentType(MediaType.APPLICATION_JSON).headers(h -> h.setBearerAuth(accessToken))
                .body(java.util.Map.of("messaging_product", "whatsapp", "to", recipient, "type", "template",
                        "template", java.util.Map.of("name", templateName, "language", java.util.Map.of("code", language),
                                "components", java.util.List.of(java.util.Map.of("type", "body", "parameters",
                                        java.util.List.of(java.util.Map.of("type", "text", "text", code)))))))
                .retrieve().toBodilessEntity();
    }
}
