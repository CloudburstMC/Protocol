package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryLayout;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryTabLeft;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryTabRight;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetPlayerInventoryOptionsPacket implements BedrockPacket {
    private InventoryTabLeft leftTab;
    private InventoryTabRight rightTab;
    private boolean filtering;
    private InventoryLayout layout;
    private InventoryLayout craftingLayout;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_PLAYER_INVENTORY_OPTIONS;
    }

    @Override
    public SetPlayerInventoryOptionsPacket clone() {
        try {
            return (SetPlayerInventoryOptionsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

