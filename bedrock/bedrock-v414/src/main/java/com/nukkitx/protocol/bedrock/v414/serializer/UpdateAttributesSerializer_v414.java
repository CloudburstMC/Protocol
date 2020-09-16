package com.nukkitx.protocol.bedrock.v414.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.UpdateAttributesPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.UpdateAttributesSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAttributesSerializer_v414 extends UpdateAttributesSerializer_v291 {

    public static final UpdateAttributesSerializer_v414 INSTANCE = new UpdateAttributesSerializer_v414();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAttributesPacket packet) {
        super.serialize(buffer, helper, packet);

        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAttributesPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}
