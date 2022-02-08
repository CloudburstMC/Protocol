package com.nukkitx.protocol.bedrock.v486.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.CodeBuilderCategoryType;
import com.nukkitx.protocol.bedrock.data.CodeBuilderOperationType;
import com.nukkitx.protocol.bedrock.packet.CodeBuilderSourcePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeBuilderSourceSerializer_v486 implements BedrockPacketSerializer<CodeBuilderSourcePacket> {

    public static final CodeBuilderSourceSerializer_v486 INSTANCE = new CodeBuilderSourceSerializer_v486();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CodeBuilderSourcePacket packet) {
        buffer.writeByte(packet.getOperation().ordinal());
        buffer.writeByte(packet.getCategory().ordinal());
        helper.writeString(buffer, packet.getValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CodeBuilderSourcePacket packet) {
        packet.setOperation(CodeBuilderOperationType.values()[buffer.readByte()]);
        packet.setCategory(CodeBuilderCategoryType.values()[buffer.readByte()]);
        packet.setValue(helper.readString(buffer));
    }
}
