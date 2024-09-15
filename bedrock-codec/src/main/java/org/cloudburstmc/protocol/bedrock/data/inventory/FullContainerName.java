package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Value;

@Value
public class FullContainerName {
   private final ContainerSlotType container;
   /**
    * May be null if not present since v729
    */
   private final Integer dynamicId;
}