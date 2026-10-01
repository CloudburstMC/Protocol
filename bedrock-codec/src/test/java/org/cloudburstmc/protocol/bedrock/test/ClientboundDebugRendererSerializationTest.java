package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v428.Bedrock_v428;
import org.cloudburstmc.protocol.bedrock.codec.v860.Bedrock_v860;
import org.cloudburstmc.protocol.bedrock.data.ClientboundDebugRendererType;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundDebugRendererPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientboundDebugRendererSerializationTest {
    private static final BedrockCodec[] CODECS = {Bedrock_v428.CODEC, Bedrock_v860.CODEC};

    // The marker color is four little endian floats, like every other float on the wire
    @Test
    void markerColorIsLittleEndian() {
        for (BedrockCodec codec : CODECS) {
            String name = "v" + codec.getProtocolVersion();
            ClientboundDebugRendererPacket packet = new ClientboundDebugRendererPacket();
            packet.setDebugMarkerType(ClientboundDebugRendererType.ADD_DEBUG_MARKER_CUBE);
            packet.setMarkerText("");
            packet.setMarkerPosition(Vector3f.ZERO);
            packet.setMarkerColorRed(0.5f);
            packet.setMarkerColorGreen(0.25f);

            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                assertTrue(ByteBufUtil.hexDump(buffer).contains("0000003f0000803e"), name);
            } finally {
                buffer.release();
            }
        }
    }
}
