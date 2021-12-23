package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.PacketViolationSeverity;
import com.nukkitx.protocol.bedrock.data.PacketViolationType;
import com.nukkitx.protocol.bedrock.packet.PacketViolationWarningPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PacketViolationWarningSerializer_v407 implements BedrockPacketSerializer<PacketViolationWarningPacket> {
    public static final PacketViolationWarningSerializer_v407 INSTANCE = new PacketViolationWarningSerializer_v407();

    protected static final PacketViolationType[] TYPES = PacketViolationType.values();
    protected static final PacketViolationSeverity[] SEVERITIES = PacketViolationSeverity.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PacketViolationWarningPacket packet) {
        VarInts.writeInt(buffer, packet.getType().ordinal() - 1);
        VarInts.writeInt(buffer, packet.getSeverity().ordinal() - 1);
        VarInts.writeInt(buffer, packet.getPacketCauseId());
        helper.writeString(buffer, packet.getContext());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PacketViolationWarningPacket packet) {
        packet.setType(TYPES[VarInts.readInt(buffer) + 1]);
        packet.setSeverity(SEVERITIES[VarInts.readInt(buffer) + 1]);
        packet.setPacketCauseId(VarInts.readInt(buffer));
        packet.setContext(helper.readString(buffer));
    }
}
