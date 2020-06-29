package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityEventSerializer_v291 implements BedrockPacketSerializer<EntityEventPacket> {
    public static final EntityEventSerializer_v291 INSTANCE = new EntityEventSerializer_v291();
    private static final InternalLogger log = InternalLoggerFactory.getInstance(EntityEventSerializer_v291.class);

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EntityEventPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(helper.getEntityEventId(packet.getType()));
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EntityEventPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        int event = buffer.readUnsignedByte();
        packet.setType(helper.getEntityEvent(event));
        packet.setData(VarInts.readInt(buffer));
        if (packet.getType() == null) {
            log.debug("Unknown EntityEvent {} in packet {}", event, packet);
        }
    }
}
