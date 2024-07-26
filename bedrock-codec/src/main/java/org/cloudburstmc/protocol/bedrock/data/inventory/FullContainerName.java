package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Value;

@Value
public class FullContainerName {

   private final ContainerSlotType container;
   private final int dynamicId;
}