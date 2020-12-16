package com.nukkitx.protocol.bedrock.v422;

import com.nukkitx.protocol.bedrock.v419.BedrockPacketHelper_v419;

import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.*;
import static com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType.CRAFT_RESULTS_DEPRECATED;

public class BedrockPacketHelper_v422 extends BedrockPacketHelper_v419 {
    public static final BedrockPacketHelper_v422 INSTANCE = new BedrockPacketHelper_v422();

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
        this.stackRequestActionTypes.put(9, CRAFT_RECIPE);
        this.stackRequestActionTypes.put(10, CRAFT_RECIPE_AUTO);
        this.stackRequestActionTypes.put(11, CRAFT_CREATIVE);
        this.stackRequestActionTypes.put(12, CRAFT_RECIPE_OPTIONAL);
        this.stackRequestActionTypes.put(13, CRAFT_NON_IMPLEMENTED_DEPRECATED);
        this.stackRequestActionTypes.put(14, CRAFT_RESULTS_DEPRECATED);
    }
}
