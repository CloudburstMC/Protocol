package org.cloudburstmc.protocol.bedrock.util;

import io.netty.util.NetUtil;
import lombok.Value;

import java.net.InetSocketAddress;
import java.util.Locale;

/**
 * The host and port a client used to reach the server, as sent in the {@code ServerAddress} field of its login
 * client data.
 * <p>
 * RakNet clients send {@code host:port}, with IPv6 unbracketed. NetherNet clients send the signaling URL with the
 * port repeated, such as {@code http://example.com:19132:19132}. Each shape is read with or without the scheme and
 * the repeated port, so an HTTP {@code Host} header reads too.
 */
@Value
public class JoinAddress {
    /**
     * The hostname or IP address as the client sent it, without IPv6 brackets.
     */
    String host;
    /**
     * The port, or the default when the address names none or an invalid one.
     */
    int port;

    /**
     * Never fails: text that is no address comes back as the host.
     *
     * @param address     the address to read
     * @param defaultPort the port when the address has none, unless an {@code http} or {@code https} scheme implies one
     * @return the host and port
     */
    public static JoinAddress parse(String address, int defaultPort) {
        String remaining = address.trim();

        int schemeEnd = remaining.indexOf("://");
        if (isScheme(remaining, schemeEnd)) {
            String scheme = remaining.substring(0, schemeEnd).toLowerCase(Locale.ROOT);
            if (scheme.equals("https")) {
                defaultPort = 443;
            } else if (scheme.equals("http")) {
                defaultPort = 80;
            }
            remaining = remaining.substring(schemeEnd + 3);
        }

        // Drop any path, query or fragment, then any user info
        for (int i = 0; i < remaining.length(); i++) {
            char c = remaining.charAt(i);
            if (c == '/' || c == '?' || c == '#') {
                remaining = remaining.substring(0, i);
                break;
            }
        }
        remaining = remaining.substring(remaining.lastIndexOf('@') + 1);

        if (remaining.startsWith("[")) {
            int end = remaining.indexOf(']');
            if (end != -1) {
                return new JoinAddress(remaining.substring(1, end), firstPort(remaining.substring(end + 1), defaultPort));
            }
        }

        int first = remaining.indexOf(':');
        if (first == -1) {
            return new JoinAddress(remaining, defaultPort);
        }
        int last = remaining.lastIndexOf(':');
        if (first != last) {
            // Unbracketed IPv6 from RakNet clients, where the port follows the last colon
            String beforeLast = remaining.substring(0, last);
            if (NetUtil.isValidIpV6Address(beforeLast)) {
                return new JoinAddress(beforeLast, parsePort(remaining.substring(last + 1), defaultPort));
            }
            if (NetUtil.isValidIpV6Address(remaining)) {
                return new JoinAddress(remaining, defaultPort);
            }
        }
        return new JoinAddress(remaining.substring(0, first), firstPort(remaining.substring(first), defaultPort));
    }

    public InetSocketAddress toUnresolved() {
        return InetSocketAddress.createUnresolved(host, port);
    }

    /**
     * Whether the text before {@code end} is an RFC 3986 scheme.
     */
    private static boolean isScheme(String value, int end) {
        if (end < 1 || !isAsciiLetter(value.charAt(0))) {
            return false;
        }
        for (int i = 1; i < end; i++) {
            char c = value.charAt(i);
            if (!isAsciiLetter(c) && (c < '0' || c > '9') && c != '+' && c != '-' && c != '.') {
                return false;
            }
        }
        return true;
    }

    private static boolean isAsciiLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * Reads {@code :port}, or the first of {@code :port:port}, as the two can only differ if the client changes.
     */
    private static int firstPort(String ports, int defaultPort) {
        if (!ports.startsWith(":")) {
            return defaultPort;
        }
        int end = ports.indexOf(':', 1);
        return parsePort(end == -1 ? ports.substring(1) : ports.substring(1, end), defaultPort);
    }

    private static int parsePort(String port, int defaultPort) {
        if (port.isEmpty() || port.length() > 5) {
            return defaultPort;
        }
        for (int i = 0; i < port.length(); i++) {
            if (port.charAt(i) < '0' || port.charAt(i) > '9') {
                return defaultPort;
            }
        }
        int parsed = Integer.parseInt(port);
        return parsed > 0 && parsed <= 65535 ? parsed : defaultPort;
    }
}
