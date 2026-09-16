package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.ServerboundDiagnosticsSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.EntityDiagnosticTimingInfo;

public class ServerboundDiagnosticsSerializer_v2193 extends ServerboundDiagnosticsSerializer_v2168 {

    public static final ServerboundDiagnosticsSerializer_v2193 INSTANCE = new ServerboundDiagnosticsSerializer_v2193();

    @Override
    protected EntityDiagnosticTimingInfo readEntityDiagnosticTimingInfo(ByteBuf buf, BedrockCodecHelper helper) {
        return new EntityDiagnosticTimingInfo(
                helper.readString(buf),
                helper.readString(buf),
                buf.readLongLE(),
                (byte) buf.readUnsignedByte(),
                helper.readVector3f(buf),
                helper.readString(buf));
    }

    @Override
    protected void writeEntityDiagnosticTimingInfo(ByteBuf buf, BedrockCodecHelper helper, EntityDiagnosticTimingInfo info) {
        helper.writeString(buf, info.getDisplayName());
        helper.writeString(buf, info.getEntity());
        buf.writeLongLE(info.getTimeInNs());
        buf.writeByte(info.getPercentOfTotal());
        helper.writeVector3f(buf, info.getPosition());
        helper.writeString(buf, info.getDimension());
    }
}
