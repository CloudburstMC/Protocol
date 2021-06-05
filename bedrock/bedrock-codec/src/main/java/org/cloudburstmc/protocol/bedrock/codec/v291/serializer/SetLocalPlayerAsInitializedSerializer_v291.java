package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetLocalPlayerAsInitializedSerializer_v291 implements BedrockPacketSerializer<SetLocalPlayerAsInitializedPacket> {
    public static final SetLocalPlayerAsInitializedSerializer_v291 INSTANCE = new SetLocalPlayerAsInitializedSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetLocalPlayerAsInitializedPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetLocalPlayerAsInitializedPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
