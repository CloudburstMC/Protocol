package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.PlaceRecipePacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceRecipeSerializer_v754 implements JavaPacketSerializer<PlaceRecipePacket> {
    public static final PlaceRecipeSerializer_v754 INSTANCE = new PlaceRecipeSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, PlaceRecipePacket packet) throws PacketSerializeException {
        buffer.writeByte(packet.getContainerId());
        helper.writeKey(buffer, packet.getRecipe());
        buffer.writeBoolean(packet.isUseAllItems());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, PlaceRecipePacket packet) throws PacketSerializeException {
        packet.setContainerId(buffer.readByte());
        packet.setRecipe(helper.readKey(buffer));
        packet.setUseAllItems(buffer.readBoolean());
    }
}
