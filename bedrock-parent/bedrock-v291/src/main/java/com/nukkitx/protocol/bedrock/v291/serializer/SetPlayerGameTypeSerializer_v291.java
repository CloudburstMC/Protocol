package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetPlayerGameTypePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetPlayerGameTypeSerializer_v291 implements BedrockPacketSerializer<SetPlayerGameTypePacket> {
    public static final SetPlayerGameTypeSerializer_v291 INSTANCE = new SetPlayerGameTypeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetPlayerGameTypePacket packet) {
        VarInts.writeInt(buffer, packet.getGamemode());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetPlayerGameTypePacket packet) {
        packet.setGamemode(VarInts.readInt(buffer));
    }
}
