package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.ServerboundLoadingScreenPacketType;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundLoadingScreenPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ServerboundLoadingScreenSerializer_v712 implements BedrockPacketSerializer<ServerboundLoadingScreenPacket> {
    public static final ServerboundLoadingScreenSerializer_v712 INSTANCE = new ServerboundLoadingScreenSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundLoadingScreenPacket packet) {
        VarInts.writeInt(buffer, packet.getType().ordinal());
        helper.writeOptionalNull(buffer, packet.getLoadingScreenId(), ByteBuf::writeIntLE);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundLoadingScreenPacket packet) {
        packet.setType(ServerboundLoadingScreenPacketType.values()[VarInts.readInt(buffer)]);
        packet.setLoadingScreenId(helper.readOptional(buffer, null, ByteBuf::readIntLE));
    }
}