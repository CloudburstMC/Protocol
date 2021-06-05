package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.LightUpdatePacket;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LightUpdateSerializer_v754 implements JavaPacketSerializer<LightUpdatePacket> {
    public static final LightUpdateSerializer_v754 INSTANCE = new LightUpdateSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, LightUpdatePacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getX());
        helper.writeVarInt(buffer, packet.getZ());
        buffer.writeBoolean(packet.isTrustEdges());
        helper.writeVarInt(buffer, packet.getSkyYMask());
        helper.writeVarInt(buffer, packet.getBlockYMask());
        helper.writeVarInt(buffer, packet.getEmptySkyYMask());
        helper.writeVarInt(buffer, packet.getEmptyBlockYMask());
        for (byte[] skyUpdate : packet.getSkyUpdates()) {
            helper.writeByteArray(buffer, skyUpdate);
        }
        for (byte[] blockUpdate : packet.getBlockUpdates()) {
            helper.writeByteArray(buffer, blockUpdate);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, LightUpdatePacket packet) throws PacketSerializeException {
        packet.setX(helper.readVarInt(buffer));
        packet.setZ(helper.readVarInt(buffer));
        packet.setTrustEdges(buffer.readBoolean());
        packet.setSkyYMask(helper.readVarInt(buffer));
        packet.setBlockYMask(helper.readVarInt(buffer));
        packet.setEmptySkyYMask(helper.readVarInt(buffer));
        packet.setBlockYMask(helper.readVarInt(buffer));
        List<byte[]> skyUpdates = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            if ((packet.getSkyYMask() & 0x01 << i) != 0) {
                skyUpdates.add(helper.readByteArray(buffer));
            }
        }
        packet.setSkyUpdates(skyUpdates);
        List<byte[]> blockUpdates = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            if ((packet.getBlockYMask() & 0x01 << i) != 0) {
                blockUpdates.add(helper.readByteArray(buffer));
            }
        }
        packet.setBlockUpdates(blockUpdates);
    }
}
