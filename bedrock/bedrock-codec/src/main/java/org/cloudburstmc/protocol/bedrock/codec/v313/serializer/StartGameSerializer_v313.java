package org.cloudburstmc.protocol.bedrock.codec.v313.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.StartGameSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v313 extends StartGameSerializer_v291 {
    public static final StartGameSerializer_v313 INSTANCE = new StartGameSerializer_v313();

    @Override
    protected void writeLevelSettings(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.writeLevelSettings(buffer, helper, packet);
        buffer.writeBoolean(packet.isFromWorldTemplate());
        buffer.writeBoolean(packet.isFromLockedWorldTemplate());
    }

    @Override
    protected void readLevelSettings(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.readLevelSettings(buffer, helper, packet);

        packet.setFromWorldTemplate(buffer.readBoolean());
        packet.setFromLockedWorldTemplate(buffer.readBoolean());
    }
}
