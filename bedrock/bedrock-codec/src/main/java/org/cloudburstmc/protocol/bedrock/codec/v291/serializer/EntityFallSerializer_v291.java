package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.EntityFallPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityFallSerializer_v291 implements BedrockPacketSerializer<EntityFallPacket> {
    public static final EntityFallSerializer_v291 INSTANCE = new EntityFallSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EntityFallPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeFloatLE(packet.getFallDistance());
        buffer.writeBoolean(packet.isInVoid());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EntityFallPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setFallDistance(buffer.readFloatLE());
        packet.setInVoid(buffer.readBoolean());
    }
}
