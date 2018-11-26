package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class PlayerSkinPacket extends BedrockPacket {
    protected UUID uuid;
    protected String skinId;
    protected String newSkinName;
    protected String oldSkinName;
    protected byte[] skinData;
    protected byte[] capeData;
    protected String geometryName;
    protected String geometryData;
    protected boolean premiumSkin;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
