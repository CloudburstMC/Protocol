package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMatchmakingStatePacket;

public class ClientboundMatchmakingStateSerializer_v2225 implements BedrockPacketSerializer<ClientboundMatchmakingStatePacket> {

    public static final ClientboundMatchmakingStateSerializer_v2225 INSTANCE = new ClientboundMatchmakingStateSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMatchmakingStatePacket packet) {
        buffer.writeByte(packet.getState().ordinal());
        helper.writeString(buffer, packet.getDestinationName());
        helper.writeOptionalNull(buffer, packet.getOptions(), (buf, opt) -> {
            helper.writeString(buf, opt.getTriggeringPlayerName());
            buf.writeBoolean(opt.isTriggeredByLocalPlayer());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundMatchmakingStatePacket packet) {
        packet.setState(ClientboundMatchmakingStatePacket.MatchmakingState.values()[buffer.readUnsignedByte()]);
        packet.setDestinationName(helper.readString(buffer));
        packet.setOptions(helper.readOptional(buffer, null, (buf) ->
                new ClientboundMatchmakingStatePacket.MatchmakingStateOptions(helper.readString(buf), buf.readBoolean())));
    }
}
