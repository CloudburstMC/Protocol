package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;


/**
 * CreativeContent is a packet sent by the server to set the creative inventory's content for a player.
 * Introduced in 1.16, this packet replaces the previous method - sending an InventoryContent packet with
 * creative inventory window ID.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CreativeContentPacket implements BedrockPacket {
    /**
     * Item entries for the creative menu. Each item must have a unique ID for the net ID manager
     *
     * @see ItemData#setNetId(int)
     */
    private ItemData[] contents;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CREATIVE_CONTENT;
    }

    @Override
    public CreativeContentPacket clone() {
        try {
            return (CreativeContentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

