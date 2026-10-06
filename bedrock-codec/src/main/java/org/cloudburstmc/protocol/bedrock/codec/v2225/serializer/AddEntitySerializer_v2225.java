package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v557.serializer.AddEntitySerializer_v557;
import org.cloudburstmc.protocol.bedrock.packet.AddEntityPacket;

public class AddEntitySerializer_v2225 extends AddEntitySerializer_v557 {

    public static final AddEntitySerializer_v2225 INSTANCE = new AddEntitySerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddEntityPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getPassengerOfBlockArguments(), helper::writePassengerOfBlockArguments);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddEntityPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setPassengerOfBlockArguments(helper.readOptional(buffer, null, helper::readPassengerOfBlockArguments));
    }
}
