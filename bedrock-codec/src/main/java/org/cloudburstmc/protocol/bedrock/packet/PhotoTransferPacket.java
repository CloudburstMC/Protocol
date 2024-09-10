package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.PhotoType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PhotoTransferPacket implements BedrockPacket {
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
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PHOTO_TRANSFER;
    }

    @Override
    public PhotoTransferPacket clone() {
        try {
            return (PhotoTransferPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

