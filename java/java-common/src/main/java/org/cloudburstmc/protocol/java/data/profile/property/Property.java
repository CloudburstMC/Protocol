package org.cloudburstmc.protocol.java.data.profile.property;

import lombok.Value;

@Value
public class Property {
    String name;
    String value;
    String signature;
}
