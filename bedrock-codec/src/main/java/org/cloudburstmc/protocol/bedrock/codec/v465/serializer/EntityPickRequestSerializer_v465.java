package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityPickRequestSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.EntityPickRequestPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityPickRequestSerializer_v465 extends EntityPickRequestSerializer_v291 {
    public static final EntityPickRequestSerializer_v465 INSTANCE = new EntityPickRequestSerializer_v465();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EntityPickRequestPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isWithData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EntityPickRequestPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setWithData(buffer.readBoolean());
    }
}
