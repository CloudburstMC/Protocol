package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.RecipePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeSerializer_v754 implements JavaPacketSerializer<RecipePacket> {
    public static final RecipeSerializer_v754 INSTANCE = new RecipeSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, RecipePacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getState().ordinal());
        buffer.writeBoolean(packet.isGuiOpen());
        buffer.writeBoolean(packet.isFilteringCraftable());
        buffer.writeBoolean(packet.isFurnaceGuiOpen());
        buffer.writeBoolean(packet.isFurnaceFilteringCraftable());
        buffer.writeBoolean(packet.isBlastingFurnaceGuiOpen());
        buffer.writeBoolean(packet.isBlastingFurnaceFilteringCraftable());
        buffer.writeBoolean(packet.isSmokerGuiOpen());
        buffer.writeBoolean(packet.isSmokerFilteringCraftable());
        helper.writeArray(buffer, packet.getRecipes(), helper::writeKey);
        if (packet.getState() == RecipePacket.State.INIT) {
            helper.writeArray(buffer, packet.getHighlightedRecipes(), helper::writeKey);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, RecipePacket packet) throws PacketSerializeException {
        packet.setState(RecipePacket.State.getById(helper.readVarInt(buffer)));
        packet.setGuiOpen(buffer.readBoolean());
        packet.setFilteringCraftable(buffer.readBoolean());
        packet.setFurnaceGuiOpen(buffer.readBoolean());
        packet.setFilteringCraftable(buffer.readBoolean());
        packet.setBlastingFurnaceGuiOpen(buffer.readBoolean());
        packet.setBlastingFurnaceFilteringCraftable(buffer.readBoolean());
        packet.setSmokerGuiOpen(buffer.readBoolean());
        packet.setSmokerFilteringCraftable(buffer.readBoolean());
        Key[] recipes = helper.readArray(buffer, new Key[0], helper::readKey);
        for (Key recipe : recipes) {
            packet.getRecipes().add(recipe);
        }
        if (packet.getState() == RecipePacket.State.INIT) {
            Key[] toHighlight = helper.readArray(buffer, new Key[0], helper::readKey);
            for (Key recipe : toHighlight) {
                packet.getHighlightedRecipes().add(recipe);
            }
        }
    }
}
