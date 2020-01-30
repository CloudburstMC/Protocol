package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.SerializedSkin;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerSkinPacket extends BedrockPacket {
    private UUID uuid;
    private SerializedSkin skin;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_SKIN;
    }
}
