package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDiagnosticsPacket;

public class ServerboundDiagnosticsSerializer_v712 implements BedrockPacketSerializer<ServerboundDiagnosticsPacket> {
    public static final ServerboundDiagnosticsSerializer_v712 INSTANCE = new ServerboundDiagnosticsSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        buffer.writeFloatLE(packet.getAvgFps());
        buffer.writeFloatLE(packet.getAvgServerSimTickTimeMS());
        buffer.writeFloatLE(packet.getAvgClientSimTickTimeMS());
        buffer.writeFloatLE(packet.getAvgBeginFrameTimeMS());
        buffer.writeFloatLE(packet.getAvgInputTimeMS());
        buffer.writeFloatLE(packet.getAvgRenderTimeMS());
        buffer.writeFloatLE(packet.getAvgEndFrameTimeMS());
        buffer.writeFloatLE(packet.getAvgRemainderTimePercent());
        buffer.writeFloatLE(packet.getAvgUnaccountedTimePercent());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        packet.setAvgFps(buffer.readFloatLE());
        packet.setAvgServerSimTickTimeMS(buffer.readFloatLE());
        packet.setAvgClientSimTickTimeMS(buffer.readFloatLE());
        packet.setAvgBeginFrameTimeMS(buffer.readFloatLE());
        packet.setAvgInputTimeMS(buffer.readFloatLE());
        packet.setAvgRenderTimeMS(buffer.readFloatLE());
        packet.setAvgEndFrameTimeMS(buffer.readFloatLE());
        packet.setAvgRemainderTimePercent(buffer.readFloatLE());
        packet.setAvgUnaccountedTimePercent(buffer.readFloatLE());
    }
}