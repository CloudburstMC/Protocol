package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class BossEventPacket_v291 extends BossEventPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, bossUniqueEntityId);
        VarInts.writeUnsignedInt(buffer, type.ordinal());

        switch (type) {
            case REGISTER_PLAYER:
            case UNREGISTER_PLAYER:
                VarInts.writeLong(buffer, playerUniqueEntityId);
                break;
            case SHOW:
                BedrockUtils.writeString(buffer, title);
                buffer.writeFloatLE(healthPercentage);
                // fall through
            case UNKNOWN:
                buffer.writeShortLE(unknown0);
                // fall through
            case TEXTURE:
                VarInts.writeUnsignedInt(buffer, color);
                VarInts.writeUnsignedInt(buffer, overlay);
                break;
            case HEALTH_PERCENTAGE:
                buffer.writeFloatLE(healthPercentage);
                break;
            case TITLE:
                BedrockUtils.writeString(buffer, title);
                break;
            default:
                throw new RuntimeException("BossEvent transactionType was unknown!");
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        bossUniqueEntityId = VarInts.readInt(buffer);
        type = Type.values()[VarInts.readUnsignedInt(buffer)];

        switch (type) {
            case REGISTER_PLAYER:
            case UNREGISTER_PLAYER:
                playerUniqueEntityId = VarInts.readLong(buffer);
                break;
            case SHOW:
                title = BedrockUtils.readString(buffer);
                healthPercentage = buffer.readFloatLE();
                // fall through
            case UNKNOWN:
                unknown0 = buffer.readShortLE();
                // fall through
            case TEXTURE:
                color = VarInts.readUnsignedInt(buffer);
                overlay = VarInts.readUnsignedInt(buffer);
                break;
            case HEALTH_PERCENTAGE:
                healthPercentage = buffer.readFloatLE();
                break;
            case TITLE:
                title = BedrockUtils.readString(buffer);
                break;
        }
    }
}
