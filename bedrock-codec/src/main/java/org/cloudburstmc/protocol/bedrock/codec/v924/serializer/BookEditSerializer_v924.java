package org.cloudburstmc.protocol.bedrock.codec.v924.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.BookEditSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.BookEditPacket;
import org.cloudburstmc.protocol.common.util.TextConverter;
import org.cloudburstmc.protocol.common.util.VarInts;

public class BookEditSerializer_v924 extends BookEditSerializer_v291 {

    public static final BookEditSerializer_v924 INSTANCE = new BookEditSerializer_v924();

    private static final int MAX_TEXT_LENGTH = 768;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, BookEditPacket packet) {
        VarInts.writeInt(buffer, packet.getInventorySlot());
        VarInts.writeUnsignedInt(buffer, packet.getAction().ordinal());

        TextConverter converter = helper.getTextConverter();
        switch (packet.getAction()) {
            case REPLACE_PAGE:
            case ADD_PAGE:
                VarInts.writeInt(buffer, packet.getPageNumber());
                helper.writeString(buffer, converter.serialize(packet.getText(CharSequence.class)));
                helper.writeString(buffer, packet.getPhotoName());
                break;
            case DELETE_PAGE:
                VarInts.writeInt(buffer, packet.getPageNumber());
                break;
            case SWAP_PAGES:
                VarInts.writeInt(buffer, packet.getPageNumber());
                VarInts.writeInt(buffer, packet.getSecondaryPageNumber());
                break;
            case SIGN_BOOK:
                helper.writeString(buffer, converter.serialize(packet.getTitle(CharSequence.class)));
                helper.writeString(buffer, converter.serialize(packet.getAuthor(CharSequence.class)));
                helper.writeString(buffer, packet.getXuid());
                break;
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, BookEditPacket packet) {
        packet.setInventorySlot(VarInts.readInt(buffer));
        packet.setAction(types.get(VarInts.readUnsignedInt(buffer)));

        TextConverter converter = helper.getTextConverter();
        switch (packet.getAction()) {
            case REPLACE_PAGE:
            case ADD_PAGE:
                packet.setPageNumber(VarInts.readInt(buffer));
                packet.setText(converter.deserialize(helper.readStringMaxLen(buffer, MAX_TEXT_LENGTH)));
                packet.setPhotoName(helper.readStringMaxLen(buffer, MAX_TEXT_LENGTH));
                break;
            case DELETE_PAGE:
                packet.setPageNumber(VarInts.readInt(buffer));
                break;
            case SWAP_PAGES:
                packet.setPageNumber(VarInts.readInt(buffer));
                packet.setSecondaryPageNumber(VarInts.readInt(buffer));
                break;
            case SIGN_BOOK:
                packet.setTitle(converter.deserialize(helper.readStringMaxLen(buffer, MAX_TEXT_LENGTH)));
                packet.setAuthor(converter.deserialize(helper.readStringMaxLen(buffer, MAX_TEXT_LENGTH)));
                packet.setXuid(helper.readStringMaxLen(buffer, MAX_TEXT_LENGTH));
                break;
        }
    }
}
