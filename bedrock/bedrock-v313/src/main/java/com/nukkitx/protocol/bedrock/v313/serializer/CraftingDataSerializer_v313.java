package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftingDataSerializer_v313 implements PacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v313 INSTANCE = new CraftingDataSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, CraftingDataPacket packet) {
        //TODO: Rewrite this.
    }

    @Override
    public void deserialize(ByteBuf buffer, CraftingDataPacket packet) {

    }
}
