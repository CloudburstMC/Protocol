package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SwapStackRequestActionData is sent by the client to swap the item in its cursor with an item present in another
 * container. The two item stacks swap places.
 */
@Getter
@AllArgsConstructor
public class SwapStackRequestActionData extends StackRequestActionData {
    StackRequestSlotInfoData source;
    StackRequestSlotInfoData destination;
}
