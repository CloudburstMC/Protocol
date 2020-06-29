package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.DebugInfoPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DebugInfoSerializer_v407 implements BedrockPacketSerializer<DebugInfoPacket> {
    public static final DebugInfoSerializer_v407 INSTANCE = new DebugInfoSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, DebugInfoPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        helper.writeString(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, DebugInfoPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setData(helper.readString(buffer));
    }
}
