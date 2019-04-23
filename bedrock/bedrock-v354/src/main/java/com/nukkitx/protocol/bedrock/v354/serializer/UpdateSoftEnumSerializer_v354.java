package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.protocol.bedrock.packet.UpdateSoftEnumPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSoftEnumSerializer_v354 implements PacketSerializer<UpdateSoftEnumPacket> {
    public static final UpdateSoftEnumSerializer_v354 INSTANCE = new UpdateSoftEnumSerializer_v354();

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
