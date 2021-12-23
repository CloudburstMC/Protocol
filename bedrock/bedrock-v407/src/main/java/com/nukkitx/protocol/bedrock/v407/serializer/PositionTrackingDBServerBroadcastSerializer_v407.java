package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PositionTrackingDBServerBroadcastPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionTrackingDBServerBroadcastSerializer_v407 implements BedrockPacketSerializer<PositionTrackingDBServerBroadcastPacket> {
    public static final PositionTrackingDBServerBroadcastSerializer_v407 INSTANCE = new PositionTrackingDBServerBroadcastSerializer_v407();

    protected static final PositionTrackingDBServerBroadcastPacket.Action[] ACTIONS = PositionTrackingDBServerBroadcastPacket.Action.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBServerBroadcastPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeInt(buffer, packet.getTrackingId());
        helper.writeTag(buffer, packet.getTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PositionTrackingDBServerBroadcastPacket packet) {
        packet.setAction(ACTIONS[buffer.readByte()]);
        packet.setTrackingId(VarInts.readInt(buffer));
        packet.setTag(helper.readTag(buffer));
    }
}
