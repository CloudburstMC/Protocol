package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class UpdateBlockPacket_v291 extends UpdateBlockPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeBlockPosition(buffer, blockPosition);
        VarInts.writeUnsignedInt(buffer, runtimeId);
        int flagValue = 0;
        for (Flag flag : flags) {
            flagValue |= (1 << flag.ordinal());
        }
        VarInts.writeUnsignedInt(buffer, flagValue);
        VarInts.writeUnsignedInt(buffer, dataLayer);
    }

    @Override
    public void decode(ByteBuf buffer) {
        blockPosition = BedrockUtils.readBlockPosition(buffer);
        runtimeId = VarInts.readUnsignedInt(buffer);
        int flagValue = VarInts.readUnsignedInt(buffer);
        for (Flag flag : Flag.values()) {
            if ((flagValue & (1 << flag.ordinal())) != 0) {
                flags.add(flag);
            }
        }
        dataLayer = VarInts.readUnsignedInt(buffer);
    }
}
