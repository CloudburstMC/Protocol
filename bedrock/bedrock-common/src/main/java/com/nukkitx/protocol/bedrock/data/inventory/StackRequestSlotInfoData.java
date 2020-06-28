package com.nukkitx.protocol.bedrock.data.inventory;

/**
 * Holds information on a specific slot client-side.
 */
public class StackRequestSlotInfoData {
    // ContainerId is the ID of the container the slot was in
    byte ContainerId;

    // slot is the index of the slot within the container
    byte slot;

    // stackNetworkId is the unique stack ID that the client assumes to be present in this slot. The server
    // must check if these IDs match. If they do not match, servers should reject the stack request that the
    // action holding this info was in.
    int stackNetworkId;
}
