package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.nbt.NBTEncodingType;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdateTradePacket;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufInputStream;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufOutputStream;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTradeSerializer_v291 implements PacketSerializer<UpdateTradePacket> {
    public static final UpdateTradeSerializer_v291 INSTANCE = new UpdateTradeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, UpdateTradePacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeByte(packet.getWindowType());
        VarInts.writeInt(buffer, packet.getUnknown0());
        VarInts.writeInt(buffer, packet.getUnknown1());
        buffer.writeBoolean(packet.isWilling());
        VarInts.writeLong(buffer, packet.getTraderUniqueEntityId());
        VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
        BedrockUtils.writeString(buffer, packet.getDisplayName());
        try (NBTOutputStream writer = new NBTOutputStream(new LittleEndianByteBufOutputStream(buffer), NBTEncodingType.BEDROCK)) {
            writer.write(packet.getOffers());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdateTradePacket packet) {
        packet.setWindowId(buffer.readUnsignedByte());
        packet.setWindowType(buffer.readUnsignedByte());
        packet.setUnknown0(VarInts.readInt(buffer));
        packet.setUnknown1(VarInts.readInt(buffer));
        packet.setWilling(buffer.readBoolean());
        packet.setTraderUniqueEntityId(VarInts.readLong(buffer));
        packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
        packet.setDisplayName(BedrockUtils.readString(buffer));
        try (NBTInputStream reader = new NBTInputStream(new LittleEndianByteBufInputStream(buffer), NBTEncodingType.BEDROCK)) {
            packet.setOffers(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
