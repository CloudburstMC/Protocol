package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.nbt.NBTEncodingType;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdateTradePacket;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufInputStream;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufOutputStream;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class UpdateTradePacket_v291 extends UpdateTradePacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(windowId);
        buffer.writeByte(windowType);
        VarInts.writeInt(buffer, unknown0);
        VarInts.writeInt(buffer, unknown1);
        buffer.writeBoolean(willing);
        VarInts.writeLong(buffer, traderUniqueEntityId);
        VarInts.writeLong(buffer, playerUniqueEntityId);
        BedrockUtils.writeString(buffer, displayName);
        try (NBTOutputStream writer = new NBTOutputStream(new LittleEndianByteBufOutputStream(buffer), NBTEncodingType.BEDROCK)) {
            writer.write(offers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        windowId = buffer.readUnsignedByte();
        windowType = buffer.readUnsignedByte();
        unknown0 = VarInts.readInt(buffer);
        unknown1 = VarInts.readInt(buffer);
        willing = buffer.readBoolean();
        traderUniqueEntityId = VarInts.readLong(buffer);
        playerUniqueEntityId = VarInts.readLong(buffer);
        displayName = BedrockUtils.readString(buffer);
        try (NBTInputStream reader = new NBTInputStream(new LittleEndianByteBufInputStream(buffer), NBTEncodingType.BEDROCK)) {
            offers = reader.readTag();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
