package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.ItemStackResponseSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemStackResponseSerializer_v2193 extends ItemStackResponseSerializer_v2168 {

    public static final ItemStackResponseSerializer_v2193 INSTANCE = new ItemStackResponseSerializer_v2193();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket packet) {
        helper.writeArray(buffer, packet.getEntries(), (buf, response) -> {
            buf.writeByte(response.getResult().ordinal());
            VarInts.writeInt(buffer, response.getRequestId());

            if (response.getContainers().isEmpty()) {
                buf.writeBoolean(false);
                return;
            }

            buf.writeBoolean(true);
            helper.writeArray(buf, response.getContainers(), helper::writeItemStackResponseContainer);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket packet) {
        List<ItemStackResponse> entries = packet.getEntries();
        helper.readArray(buffer, entries, buf -> {
            ItemStackResponseStatus result = ItemStackResponseStatus.values()[buf.readByte()];
            int requestId = VarInts.readInt(buf);

            if (buffer.readBoolean()) { // only 1 bool
                List<ItemStackResponseContainer> containerEntries = new ArrayList<>();
                helper.readArray(buf, containerEntries, helper::readItemStackResponseContainer);
                return new ItemStackResponse(result, requestId, containerEntries);
            } else {
                return new ItemStackResponse(result, requestId, Collections.emptyList());
            }
        });
    }
}
