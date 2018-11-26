package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class PlayerSkinPacket_v291 extends PlayerSkinPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeUuid(buffer, uuid);
        BedrockUtils.writeString(buffer, skinId);
        BedrockUtils.writeString(buffer, newSkinName);
        BedrockUtils.writeString(buffer, oldSkinName);
        BedrockUtils.writeByteArray(buffer, skinData);
        BedrockUtils.writeByteArray(buffer, capeData);
        BedrockUtils.writeString(buffer, geometryName);
        BedrockUtils.writeString(buffer, geometryData);
        buffer.writeBoolean(premiumSkin);
    }

    @Override
    public void decode(ByteBuf buffer) {
        BedrockUtils.writeUuid(buffer, uuid);
        BedrockUtils.writeString(buffer, skinId);
        BedrockUtils.writeString(buffer, newSkinName);
        BedrockUtils.writeString(buffer, oldSkinName);
        BedrockUtils.writeByteArray(buffer, skinData);
        BedrockUtils.writeByteArray(buffer, capeData);
        BedrockUtils.writeString(buffer, geometryName);
        BedrockUtils.writeString(buffer, geometryData);
        premiumSkin = buffer.readBoolean();
    }
}
