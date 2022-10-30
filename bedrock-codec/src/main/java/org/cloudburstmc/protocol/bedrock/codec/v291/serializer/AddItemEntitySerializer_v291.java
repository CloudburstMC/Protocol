package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AddItemEntityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddItemEntitySerializer_v291 implements BedrockPacketSerializer<AddItemEntityPacket> {
    public static final AddItemEntitySerializer_v291 INSTANCE = new AddItemEntitySerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AddItemEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeItem(buffer, packet.getItemInHand());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getMotion());
        helper.writeEntityData(buffer, packet.getMetadata());
        buffer.writeBoolean(packet.isFromFishing());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AddItemEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setItemInHand(helper.readItem(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        helper.readEntityData(buffer, packet.getMetadata());
        packet.setFromFishing(buffer.readBoolean());
    }
}
