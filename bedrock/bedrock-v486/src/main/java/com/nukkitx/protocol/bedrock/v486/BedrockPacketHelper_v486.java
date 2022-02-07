package com.nukkitx.protocol.bedrock.v486;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.v475.BedrockPacketHelper_v475;

import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.*;
import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.CRAFT_RESULTS_DEPRECATED;

public class BedrockPacketHelper_v486 extends BedrockPacketHelper_v475 {
    public static final BedrockPacketHelper_v486 INSTANCE = new BedrockPacketHelper_v486();

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();
        this.addEntityFlag(100, EntityFlag.CROAKING);
        this.addEntityFlag(101, EntityFlag.EAT_MOB);
    }

    @Override
    protected void registerStackActionRequestTypes() {
        this.stackRequestActionTypes.put(0, TAKE);
        this.stackRequestActionTypes.put(1, PLACE);
        this.stackRequestActionTypes.put(2, SWAP);
        this.stackRequestActionTypes.put(3, DROP);
        this.stackRequestActionTypes.put(4, DESTROY);
        this.stackRequestActionTypes.put(5, CONSUME);
        this.stackRequestActionTypes.put(6, CREATE);
        this.stackRequestActionTypes.put(7, PLACE_IN_ITEM_CONTAINER); // since v486
        this.stackRequestActionTypes.put(8, TAKE_FROM_ITEM_CONTAINER); // since v486
        this.stackRequestActionTypes.put(9, LAB_TABLE_COMBINE);
        this.stackRequestActionTypes.put(10, BEACON_PAYMENT);
        this.stackRequestActionTypes.put(11, MINE_BLOCK);
        this.stackRequestActionTypes.put(12, CRAFT_RECIPE);
        this.stackRequestActionTypes.put(13, CRAFT_RECIPE_AUTO);
        this.stackRequestActionTypes.put(14, CRAFT_CREATIVE);
        this.stackRequestActionTypes.put(15, CRAFT_RECIPE_OPTIONAL);
        this.stackRequestActionTypes.put(16, CRAFT_REPAIR_AND_DISENCHANT);
        this.stackRequestActionTypes.put(17, CRAFT_LOOM);
        this.stackRequestActionTypes.put(18, CRAFT_NON_IMPLEMENTED_DEPRECATED);
        this.stackRequestActionTypes.put(19, CRAFT_RESULTS_DEPRECATED);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();
        this.addSoundEvent(372, SoundEvent.TONGUE);
        this.addSoundEvent(373, SoundEvent.CRACK_IRON_GOLEM);
        this.addSoundEvent(374, SoundEvent.REPAIR_IRON_GOLEM);
        this.addSoundEvent(375, SoundEvent.UNDEFINED);
    }
}
