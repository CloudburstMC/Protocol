package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.CommandBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandBlockUpdateSerializer_v291 implements PacketSerializer<CommandBlockUpdatePacket> {
    public static final CommandBlockUpdateSerializer_v291 INSTANCE = new CommandBlockUpdateSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, CommandBlockUpdatePacket packet) {
        buffer.writeBoolean(packet.isBlock());

        if (packet.isBlock()) {
            BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeUnsignedInt(buffer, packet.getCommandBlockMode());
            buffer.writeBoolean(packet.isRedstoneMode());
            buffer.writeBoolean(packet.isConditional());
        } else {
            VarInts.writeUnsignedLong(buffer, packet.getMinecartRuntimeEntityId());
        }

        BedrockUtils.writeString(buffer, packet.getCommand());
        BedrockUtils.writeString(buffer, packet.getLastOutput());
        BedrockUtils.writeString(buffer, packet.getName());
        buffer.writeBoolean(packet.isOutputTracked());
    }

    @Override
    public void deserialize(ByteBuf buffer, CommandBlockUpdatePacket packet) {
        packet.setBlock(buffer.readBoolean());

        if (packet.isBlock()) {
            packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
            packet.setCommandBlockMode(VarInts.readUnsignedInt(buffer));
            packet.setRedstoneMode(buffer.readBoolean());
            packet.setConditional(buffer.readBoolean());
        } else {
            packet.setMinecartRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        }

        packet.setCommand(BedrockUtils.readString(buffer));
        packet.setLastOutput(BedrockUtils.readString(buffer));
        packet.setName(BedrockUtils.readString(buffer));
        packet.setOutputTracked(buffer.readBoolean());
    }
}
