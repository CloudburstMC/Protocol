package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePackClientResponsePacket implements BedrockPacket {
    private final List<String> packIds = new ObjectArrayList<>();
    private Status status;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
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

    @Override
    public ResourcePackClientResponsePacket clone() {
        try {
            return (ResourcePackClientResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

