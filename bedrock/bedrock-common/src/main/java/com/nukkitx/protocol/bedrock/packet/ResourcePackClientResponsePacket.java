package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ResourcePackClientResponsePacket extends BedrockPacket {
    private final List<String> packIds = new ObjectArrayList<>();
    private Status status;

    public ResourcePackClientResponsePacket addPackId(String packId) {
        this.packIds.add(packId);
        return this;
    }

    public ResourcePackClientResponsePacket addPackIds(String... packIds) {
        this.packIds.addAll(Arrays.asList(packIds));
        return this;
    }

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
