package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemDataInstance;
import com.nukkitx.protocol.bedrock.packet.CreativeContentPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreativeContentSerializer_v407 implements BedrockPacketSerializer<CreativeContentPacket> {

    public static final CreativeContentSerializer_v407 INSTANCE = new CreativeContentSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CreativeContentPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());
        for (ItemDataInstance instance : packet.getEntries().values()) {
            helper.writeItemInstance(buffer, instance);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CreativeContentPacket packet) {
        Int2ObjectMap<ItemDataInstance> entries = packet.getEntries();

        int count = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < count; i++) {
            ItemDataInstance instance = helper.readItemInstance(buffer);
            if (entries.putIfAbsent(instance.getNetworkId(), instance) != null) {
                throw new IllegalStateException("Creative content net ID collision!");
            }
        }
    }
}
