package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.JigsawGeneratePacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JigsawGenerateSerializer_v754 implements JavaPacketSerializer<JigsawGeneratePacket> {
    public static final JigsawGenerateSerializer_v754 INSTANCE = new JigsawGenerateSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, JigsawGeneratePacket packet) throws PacketSerializeException {
        helper.writeBlockPosition(buffer, packet.getPosition());
        helper.writeVarInt(buffer, packet.getLevels());
        buffer.writeBoolean(packet.isKeepJigsaws());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, JigsawGeneratePacket packet) throws PacketSerializeException {
        packet.setPosition(helper.readBlockPosition(buffer));
        packet.setLevels(helper.readVarInt(buffer));
        packet.setKeepJigsaws(buffer.readBoolean());
    }
}
