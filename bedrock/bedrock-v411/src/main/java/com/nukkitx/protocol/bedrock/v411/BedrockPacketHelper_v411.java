package com.nukkitx.protocol.bedrock.v411;

import com.nukkitx.protocol.bedrock.v409.BedrockPacketHelper_v409;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v411 extends BedrockPacketHelper_v409 {
    public static final BedrockPacketHelper_v411 INSTANCE = new BedrockPacketHelper_v411();

    @Override
    public boolean isBlockingItem(int id) {
        return id == 513;
    }

}
