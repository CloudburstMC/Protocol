package org.cloudburstmc.protocol.common;

@SuppressWarnings("InstantiationOfUtilityClass")
public final class PacketSignal {

    public static final PacketSignal HANDLED = new PacketSignal();
    public static final PacketSignal UNHANDLED = new PacketSignal();

    public PacketSignal() {
    }
}
