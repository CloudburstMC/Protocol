package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerOpenSerializer_v291 implements BedrockPacketSerializer<ContainerOpenPacket> {
    public static final ContainerOpenSerializer_v291 INSTANCE = new ContainerOpenSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerOpenPacket packet) {
        buffer.writeByte(packet.getId());
        buffer.writeByte(packet.getType().getId());
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ContainerOpenPacket packet) {
        packet.setId(buffer.readByte());
        packet.setType(ContainerType.from(buffer.readByte()));
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setUniqueEntityId(VarInts.readLong(buffer));
    }
}
