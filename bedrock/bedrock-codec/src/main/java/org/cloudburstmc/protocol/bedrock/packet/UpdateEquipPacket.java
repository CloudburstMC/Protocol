package org.cloudburstmc.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateEquipPacket implements BedrockPacket {
    private short windowId;
    private short windowType;
    private int size; // Couldn't find anything on this one. Looks like it isn't used?
    private long uniqueEntityId;
    private NbtMap tag;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_EQUIP;
    }
}
