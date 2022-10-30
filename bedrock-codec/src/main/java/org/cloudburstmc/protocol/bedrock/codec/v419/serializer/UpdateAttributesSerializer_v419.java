package org.cloudburstmc.protocol.bedrock.codec.v419.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.UpdateAttributesSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.UpdateAttributesPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAttributesSerializer_v419 extends UpdateAttributesSerializer_v291 {

    public static final UpdateAttributesSerializer_v419 INSTANCE = new UpdateAttributesSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAttributesPacket packet) {
        super.serialize(buffer, helper, packet);

        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAttributesPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}
