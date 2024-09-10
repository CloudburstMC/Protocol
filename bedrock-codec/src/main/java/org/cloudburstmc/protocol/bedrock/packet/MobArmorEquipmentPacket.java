package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MobArmorEquipmentPacket implements BedrockPacket {
    private long runtimeEntityId;
    private ItemData helmet;
    private ItemData chestplate;
    private ItemData leggings;
    private ItemData boots;
    /**
     * @since v712
     */
    private ItemData body;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOB_ARMOR_EQUIPMENT;
    }

    @Override
    public MobArmorEquipmentPacket clone() {
        try {
            return (MobArmorEquipmentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

