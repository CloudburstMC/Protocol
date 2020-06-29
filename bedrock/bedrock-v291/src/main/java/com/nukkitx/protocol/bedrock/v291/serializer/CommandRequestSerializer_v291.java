package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommandRequestSerializer_v291 implements BedrockPacketSerializer<CommandRequestPacket> {
    public static final CommandRequestSerializer_v291 INSTANCE = new CommandRequestSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CommandRequestPacket packet) {
        helper.writeString(buffer, packet.getCommand());
        helper.writeCommandOrigin(buffer, packet.getCommandOriginData());
        buffer.writeBoolean(packet.isInternal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CommandRequestPacket packet) {
        packet.setCommand(helper.readString(buffer));
        packet.setCommandOriginData(helper.readCommandOrigin(buffer));
        packet.setInternal(buffer.readBoolean());
    }
}
