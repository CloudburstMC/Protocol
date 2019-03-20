package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.BossEventPacket.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BossEventSerializer_v332 implements PacketSerializer<BossEventPacket> {
    public static final BossEventSerializer_v332 INSTANCE = new BossEventSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, BossEventPacket packet) {
        VarInts.writeLong(buffer, packet.getBossUniqueEntityId());
        VarInts.writeUnsignedInt(buffer, packet.getType().ordinal());

        switch (packet.getType()) {
            case REGISTER_PLAYER:
            case UNREGISTER_PLAYER:
                VarInts.writeLong(buffer, packet.getPlayerUniqueEntityId());
                break;
            case SHOW:
                BedrockUtils.writeString(buffer, packet.getTitle());
                buffer.writeFloatLE(packet.getHealthPercentage());
                // fall through
            case DARKEN_SKY:
                buffer.writeShortLE(packet.getDarkenSky());
                // fall through
            case OVERLAY:
                VarInts.writeUnsignedInt(buffer, packet.getColor());
                VarInts.writeUnsignedInt(buffer, packet.getOverlay());
                break;
            case HEALTH_PERCENTAGE:
                buffer.writeFloatLE(packet.getHealthPercentage());
                break;
            case TITLE:
                BedrockUtils.writeString(buffer, packet.getTitle());
                break;
            default:
                throw new RuntimeException("BossEvent transactionType was unknown!");
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BossEventPacket packet) {
        packet.setBossUniqueEntityId(VarInts.readInt(buffer));
        Type type = Type.values()[VarInts.readUnsignedInt(buffer)];
        packet.setType(type);

        switch (type) {
            case REGISTER_PLAYER:
            case UNREGISTER_PLAYER:
                packet.setPlayerUniqueEntityId(VarInts.readLong(buffer));
                break;
            case SHOW:
                packet.setTitle(BedrockUtils.readString(buffer));
                packet.setHealthPercentage(buffer.readFloatLE());
                // fall through
            case DARKEN_SKY:
                packet.setDarkenSky(buffer.readUnsignedShortLE());
                // fall through
            case OVERLAY:
                packet.setColor(VarInts.readUnsignedInt(buffer));
                packet.setOverlay(VarInts.readUnsignedInt(buffer));
                break;
            case HEALTH_PERCENTAGE:
                packet.setHealthPercentage(buffer.readFloatLE());
                break;
            case TITLE:
                packet.setTitle(BedrockUtils.readString(buffer));
                break;
        }
    }
}
