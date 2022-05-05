package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sends a toast notification to the client.
 *
 * @since v526
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ToastRequestPacket extends BedrockPacket {

    private String title;
    private String content;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TOAST_REQUEST;
    }
}
