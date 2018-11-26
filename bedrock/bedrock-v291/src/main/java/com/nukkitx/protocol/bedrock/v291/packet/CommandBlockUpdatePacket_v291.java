package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.CommandBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class CommandBlockUpdatePacket_v291 extends CommandBlockUpdatePacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeBoolean(block);

        if (block) {
            BedrockUtils.writeBlockPosition(buffer, blockPosition);
            VarInts.writeUnsignedInt(buffer, commandBlockMode);
            buffer.writeBoolean(redstoneMode);
            buffer.writeBoolean(conditional);
        } else {
            VarInts.writeUnsignedLong(buffer, minecartRuntimeEntityId);
        }

        BedrockUtils.writeString(buffer, command);
        BedrockUtils.writeString(buffer, lastOutput);
        BedrockUtils.writeString(buffer, name);
        buffer.writeBoolean(outputTracked);
    }

    @Override
    public void decode(ByteBuf buffer) {
        block = buffer.readBoolean();

        if (block) {
            blockPosition = BedrockUtils.readBlockPosition(buffer);
            commandBlockMode = VarInts.readUnsignedInt(buffer);
            redstoneMode = buffer.readBoolean();
            conditional = buffer.readBoolean();
        } else {
            minecartRuntimeEntityId = VarInts.readUnsignedLong(buffer);
        }

        command = BedrockUtils.readString(buffer);
        lastOutput = BedrockUtils.readString(buffer);
        name = BedrockUtils.readString(buffer);
        outputTracked = buffer.readBoolean();
    }
}
