package com.nukkitx.protocol.bedrock.v409;

import com.nukkitx.protocol.bedrock.v407.BedrockPacketHelper_v407;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v409 extends BedrockPacketHelper_v407 {
    public static final BedrockPacketHelper_v409 INSTANCE = new BedrockPacketHelper_v409();

    @Override
    public boolean isBlockingItem(int id) {
        return id == 354;
    }
}
