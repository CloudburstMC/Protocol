package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.CommandBlockMode;
import com.nukkitx.protocol.bedrock.packet.CommandBlockUpdatePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommandBlockUpdateSerializer_v361 implements BedrockPacketSerializer<CommandBlockUpdatePacket> {
    public static final CommandBlockUpdateSerializer_v361 INSTANCE = new CommandBlockUpdateSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CommandBlockUpdatePacket packet) {
        buffer.writeBoolean(packet.isBlock());

        if (packet.isBlock()) {
            helper.writeBlockPosition(buffer, packet.getBlockPosition());
            VarInts.writeUnsignedInt(buffer, packet.getMode().ordinal());
            buffer.writeBoolean(packet.isRedstoneMode());
            buffer.writeBoolean(packet.isConditional());
        } else {
            VarInts.writeUnsignedLong(buffer, packet.getMinecartRuntimeEntityId());
        }

        helper.writeString(buffer, packet.getCommand());
        helper.writeString(buffer, packet.getLastOutput());
        helper.writeString(buffer, packet.getName());
        buffer.writeBoolean(packet.isOutputTracked());
        buffer.writeIntLE((int) packet.getTickDelay());
        buffer.writeBoolean(packet.isExecutingOnFirstTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CommandBlockUpdatePacket packet) {
        packet.setBlock(buffer.readBoolean());

        if (packet.isBlock()) {
            packet.setBlockPosition(helper.readBlockPosition(buffer));
            packet.setMode(CommandBlockMode.values()[VarInts.readUnsignedInt(buffer)]);
            packet.setRedstoneMode(buffer.readBoolean());
            packet.setConditional(buffer.readBoolean());
        } else {
            packet.setMinecartRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        }

        packet.setCommand(helper.readString(buffer));
        packet.setLastOutput(helper.readString(buffer));
        packet.setName(helper.readString(buffer));
        packet.setOutputTracked(buffer.readBoolean());
        packet.setTickDelay(buffer.readUnsignedIntLE());
        packet.setExecutingOnFirstTick(buffer.readBoolean());
    }
}
