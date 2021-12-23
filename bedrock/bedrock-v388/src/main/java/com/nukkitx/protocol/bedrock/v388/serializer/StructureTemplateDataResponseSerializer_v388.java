package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.structure.StructureTemplateResponseType;
import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataResponsePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StructureTemplateDataResponseSerializer_v388 implements BedrockPacketSerializer<StructureTemplateDataResponsePacket> {
    public static final StructureTemplateDataResponseSerializer_v388 INSTANCE = new StructureTemplateDataResponseSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataResponsePacket packet) {
        helper.writeString(buffer, packet.getName());
        boolean save = packet.isSave();
        buffer.writeBoolean(save);

        if (save) {
            helper.writeTag(buffer, packet.getTag());
        }
        buffer.writeByte(packet.getType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataResponsePacket packet) {
        packet.setName(helper.readString(buffer));

        boolean save = buffer.readBoolean();
        packet.setSave(save);

        if (save) {
            packet.setTag(helper.readTag(buffer));
        }
        packet.setType(StructureTemplateResponseType.from(buffer.readByte()));
    }
}
