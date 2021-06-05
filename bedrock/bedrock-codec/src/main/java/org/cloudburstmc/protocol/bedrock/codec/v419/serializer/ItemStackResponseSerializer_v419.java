package org.cloudburstmc.protocol.bedrock.codec.v419.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.ItemStackResponseSerializer_v407;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemStackResponseSerializer_v419 extends ItemStackResponseSerializer_v407 {

    public static final ItemStackResponseSerializer_v419 INSTANCE = new ItemStackResponseSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket packet) {
        helper.writeArray(buffer, packet.getEntries(), (buf, response) -> {
            buf.writeByte(response.getResult().ordinal());
            VarInts.writeInt(buffer, response.getRequestId());

            if (response.getResult() != ItemStackResponsePacket.ResponseStatus.OK)
                return;

            helper.writeArray(buf, response.getContainers(), (buf2, containerEntry) -> {
                buf2.writeByte(containerEntry.getContainer().ordinal());

                helper.writeArray(buf2, containerEntry.getItems(), this::writeItemEntry);
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket packet) {
        List<ItemStackResponsePacket.Response> entries = packet.getEntries();
        helper.readArray(buffer, entries, buf -> {
            ItemStackResponsePacket.ResponseStatus result = ItemStackResponsePacket.ResponseStatus.values()[buf.readByte()];
            int requestId = VarInts.readInt(buf);

            if (result != ItemStackResponsePacket.ResponseStatus.OK)
                return new ItemStackResponsePacket.Response(result, requestId, Collections.emptyList());

            List<ItemStackResponsePacket.ContainerEntry> containerEntries = new ArrayList<>();
            helper.readArray(buf, containerEntries, buf2 -> {
                ContainerSlotType container = ContainerSlotType.values()[buf2.readByte()];

                List<ItemStackResponsePacket.ItemEntry> itemEntries = new ArrayList<>();
                helper.readArray(buf2, itemEntries, byteBuf -> this.readItemEntry(buf2, helper));
                return new ItemStackResponsePacket.ContainerEntry(container, itemEntries);
            });
            return new ItemStackResponsePacket.Response(result, requestId, containerEntries);
        });
    }

}
