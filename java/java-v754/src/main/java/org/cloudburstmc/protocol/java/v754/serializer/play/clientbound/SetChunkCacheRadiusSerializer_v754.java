package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetChunkCacheRadiusPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetChunkCacheRadiusSerializer_v754 implements JavaPacketSerializer<SetChunkCacheRadiusPacket> {
    public static final SetChunkCacheRadiusSerializer_v754 INSTANCE = new SetChunkCacheRadiusSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetChunkCacheRadiusPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getRadius());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetChunkCacheRadiusPacket packet) throws PacketSerializeException {
        packet.setRadius(helper.readVarInt(buffer));
    }
}
