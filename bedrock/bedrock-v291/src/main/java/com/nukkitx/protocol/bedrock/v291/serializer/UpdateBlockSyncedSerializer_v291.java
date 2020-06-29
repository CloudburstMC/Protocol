package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.BlockSyncType;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockSyncedPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket.Flag;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateBlockSyncedSerializer_v291 implements BedrockPacketSerializer<UpdateBlockSyncedPacket> {
    public static final UpdateBlockSyncedSerializer_v291 INSTANCE = new UpdateBlockSyncedSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockSyncedPacket packet) {
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeUnsignedInt(buffer, packet.getRuntimeId());
        int flagValue = 0;
        for (Flag flag : packet.getFlags()) {
            flagValue |= (1 << flag.ordinal());
        }
        VarInts.writeUnsignedInt(buffer, flagValue);
        VarInts.writeUnsignedInt(buffer, packet.getDataLayer());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getEntityBlockSyncType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockSyncedPacket packet) {
        packet.setBlockPosition(helper.readBlockPosition(buffer));
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
        packet.setEntityBlockSyncType(BlockSyncType.values()[(int) VarInts.readUnsignedLong(buffer)]);
    }
}
