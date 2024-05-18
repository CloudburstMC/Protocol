package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.packet.EntityEventPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor
public class EntityEventSerializer_v291 implements BedrockPacketSerializer<EntityEventPacket> {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(EntityEventSerializer_v291.class);

    private final TypeMap<EntityEventType> typeMap;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EntityEventPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(this.typeMap.getId(packet.getType()));
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EntityEventPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        int event = buffer.readUnsignedByte();
        packet.setType(this.typeMap.getType(event));
        packet.setData(VarInts.readInt(buffer));
        if (log.isDebugEnabled() && packet.getType() == null) {
            log.debug("Unknown EntityEvent {} in packet {}", event, packet);
        }
    }
}
