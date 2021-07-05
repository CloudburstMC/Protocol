package org.cloudburstmc.protocol.java.packet;

import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DirectionAvailability {
    JavaPacketType.Direction value();
}
