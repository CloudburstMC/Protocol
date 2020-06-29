package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BossEventSerializer_v291 implements BedrockPacketSerializer<BossEventPacket> {
    public static final BossEventSerializer_v291 INSTANCE = new BossEventSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, BossEventPacket packet) {
        VarInts.writeLong(buffer, packet.getBossUniqueEntityId());
        VarInts.writeUnsignedInt(buffer, packet.getAction().ordinal());

        switch (packet.getAction()) {
            case REGISTER_PLAYER:
            case UNREGISTER_PLAYER:
                VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
                break;
            case CREATE:
                helper.writeString(buffer, packet.getTitle());
                buffer.writeFloatLE(packet.getHealthPercentage());
                // fall through
            case UPDATE_PROPERTIES:
                buffer.writeShortLE(packet.getDarkenSky());
                // fall through
            case UPDATE_STYLE:
                VarInts.writeUnsignedInt(buffer, packet.getColor());
                VarInts.writeUnsignedInt(buffer, packet.getOverlay());
                break;
            case UPDATE_PERCENTAGE:
                buffer.writeFloatLE(packet.getHealthPercentage());
                break;
            case UPDATE_NAME:
                helper.writeString(buffer, packet.getTitle());
                break;
            default:
                throw new RuntimeException("BossEvent transactionType was unknown!");
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, BossEventPacket packet) {
        packet.setBossUniqueEntityId(VarInts.readInt(buffer));
        BossEventPacket.Action action = BossEventPacket.Action.values()[VarInts.readUnsignedInt(buffer)];
        packet.setAction(action);

        switch (action) {
            case REGISTER_PLAYER:
            case UNREGISTER_PLAYER:
                packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
                break;
            case CREATE:
                packet.setTitle(helper.readString(buffer));
                packet.setHealthPercentage(buffer.readFloatLE());
                // fall through
            case UPDATE_PROPERTIES:
                packet.setDarkenSky(buffer.readUnsignedShortLE());
                // fall through
            case UPDATE_STYLE:
                packet.setColor(VarInts.readUnsignedInt(buffer));
                packet.setOverlay(VarInts.readUnsignedInt(buffer));
                break;
            case UPDATE_PERCENTAGE:
                packet.setHealthPercentage(buffer.readFloatLE());
                break;
            case UPDATE_NAME:
                packet.setTitle(helper.readString(buffer));
                break;
        }
    }
}
