package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.command.SoftEnumUpdateType;
import com.nukkitx.protocol.bedrock.packet.UpdateSoftEnumPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateSoftEnumSerializer_v291 implements BedrockPacketSerializer<UpdateSoftEnumPacket> {
    public static final UpdateSoftEnumSerializer_v291 INSTANCE = new UpdateSoftEnumSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateSoftEnumPacket packet) {
        helper.writeCommandEnum(buffer, packet.getSoftEnum());
        buffer.writeIntLE(packet.getType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateSoftEnumPacket packet) {
        packet.setSoftEnum(helper.readCommandEnum(buffer, true));
        packet.setType(SoftEnumUpdateType.values()[buffer.readIntLE()]);
    }
}
