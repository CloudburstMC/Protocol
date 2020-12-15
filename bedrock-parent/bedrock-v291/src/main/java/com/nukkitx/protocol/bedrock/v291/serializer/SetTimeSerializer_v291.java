package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetTimePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetTimeSerializer_v291 implements BedrockPacketSerializer<SetTimePacket> {
    public static final SetTimeSerializer_v291 INSTANCE = new SetTimeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetTimePacket packet) {
        VarInts.writeInt(buffer, packet.getTime());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetTimePacket packet) {
        packet.setTime(VarInts.readInt(buffer));
    }
}
