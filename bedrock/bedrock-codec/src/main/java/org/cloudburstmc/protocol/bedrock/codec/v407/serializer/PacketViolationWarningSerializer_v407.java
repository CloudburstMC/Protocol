package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.PacketViolationSeverity;
import org.cloudburstmc.protocol.bedrock.data.PacketViolationType;
import org.cloudburstmc.protocol.bedrock.packet.PacketViolationWarningPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PacketViolationWarningSerializer_v407 implements BedrockPacketSerializer<PacketViolationWarningPacket> {
    public static final PacketViolationWarningSerializer_v407 INSTANCE = new PacketViolationWarningSerializer_v407();

    protected static final PacketViolationType[] TYPES = PacketViolationType.values();
    protected static final PacketViolationSeverity[] SEVERITIES = PacketViolationSeverity.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PacketViolationWarningPacket packet) {
        VarInts.writeInt(buffer, packet.getType().ordinal() - 1);
        VarInts.writeInt(buffer, packet.getSeverity().ordinal() - 1);
        VarInts.writeInt(buffer, packet.getPacketCauseId());
        helper.writeString(buffer, packet.getContext());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PacketViolationWarningPacket packet) {
        packet.setType(TYPES[VarInts.readInt(buffer) + 1]);
        packet.setSeverity(SEVERITIES[VarInts.readInt(buffer) + 1]);
        packet.setPacketCauseId(VarInts.readInt(buffer));
        packet.setContext(helper.readString(buffer));
    }
}
