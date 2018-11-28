package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.UpdateSoftEnumPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSoftEnumSerializer_v291 implements PacketSerializer<UpdateSoftEnumPacket> {
    public static final UpdateSoftEnumSerializer_v291 INSTANCE = new UpdateSoftEnumSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, UpdateSoftEnumPacket packet) {
        BedrockUtils.writeString(buffer, packet.getEnumName());
        BedrockUtils.writeArray(buffer, packet.getValues(), BedrockUtils::writeString);
        buffer.writeByte(packet.getType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdateSoftEnumPacket packet) {
        packet.setEnumName(BedrockUtils.readString(buffer));
        BedrockUtils.readArray(buffer, packet.getValues(), BedrockUtils::readString);
        packet.setType(UpdateSoftEnumPacket.Type.values()[buffer.readUnsignedByte()]);
    }
}
