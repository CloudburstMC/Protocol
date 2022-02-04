package org.cloudburstmc.protocol.bedrock.codec.v485.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderCategoryType;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderOperationType;
import org.cloudburstmc.protocol.bedrock.packet.CodeBuilderSourcePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeBuilderSourceSerializer_v485 implements BedrockPacketSerializer<CodeBuilderSourcePacket> {

    public static final CodeBuilderSourceSerializer_v485 INSTANCE = new CodeBuilderSourceSerializer_v485();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CodeBuilderSourcePacket packet) {
        buffer.writeByte(packet.getOperation().ordinal());
        buffer.writeByte(packet.getCategory().ordinal());
        helper.writeString(buffer, packet.getValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CodeBuilderSourcePacket packet) {
        packet.setOperation(CodeBuilderOperationType.values()[buffer.readByte()]);
        packet.setCategory(CodeBuilderCategoryType.values()[buffer.readByte()]);
        packet.setValue(helper.readString(buffer));
    }
}
