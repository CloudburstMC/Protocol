package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdateAttributesPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAttributesSerializer_v332 implements PacketSerializer<UpdateAttributesPacket> {
    public static final UpdateAttributesSerializer_v332 INSTANCE = new UpdateAttributesSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, UpdateAttributesPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeArray(buffer, packet.getAttributes(), BedrockUtils::writePlayerAttribute);
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdateAttributesPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        BedrockUtils.readArray(buffer, packet.getAttributes(), BedrockUtils::readPlayerAttribute);
    }
}
