package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LevelEventGenericPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelEventGenericSerializer_v361 implements PacketSerializer<LevelEventGenericPacket> {
    public static final LevelEventGenericSerializer_v361 INSTANCE = new LevelEventGenericSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, LevelEventGenericPacket packet) {
        VarInts.writeInt(buffer, packet.getEventId());
    }

    @Override
    public void deserialize(ByteBuf buffer, LevelEventGenericPacket packet) {
        packet.setEventId(VarInts.readInt(buffer));
    }
}
