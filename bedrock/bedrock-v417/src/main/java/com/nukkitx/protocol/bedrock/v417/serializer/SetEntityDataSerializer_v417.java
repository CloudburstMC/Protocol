package com.nukkitx.protocol.bedrock.v417.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.SetEntityDataSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEntityDataSerializer_v417 extends SetEntityDataSerializer_v291 {

    public static final SetEntityDataSerializer_v417 INSTANCE = new SetEntityDataSerializer_v417();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
        super.serialize(buffer, helper, packet);

        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}
