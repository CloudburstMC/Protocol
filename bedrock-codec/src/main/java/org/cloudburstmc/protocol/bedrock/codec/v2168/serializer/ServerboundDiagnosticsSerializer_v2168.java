package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.ServerboundDiagnosticsSerializer_v975;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.SystemCategory;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.WhiskerScopeDataSummary;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDiagnosticsPacket;

import java.util.List;

public class ServerboundDiagnosticsSerializer_v2168 extends ServerboundDiagnosticsSerializer_v975 { // 975 intentional, system before whisker

    public static final ServerboundDiagnosticsSerializer_v2168 INSTANCE = new ServerboundDiagnosticsSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.serialize(buffer, helper, packet);

        this.writeSystemCategories(buffer, helper, packet.getSystemCategories());

        helper.writeArray(buffer, packet.getWhiskerScopes(), ((buf, h, info) -> {
            helper.writeString(buf, info.getLabel());
            helper.writeString(buf, info.getIndentation());
            buf.writeLongLE(info.getTotalHighCostNS());
            buf.writeLongLE(info.getTotalMidCostNS());
            buf.writeLongLE(info.getTotalLowCostNS());
        }));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.deserialize(buffer, helper, packet);

        this.readSystemCategories(buffer, helper, packet.getSystemCategories());

        helper.readArray(buffer, packet.getWhiskerScopes(), (buf, h) ->
                new WhiskerScopeDataSummary(
                        helper.readString(buf),
                        helper.readString(buf),
                        buf.readLongLE(),
                        buf.readLongLE(),
                        buf.readLongLE()));
    }

    protected void writeSystemCategories(ByteBuf buffer, BedrockCodecHelper helper, List<SystemCategory> categories) {
        helper.writeArray(buffer, categories, ((buf, h, info) -> {
            helper.writeString(buf, info.getCategoryName());
            buf.writeLongLE(info.getSystemIndex());
        }));
    }

    protected void readSystemCategories(ByteBuf buffer, BedrockCodecHelper helper, List<SystemCategory> categories) {
        helper.readArray(buffer, categories, (buf, h) ->
                new SystemCategory(
                        helper.readString(buf),
                        buf.readLongLE()));
    }
}
