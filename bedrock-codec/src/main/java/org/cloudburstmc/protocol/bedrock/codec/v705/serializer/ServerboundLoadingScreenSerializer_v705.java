package org.cloudburstmc.protocol.bedrock.codec.v705.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.ServerboundLoadingScreenPacketType;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundLoadingScreenPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Optional;

public class ServerboundLoadingScreenSerializer_v705 implements BedrockPacketSerializer<ServerboundLoadingScreenPacket> {
    public static final ServerboundLoadingScreenSerializer_v705 INSTANCE = new ServerboundLoadingScreenSerializer_v705();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundLoadingScreenPacket packet) {
        VarInts.writeInt(buffer, packet.getType().ordinal());
        helper.writeOptionalNull(buffer, packet.getLoadingScreenId(), (byteBuf, loadingScreenId) -> byteBuf.writeIntLE(loadingScreenId.get()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundLoadingScreenPacket packet) {
        packet.setType(ServerboundLoadingScreenPacketType.values()[VarInts.readInt(buffer)]);
        packet.setLoadingScreenId(helper.readOptional(buffer, Optional.empty(), byteBuf -> Optional.of(byteBuf.readIntLE())));
    }
}