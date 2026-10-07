package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * When the server updates a recipe in the stonecutter UI model, the server sends this packet. Then the client synchronizes the selected recipe in the client-side stonecutter model.
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundStonecutterSetRecipePacket implements BedrockPacket {

    private long playerUniqueEntityId;
    private int containerId;
    private int recipeIndex;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_STONECUTTER_SET_RECIPE;
    }

    @Override
    public ClientboundStonecutterSetRecipePacket clone() {
        try {
            return (ClientboundStonecutterSetRecipePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
