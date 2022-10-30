package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.TakeItemEntityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TakeItemEntitySerializer_v291 implements BedrockPacketSerializer<TakeItemEntityPacket> {
    public static final TakeItemEntitySerializer_v291 INSTANCE = new TakeItemEntitySerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TakeItemEntityPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getItemRuntimeEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TakeItemEntityPacket packet) {
        packet.setItemRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
