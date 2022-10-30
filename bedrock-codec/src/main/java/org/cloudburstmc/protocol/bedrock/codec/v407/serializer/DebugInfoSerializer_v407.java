package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.DebugInfoPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DebugInfoSerializer_v407 implements BedrockPacketSerializer<DebugInfoPacket> {
    public static final DebugInfoSerializer_v407 INSTANCE = new DebugInfoSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, DebugInfoPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        helper.writeString(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, DebugInfoPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setData(helper.readString(buffer));
    }
}
