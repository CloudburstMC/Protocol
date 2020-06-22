package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SetDifficultyPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetDifficultySerializer_v291 implements BedrockPacketSerializer<SetDifficultyPacket> {
    public static final SetDifficultySerializer_v291 INSTANCE = new SetDifficultySerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetDifficultyPacket packet) {
        VarInts.writeInt(buffer, packet.getDifficulty());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetDifficultyPacket packet) {
        packet.setDifficulty(VarInts.readInt(buffer));
    }
}
