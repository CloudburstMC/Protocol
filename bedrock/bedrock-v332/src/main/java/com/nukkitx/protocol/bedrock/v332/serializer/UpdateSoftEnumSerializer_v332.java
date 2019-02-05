package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.UpdateSoftEnumPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSoftEnumSerializer_v332 implements PacketSerializer<UpdateSoftEnumPacket> {
    public static final UpdateSoftEnumSerializer_v332 INSTANCE = new UpdateSoftEnumSerializer_v332();

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
