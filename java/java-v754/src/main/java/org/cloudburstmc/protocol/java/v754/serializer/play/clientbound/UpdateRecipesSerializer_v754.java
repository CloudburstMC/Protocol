package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.crafting.Recipe;
import org.cloudburstmc.protocol.java.packet.play.clientbound.UpdateRecipesPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRecipesSerializer_v754 implements JavaPacketSerializer<UpdateRecipesPacket> {
    public static final UpdateRecipesSerializer_v754 INSTANCE = new UpdateRecipesSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, UpdateRecipesPacket packet) throws PacketSerializeException {
        helper.writeArray(buffer, packet.getRecipes(), helper::writeRecipe);
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, UpdateRecipesPacket packet) throws PacketSerializeException {
        packet.setRecipes(new ObjectArrayList<>(helper.readArray(buffer, new Recipe[0], helper::readRecipe)));
    }
}
