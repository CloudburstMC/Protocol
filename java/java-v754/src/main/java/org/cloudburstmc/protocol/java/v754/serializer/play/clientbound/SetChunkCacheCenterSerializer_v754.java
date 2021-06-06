package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetChunkCacheCenterPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetChunkCacheCenterSerializer_v754 implements JavaPacketSerializer<SetChunkCacheCenterPacket> {
    public static final SetChunkCacheCenterSerializer_v754 INSTANCE = new SetChunkCacheCenterSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetChunkCacheCenterPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getX());
        helper.writeVarInt(buffer, packet.getZ());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetChunkCacheCenterPacket packet) throws PacketSerializeException {
        packet.setX(helper.readVarInt(buffer));
        packet.setZ(helper.readVarInt(buffer));
    }
}
