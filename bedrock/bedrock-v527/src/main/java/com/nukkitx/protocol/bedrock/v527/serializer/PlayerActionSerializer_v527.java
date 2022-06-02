package com.nukkitx.protocol.bedrock.v527.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerActionSerializer_v527 implements BedrockPacketSerializer<PlayerActionPacket> {
    public static final PlayerActionSerializer_v527 INSTANCE = new PlayerActionSerializer_v527();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerActionPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getAction().ordinal());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        helper.writeBlockPosition(buffer, packet.getResultPosition());
        VarInts.writeInt(buffer, packet.getFace());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerActionPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setAction(PlayerActionType.values()[VarInts.readInt(buffer)]);
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setResultPosition(helper.readBlockPosition(buffer));
        packet.setFace(VarInts.readInt(buffer));
    }
}
