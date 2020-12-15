package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetDefaultGameTypePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetDefaultGameTypeSerializer_v291 implements BedrockPacketSerializer<SetDefaultGameTypePacket> {
    public static final SetDefaultGameTypeSerializer_v291 INSTANCE = new SetDefaultGameTypeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetDefaultGameTypePacket packet) {
        VarInts.writeInt(buffer, packet.getGamemode());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetDefaultGameTypePacket packet) {
        packet.setGamemode(VarInts.readInt(buffer));
    }
}
