package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.nbt.stream.NetworkDataInputStream;
import com.nukkitx.nbt.stream.NetworkDataOutputStream;
import com.nukkitx.protocol.bedrock.packet.BiomeDefinitionListPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BiomeDefinitionListSerializer_v354 implements PacketSerializer<BiomeDefinitionListPacket> {
    public static final BiomeDefinitionListSerializer_v354 INSTANCE = new BiomeDefinitionListSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, BiomeDefinitionListPacket packet) {
        try (NBTOutputStream writer = new NBTOutputStream(new NetworkDataOutputStream(new ByteBufOutputStream(buffer)))) {
            writer.write(packet.getTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BiomeDefinitionListPacket packet) {
        try (NBTInputStream reader = new NBTInputStream(new NetworkDataInputStream(new ByteBufInputStream(buffer)))) {
            packet.setTag(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
