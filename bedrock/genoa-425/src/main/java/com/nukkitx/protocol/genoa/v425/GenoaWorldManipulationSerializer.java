package com.nukkitx.protocol.genoa.v425;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataRequestPacket;
import com.nukkitx.protocol.bedrock.v361.serializer.StructureTemplateDataRequestSerializer_v361;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    //TODO: Change this to not use the StructureTemplateDataRequestPacket once we have a working idea
    public class GenoaWorldManipulationSerializer implements BedrockPacketSerializer<StructureTemplateDataRequestPacket> {
        public static final GenoaWorldManipulationSerializer INSTANCE = new GenoaWorldManipulationSerializer();

        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataRequestPacket packet) {
            System.out.println("==GenoaWorldManipulationPacket=="+packet);
//        helper.writeString(buffer, packet.getName());
//        helper.writeBlockPosition(buffer, packet.getPosition());
//        helper.writeStructureSettings(buffer, packet.getSettings());
//        buffer.writeByte(packet.getOperation().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataRequestPacket packet) {
            System.out.println("==GenoaWorldManipulationPacket=="+packet);
//        packet.setName(helper.readString(buffer));
//        packet.setPosition(helper.readBlockPosition(buffer));
//        packet.setSettings(helper.readStructureSettings(buffer));
//        packet.setOperation(StructureTemplateRequestOperation.from(buffer.readByte()));
        }
    }


