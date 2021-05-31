package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.BossEventPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BossEventSerializer_v291 implements BedrockPacketSerializer<BossEventPacket> {
    public static final BossEventSerializer_v291 INSTANCE = new BossEventSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, BossEventPacket packet) {
        VarInts.writeLong(buffer, packet.getBossUniqueEntityId());
        VarInts.writeUnsignedInt(buffer, packet.getAction().ordinal());

        switch (packet.getAction()) {
            case Action.REGISTER_PLAYER:
            case Action.UNREGISTER_PLAYER:
                VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
                break;
            case Action.CREATE:
                helper.writeString(buffer, packet.getTitle());
                buffer.writeFloatLE(packet.getHealthPercentage());
                // fall through
            case Action.UPDATE_PROPERTIES:
                buffer.writeShortLE(packet.getDarkenSky());
                // fall through
            case Action.UPDATE_STYLE:
                VarInts.writeUnsignedInt(buffer, packet.getColor());
                VarInts.writeUnsignedInt(buffer, packet.getOverlay());
                break;
            case Action.UPDATE_PERCENTAGE:
                buffer.writeFloatLE(packet.getHealthPercentage());
                break;
            case Action.UPDATE_NAME:
                helper.writeString(buffer, packet.getTitle());
                break;
            case Action.REMOVE:
                break;
            default:
                throw new RuntimeException("BossEvent transactionType was unknown!");
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, BossEventPacket packet) {
        packet.setBossUniqueEntityId(VarInts.readInt(buffer));
        BossEventPacket.Action action = BossEventPacket.Action.values()[VarInts.readUnsignedInt(buffer)];
        packet.setAction(action);

        switch (action) {
            case Action.REGISTER_PLAYER:
            case Action.UNREGISTER_PLAYER:
                packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
                break;
            case Action.CREATE:
                packet.setTitle(helper.readString(buffer));
                packet.setHealthPercentage(buffer.readFloatLE());
                // fall through
            case Action.UPDATE_PROPERTIES:
                packet.setDarkenSky(buffer.readUnsignedShortLE());
                // fall through
            case Action.UPDATE_STYLE:
                packet.setColor(VarInts.readUnsignedInt(buffer));
                packet.setOverlay(VarInts.readUnsignedInt(buffer));
                break;
            case Action.UPDATE_PERCENTAGE:
                packet.setHealthPercentage(buffer.readFloatLE());
                break;
            case Action.UPDATE_NAME:
                packet.setTitle(helper.readString(buffer));
                break;
        }
    }
}
