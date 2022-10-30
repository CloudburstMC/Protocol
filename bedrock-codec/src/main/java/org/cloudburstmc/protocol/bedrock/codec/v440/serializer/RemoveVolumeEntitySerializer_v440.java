package org.cloudburstmc.protocol.bedrock.codec.v440.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.RemoveVolumeEntityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemoveVolumeEntitySerializer_v440 implements BedrockPacketSerializer<RemoveVolumeEntityPacket> {

    public static final RemoveVolumeEntitySerializer_v440 INSTANCE = new RemoveVolumeEntitySerializer_v440();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RemoveVolumeEntityPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RemoveVolumeEntityPacket packet) {
        packet.setId(VarInts.readUnsignedInt(buffer));
    }
}
