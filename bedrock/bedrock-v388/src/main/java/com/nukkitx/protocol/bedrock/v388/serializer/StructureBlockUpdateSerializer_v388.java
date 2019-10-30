package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StructureBlockUpdateSerializer_v388 implements PacketSerializer<StructureBlockUpdatePacket> {
    public static final StructureBlockUpdateSerializer_v388 INSTANCE = new StructureBlockUpdateSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, StructureBlockUpdatePacket packet) {
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        BedrockUtils.writeStructureEditorData(buffer, packet.getEditorData());
        buffer.writeBoolean(packet.isPowered());
    }

    @Override
    public void deserialize(ByteBuf buffer, StructureBlockUpdatePacket packet) {
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setEditorData(BedrockUtils.readStructureEditorData(buffer));
        packet.setPowered(buffer.readBoolean());
    }
}
