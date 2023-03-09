package org.cloudburstmc.protocol.bedrock.codec.v575.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.UnlockedRecipesPacket;

public class UnlockedRecipesSerializer_v575 implements BedrockPacketSerializer<UnlockedRecipesPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UnlockedRecipesPacket packet) {
        buffer.writeBoolean(packet.isUnlockedNotification());
        helper.writeArray(buffer, packet.getUnlockedRecipes(), helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UnlockedRecipesPacket packet) {
        packet.setUnlockedNotification(buffer.readBoolean());
        helper.readArray(buffer, packet.getUnlockedRecipes(), helper::readString);
    }
}
