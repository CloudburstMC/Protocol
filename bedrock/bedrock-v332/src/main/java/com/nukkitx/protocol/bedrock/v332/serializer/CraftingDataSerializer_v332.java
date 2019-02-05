package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CraftingDataSerializer_v332 implements PacketSerializer<CraftingDataPacket> {
    public static final CraftingDataSerializer_v332 INSTANCE = new CraftingDataSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, CraftingDataPacket packet) {
        //TODO: Rewrite this.
    }

    @Override
    public void deserialize(ByteBuf buffer, CraftingDataPacket packet) {

    }
}
