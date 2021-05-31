package org.cloudburstmc.protocol.bedrock.codec.v332.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextSerializer_v332 implements BedrockPacketSerializer<TextPacket> {
    public static final TextSerializer_v332 INSTANCE = new TextSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        TextPacket.Type type = packet.getType();
        buffer.writeByte(type.ordinal());
        buffer.writeBoolean(packet.isNeedsTranslation());

        switch (type) {
            case Type.CHAT:
            case Type.WHISPER:
            case Type.ANNOUNCEMENT:
                helper.writeString(buffer, packet.getSourceName());
            case Type.RAW:
            case Type.TIP:
            case Type.SYSTEM:
            case Type.OBJECT:
            case Type.OBJECT_WHISPER:
                helper.writeString(buffer, packet.getMessage());
                break;
            case Type.TRANSLATION:
            case Type.POPUP:
            case Type.JUKEBOX_POPUP:
                helper.writeString(buffer, packet.getMessage());
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
        packet.setNeedsTranslation(buffer.readBoolean());

        switch (type) {
            case Type.CHAT:
            case Type.WHISPER:
            case Type.ANNOUNCEMENT:
                packet.setSourceName(helper.readString(buffer));
            case Type.RAW:
            case Type.TIP:
            case Type.SYSTEM:
            case Type.OBJECT:
            case Type.OBJECT_WHISPER:
                packet.setMessage(helper.readString(buffer));
                break;
            case Type.TRANSLATION:
            case Type.POPUP:
            case Type.JUKEBOX_POPUP:
                packet.setMessage(helper.readString(buffer));
                helper.readArray(buffer, packet.getParameters(), helper::readString);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TextType " + type);
        }

        packet.setXuid(helper.readString(buffer));
        packet.setPlatformChatId(helper.readString(buffer));
    }
}
