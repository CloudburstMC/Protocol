package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ToastRequestPacket implements BedrockPacket {

    private CharSequence title;
    private CharSequence content;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TOAST_REQUEST;
    }

    @Override
    public ToastRequestPacket clone() {
        try {
            return (ToastRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public String getTitle() {
        return getTitle(String.class);
    }

    public <T extends CharSequence> T getTitle(Class<T> type) {
        return type.cast(title);
    }

    public String getContent() {
        return getContent(String.class);
    }

    public <T extends CharSequence> T getContent(Class<T> type) {
        return type.cast(content);
    }
}

