package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftingDataSerializer_v291 implements PacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v291 INSTANCE = new CraftingDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, CraftingDataPacket packet) {
        //TODO: Rewrite this.
    }

    @Override
    public void deserialize(ByteBuf buffer, CraftingDataPacket packet) {

    }
}
