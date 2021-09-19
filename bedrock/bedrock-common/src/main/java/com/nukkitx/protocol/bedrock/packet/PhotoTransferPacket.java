package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.PhotoType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class PhotoTransferPacket extends BedrockPacket {
    private String name;
    private byte[] data;
    private String bookId;
    /**
     * @since v465
     */
    private PhotoType photoType;
    /**
     * @since v465
     */
    private PhotoType sourceType;
    /**
     * @since v465
     */
    private long ownerId;
    /**
     * @since v465
     */
    private String newPhotoName;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PHOTO_TRANSFER;
    }
}
