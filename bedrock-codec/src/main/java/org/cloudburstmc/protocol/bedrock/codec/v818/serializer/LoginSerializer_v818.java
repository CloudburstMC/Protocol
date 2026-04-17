package org.cloudburstmc.protocol.bedrock.codec.v818.serializer;

import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LoginSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthType;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.jose4j.json.JsonUtil;
import org.jose4j.lang.JoseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

public class LoginSerializer_v818 extends LoginSerializer_v291 {

    public static final LoginSerializer_v818 INSTANCE = new LoginSerializer_v818();

    @Override
    protected String writeAuthJwt(LoginPacket packet) {
        checkArgument(packet.getAuthType() != null && packet.getAuthType() != AuthType.UNKNOWN,
                "Client requires non-null and non-UNKNOWN AuthType for login");
        Map<String, Object> object = new HashMap<>();
        object.put("AuthenticationType", packet.getAuthType().ordinal() - 1); // Adjusting ordinal to match the enum definition
        if (packet.getToken() != null) {
            object.put("Token", packet.getToken());
        }
        if (!packet.getCertificateChain().isEmpty()) {
            Map<String, Object> json = new HashMap<>();
            json.put("chain", packet.getCertificateChain());
            object.put("Certificate", JsonUtil.toJson(json));
        }
        return JsonUtil.toJson(object);
    }

    @Override
    protected void readAuthJwt(LoginPacket packet, String authJwt) {
        try {
            Map<String, Object> payload = JsonUtil.parseJson(authJwt);
            checkArgument(payload.containsKey("AuthenticationType"), "Missing AuthenticationType in JWT");
            int authTypeOrdinal = ((Number) payload.get("AuthenticationType")).intValue();
            if (authTypeOrdinal < 0 || authTypeOrdinal >= AuthType.values().length - 1) {
                throw new IllegalArgumentException("Invalid AuthenticationType ordinal: " + authTypeOrdinal);
            }

            packet.setAuthType(AuthType.values()[authTypeOrdinal + 1]);

            if (payload.containsKey("Token") && payload.get("Token") instanceof String && !((String) payload.get("Token")).isEmpty()) {
                 packet.setToken((String) payload.get("Token"));
            }
            if (payload.containsKey("Certificate") && payload.get("Certificate") instanceof String && !((String) payload.get("Certificate")).isEmpty()) {
                String certJson = (String) payload.get("Certificate");
                Map<String, Object> certData = JsonUtil.parseJson(certJson);
                if (!certData.containsKey("chain") || !(certData.get("chain") instanceof List)) {
                    throw new IllegalArgumentException("Invalid Certificate chain in JWT");
                }
                for (Object node : (List<?>) certData.get("chain")) {
                    checkArgument(node instanceof String, "Expected String in login certificate chain");
                    packet.getCertificateChain().add((String) node);
                }
            }
        } catch (JoseException e) {
            throw new IllegalArgumentException("Failed to parse auth payload", e);
        }
    }
}
