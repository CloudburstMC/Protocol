package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.protocol.bedrock.packet.AvailableEntityIdentifiersPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableEntityIdentifiersSerializer_v313 implements PacketSerializer<AvailableEntityIdentifiersPacket> {
    public static final AvailableEntityIdentifiersSerializer_v313 INSTANCE = new AvailableEntityIdentifiersSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, AvailableEntityIdentifiersPacket packet) {
        try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
            writer.write(packet.getTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, AvailableEntityIdentifiersPacket packet) {
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            packet.setTag(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
