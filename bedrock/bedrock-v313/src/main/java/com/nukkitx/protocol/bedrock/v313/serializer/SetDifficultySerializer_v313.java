package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetDifficultyPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetDifficultySerializer_v313 implements PacketSerializer<SetDifficultyPacket> {
    public static final SetDifficultySerializer_v313 INSTANCE = new SetDifficultySerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, SetDifficultyPacket packet) {
        VarInts.writeInt(buffer, packet.getDifficulty());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetDifficultyPacket packet) {
        packet.setDifficulty(VarInts.readInt(buffer));
    }
}
