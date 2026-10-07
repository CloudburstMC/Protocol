package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetPassengerOfBlockPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class SetPassengerOfBlockSerializer_v2225 implements BedrockPacketSerializer<SetPassengerOfBlockPacket> {

    public static final SetPassengerOfBlockSerializer_v2225 INSTANCE = new SetPassengerOfBlockSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetPassengerOfBlockPacket packet) {
        VarInts.writeLong(buffer, packet.getEntityUniqueId());
        helper.writeOptionalNull(buffer, packet.getData(), helper::writePassengerOfBlockArguments);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetPassengerOfBlockPacket packet) {
        packet.setEntityUniqueId(VarInts.readLong(buffer));
        packet.setData(helper.readOptional(buffer, null, helper::readPassengerOfBlockArguments));
    }
}
