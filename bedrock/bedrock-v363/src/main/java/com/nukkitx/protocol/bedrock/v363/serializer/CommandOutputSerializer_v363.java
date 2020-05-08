package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.CommandOutputPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandOutputSerializer_v363 implements PacketSerializer<CommandOutputPacket> {
    public static final CommandOutputSerializer_v363 INSTANCE = new CommandOutputSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, CommandOutputPacket packet) {
        BedrockUtils.writeCommandOriginData(buffer, packet.getCommandOriginData());
        buffer.writeByte(packet.getOutputType());
        VarInts.writeUnsignedInt(buffer, packet.getSuccessCount());

        BedrockUtils.writeArray(buffer, packet.getMessages(), BedrockUtils::writeCommandOutputMessage);

        if (packet.getOutputType() == 4) {
            BedrockUtils.writeString(buffer, packet.getData());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, CommandOutputPacket packet) {
        packet.setCommandOriginData(BedrockUtils.readCommandOriginData(buffer));
        packet.setOutputType(buffer.readUnsignedByte());
        packet.setSuccessCount(VarInts.readUnsignedInt(buffer));

        BedrockUtils.readArray(buffer, packet.getMessages(), BedrockUtils::readCommandOutputMessage);

        if (packet.getOutputType() == 4) {
            packet.setData(BedrockUtils.readString(buffer));
        }
    }
}
