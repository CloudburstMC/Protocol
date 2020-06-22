package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPropertiesPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateBlockPropertiesSerializer_v361 implements BedrockPacketSerializer<UpdateBlockPropertiesPacket> {
    public static final UpdateBlockPropertiesSerializer_v361 INSTANCE = new UpdateBlockPropertiesSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockPropertiesPacket packet) {
        try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
            writer.write(packet.getProperties());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockPropertiesPacket packet) {
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            packet.setProperties(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
