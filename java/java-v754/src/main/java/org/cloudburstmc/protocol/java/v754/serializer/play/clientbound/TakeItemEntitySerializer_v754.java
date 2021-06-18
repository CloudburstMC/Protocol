package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.TakeItemEntityPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TakeItemEntitySerializer_v754 implements JavaPacketSerializer<TakeItemEntityPacket> {
    public static final TakeItemEntitySerializer_v754 INSTANCE = new TakeItemEntitySerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, TakeItemEntityPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        helper.writeVarInt(buffer, packet.getTakerId());
        helper.writeVarInt(buffer, packet.getAmount());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, TakeItemEntityPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        packet.setTakerId(helper.readVarInt(buffer));
        packet.setAmount(helper.readVarInt(buffer));
    }
}
