package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

/**
 * LabTableCombineStackRequestActionData is sent by the client when it uses a lab table to combine item stacks.
 */
@Value
public class LabTableCombineRequestActionData implements StackRequestActionData {

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.LAB_TABLE_COMBINE;
    }
}
