package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.PlayerActionPacket.Action;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerActionSerializer_v332 implements PacketSerializer<PlayerActionPacket> {
    public static final PlayerActionSerializer_v332 INSTANCE = new PlayerActionSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, PlayerActionPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getAction().ordinal());
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getFace());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerActionPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setAction(Action.values()[VarInts.readInt(buffer)]);
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        packet.setFace(VarInts.readInt(buffer));
    }
}
