package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataExportRequestPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StructureTemplateDataExportRequestSerializer_v363 implements PacketSerializer<StructureTemplateDataExportRequestPacket> {
    public static final StructureTemplateDataExportRequestSerializer_v363 INSTANCE = new StructureTemplateDataExportRequestSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, StructureTemplateDataExportRequestPacket packet) {
        BedrockUtils.writeString(buffer, packet.getName());
        BedrockUtils.writeBlockPosition(buffer, packet.getPosition());
        BedrockUtils.writeStructureSettings(buffer, packet.getSettings());
        buffer.writeByte(packet.getOperation());
    }

    @Override
    public void deserialize(ByteBuf buffer, StructureTemplateDataExportRequestPacket packet) {
        packet.setName(BedrockUtils.readString(buffer));
        packet.setPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setSettings(BedrockUtils.readStructureSettings(buffer));
        packet.setOperation(buffer.readByte());
    }
}
