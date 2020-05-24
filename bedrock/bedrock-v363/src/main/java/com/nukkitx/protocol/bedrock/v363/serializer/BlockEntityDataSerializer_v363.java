package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockEntityDataSerializer_v363 implements PacketSerializer<BlockEntityDataPacket> {
    public static final BlockEntityDataSerializer_v363 INSTANCE = new BlockEntityDataSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, BlockEntityDataPacket packet) {
        BedrockUtils.writeBlockPosition(buffer, packet.getBlockPosition());
        try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
            writer.write(packet.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BlockEntityDataPacket packet) {
        packet.setBlockPosition(BedrockUtils.readBlockPosition(buffer));
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            packet.setData(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
