package org.cloudburstmc.protocol.bedrock.codec.v557.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.SetEntityDataSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.SetEntityDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class SetEntityDataSerializer_v557 extends SetEntityDataSerializer_v291 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetEntityDataPacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeEntityProperties(buffer, packet.getProperties()); // Added
        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetEntityDataPacket packet) {
        super.deserialize(buffer, helper, packet);

        helper.readEntityProperties(buffer, packet.getProperties()); // Added
        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}
