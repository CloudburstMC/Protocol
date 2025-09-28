package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.cloudburstmc.protocol.common.util.TextConverter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextSerializer_v291 implements BedrockPacketSerializer<TextPacket> {
    public static final TextSerializer_v291 INSTANCE = new TextSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        TextPacket.Type type = packet.getType();
        buffer.writeByte(type.ordinal());
        TextConverter converter = helper.getTextConverter();
        CharSequence message = packet.getMessage(CharSequence.class);
        Boolean needsTranslation = converter.needsTranslation(message);
        buffer.writeBoolean(needsTranslation != null ? needsTranslation : packet.isNeedsTranslation());

        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                helper.writeString(buffer, packet.getSourceName());
            case RAW:
            case TIP:
            case SYSTEM:
                helper.writeString(buffer, converter.serialize(message));
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                String text = converter.serializeWithArguments(message, packet.getParameters());
                helper.writeString(buffer, text);
                helper.writeArray(buffer, packet.getParameters(), helper::writeString);
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
        TextConverter converter = helper.getTextConverter();
        boolean needsTranslation = buffer.readBoolean();

        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                packet.setSourceName(helper.readString(buffer));
            case RAW:
            case TIP:
            case SYSTEM:
                packet.setMessage(converter.deserialize(helper.readString(buffer), needsTranslation));
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                String text = helper.readString(buffer);
                ObjectList<String> parameters = new ObjectArrayList<>();
                helper.readArray(buffer, parameters, helper::readString);
                CharSequence message2 = converter.deserializeWithArguments(text, parameters, needsTranslation);
                packet.setMessage(message2);
                packet.setParameters(parameters);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TextType " + type);
        }

        packet.setXuid(helper.readString(buffer));
        packet.setPlatformChatId(helper.readString(buffer));
    }
}
