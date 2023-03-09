package org.cloudburstmc.protocol.bedrock.codec.v575.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.CameraPresetsPacket;

public class CameraPresetsSerializer_v575 implements BedrockPacketSerializer<CameraPresetsPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraPresetsPacket packet) {
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraPresetsPacket packet) {
        packet.setData(helper.readTag(buffer, NbtMap.class));
    }
}
