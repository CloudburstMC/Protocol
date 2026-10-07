package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * When a player selects a recipe in the stonecutter UI on the client, the client sends this packet. Then the server synchronizes the selected recipe in the server-side stonecutter model.
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundStonecutterSetRecipePacket implements BedrockPacket {

    private int containerId;
    private int recipeIndex;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_STONECUTTER_SET_RECIPE;
    }

    @Override
    public ServerboundStonecutterSetRecipePacket clone() {
        try {
            return (ServerboundStonecutterSetRecipePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
