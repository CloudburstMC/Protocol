package com.nukkitx.protocol.bedrock.v428.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.ClientboundDebugRendererType;
import com.nukkitx.protocol.bedrock.packet.ClientboundDebugRendererPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundDebugRendererSerializer_v428 implements BedrockPacketSerializer<ClientboundDebugRendererPacket> {

    public static final ClientboundDebugRendererSerializer_v428 INSTANCE = new ClientboundDebugRendererSerializer_v428();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ClientboundDebugRendererPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getDebugMarkerType().ordinal());
        if (packet.getDebugMarkerType() == ClientboundDebugRendererType.ADD_DEBUG_MARKER_CUBE) {
            helper.writeString(buffer, packet.getMarkerText());
            helper.writeVector3f(buffer, packet.getMarkerPosition());
            buffer.writeFloat(packet.getMarkerColorRed());
            buffer.writeFloat(packet.getMarkerColorGreen());
            buffer.writeFloat(packet.getMarkerColorBlue());
            buffer.writeFloat(packet.getMarkerColorAlpha());
            buffer.writeLongLE(packet.getMarkerDuration());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ClientboundDebugRendererPacket packet) {
        packet.setDebugMarkerType(ClientboundDebugRendererType.values()[VarInts.readUnsignedInt(buffer)]);
        if (packet.getDebugMarkerType() == ClientboundDebugRendererType.ADD_DEBUG_MARKER_CUBE) {
            packet.setMarkerText(helper.readString(buffer));
            packet.setMarkerPosition(helper.readVector3f(buffer));
            packet.setMarkerColorRed(buffer.readFloat());
            packet.setMarkerColorGreen(buffer.readFloat());
            packet.setMarkerColorBlue(buffer.readFloat());
            packet.setMarkerColorAlpha(buffer.readFloat());
            packet.setMarkerDuration(buffer.readLongLE());
        }
    }
}
