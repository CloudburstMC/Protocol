package com.nukkitx.protocol.bedrock.v560.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.UpdateClientInputLocksPacket;
import io.netty.buffer.ByteBuf;

public class UpdateClientInputLocksSerializer_v560 implements BedrockPacketSerializer<UpdateClientInputLocksPacket> {

    public static final UpdateClientInputLocksSerializer_v560 INSTANCE = new UpdateClientInputLocksSerializer_v560();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateClientInputLocksPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getLockComponentData());
        helper.writeVector3f(buffer, packet.getServerPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateClientInputLocksPacket packet) {
        packet.setLockComponentData(VarInts.readUnsignedInt(buffer));
        packet.setServerPosition(helper.readVector3f(buffer));
    }
}
