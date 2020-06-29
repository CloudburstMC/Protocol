package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PositionTrackingDBClientRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionTrackingDBClientRequestSerializer_v407 implements BedrockPacketSerializer<PositionTrackingDBClientRequestPacket> {
    public static final PositionTrackingDBClientRequestSerializer_v407 INSTANCE = new PositionTrackingDBClientRequestSerializer_v407();

    protected static final PositionTrackingDBClientRequestPacket.Action[] ACTIONS = PositionTrackingDBClientRequestPacket.Action.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBClientRequestPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeInt(buffer, packet.getTrackingId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBClientRequestPacket packet) {
        packet.setAction(ACTIONS[buffer.readByte()]);
        packet.setTrackingId(VarInts.readInt(buffer));
    }
}
