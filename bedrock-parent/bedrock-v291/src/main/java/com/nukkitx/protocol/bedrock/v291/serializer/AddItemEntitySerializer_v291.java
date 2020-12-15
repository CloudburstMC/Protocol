package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.packet.AddItemEntityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddItemEntitySerializer_v291 implements BedrockPacketSerializer<AddItemEntityPacket> {
    public static final AddItemEntitySerializer_v291 INSTANCE = new AddItemEntitySerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AddItemEntityPacket packet, BedrockSession session) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeItem(buffer, packet.getItemInHand(), session);
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getMotion());
        helper.writeEntityData(buffer, packet.getMetadata());
        buffer.writeBoolean(packet.isFromFishing());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AddItemEntityPacket packet, BedrockSession session) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setItemInHand(helper.readItem(buffer, session));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        helper.readEntityData(buffer, packet.getMetadata());
        packet.setFromFishing(buffer.readBoolean());
    }
}
