package org.cloudburstmc.protocol.bedrock.codec.v475.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.StartGameSerializer_v465;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v475 extends StartGameSerializer_v465 {

    public static final StartGameSerializer_v475 INSTANCE = new StartGameSerializer_v475();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeLongLE(packet.getBlockRegistryChecksum());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setBlockRegistryChecksum(buffer.readLongLE());
    }
}
