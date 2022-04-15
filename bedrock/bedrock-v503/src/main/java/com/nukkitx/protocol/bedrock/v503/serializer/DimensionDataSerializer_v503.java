package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.definition.DimensionDefinition;
import com.nukkitx.protocol.bedrock.packet.DimensionDataPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DimensionDataSerializer_v503 implements BedrockPacketSerializer<DimensionDataPacket> {
    public static final DimensionDataSerializer_v503 INSTANCE = new DimensionDataSerializer_v503();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, DimensionDataPacket packet) {
        helper.writeArray(buffer, packet.getDefinitions(), this::writeDefinition);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, DimensionDataPacket packet) {
        helper.readArray(buffer, packet.getDefinitions(), this::readDefinition);
    }

    protected void writeDefinition(ByteBuf buffer, BedrockPacketHelper helper, DimensionDefinition definition) {
        helper.writeString(buffer, definition.getId());
        VarInts.writeInt(buffer, definition.getMaximumHeight());
        VarInts.writeInt(buffer, definition.getMinimumHeight());
        VarInts.writeInt(buffer, definition.getGeneratorType());
    }

    protected DimensionDefinition readDefinition(ByteBuf buffer, BedrockPacketHelper helper) {
        String id = helper.readString(buffer);
        int maximumHeight = VarInts.readInt(buffer);
        int minimumHeight = VarInts.readInt(buffer);
        int generatorType = VarInts.readInt(buffer);
        return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType);
    }
}