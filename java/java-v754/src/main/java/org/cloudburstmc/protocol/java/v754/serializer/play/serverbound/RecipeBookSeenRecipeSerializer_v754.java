package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.RecipeBookSeenRecipePacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeBookSeenRecipeSerializer_v754 implements JavaPacketSerializer<RecipeBookSeenRecipePacket> {
    public static final RecipeBookSeenRecipeSerializer_v754 INSTANCE = new RecipeBookSeenRecipeSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, RecipeBookSeenRecipePacket packet) throws PacketSerializeException {
        helper.writeKey(buffer, packet.getRecipe());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, RecipeBookSeenRecipePacket packet) throws PacketSerializeException {
        packet.setRecipe(helper.readKey(buffer));
    }
}
