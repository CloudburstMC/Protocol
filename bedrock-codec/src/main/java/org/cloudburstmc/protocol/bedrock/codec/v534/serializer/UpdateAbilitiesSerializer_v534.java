package org.cloudburstmc.protocol.bedrock.codec.v534.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.UpdateAbilitiesPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAbilitiesSerializer_v534 implements BedrockPacketSerializer<UpdateAbilitiesPacket> {
    public static final UpdateAbilitiesSerializer_v534 INSTANCE = new UpdateAbilitiesSerializer_v534();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAbilitiesPacket packet) {
        helper.writePlayerAbilities(buffer, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAbilitiesPacket packet) {
        helper.readPlayerAbilities(buffer, packet);
    }
}
