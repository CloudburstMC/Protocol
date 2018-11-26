package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.TextPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class TextPacket_v291 extends TextPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(type.ordinal());
        buffer.writeBoolean(needsTranslation);

        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                BedrockUtils.writeString(buffer, sourceName);
            case RAW:
            case TIP:
            case SYSTEM:
                BedrockUtils.writeString(buffer, message);
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                BedrockUtils.writeString(buffer, message);
                BedrockUtils.writeArray(buffer, parameters, BedrockUtils::writeString);
                break;
        }

        BedrockUtils.writeString(buffer, xuid);
        BedrockUtils.writeString(buffer, platformChatId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        type = Type.values()[buffer.readUnsignedByte()];
        needsTranslation = buffer.readBoolean();
        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                sourceName = BedrockUtils.readString(buffer);
            case RAW:
            case TIP:
            case SYSTEM:
                message = BedrockUtils.readString(buffer);
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                message = BedrockUtils.readString(buffer);
                BedrockUtils.readArray(buffer, parameters, BedrockUtils::readString);
                break;
        }
        xuid = BedrockUtils.readString(buffer);
        platformChatId = BedrockUtils.readString(buffer);
    }
}
