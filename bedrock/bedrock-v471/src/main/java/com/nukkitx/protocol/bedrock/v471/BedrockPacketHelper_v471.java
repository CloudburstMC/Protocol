package com.nukkitx.protocol.bedrock.v471;

import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.data.skin.AnimationData;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.data.skin.PersonaPieceData;
import com.nukkitx.protocol.bedrock.data.skin.PersonaPieceTintData;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.v448.BedrockPacketHelper_v448;
import com.nukkitx.protocol.bedrock.v465.BedrockPacketHelper_v465;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.*;
import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED;
import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.CRAFT_RECIPE_OPTIONAL;
import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.CRAFT_RESULTS_DEPRECATED;
import static java.util.Objects.requireNonNull;

public class BedrockPacketHelper_v471 extends BedrockPacketHelper_v465 {
    public static final BedrockPacketHelper_v471 INSTANCE = new BedrockPacketHelper_v471();

    @Override
    protected void registerEntityEvents() {
        super.registerEntityEvents();
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(2036, LevelEventType.SCULK_CATALYST_BLOOM);

        int legacy = 0x4000;
        this.addLevelEvent(83 + legacy, LevelEventType.PARTICLE_SCULK_SOUL);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(365, SoundEvent.SCULK_CATALYST_BLOOM);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void registerStackActionRequestTypes() {
        this.stackRequestActionTypes.put(0, TAKE);
        this.stackRequestActionTypes.put(1, PLACE);
        this.stackRequestActionTypes.put(2, SWAP);
        this.stackRequestActionTypes.put(3, DROP);
        this.stackRequestActionTypes.put(4, DESTROY);
        this.stackRequestActionTypes.put(5, CONSUME);
        this.stackRequestActionTypes.put(6, CREATE);
        this.stackRequestActionTypes.put(7, LAB_TABLE_COMBINE);
        this.stackRequestActionTypes.put(8, BEACON_PAYMENT);
        this.stackRequestActionTypes.put(9, MINE_BLOCK);
        this.stackRequestActionTypes.put(10, CRAFT_RECIPE);
        this.stackRequestActionTypes.put(11, CRAFT_RECIPE_AUTO);
        this.stackRequestActionTypes.put(12, CRAFT_CREATIVE);
        this.stackRequestActionTypes.put(13, CRAFT_RECIPE_OPTIONAL);
        this.stackRequestActionTypes.put(14, CRAFT_REPAIR_AND_DISENCHANT); // new for v471
        this.stackRequestActionTypes.put(15, CRAFT_LOOM); // new for v471
        this.stackRequestActionTypes.put(16, CRAFT_NON_IMPLEMENTED_DEPRECATED);
        this.stackRequestActionTypes.put(17, CRAFT_RESULTS_DEPRECATED);
    }
}
