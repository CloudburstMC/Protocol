package com.nukkitx.protocol.bedrock.v363;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.v361.BedrockPacketHelper_v361;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v363 extends BedrockPacketHelper_v361 {
    public static final BedrockPacketHelper INSTANCE = new BedrockPacketHelper_v363();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(107, EntityData.AMBIENT_SOUND_INTERVAL);
        this.addEntityData(108, EntityData.AMBIENT_SOUND_INTERVAL_RANGE);
        this.addEntityData(109, EntityData.AMBIENT_SOUND_EVENT_NAME);
    }

}
