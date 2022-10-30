package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.UUID;

import static org.cloudburstmc.protocol.bedrock.packet.SetScoreboardIdentityPacket.Action;
import static org.cloudburstmc.protocol.bedrock.packet.SetScoreboardIdentityPacket.Entry;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetScoreboardIdentitySerializer_v291 implements BedrockPacketSerializer<SetScoreboardIdentityPacket> {
    public static final SetScoreboardIdentitySerializer_v291 INSTANCE = new SetScoreboardIdentitySerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetScoreboardIdentityPacket packet) {
        SetScoreboardIdentityPacket.Action action = packet.getAction();
        buffer.writeByte(action.ordinal());
        helper.writeArray(buffer, packet.getEntries(), (buf, entry) -> {
            VarInts.writeLong(buffer, entry.getScoreboardId());
            if (action == Action.ADD) {
                helper.writeUuid(buffer, entry.getUuid());
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetScoreboardIdentityPacket packet) {
        SetScoreboardIdentityPacket.Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        helper.readArray(buffer, packet.getEntries(), buf -> {
            long scoreboardId = VarInts.readLong(buffer);
            UUID uuid = null;
            if (action == Action.ADD) {
                uuid = helper.readUuid(buffer);
            }
            return new Entry(scoreboardId, uuid);
        });
    }
}
