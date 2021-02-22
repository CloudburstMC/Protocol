package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.structure.StructureTemplateRequestOperation;
import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StructureTemplateDataRequestSerializer_v361 implements BedrockPacketSerializer<StructureTemplateDataRequestPacket> {
    public static final StructureTemplateDataRequestSerializer_v361 INSTANCE = new StructureTemplateDataRequestSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataRequestPacket packet) {
        System.out.println("==Mystery Packet!==: "+packet);
//        helper.writeString(buffer, packet.getName());
//        helper.writeBlockPosition(buffer, packet.getPosition());
//        helper.writeStructureSettings(buffer, packet.getSettings());
//        buffer.writeByte(packet.getOperation().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataRequestPacket packet) {
        System.out.println("==Mystery Packet!==: "+packet);
//        packet.setName(helper.readString(buffer));
//        packet.setPosition(helper.readBlockPosition(buffer));
//        packet.setSettings(helper.readStructureSettings(buffer));
//        packet.setOperation(StructureTemplateRequestOperation.from(buffer.readByte()));
    }
}
