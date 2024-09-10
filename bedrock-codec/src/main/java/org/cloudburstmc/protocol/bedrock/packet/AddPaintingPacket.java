package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
@ToString(doNotUseGetters = true)
public class AddPaintingPacket extends AddHangingEntityPacket {
    private String motive;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_PAINTING;
    }

    @Override
    public AddPaintingPacket clone() {
        return (AddPaintingPacket) super.clone();
    }
}
