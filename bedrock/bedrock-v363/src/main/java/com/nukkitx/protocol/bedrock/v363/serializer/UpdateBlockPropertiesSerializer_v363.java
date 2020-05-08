package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPropertiesPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBlockPropertiesSerializer_v363 implements PacketSerializer<UpdateBlockPropertiesPacket> {
    public static final UpdateBlockPropertiesSerializer_v363 INSTANCE = new UpdateBlockPropertiesSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, UpdateBlockPropertiesPacket packet) {
        try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
            writer.write(packet.getProperties());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdateBlockPropertiesPacket packet) {
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            packet.setProperties(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
