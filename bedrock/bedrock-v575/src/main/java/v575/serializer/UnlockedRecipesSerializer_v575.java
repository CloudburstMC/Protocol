package v575.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.UnlockedRecipesPacket;
import io.netty.buffer.ByteBuf;

public class UnlockedRecipesSerializer_v575 implements BedrockPacketSerializer<UnlockedRecipesPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UnlockedRecipesPacket packet) {
        buffer.writeBoolean(packet.isUnlockedNotification());
        helper.writeArray(buffer, packet.getUnlockedRecipes(), helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UnlockedRecipesPacket packet) {
        packet.setUnlockedNotification(buffer.readBoolean());
        helper.readArray(buffer, packet.getUnlockedRecipes(), helper::readString);
    }
}