package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket.Action;
import static com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket.Entry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetScoreboardIdentitySerializer_v363 implements PacketSerializer<SetScoreboardIdentityPacket> {
    public static final SetScoreboardIdentitySerializer_v363 INSTANCE = new SetScoreboardIdentitySerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, SetScoreboardIdentityPacket packet) {
        SetScoreboardIdentityPacket.Action action = packet.getAction();
        buffer.writeByte(action.ordinal());
        BedrockUtils.writeArray(buffer, packet.getEntries(), (buf, entry) -> {
            VarInts.writeLong(buffer, entry.getScoreboardId());
            if (action == SetScoreboardIdentityPacket.Action.ADD) {
                BedrockUtils.writeUuid(buffer, entry.getUuid());
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, SetScoreboardIdentityPacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        BedrockUtils.readArray(buffer, packet.getEntries(), buf -> {
            long scoreboardId = VarInts.readLong(buffer);
            UUID uuid = null;
            if (action == Action.ADD) {
                uuid = BedrockUtils.readUuid(buffer);
            }
            return new Entry(scoreboardId, uuid);
        });
    }
}
