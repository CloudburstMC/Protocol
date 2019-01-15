package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.BookEditPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.BookEditPacket.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookEditSerializer_v291 implements PacketSerializer<BookEditPacket> {
    public static final BookEditSerializer_v291 INSTANCE = new BookEditSerializer_v291();
    private static final TIntHashBiMap<Type> types = new TIntHashBiMap<>();

    static {
        types.put(0, Type.REPLACE_PAGE);
        types.put(1, Type.ADD_PAGE);
        types.put(2, Type.DELETE_PAGE);
        types.put(3, Type.SWAP_PAGES);
        types.put(4, Type.SIGN_BOOK);
    }

    @Override
    public void serialize(ByteBuf buffer, BookEditPacket packet) {
        buffer.writeByte(packet.getType().ordinal());
        buffer.writeByte(packet.getInventorySlot());
        switch (packet.getType()) {
            case REPLACE_PAGE:
            case ADD_PAGE:
                buffer.writeByte(packet.getPageNumber());
                BedrockUtils.writeString(buffer, packet.getText());
                BedrockUtils.writeString(buffer, packet.getPhotoName());
                break;
            case DELETE_PAGE:
                buffer.writeByte(packet.getPageNumber());
                break;
            case SWAP_PAGES:
                buffer.writeByte(packet.getPageNumber());
                buffer.writeByte(packet.getSecondaryPageNumber());
                break;
            case SIGN_BOOK:
                BedrockUtils.writeString(buffer, packet.getTitle());
                BedrockUtils.writeString(buffer, packet.getAuthor());
                BedrockUtils.writeString(buffer, packet.getXuid());
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BookEditPacket packet) {
        packet.setType(types.get(buffer.readUnsignedByte()));
        packet.setInventorySlot(buffer.readUnsignedByte());
        switch (packet.getType()) {
            case REPLACE_PAGE:
            case ADD_PAGE:
                packet.setPageNumber(buffer.readUnsignedByte());
                packet.setText(BedrockUtils.readString(buffer));
                packet.setPhotoName(BedrockUtils.readString(buffer));
                break;
            case DELETE_PAGE:
                packet.setPageNumber(buffer.readUnsignedByte());
                break;
            case SWAP_PAGES:
                packet.setPageNumber(buffer.readUnsignedByte());
                packet.setSecondaryPageNumber(buffer.readUnsignedByte());
                break;
            case SIGN_BOOK:
                packet.setTitle(BedrockUtils.readString(buffer));
                packet.setAuthor(BedrockUtils.readString(buffer));
                packet.setXuid(BedrockUtils.readString(buffer));
                break;
        }
    }
}
