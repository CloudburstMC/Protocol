package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerSkinPacket extends BedrockPacket {
    private UUID uuid;
    private String skinId;
    private String newSkinName;
    private String oldSkinName;
    private byte[] skinData;
    private byte[] capeData;
    private String geometryName;
    private String geometryData;
    private boolean premiumSkin;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
