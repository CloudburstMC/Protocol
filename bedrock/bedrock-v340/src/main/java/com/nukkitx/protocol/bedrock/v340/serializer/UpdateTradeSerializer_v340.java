package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.UpdateTradePacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTradeSerializer_v340 implements PacketSerializer<UpdateTradePacket> {
    public static final UpdateTradeSerializer_v340 INSTANCE = new UpdateTradeSerializer_v340();


    @Override
    public void serialize(ByteBuf buffer, UpdateTradePacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeByte(packet.getWindowType());
        VarInts.writeInt(buffer, packet.getUnknownInt());
        VarInts.writeInt(buffer, packet.isScreen2() ? 40 : 0);
        VarInts.writeInt(buffer, packet.getTradeTier());
        buffer.writeBoolean(packet.isWilling());
        VarInts.writeLong(buffer, packet.getTraderUniqueEntityId());
        VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
        BedrockUtils.writeString(buffer, packet.getDisplayName());
        try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
            writer.write(packet.getOffers());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, UpdateTradePacket packet) {
        packet.setWindowId(buffer.readUnsignedByte());
        packet.setWindowType(buffer.readUnsignedByte());
        packet.setUnknownInt(VarInts.readInt(buffer));
        packet.setScreen2(VarInts.readInt(buffer) >= 40);
        packet.setTradeTier(VarInts.readInt(buffer));
        packet.setWilling(buffer.readBoolean());
        packet.setTraderUniqueEntityId(VarInts.readLong(buffer));
        packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
        packet.setDisplayName(BedrockUtils.readString(buffer));
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            packet.setOffers(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
