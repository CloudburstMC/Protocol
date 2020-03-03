package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ResourcePackClientResponsePacket extends BedrockPacket {
    private final List<String> packIds = new ArrayList<>();
    private Status status;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_CLIENT_RESPONSE;
    }

    public enum Status {
        NONE,
        REFUSED,
        SEND_PACKS,
        HAVE_ALL_PACKS,
        COMPLETED
    }
}
