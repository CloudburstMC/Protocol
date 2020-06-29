package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.EntityPickRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityPickRequestSerializer_v291 implements BedrockPacketSerializer<EntityPickRequestPacket> {
    public static final EntityPickRequestSerializer_v291 INSTANCE = new EntityPickRequestSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EntityPickRequestPacket packet) {
        buffer.writeLongLE(packet.getRuntimeEntityId());
        buffer.writeByte(packet.getHotbarSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EntityPickRequestPacket packet) {
        packet.setRuntimeEntityId(buffer.readLongLE());
        packet.setHotbarSlot(buffer.readUnsignedByte());
    }
}
