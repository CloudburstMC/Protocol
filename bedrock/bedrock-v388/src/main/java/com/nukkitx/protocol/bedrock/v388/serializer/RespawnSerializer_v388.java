package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RespawnSerializer_v388 implements BedrockPacketSerializer<RespawnPacket> {
    public static final RespawnSerializer_v388 INSTANCE = new RespawnSerializer_v388();

    private static final RespawnPacket.State[] VALUES = RespawnPacket.State.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RespawnPacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
        buffer.writeByte(packet.getState().ordinal());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RespawnPacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
        packet.setState(VALUES[buffer.readUnsignedByte()]);
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
