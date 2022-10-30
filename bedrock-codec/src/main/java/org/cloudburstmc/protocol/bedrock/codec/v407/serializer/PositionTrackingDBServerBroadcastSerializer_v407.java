package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PositionTrackingDBServerBroadcastPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionTrackingDBServerBroadcastSerializer_v407 implements BedrockPacketSerializer<PositionTrackingDBServerBroadcastPacket> {
    public static final PositionTrackingDBServerBroadcastSerializer_v407 INSTANCE = new PositionTrackingDBServerBroadcastSerializer_v407();

    protected static final PositionTrackingDBServerBroadcastPacket.Action[] ACTIONS = PositionTrackingDBServerBroadcastPacket.Action.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PositionTrackingDBServerBroadcastPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeInt(buffer, packet.getTrackingId());
        helper.writeTag(buffer, packet.getTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PositionTrackingDBServerBroadcastPacket packet) {
        packet.setAction(ACTIONS[buffer.readByte()]);
        packet.setTrackingId(VarInts.readInt(buffer));
        packet.setTag(helper.readTag(buffer, NbtMap.class));
    }
}
