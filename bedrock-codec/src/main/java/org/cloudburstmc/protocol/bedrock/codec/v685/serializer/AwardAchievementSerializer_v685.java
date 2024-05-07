package org.cloudburstmc.protocol.bedrock.codec.v685.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AwardAchievementPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AwardAchievementSerializer_v685 implements BedrockPacketSerializer<AwardAchievementPacket> {
    public static final AwardAchievementSerializer_v685 INSTANCE = new AwardAchievementSerializer_v685();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AwardAchievementPacket packet) {
        buffer.writeIntLE(packet.getAchievementId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AwardAchievementPacket packet) {
        packet.setAchievementId(buffer.readIntLE());
    }
}