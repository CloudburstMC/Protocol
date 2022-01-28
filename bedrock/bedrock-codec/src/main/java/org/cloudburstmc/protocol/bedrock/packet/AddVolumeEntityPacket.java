package org.cloudburstmc.protocol.bedrock.packet;

import com.nukkitx.nbt.NbtMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AddVolumeEntityPacket implements BedrockPacket {
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

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_VOLUME_ENTITY;
    }
}
