package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ItemDescriptorWithCount {

    public static final ItemDescriptorWithCount EMPTY = new ItemDescriptorWithCount(InvalidDescriptor.INSTANCE, 0);

    private final ItemDescriptor descriptor;
    private final int count;

    public ItemData toItem() {
        if (descriptor == InvalidDescriptor.INSTANCE) {
            return ItemData.AIR;
        }
        return descriptor.toItem()
                .count(count)
                .build();
    }

    public static ItemDescriptorWithCount fromItem(ItemData item) {
        if (item == ItemData.AIR) {
            return EMPTY;
        }
        return new ItemDescriptorWithCount(new DefaultDescriptor(item.getDefinition(), item.getDamage()), item.getCount());
    }
}