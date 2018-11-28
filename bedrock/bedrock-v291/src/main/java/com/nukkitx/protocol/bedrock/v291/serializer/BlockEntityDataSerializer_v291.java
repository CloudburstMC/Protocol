package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.nbt.NBTEncodingType;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufInputStream;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufOutputStream;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockEntityDataSerializer_v291 implements PacketSerializer<BlockEntityDataPacket> {
    public static final BlockEntityDataSerializer_v291 INSTANCE = new BlockEntityDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BlockEntityDataPacket packet) {
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPostion());
        try (NBTOutputStream writer = new NBTOutputStream(new LittleEndianByteBufOutputStream(buffer), NBTEncodingType.BEDROCK)) {
            writer.write(packet.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BlockEntityDataPacket packet) {
        packet.setBlockPostion(BedrockUtils.readBlockPosition(buffer));
        try (NBTInputStream reader = new NBTInputStream(new LittleEndianByteBufInputStream(buffer), NBTEncodingType.BEDROCK)) {
            packet.setData(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
