package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemStackResponseSerializer_v407 implements BedrockPacketSerializer<ItemStackResponsePacket> {

    public static final ItemStackResponseSerializer_v407 INSTANCE = new ItemStackResponseSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket packet) {
        helper.writeArray(buffer, packet.getEntries(), (buf, response) -> {
            buf.writeBoolean(response.isSuccess());
            VarInts.writeInt(buffer, response.getRequestId());

            if (!response.isSuccess())
                return;

            helper.writeArray(buf, response.getContainers(), (buf2, containerEntry) -> {
                helper.writeContainerSlotType(buf2, containerEntry.getContainer());
                helper.writeArray(buf2, containerEntry.getItems(), this::writeItemEntry);
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket packet) {
        List<ItemStackResponse> entries = packet.getEntries();
        helper.readArray(buffer, entries, buf -> {
            boolean success = buf.readBoolean();
            int requestId = VarInts.readInt(buf);

            if (!success)
                return new ItemStackResponse(success, requestId, Collections.emptyList());

            List<ItemStackResponseContainer> containerEntries = new ArrayList<>();
            helper.readArray(buf, containerEntries, buf2 -> {
                ContainerSlotType container = helper.readContainerSlotType(buf2);

                List<ItemStackResponseSlot> itemEntries = new ArrayList<>();
                helper.readArray(buf2, itemEntries, byteBuf -> this.readItemEntry(byteBuf, helper));
                return new ItemStackResponseContainer(container, itemEntries, null);
            });
            return new ItemStackResponse(success, requestId, containerEntries);
        });
    }

    protected ItemStackResponseSlot readItemEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        return new ItemStackResponseSlot(
                buffer.readUnsignedByte(),
                buffer.readUnsignedByte(),
                buffer.readUnsignedByte(),
                VarInts.readInt(buffer),
                "",
                0);
    }

    protected void writeItemEntry(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponseSlot itemEntry) {
        buffer.writeByte(itemEntry.getSlot());
        buffer.writeByte(itemEntry.getHotbarSlot());
        buffer.writeByte(itemEntry.getCount());
        VarInts.writeInt(buffer, itemEntry.getStackNetworkId());
    }
}
