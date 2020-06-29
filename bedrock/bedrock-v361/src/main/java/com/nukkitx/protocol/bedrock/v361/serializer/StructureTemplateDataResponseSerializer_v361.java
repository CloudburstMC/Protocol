package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.nbt.tag.Tag;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StructureTemplateDataResponseSerializer_v361 implements BedrockPacketSerializer<StructureTemplateDataResponsePacket> {
    public static final StructureTemplateDataResponseSerializer_v361 INSTANCE = new StructureTemplateDataResponseSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataResponsePacket packet) {
        helper.writeString(buffer, packet.getName());
        boolean save = packet.isSave();
        buffer.writeBoolean(save);

        if (save) {
            try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
                writer.write(packet.getTag());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StructureTemplateDataResponsePacket packet) {
        packet.setName(helper.readString(buffer));

        boolean save = buffer.readBoolean();
        packet.setSave(save);

        if (save) {
            try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
                Tag<?> tag = reader.readTag();
                if (tag instanceof CompoundTag) {
                    packet.setTag((CompoundTag) tag);
                } else {
                    throw new IllegalArgumentException("Tag received was not a CompoundTag");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
