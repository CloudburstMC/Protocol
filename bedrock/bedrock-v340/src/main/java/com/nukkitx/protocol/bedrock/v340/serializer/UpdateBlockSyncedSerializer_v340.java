package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockSyncedPacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket.Flag;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBlockSyncedSerializer_v340 implements PacketSerializer<UpdateBlockSyncedPacket> {
    public static final UpdateBlockSyncedSerializer_v340 INSTANCE = new UpdateBlockSyncedSerializer_v340();


    @Override
    public void serialize(ByteBuf buffer, UpdateBlockSyncedPacket packet) {
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeUnsignedInt(buffer, packet.getRuntimeId());
        int flagValue = 0;
        for (Flag flag : packet.getFlags()) {
            flagValue |= (1 << flag.ordinal());
        }
        VarInts.writeUnsignedInt(buffer, flagValue);
        VarInts.writeUnsignedInt(buffer, packet.getDataLayer());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getUnknownLong1());
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdateBlockSyncedPacket packet) {
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setRuntimeId(VarInts.readUnsignedInt(buffer));
        int flagValue = VarInts.readUnsignedInt(buffer);
        Set<Flag> flags = packet.getFlags();
        for (Flag flag : Flag.values()) {
            if ((flagValue & (1 << flag.ordinal())) != 0) {
                flags.add(flag);
            }
        }
        packet.setDataLayer(VarInts.readUnsignedInt(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setUnknownLong1(VarInts.readUnsignedLong(buffer));
    }
}
