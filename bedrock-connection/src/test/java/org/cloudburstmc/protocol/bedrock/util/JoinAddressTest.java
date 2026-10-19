package org.cloudburstmc.protocol.bedrock.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinAddressTest {
    private static final int DEFAULT_PORT = 19132;

    private static void assertParsed(String input, String host, int port) {
        assertEquals(new JoinAddress(host, port), JoinAddress.parse(input, DEFAULT_PORT), input);
    }

    @Test
    public void raknetAddresses() {
        assertParsed("example.com:19133", "example.com", 19133);
        assertParsed("127.0.0.1:19133", "127.0.0.1", 19133);
        assertParsed("example.com", "example.com", DEFAULT_PORT);
        assertParsed("  Example.COM:19133 ", "Example.COM", 19133);
    }

    @Test
    public void unbracketedIpv6() {
        assertParsed("2001:db8::1:19133", "2001:db8::1", 19133);
        assertParsed("::ffff:192.0.2.1:19133", "::ffff:192.0.2.1", 19133);
        assertParsed("2001:db8::1", "2001:db8::1", DEFAULT_PORT);
        assertParsed("::1", "::1", DEFAULT_PORT);
    }

    @Test
    public void bracketedIpv6() {
        assertParsed("[2001:db8::1]:19133", "2001:db8::1", 19133);
        assertParsed("[2001:db8::1]:19133:19133", "2001:db8::1", 19133);
        assertParsed("[2001:db8::1]", "2001:db8::1", DEFAULT_PORT);
    }

    @Test
    public void netherNetSignalingUrls() {
        assertParsed("http://example.com:19133:19133", "example.com", 19133);
        assertParsed("https://example.com:443:443", "example.com", 443);
        assertParsed("http://127.0.0.1:19133:19133", "127.0.0.1", 19133);
        assertParsed("http://[2001:db8::1]:19133:19133", "2001:db8::1", 19133);
    }

    @Test
    public void netherNetWithoutRepeatedPort() {
        assertParsed("http://example.com:19133", "example.com", 19133);
        assertParsed("https://[2001:db8::1]:19133", "2001:db8::1", 19133);
        assertParsed("https://[2001:db8::1]", "2001:db8::1", 443);
        assertParsed("https://example.com", "example.com", 443);
        assertParsed("http://example.com", "example.com", 80);
        assertParsed("HTTPS://example.com", "example.com", 443);
    }

    @Test
    public void netherNetWithoutScheme() {
        assertParsed("example.com:19133:19133", "example.com", 19133);
        assertParsed("127.0.0.1:19133:19133", "127.0.0.1", 19133);
    }

    @Test
    public void differingPortsTakeTheFirst() {
        assertParsed("http://example.com:19133:19134", "example.com", 19133);
        assertParsed("example.com:19133:19134", "example.com", 19133);
    }

    @Test
    public void urlExtras() {
        assertParsed("http://example.com:19133/v1/join?x=1", "example.com", 19133);
        assertParsed("http://example.com/v1/join", "example.com", 80);
        assertParsed("https://user:pass@example.com:19133:19133", "example.com", 19133);
        assertParsed("ws://example.com", "example.com", DEFAULT_PORT);
        assertParsed("http://example.com/x@evil.com", "example.com", 80);
    }

    @Test
    public void invalidSchemeIsNotStripped() {
        assertParsed("example.com/x://evil.com", "example.com", DEFAULT_PORT);
        assertParsed("://example.com", "", DEFAULT_PORT);
    }

    @Test
    public void invalidPortsFallBack() {
        assertParsed("example.com:notaport", "example.com", DEFAULT_PORT);
        assertParsed("example.com:0", "example.com", DEFAULT_PORT);
        assertParsed("example.com:70000", "example.com", DEFAULT_PORT);
        assertParsed("example.com:+19133", "example.com", DEFAULT_PORT);
        assertParsed("example.com:", "example.com", DEFAULT_PORT);
        assertParsed("https://example.com:x:x", "example.com", 443);
    }

    @Test
    public void notAnAddress() {
        assertParsed("", "", DEFAULT_PORT);
        assertParsed("12345678901234567890", "12345678901234567890", DEFAULT_PORT);
    }
}
