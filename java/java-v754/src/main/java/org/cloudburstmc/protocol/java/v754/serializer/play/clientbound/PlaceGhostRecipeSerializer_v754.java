package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.PlaceGhostRecipePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceGhostRecipeSerializer_v754 implements JavaPacketSerializer<PlaceGhostRecipePacket> {
    public static final PlaceGhostRecipeSerializer_v754 INSTANCE = new PlaceGhostRecipeSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, PlaceGhostRecipePacket packet) throws PacketSerializeException {
        buffer.writeByte(packet.getContainerId());
        helper.writeKey(buffer, packet.getRecipe());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, PlaceGhostRecipePacket packet) throws PacketSerializeException {
        packet.setContainerId(buffer.readByte());
        packet.setRecipe(helper.readKey(buffer));
    }
}
