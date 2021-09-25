package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class EmoteListPacket extends BedrockPacket {
    private long runtimeEntityId;
    private final List<UUID> pieceIds = new ObjectArrayList<>();

    public EmoteListPacket addPieceId(UUID pieceId) {
        this.pieceIds.add(pieceId);
        return this;
    }

    public EmoteListPacket addPieceIds(UUID... pieceIds) {
        this.pieceIds.addAll(Arrays.asList(pieceIds));
        return this;
    }

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EMOTE_LIST;
    }
}
