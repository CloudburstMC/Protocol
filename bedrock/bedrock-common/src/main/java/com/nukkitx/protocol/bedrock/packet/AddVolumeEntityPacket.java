package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AddVolumeEntityPacket extends BedrockPacket {
    private int id;
    private NbtMap data;
    /**
     * @since v465
     */
    private String engineVersion;
    /**
     * @since v485
     */
    private String identifier;
    /**
     * @since v485
     */
    private String instanceName;
    /**
     * @since v503
     */
    private Vector3i minBounds;
    /**
     * @since v503
     */
    private Vector3i maxBounds;
    /**
     * @since v503
     */
    private int dimension;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_VOLUME_ENTITY;
    }
}
