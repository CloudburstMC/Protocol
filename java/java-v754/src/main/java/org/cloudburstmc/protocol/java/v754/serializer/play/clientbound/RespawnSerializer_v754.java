package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.GameType;
import org.cloudburstmc.protocol.java.packet.play.clientbound.RespawnPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RespawnSerializer_v754 implements JavaPacketSerializer<RespawnPacket> {
    public static final RespawnSerializer_v754 INSTANCE = new RespawnSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, RespawnPacket packet) throws PacketSerializeException {
        helper.writeTag(buffer, packet.getDimension());
        helper.writeKey(buffer, packet.getDimensionType());
        buffer.writeLong(packet.getSeed());
        buffer.writeByte(packet.getGameType().ordinal());
        buffer.writeByte(packet.getPreviousGameType().ordinal());
        buffer.writeBoolean(packet.isDebug());
        buffer.writeBoolean(packet.isFlat());
        buffer.writeBoolean(packet.isKeepAllPlayerData());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, RespawnPacket packet) throws PacketSerializeException {
        packet.setDimension(helper.readTag(buffer));
        packet.setDimensionType(helper.readKey(buffer));
        packet.setSeed(buffer.readLong());
        packet.setGameType(GameType.getById(buffer.readUnsignedByte()));
        packet.setPreviousGameType(GameType.getById(buffer.readUnsignedByte()));
        packet.setDebug(buffer.readBoolean());
        packet.setFlat(buffer.readBoolean());
        packet.setKeepAllPlayerData(buffer.readBoolean());
    }
}
