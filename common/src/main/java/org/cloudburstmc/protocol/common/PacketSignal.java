package org.cloudburstmc.protocol.common;

@SuppressWarnings("InstantiationOfUtilityClass")
public class PacketSignal {

    public static final PacketSignal HANDLED = new PacketSignal();
    public static final PacketSignal UNHANDLED = new PacketSignal();

    public PacketSignal() {
    }
}
