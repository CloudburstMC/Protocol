package com.nukkitx.protocol.bedrock.v527.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.packet.RequestPermissionsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestPermissionsSerializer_v527 implements BedrockPacketSerializer<RequestPermissionsPacket> {
    public static final RequestPermissionsSerializer_v527 INSTANCE = new RequestPermissionsSerializer_v527();

    protected static final PlayerPermission[] VALUES = PlayerPermission.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RequestPermissionsPacket packet) {
        buffer.writeLongLE(packet.getUniqueEntityId());
        VarInts.writeInt(buffer, packet.getPermissions().ordinal());
        buffer.writeShortLE(packet.getCustomPermissions());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RequestPermissionsPacket packet) {
        packet.setUniqueEntityId(buffer.readLongLE());
        packet.setPermissions(VALUES[VarInts.readInt(buffer)]);
        packet.setCustomPermissions(buffer.readUnsignedShortLE());
    }
}
