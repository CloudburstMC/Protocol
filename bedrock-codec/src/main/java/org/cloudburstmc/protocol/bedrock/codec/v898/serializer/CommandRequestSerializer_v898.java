package org.cloudburstmc.protocol.bedrock.codec.v898.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v567.serializer.CommandRequestSerializer_v567;
import org.cloudburstmc.protocol.bedrock.packet.CommandRequestPacket;

public class CommandRequestSerializer_v898 extends CommandRequestSerializer_v567 {

    public static final CommandRequestSerializer_v898 INSTANCE = new CommandRequestSerializer_v898();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CommandRequestPacket packet) {
        helper.writeString(buffer, packet.getCommand());
        helper.writeCommandOrigin(buffer, packet.getCommandOriginData());
        buffer.writeBoolean(packet.isInternal());

        helper.writeString(buffer, "latest"); // Hardcode "latest" for now
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CommandRequestPacket packet) {
        packet.setCommand(helper.readStringMaxLen(buffer, 1000));
        packet.setCommandOriginData(helper.readCommandOrigin(buffer));
        packet.setInternal(buffer.readBoolean());

        helper.readString(buffer);
        packet.setVersion(48); // Hardcode "latest" for now
    }
}
