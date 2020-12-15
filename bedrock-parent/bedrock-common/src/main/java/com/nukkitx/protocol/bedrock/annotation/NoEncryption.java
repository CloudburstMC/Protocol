package com.nukkitx.protocol.bedrock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Packets with this annotation will be sent in plain text over the network
 *
 * @see com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NoEncryption {
}
