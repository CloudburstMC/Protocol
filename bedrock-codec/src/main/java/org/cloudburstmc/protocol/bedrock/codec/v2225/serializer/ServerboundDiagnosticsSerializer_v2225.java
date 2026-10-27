package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2193.serializer.ServerboundDiagnosticsSerializer_v2193;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.EntityDiagnosticTimingInfo;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.SystemCategory;

import java.util.List;

public class ServerboundDiagnosticsSerializer_v2225 extends ServerboundDiagnosticsSerializer_v2193 {

    public static final ServerboundDiagnosticsSerializer_v2225 INSTANCE = new ServerboundDiagnosticsSerializer_v2225();

    @Override
    protected EntityDiagnosticTimingInfo readEntityDiagnosticTimingInfo(ByteBuf buf, BedrockCodecHelper helper) {
        return new EntityDiagnosticTimingInfo(
                helper.readString(buf),
                helper.readString(buf),
                buf.readLongLE(),
                (byte) buf.readUnsignedByte(),
                helper.readOptional(buf, null, helper::readVector3f),
                helper.readOptional(buf, null, helper::readString));
    }

    @Override
    protected void writeEntityDiagnosticTimingInfo(ByteBuf buf, BedrockCodecHelper helper, EntityDiagnosticTimingInfo info) {
        helper.writeString(buf, info.getDisplayName());
        helper.writeString(buf, info.getEntity());
        buf.writeLongLE(info.getTimeInNs());
        buf.writeByte(info.getPercentOfTotal());
        helper.writeOptionalNull(buf, info.getPosition(), helper::writeVector3f);
        helper.writeOptionalNull(buf, info.getDimension(), helper::writeString);
    }

    @Override
    protected void writeSystemCategories(ByteBuf buffer, BedrockCodecHelper helper, List<SystemCategory> categories) {
        boolean present = !categories.isEmpty();
        buffer.writeBoolean(present);
        if (present) {
            super.writeSystemCategories(buffer, helper, categories);
        }
    }

    @Override
    protected void readSystemCategories(ByteBuf buffer, BedrockCodecHelper helper, List<SystemCategory> categories) {
        if (buffer.readBoolean()) {
            super.readSystemCategories(buffer, helper, categories);
        }
    }
}
