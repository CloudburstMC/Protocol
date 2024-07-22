package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.JigsawStructureDataPacket;

public class JigsawStructureDataSerializer_v712 implements BedrockPacketSerializer<JigsawStructureDataPacket> {
    public static final JigsawStructureDataSerializer_v712 INSTANCE = new JigsawStructureDataSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, JigsawStructureDataPacket packet) {
        helper.writeTag(buffer, packet.getJigsawStructureDataTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, JigsawStructureDataPacket packet) {
        packet.setJigsawStructureDataTag(helper.readTag(buffer, NbtMap.class));
    }
}