package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetPlayerGameTypePacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetPlayerGameTypeSerializer_v291 implements PacketSerializer<SetPlayerGameTypePacket> {
    public static final SetPlayerGameTypeSerializer_v291 INSTANCE = new SetPlayerGameTypeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, SetPlayerGameTypePacket packet) {
        VarInts.writeInt(buffer, packet.getGamemode());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetPlayerGameTypePacket packet) {
        packet.setGamemode(VarInts.readInt(buffer));
    }
}
