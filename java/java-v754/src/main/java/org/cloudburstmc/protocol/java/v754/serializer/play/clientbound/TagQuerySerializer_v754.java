package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.TagQueryPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagQuerySerializer_v754 implements JavaPacketSerializer<TagQueryPacket> {
    public static final TagQuerySerializer_v754 INSTANCE = new TagQuerySerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, TagQueryPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getTransactionId());
        helper.writeTag(buffer, packet.getTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, TagQueryPacket packet) throws PacketSerializeException {
        packet.setTransactionId(helper.readVarInt(buffer));
        packet.setTag(helper.readTag(buffer));
    }
}
