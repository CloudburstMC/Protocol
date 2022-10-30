package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetTimePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetTimeSerializer_v291 implements BedrockPacketSerializer<SetTimePacket> {
    public static final SetTimeSerializer_v291 INSTANCE = new SetTimeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetTimePacket packet) {
        VarInts.writeInt(buffer, packet.getTime());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetTimePacket packet) {
        packet.setTime(VarInts.readInt(buffer));
    }
}
