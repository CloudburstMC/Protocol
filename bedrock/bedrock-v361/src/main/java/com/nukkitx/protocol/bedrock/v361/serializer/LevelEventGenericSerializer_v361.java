package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.nbt.NbtType;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.LevelEventGenericPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelEventGenericSerializer_v361 implements BedrockPacketSerializer<LevelEventGenericPacket> {
    public static final LevelEventGenericSerializer_v361 INSTANCE = new LevelEventGenericSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventGenericPacket packet) {
        VarInts.writeInt(buffer, packet.getEventId());
        helper.writeTagValue(buffer, packet.getTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventGenericPacket packet) {
        packet.setEventId(VarInts.readInt(buffer));
        packet.setTag(helper.readTagValue(buffer, NbtType.COMPOUND));
    }
}
