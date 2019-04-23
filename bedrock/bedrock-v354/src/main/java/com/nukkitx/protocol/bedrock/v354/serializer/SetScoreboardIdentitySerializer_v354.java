package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket.Entry;
import static com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetScoreboardIdentitySerializer_v354 implements PacketSerializer<SetScoreboardIdentityPacket> {
    public static final SetScoreboardIdentitySerializer_v354 INSTANCE = new SetScoreboardIdentitySerializer_v354();


    @Override
    public void serialize(ByteBuf buffer, SetScoreboardIdentityPacket packet) {
        Type type = packet.getType();
        buffer.writeByte(type.ordinal());
        BedrockUtils.writeArray(buffer, packet.getEntries(), (buf, entry) -> {
            VarInts.writeLong(buffer, entry.getScoreboardId());
            if (type == Type.ADD) {
                BedrockUtils.writeUuid(buffer, entry.getUuid());
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, SetScoreboardIdentityPacket packet) {
        Type type = Type.values()[buffer.readUnsignedByte()];
        packet.setType(type);
        BedrockUtils.readArray(buffer, packet.getEntries(), buf -> {
            long scoreboardId = VarInts.readLong(buffer);
            UUID uuid = null;
            if (type == Type.ADD) {
                uuid = BedrockUtils.readUuid(buffer);
            }
            return new Entry(scoreboardId, uuid);
        });
    }
}
