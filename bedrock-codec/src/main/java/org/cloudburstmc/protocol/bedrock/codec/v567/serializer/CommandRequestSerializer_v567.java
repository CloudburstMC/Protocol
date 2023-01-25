package org.cloudburstmc.protocol.bedrock.codec.v567.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.CommandRequestSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.CommandRequestPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class CommandRequestSerializer_v567 extends CommandRequestSerializer_v291 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CommandRequestPacket packet) {
        super.serialize(buffer, helper, packet);

        VarInts.writeInt(buffer, packet.getVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CommandRequestPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setVersion(VarInts.readInt(buffer));
    }
}
