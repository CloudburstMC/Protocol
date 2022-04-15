package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.definition.DimensionDefinition;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Sends a list of the data-driven dimensions to the client.
 * This packet is sent before the {@link StartGamePacket} in the login sequence.
 *
 * <b>Note:</b> The client only supports sending the <code>minecraft:overworld</code> dimension as of 1.18.30
 *
 * @since v503
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class DimensionDataPacket extends BedrockPacket {
    private final List<DimensionDefinition> definitions = new ObjectArrayList<>();

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DIMENSION_DATA;
    }
}