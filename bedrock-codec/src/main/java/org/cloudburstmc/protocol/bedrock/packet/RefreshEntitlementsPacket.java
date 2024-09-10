package org.cloudburstmc.protocol.bedrock.packet;
import lombok.Data;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
public class RefreshEntitlementsPacket implements BedrockPacket {

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REFRESH_ENTITLEMENTS;
    }

    @Override
    public RefreshEntitlementsPacket clone() {
        try {
            return (RefreshEntitlementsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

