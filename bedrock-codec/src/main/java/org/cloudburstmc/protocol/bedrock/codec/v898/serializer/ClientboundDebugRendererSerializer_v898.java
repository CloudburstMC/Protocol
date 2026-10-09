package org.cloudburstmc.protocol.bedrock.codec.v898.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.ClientboundDebugRendererType;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundDebugRendererPacket;

/**
 * From v898 the type is sent by name and the marker data is optional, with one ARGB int for the color.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundDebugRendererSerializer_v898 implements BedrockPacketSerializer<ClientboundDebugRendererPacket> {

    public static final ClientboundDebugRendererSerializer_v898 INSTANCE = new ClientboundDebugRendererSerializer_v898();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundDebugRendererPacket packet) {
        helper.writeString(buffer, packet.getDebugMarkerType().getSerializeName());
        helper.writeOptional(buffer, p -> p.getDebugMarkerType() == ClientboundDebugRendererType.ADD_DEBUG_MARKER_CUBE, packet, (buf, h, p) -> {
            h.writeString(buf, p.getMarkerText());
            h.writeVector3f(buf, p.getMarkerPosition());
            buf.writeIntLE(toArgb(p.getMarkerColorRed(), p.getMarkerColorGreen(), p.getMarkerColorBlue(), p.getMarkerColorAlpha()));
            buf.writeLongLE(p.getMarkerDuration());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundDebugRendererPacket packet) {
        packet.setDebugMarkerType(ClientboundDebugRendererType.fromName(helper.readString(buffer)));
        if (buffer.readBoolean()) {
            packet.setMarkerText(helper.readString(buffer));
            packet.setMarkerPosition(helper.readVector3f(buffer));
            int argb = buffer.readIntLE();
            packet.setMarkerColorRed(((argb >> 16) & 0xff) / 255f);
            packet.setMarkerColorGreen(((argb >> 8) & 0xff) / 255f);
            packet.setMarkerColorBlue((argb & 0xff) / 255f);
            packet.setMarkerColorAlpha(((argb >>> 24) & 0xff) / 255f);
            packet.setMarkerDuration(buffer.readLongLE());
        }
    }

    protected static int toArgb(float red, float green, float blue, float alpha) {
        return channel(alpha) << 24 | channel(red) << 16 | channel(green) << 8 | channel(blue);
    }

    private static int channel(float value) {
        return Math.round(Math.max(0f, Math.min(1f, value)) * 255f);
    }
}
