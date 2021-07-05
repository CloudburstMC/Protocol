package org.cloudburstmc.protocol.java.data;

import lombok.Value;
import net.kyori.adventure.key.Key;

import java.util.List;

@Value
public class Advancement {
    Key parentId;
    Key id;
    DisplayInfo displayInfo;
    List<Key> criteria;
    List<Key[]> requirements;
}
