package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.nbt.NBTEncodingType;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdateEquipPacket;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufInputStream;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufOutputStream;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class UpdateEquipPacket_v291 extends UpdateEquipPacket {


    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(windowId);
        buffer.writeByte(windowType);
        VarInts.writeInt(buffer, unknown0);
        VarInts.writeLong(buffer, uniqueEntityId);
        try (NBTOutputStream writer = new NBTOutputStream(new LittleEndianByteBufOutputStream(buffer), NBTEncodingType.BEDROCK)) {
            writer.write(tag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        windowId = buffer.readUnsignedByte();
        windowType = buffer.readUnsignedByte();
        unknown0 = VarInts.readInt(buffer);
        uniqueEntityId = VarInts.readLong(buffer);
        try (NBTInputStream reader = new NBTInputStream(new LittleEndianByteBufInputStream(buffer), NBTEncodingType.BEDROCK)) {
            tag = reader.readTag();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
