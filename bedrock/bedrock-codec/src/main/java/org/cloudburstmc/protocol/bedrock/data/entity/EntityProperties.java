package org.cloudburstmc.protocol.bedrock.data.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Value;

import java.util.List;

@Value
public class EntityProperties {
    List<IntEntityProperty> intProperties = new ObjectArrayList<>();
    List<FloatEntityProperty> floatProperties = new ObjectArrayList<>();
}
