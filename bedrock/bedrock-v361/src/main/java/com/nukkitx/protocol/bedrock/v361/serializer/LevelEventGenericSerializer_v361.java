package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.LevelEventGenericPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelEventGenericSerializer_v361 implements BedrockPacketSerializer<LevelEventGenericPacket> {
    public static final LevelEventGenericSerializer_v361 INSTANCE = new LevelEventGenericSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventGenericPacket packet) {
        VarInts.writeInt(buffer, packet.getEventId());
        try (NBTOutputStream writer = NbtUtils.createNetworkWriter(new ByteBufOutputStream(buffer))) {
            writer.write(packet.getTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelEventGenericPacket packet) {
        packet.setEventId(VarInts.readInt(buffer));
        try (NBTInputStream reader = NbtUtils.createNetworkReader(new ByteBufInputStream(buffer))) {
            packet.setTag(reader.readTag());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
