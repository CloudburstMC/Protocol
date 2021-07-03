package org.cloudburstmc.protocol.bedrock.packet;

import com.nimbusds.jwt.SignedJWT;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false, exclude = {"skinData"})
@ToString(doNotUseGetters = true, exclude = {"chainData", "skinData"})
public class LoginPacket implements BedrockPacket {
    private int protocolVersion;
    private final List<SignedJWT> chain = new ArrayList<>();
    private SignedJWT extra;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LOGIN;
    }
}
