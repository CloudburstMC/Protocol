package com.nukkitx.protocol.bedrock.beta.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.RequestPermissionsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestPermissionsSerializerBeta implements BedrockPacketSerializer<RequestPermissionsPacket> {
    public static final RequestPermissionsSerializerBeta INSTANCE = new RequestPermissionsSerializerBeta();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestPermissionsPacket packet) {
        buffer.writeLongLE(packet.getUniqueEntityId());
        VarInts.writeInt(buffer, packet.getPermissions());
        buffer.writeShortLE(packet.getCustomPermissions());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestPermissionsPacket packet) {
        packet.setUniqueEntityId(buffer.readLongLE());
        packet.setPermissions(VarInts.readInt(buffer));
        packet.setCustomPermissions(buffer.readUnsignedShortLE());
    }
}
