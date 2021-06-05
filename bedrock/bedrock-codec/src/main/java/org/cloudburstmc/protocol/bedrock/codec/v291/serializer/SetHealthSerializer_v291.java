package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetHealthPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetHealthSerializer_v291 implements BedrockPacketSerializer<SetHealthPacket> {
    public static final SetHealthSerializer_v291 INSTANCE = new SetHealthSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetHealthPacket packet) {
        VarInts.writeInt(buffer, packet.getHealth());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetHealthPacket packet) {
        packet.setHealth(VarInts.readInt(buffer));
    }
}
