package org.cloudburstmc.protocol.bedrock.codec.v575.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.CameraInstructionPacket;

public class CameraInstructionSerializer_v575 implements BedrockPacketSerializer<CameraInstructionPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraInstructionPacket packet) {
        packet.setData(helper.readTag(buffer, NbtMap.class));
    }
}
