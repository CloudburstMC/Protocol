package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetDefaultGameTypePacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetDefaultGameTypeSerializer_v291 implements PacketSerializer<SetDefaultGameTypePacket> {
    public static final SetDefaultGameTypeSerializer_v291 INSTANCE = new SetDefaultGameTypeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, SetDefaultGameTypePacket packet) {
        VarInts.writeInt(buffer, packet.getGamemode());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetDefaultGameTypePacket packet) {
        packet.setGamemode(VarInts.readInt(buffer));
    }
}
