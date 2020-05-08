package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.CommandBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandBlockUpdateSerializer_v363 implements PacketSerializer<CommandBlockUpdatePacket> {
    public static final CommandBlockUpdateSerializer_v363 INSTANCE = new CommandBlockUpdateSerializer_v363();

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
        buffer.writeIntLE((int) packet.getTickDelay());
        buffer.writeBoolean(packet.isExecutingOnFirstTick());
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
        packet.setTickDelay(buffer.readUnsignedIntLE());
        packet.setExecutingOnFirstTick(buffer.readBoolean());
    }
}
