package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.SetCommandBlockPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetCommandBlockSerializer_v754 implements JavaPacketSerializer<SetCommandBlockPacket> {
    public static final SetCommandBlockSerializer_v754 INSTANCE = new SetCommandBlockSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetCommandBlockPacket packet) throws PacketSerializeException {
        helper.writeBlockPosition(buffer, packet.getPosition());
        helper.writeString(buffer, packet.getCommand());
        helper.writeVarInt(buffer, packet.getMode().ordinal());
        byte flags = 0;
        if (packet.isTrackOutput()) {
            flags |= 0x01;
        }
        if (packet.isConditional()) {
            flags |= 0x02;
        }
        if (packet.isAutomatic()) {
            flags |= 0x04;
        }
        buffer.writeByte(flags);
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetCommandBlockPacket packet) throws PacketSerializeException {
        packet.setPosition(helper.readBlockPosition(buffer));
        packet.setCommand(helper.readString(buffer));
        byte flags = buffer.readByte();
        packet.setTrackOutput((flags & 0x01) == 0x01);
        packet.setConditional((flags & 0x02) == 0x02);
        packet.setAutomatic((flags & 0x04) == 0x04);
    }
}
