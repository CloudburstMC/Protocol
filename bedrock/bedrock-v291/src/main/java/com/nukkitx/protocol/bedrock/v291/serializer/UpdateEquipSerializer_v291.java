package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.nbt.NBTEncodingType;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdateEquipPacket;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufInputStream;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufOutputStream;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateEquipSerializer_v291 implements PacketSerializer<UpdateEquipPacket> {
    public static final UpdateEquipSerializer_v291 INSTANCE = new UpdateEquipSerializer_v291();



    @Override
    public void serialize(ByteBuf buffer, UpdateEquipPacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeByte(packet.getWindowType());
        VarInts.writeInt(buffer, packet.getUnknown0());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        try (NBTOutputStream writer = new NBTOutputStream(new LittleEndianByteBufOutputStream(buffer), NBTEncodingType.BEDROCK)) {
            writer.write(packet.getTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdateEquipPacket packet) {
        packet.setWindowId(buffer.readUnsignedByte());
        packet.setWindowType(buffer.readUnsignedByte());
        packet.setUnknown0(VarInts.readInt(buffer));
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        try (NBTInputStream reader = new NBTInputStream(new LittleEndianByteBufInputStream(buffer), NBTEncodingType.BEDROCK)) {
            packet.setTag(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
