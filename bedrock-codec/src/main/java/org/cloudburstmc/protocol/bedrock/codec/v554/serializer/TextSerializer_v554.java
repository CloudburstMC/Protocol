package org.cloudburstmc.protocol.bedrock.codec.v554.serializer;

import io.netty.buffer.ByteBuf;
import net.kyori.adventure.text.TranslatableComponent;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.TextSerializer_v332;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;

public class TextSerializer_v554 extends TextSerializer_v332 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        TextPacket.Type type = packet.getType();
        buffer.writeByte(type.ordinal());
        buffer.writeBoolean(packet.getMessage() instanceof TranslatableComponent);

        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                helper.writeString(buffer, packet.getSourceName());
            case RAW:
            case TIP:
            case SYSTEM:
            case JSON:
            case WHISPER_JSON:
            case ANNOUNCEMENT_JSON:
                helper.writeComponent(buffer, packet.getMessage(), type != TextPacket.Type.JSON && type != TextPacket.Type.WHISPER_JSON && type != TextPacket.Type.ANNOUNCEMENT_JSON);
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                helper.writeComponentWithArguments(buffer, packet.getMessage(), true);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TextType " + type);
        }

        helper.writeString(buffer, packet.getXuid());
        helper.writeString(buffer, packet.getPlatformChatId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        TextPacket.Type type = TextPacket.Type.values()[buffer.readUnsignedByte()];
        packet.setType(type);
        boolean needsTranslation = buffer.readBoolean();

        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                packet.setSourceName(helper.readString(buffer));
            case RAW:
            case TIP:
            case SYSTEM:
            case JSON:
            case WHISPER_JSON:
            case ANNOUNCEMENT_JSON:
                packet.setMessage(helper.readComponent(buffer, needsTranslation, type != TextPacket.Type.JSON && type != TextPacket.Type.WHISPER_JSON && type != TextPacket.Type.ANNOUNCEMENT_JSON));
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                packet.setMessage(helper.readComponentWithArguments(buffer, needsTranslation, true));
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TextType " + type);
        }

        packet.setXuid(helper.readString(buffer));
        packet.setPlatformChatId(helper.readString(buffer));
    }
}
