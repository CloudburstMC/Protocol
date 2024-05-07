package org.cloudburstmc.protocol.bedrock.codec.v685.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderCategoryType;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderCodeStatus;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderOperationType;
import org.cloudburstmc.protocol.bedrock.packet.CodeBuilderSourcePacket;

public class CodeBuilderSourceSerializer_v685 implements BedrockPacketSerializer<CodeBuilderSourcePacket> {
    public static final CodeBuilderSourceSerializer_v685 INSTANCE = new CodeBuilderSourceSerializer_v685();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CodeBuilderSourcePacket packet) {
        buffer.writeByte(packet.getOperation().ordinal());
        buffer.writeByte(packet.getCategory().ordinal());
        buffer.writeByte(packet.getCodeStatus().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CodeBuilderSourcePacket packet) {
        packet.setOperation(CodeBuilderOperationType.values()[buffer.readByte()]);
        packet.setCategory(CodeBuilderCategoryType.values()[buffer.readByte()]);
        packet.setCodeStatus(CodeBuilderCodeStatus.values()[buffer.readByte()]);
    }
}