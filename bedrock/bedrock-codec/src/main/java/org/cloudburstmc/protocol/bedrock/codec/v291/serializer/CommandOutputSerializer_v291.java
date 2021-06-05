package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOutputMessage;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOutputType;
import org.cloudburstmc.protocol.bedrock.packet.CommandOutputPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommandOutputSerializer_v291 implements BedrockPacketSerializer<CommandOutputPacket> {
    public static final CommandOutputSerializer_v291 INSTANCE = new CommandOutputSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CommandOutputPacket packet) {
        helper.writeCommandOrigin(buffer, packet.getCommandOriginData());
        buffer.writeByte(packet.getType().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getSuccessCount());

        helper.writeArray(buffer, packet.getMessages(), this::writeMessage);

        if (packet.getType() == CommandOutputType.DATA_SET) {
            helper.writeString(buffer, packet.getData());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CommandOutputPacket packet) {
        packet.setCommandOriginData(helper.readCommandOrigin(buffer));
        packet.setType(CommandOutputType.values()[buffer.readUnsignedByte()]);
        packet.setSuccessCount(VarInts.readUnsignedInt(buffer));

        helper.readArray(buffer, packet.getMessages(), this::readMessage);

        if (packet.getType() == CommandOutputType.DATA_SET) {
            packet.setData(helper.readString(buffer));
        }
    }

    public CommandOutputMessage readMessage(ByteBuf buffer, BedrockCodecHelper helper) {
        boolean internal = buffer.readBoolean();
        String messageId = helper.readString(buffer);
        String[] parameters = helper.readArray(buffer, new String[0], helper::readString);
        return new CommandOutputMessage(internal, messageId, parameters);
    }

    public void writeMessage(ByteBuf buffer, BedrockCodecHelper helper, CommandOutputMessage outputMessage) {
        requireNonNull(outputMessage, "CommandOutputMessage is null");

        buffer.writeBoolean(outputMessage.isInternal());
        helper.writeString(buffer, outputMessage.getMessageId());
        helper.writeArray(buffer, outputMessage.getParameters(), helper::writeString);
    }
}
