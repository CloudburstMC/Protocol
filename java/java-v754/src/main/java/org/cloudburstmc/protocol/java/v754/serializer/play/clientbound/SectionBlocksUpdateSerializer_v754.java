package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.world.BlockUpdateEntry;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SectionBlocksUpdatePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SectionBlocksUpdateSerializer_v754 implements JavaPacketSerializer<SectionBlocksUpdatePacket> {
    public static final SectionBlocksUpdateSerializer_v754 INSTANCE = new SectionBlocksUpdateSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SectionBlocksUpdatePacket packet) throws PacketSerializeException {
        buffer.writeLong(((packet.getSectionX() & 0x3FFFFFL) << 42L)
                | ((packet.getSectionY() & 0xFFFFFL))
                | ((packet.getSectionZ() & 0x3FFFFFL) << 20L));
        buffer.writeBoolean(packet.isSuppressLightUpdates());
        helper.writeArray(buffer, packet.getEntries(), (buf, entry) -> {
            short position = (short) ((entry.getPosition().getX() - (packet.getSectionX() << 4)) << 8
                    | (entry.getPosition().getY() - (packet.getSectionY() << 4))
                    | (entry.getPosition().getZ() - (packet.getSectionZ() << 4)) << 4);
            helper.writeVarLong(buffer, ((long) entry.getBlockState() << 12) | position);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SectionBlocksUpdatePacket packet) throws PacketSerializeException {
        long sectionPos = buffer.readLong();
        packet.setSectionX((int) (sectionPos >> 42L));
        packet.setSectionY((int) (sectionPos << 44L >> 44L));
        packet.setSectionZ((int) (sectionPos << 22L >> 42L));
        packet.setEntries(helper.readArray(buffer, new BlockUpdateEntry[0], buf -> {
            long data = helper.readVarLong(buffer);
            short position = (short) (data & 0xFFFL);
            return new BlockUpdateEntry(
                    Vector3i.from(
                            (packet.getSectionX() << 4) + (position >>> 8 & 0xF),
                            (packet.getSectionY() << 4) + (position & 0xF),
                            (packet.getSectionZ() << 4) + (position >>> 4 & 0xF)
                    ),
                    (int) (data >>> 12)
            );
        }));
    }
}
