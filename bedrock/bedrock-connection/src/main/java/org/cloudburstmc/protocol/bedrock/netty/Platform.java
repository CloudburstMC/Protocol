package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.channel.epoll.Native;

import java.util.Optional;

public class Platform {
    protected static final Optional<int[]> kernelversion;
    protected static final int[] reusePortVersion = new int[]{3, 9, 0};
    protected static final boolean reusePort;

    static {
        String kernelVersion;
        try {
            kernelVersion = Native.KERNEL_VERSION;
        } catch (Throwable e) {
            kernelVersion = null;
        }
        if (kernelVersion != null && kernelVersion.contains("-")) {
            int index = kernelVersion.indexOf('-');
            if (index > -1) {
                kernelVersion = kernelVersion.substring(0, index);
            }
            int[] kernelVer = fromString(kernelVersion);
            kernelversion = Optional.of(kernelVer);
            reusePort = checkVersion(kernelVer, 0);
        } else {
            kernelversion = Optional.empty();
            reusePort = false;
        }
    }

    public static boolean reusePort() {
        return reusePort;
    }

    private static int[] fromString(String ver) {
        String[] parts = ver.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("At least 2 version numbers required");
        }

        return new int[]{
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                parts.length == 2 ? 0 : Integer.parseInt(parts[2])
        };
    }

    private static boolean checkVersion(int[] ver, int i) {
        if (ver[i] > reusePortVersion[i]) {
            return true;
        }

        if (ver[i] != reusePortVersion[i]) {
            return false;
        }

        if (ver.length == (i + 1)) {
            return true;
        } else {
            return checkVersion(ver, i + 1);
        }
    }
}
