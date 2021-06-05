package org.cloudburstmc.protocol.bedrock.codec.v361.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventGenericPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelEventGenericSerializer_v361 implements BedrockPacketSerializer<LevelEventGenericPacket> {
    public static final LevelEventGenericSerializer_v361 INSTANCE = new LevelEventGenericSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelEventGenericPacket packet) {
        VarInts.writeInt(buffer, packet.getEventId());
        helper.writeTag(buffer, packet.getTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelEventGenericPacket packet) {
        packet.setEventId(VarInts.readInt(buffer));
        packet.setTag(helper.readTag(buffer));
    }
}
