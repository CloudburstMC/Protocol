package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.UpdateSoftEnumPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSoftEnumSerializer_v388 implements PacketSerializer<UpdateSoftEnumPacket> {
    public static final UpdateSoftEnumSerializer_v388 INSTANCE = new UpdateSoftEnumSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, UpdateSoftEnumPacket packet) {
        BedrockUtils.writeCommandEnumData(buffer, packet.getSoftEnum());
        buffer.writeByte(packet.getType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdateSoftEnumPacket packet) {
        packet.setSoftEnum(BedrockUtils.readCommandEnumData(buffer, true));
        packet.setType(UpdateSoftEnumPacket.Type.values()[buffer.readUnsignedByte()]);
    }
}
