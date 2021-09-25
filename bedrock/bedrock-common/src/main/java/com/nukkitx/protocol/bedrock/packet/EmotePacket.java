package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.EmoteFlag;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class EmotePacket extends BedrockPacket {
    private long runtimeEntityId;
    private String emoteId;
    private final Set<EmoteFlag> flags = EnumSet.noneOf(EmoteFlag.class);

    public EmotePacket addFlag(EmoteFlag flag) {
        this.flags.add(flag);
        return this;
    }

    public EmotePacket addFlags(EmoteFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EMOTE;
    }
}
