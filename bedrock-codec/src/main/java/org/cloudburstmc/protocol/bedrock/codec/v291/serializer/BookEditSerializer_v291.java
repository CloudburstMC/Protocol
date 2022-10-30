package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.BookEditPacket;
import org.cloudburstmc.protocol.common.util.Int2ObjectBiMap;

import static org.cloudburstmc.protocol.bedrock.packet.BookEditPacket.Action;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEditSerializer_v291 implements BedrockPacketSerializer<BookEditPacket> {
    public static final BookEditSerializer_v291 INSTANCE = new BookEditSerializer_v291();
    private static final Int2ObjectBiMap<Action> types = new Int2ObjectBiMap<>();

    static {
        types.put(0, Action.REPLACE_PAGE);
        types.put(1, Action.ADD_PAGE);
        types.put(2, Action.DELETE_PAGE);
        types.put(3, Action.SWAP_PAGES);
        types.put(4, Action.SIGN_BOOK);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, BookEditPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        buffer.writeByte(packet.getInventorySlot());
        switch (packet.getAction()) {
            case REPLACE_PAGE:
            case ADD_PAGE:
                buffer.writeByte(packet.getPageNumber());
                helper.writeString(buffer, packet.getText());
                helper.writeString(buffer, packet.getPhotoName());
                break;
            case DELETE_PAGE:
                buffer.writeByte(packet.getPageNumber());
                break;
            case SWAP_PAGES:
                buffer.writeByte(packet.getPageNumber());
                buffer.writeByte(packet.getSecondaryPageNumber());
                break;
            case SIGN_BOOK:
                helper.writeString(buffer, packet.getTitle());
                helper.writeString(buffer, packet.getAuthor());
                helper.writeString(buffer, packet.getXuid());
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, BookEditPacket packet) {
        packet.setAction(types.get(buffer.readUnsignedByte()));
        packet.setInventorySlot(buffer.readUnsignedByte());
        switch (packet.getAction()) {
            case REPLACE_PAGE:
            case ADD_PAGE:
                packet.setPageNumber(buffer.readUnsignedByte());
                packet.setText(helper.readString(buffer));
                packet.setPhotoName(helper.readString(buffer));
                break;
            case DELETE_PAGE:
                packet.setPageNumber(buffer.readUnsignedByte());
                break;
            case SWAP_PAGES:
                packet.setPageNumber(buffer.readUnsignedByte());
                packet.setSecondaryPageNumber(buffer.readUnsignedByte());
                break;
            case SIGN_BOOK:
                packet.setTitle(helper.readString(buffer));
                packet.setAuthor(helper.readString(buffer));
                packet.setXuid(helper.readString(buffer));
                break;
        }
    }
}
