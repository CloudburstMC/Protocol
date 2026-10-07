package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundStonecutterSetRecipePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ClientboundStonecutterSetRecipeSerializer_v2225 implements BedrockPacketSerializer<ClientboundStonecutterSetRecipePacket> {

    public static final ClientboundStonecutterSetRecipeSerializer_v2225 INSTANCE = new ClientboundStonecutterSetRecipeSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundStonecutterSetRecipePacket packet) {
        VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
        buffer.writeByte(packet.getContainerId());
        VarInts.writeInt(buffer, packet.getRecipeIndex());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundStonecutterSetRecipePacket packet) {
        packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
        packet.setContainerId(buffer.readUnsignedByte());
        packet.setRecipeIndex(VarInts.readInt(buffer));
    }
}
