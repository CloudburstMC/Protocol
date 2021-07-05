package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.LockDifficultyPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LockDifficultySerializer_v754 implements JavaPacketSerializer<LockDifficultyPacket> {
    public static final LockDifficultySerializer_v754 INSTANCE = new LockDifficultySerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, LockDifficultyPacket packet) throws PacketSerializeException {
        buffer.writeBoolean(packet.isLocked());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, LockDifficultyPacket packet) throws PacketSerializeException {
        packet.setLocked(buffer.readBoolean());
    }
}
