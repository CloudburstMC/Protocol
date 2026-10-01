package org.cloudburstmc.protocol.bedrock.codec.v428.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.ClientboundDebugRendererType;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundDebugRendererPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundDebugRendererSerializer_v428 implements BedrockPacketSerializer<ClientboundDebugRendererPacket> {

    public static final ClientboundDebugRendererSerializer_v428 INSTANCE = new ClientboundDebugRendererSerializer_v428();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundDebugRendererPacket packet) {
        this.writeMarkerType(buffer, helper, packet.getDebugMarkerType());
        if (packet.getDebugMarkerType() == ClientboundDebugRendererType.ADD_DEBUG_MARKER_CUBE) {
            helper.writeString(buffer, packet.getMarkerText());
            helper.writeVector3f(buffer, packet.getMarkerPosition());
            buffer.writeFloatLE(packet.getMarkerColorRed());
            buffer.writeFloatLE(packet.getMarkerColorGreen());
            buffer.writeFloatLE(packet.getMarkerColorBlue());
            buffer.writeFloatLE(packet.getMarkerColorAlpha());
            buffer.writeLongLE(packet.getMarkerDuration());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundDebugRendererPacket packet) {
        packet.setDebugMarkerType(this.readMarkerType(buffer, helper));
        if (packet.getDebugMarkerType() == ClientboundDebugRendererType.ADD_DEBUG_MARKER_CUBE) {
            packet.setMarkerText(helper.readString(buffer));
            packet.setMarkerPosition(helper.readVector3f(buffer));
            packet.setMarkerColorRed(buffer.readFloatLE());
            packet.setMarkerColorGreen(buffer.readFloatLE());
            packet.setMarkerColorBlue(buffer.readFloatLE());
            packet.setMarkerColorAlpha(buffer.readFloatLE());
            packet.setMarkerDuration(buffer.readLongLE());
        }
    }

    protected void writeMarkerType(ByteBuf buffer, BedrockCodecHelper helper, ClientboundDebugRendererType type) {
        VarInts.writeUnsignedInt(buffer, type.ordinal());
    }

    protected ClientboundDebugRendererType readMarkerType(ByteBuf buffer, BedrockCodecHelper helper) {
        return ClientboundDebugRendererType.values()[VarInts.readUnsignedInt(buffer)];
    }
}
