package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.MoveEntityDeltaSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityDeltaPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class MoveEntityDeltaSerializer_v2193 extends MoveEntityDeltaSerializer_v2168 {

    public static final MoveEntityDeltaSerializer_v2193 INSTANCE = new MoveEntityDeltaSerializer_v2193();

    protected MoveEntityDeltaSerializer_v2193() {
        super();
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityDeltaPacket packet) {
        super.serialize(buffer, helper, packet);
        VarInts.writeUnsignedLong(buffer, packet.getTicks());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityDeltaPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setTicks(VarInts.readUnsignedLong(buffer));
    }
}
