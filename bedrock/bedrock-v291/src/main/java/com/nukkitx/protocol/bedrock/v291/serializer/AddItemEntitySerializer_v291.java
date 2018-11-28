package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AddItemEntityPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddItemEntitySerializer_v291 implements PacketSerializer<AddItemEntityPacket> {
    public static final AddItemEntitySerializer_v291 INSTANCE = new AddItemEntitySerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, AddItemEntityPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeItemInstance(buffer, packet.getItemInstance());
        BedrockUtils.writeVector3f(buffer, packet.getPosition());
        BedrockUtils.writeVector3f(buffer, packet.getMotion());
        BedrockUtils.writeMetadata(buffer, packet.getMetadata());
        buffer.writeBoolean(packet.isFromFishing());
    }

    @Override
    public void deserialize(ByteBuf buffer, AddItemEntityPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setItemInstance(BedrockUtils.readItemInstance(buffer));
        packet.setPosition(BedrockUtils.readVector3f(buffer));
        packet.setMotion(BedrockUtils.readVector3f(buffer));
        BedrockUtils.readMetadata(buffer, packet.getMetadata());
        packet.setFromFishing(buffer.readBoolean());
    }
}
