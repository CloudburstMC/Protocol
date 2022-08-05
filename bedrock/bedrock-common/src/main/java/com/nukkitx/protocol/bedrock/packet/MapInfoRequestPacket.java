package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector2i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class MapInfoRequestPacket extends BedrockPacket {
    private long uniqueMapId;
    /**
     * Lets the server know what pixels the client does not have rendered on their map.
     *
     * @since v544
     */
    private final List<Vector2i> requestedPixels = new ObjectArrayList<>();

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MAP_INFO_REQUEST;
    }
}
