package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.SetScorePacket.Action;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetScoreSerializer_v291 implements BedrockPacketSerializer<SetScorePacket> {
    public static final SetScoreSerializer_v291 INSTANCE = new SetScoreSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetScorePacket packet) {
        Action action = packet.getAction();
        buffer.writeByte(action.ordinal());

        helper.writeArray(buffer, packet.getInfos(), (buf, scoreInfo) -> {
            VarInts.writeLong(buf, scoreInfo.getScoreboardId());
            helper.writeString(buf, scoreInfo.getObjectiveId());
            buf.writeIntLE(scoreInfo.getScore());
            if (action == Action.SET) {
                buf.writeByte(scoreInfo.getType().ordinal());
                switch (scoreInfo.getType()) {
                    case ENTITY:
                    case PLAYER:
                        VarInts.writeLong(buf, scoreInfo.getEntityId());
                        break;
                    case FAKE:
                        helper.writeString(buf, scoreInfo.getName());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid score info received");
                }
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetScorePacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);

        helper.readArray(buffer, packet.getInfos(), buf -> {
            long scoreboardId = VarInts.readLong(buf);
            String objectiveId = helper.readString(buf);
            int score = buf.readIntLE();
            if (action == Action.SET) {
                ScoreInfo.ScorerType type = ScoreInfo.ScorerType.values()[buf.readUnsignedByte()];
                switch (type) {
                    case ENTITY:
                    case PLAYER:
                        long entityId = VarInts.readLong(buf);
                        return new ScoreInfo(scoreboardId, objectiveId, score, type, entityId);
                    case FAKE:
                        String name = helper.readString(buf);
                        return new ScoreInfo(scoreboardId, objectiveId, score, name);
                    default:
                        throw new IllegalArgumentException("Invalid score info received");
                }
            } else {
                return new ScoreInfo(scoreboardId, objectiveId, score);
            }
        });
    }

}
