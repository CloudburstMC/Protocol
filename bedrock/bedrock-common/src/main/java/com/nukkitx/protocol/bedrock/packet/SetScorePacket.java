package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SetScorePacket extends BedrockPacket {
    private Action action;
    private List<ScoreInfo> infos = new ObjectArrayList<>();

    public SetScorePacket addInfo(ScoreInfo info) {
        this.infos.add(info);
        return this;
    }

    public SetScorePacket addInfos(ScoreInfo... infos) {
        this.infos.addAll(Arrays.asList(infos));
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_SCORE;
    }

    public enum Action {
        SET,
        REMOVE
    }
}
