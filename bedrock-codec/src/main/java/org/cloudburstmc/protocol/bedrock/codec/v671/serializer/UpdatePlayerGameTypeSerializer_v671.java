package org.cloudburstmc.protocol.bedrock.codec.v671.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.UpdatePlayerGameTypeSerializer_v407;
import org.cloudburstmc.protocol.bedrock.packet.UpdatePlayerGameTypePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class UpdatePlayerGameTypeSerializer_v671 extends UpdatePlayerGameTypeSerializer_v407 {
    public static final UpdatePlayerGameTypeSerializer_v671 INSTANCE = new UpdatePlayerGameTypeSerializer_v671();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdatePlayerGameTypePacket packet) {
        super.serialize(buffer, helper, packet);
        VarInts.writeUnsignedInt(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdatePlayerGameTypePacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setTick(VarInts.readUnsignedInt(buffer));
    }
}
