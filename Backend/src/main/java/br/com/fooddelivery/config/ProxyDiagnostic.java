package br.com.fooddelivery.config;

import jakarta.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(name="app.security.proxy-diagnostic", havingValue="true")
@PreAuthorize("hasRole('ADMIN')")
public class ProxyDiagnostic {
    private static final Logger LOG = LoggerFactory.getLogger(ProxyDiagnostic.class);
    private final SecretKey key;

    public ProxyDiagnostic() throws GeneralSecurityException {
        key = KeyGenerator.getInstance("HmacSHA256").generateKey();
    }

    private String fingerprint(String value) throws GeneralSecurityException {
        if (value == null || value.isBlank()) return "ausente";
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        return HexFormat.of().formatHex(mac.doFinal(value.trim().getBytes(java.nio.charset.StandardCharsets.UTF_8)), 0, 12);
    }

    @GetMapping("/api/v1/admin/proxy-diagnostic")
    public void diagnose(HttpServletRequest request) throws GeneralSecurityException {
        String chain = request.getHeader("X-Diagnostic-Chain");
        List<String> hops = new java.util.ArrayList<>();
        if (chain != null) {
            for (String hop : Arrays.stream(chain.split(",", 17)).limit(16).toList()) hops.add(fingerprint(hop));
        }
        LOG.info("Diagnóstico temporário peer={} cadeia={} selecionado={}",
            fingerprint(request.getHeader("X-Diagnostic-Peer")), hops,
            fingerprint(request.getHeader("X-Fooddelivery-Client-IP")));
    }
}
