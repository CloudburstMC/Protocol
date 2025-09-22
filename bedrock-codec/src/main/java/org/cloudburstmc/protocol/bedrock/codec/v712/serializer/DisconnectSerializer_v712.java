package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.DisconnectFailReason;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class DisconnectSerializer_v712 implements BedrockPacketSerializer<DisconnectPacket> {
    public static final DisconnectSerializer_v712 INSTANCE = new DisconnectSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, DisconnectPacket packet) {
        VarInts.writeInt(buffer, packet.getReason().ordinal());
        buffer.writeBoolean(packet.isMessageSkipped());
        if (!packet.isMessageSkipped()) {
            helper.writeString(buffer, packet.getKickMessage());
            helper.writeString(buffer, packet.getFilteredMessage());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, DisconnectPacket packet) {
        packet.setReason(DisconnectFailReason.values()[VarInts.readInt(buffer)]);
        packet.setMessageSkipped(buffer.readBoolean());
        if (!packet.isMessageSkipped()) {
            packet.setKickMessage(helper.readString(buffer));
            packet.setFilteredMessage(helper.readString(buffer));
        }
    }
}