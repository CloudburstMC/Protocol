package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockEntityDataSerializer_v291 implements BedrockPacketSerializer<BlockEntityDataPacket> {
    public static final BlockEntityDataSerializer_v291 INSTANCE = new BlockEntityDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, BlockEntityDataPacket packet) {
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
            writer.write(packet.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, BlockEntityDataPacket packet) {
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            packet.setData(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
