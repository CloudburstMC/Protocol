package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetTimePacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetTimeSerializer_v361 implements PacketSerializer<SetTimePacket> {
    public static final SetTimeSerializer_v361 INSTANCE = new SetTimeSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, SetTimePacket packet) {
        VarInts.writeInt(buffer, packet.getTime());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetTimePacket packet) {
        packet.setTime(VarInts.readInt(buffer));
    }
}
