package com.nukkitx.protocol.bedrock.v402.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdatePlayerGameTypePacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UpdatePlayerGameTypeSerializer_v402 implements PacketSerializer<UpdatePlayerGameTypePacket> {

    public static final UpdatePlayerGameTypeSerializer_v402 INSTANCE = new UpdatePlayerGameTypeSerializer_v402();

    @Override
    public void serialize(ByteBuf buffer, UpdatePlayerGameTypePacket packet) {
        VarInts.writeInt(buffer, packet.getGameType());
        VarInts.writeLong(buffer, packet.getEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdatePlayerGameTypePacket packet) {
        packet.setGameType(VarInts.readInt(buffer));
        packet.setEntityId(VarInts.readLong(buffer));
    }
}
