package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.PlayerActionType;
import org.cloudburstmc.protocol.bedrock.packet.PlayerActionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerActionSerializer_v291 implements BedrockPacketSerializer<PlayerActionPacket> {
    public static final PlayerActionSerializer_v291 INSTANCE = new PlayerActionSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerActionPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getAction().ordinal());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getFace());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerActionPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setAction(PlayerActionType.values()[VarInts.readInt(buffer)]);
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setFace(VarInts.readInt(buffer));
    }
}
