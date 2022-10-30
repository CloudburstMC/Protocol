package org.cloudburstmc.protocol.bedrock.codec.v527.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.PlayerActionType;
import org.cloudburstmc.protocol.bedrock.packet.PlayerActionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor
public class PlayerActionSerializer_v527 implements BedrockPacketSerializer<PlayerActionPacket> {

    private static final PlayerActionType[] TYPES = PlayerActionType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerActionPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getAction().ordinal());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        helper.writeBlockPosition(buffer, packet.getResultPosition());
        VarInts.writeInt(buffer, packet.getFace());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerActionPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setAction(TYPES[VarInts.readInt(buffer)]);
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setResultPosition(helper.readBlockPosition(buffer));
        packet.setFace(VarInts.readInt(buffer));
    }
}