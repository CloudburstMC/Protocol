package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetPong;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

@Data
public class BedrockPong {
    private String edition;
    private String motd;
    private int protocolVersion = -1;
    private String version;
    private int playerCount = -1;
    private int maximumPlayerCount = -1;
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private long serverId;
    private String subMotd;
    private String gameType;
    private boolean nintendoLimited;
    private int ipv4Port = -1;
    private int ipv6Port = -1;
    private String[] extras;

    static BedrockPong fromRakNet(RakNetPong pong) {
        String info = new String(pong.getUserData(), StandardCharsets.UTF_8);

        BedrockPong bedrockPong = new BedrockPong();

        String[] infos = info.split(";");

        switch (infos.length) {
            case 0:
                break;
            default:
                bedrockPong.extras = new String[infos.length - 12];
                System.arraycopy(infos, 12, bedrockPong.extras, 0, bedrockPong.extras.length);
            case 12:
                try {
                    bedrockPong.ipv6Port = Integer.parseInt(infos[11]);
                } catch (NumberFormatException e) {
                    // ignore
                }
            case 11:
                try {
                    bedrockPong.ipv4Port = Integer.parseInt(infos[10]);
                } catch (NumberFormatException e) {
                    // ignore
                }
            case 10:
                bedrockPong.nintendoLimited = !"1".equalsIgnoreCase(infos[9]);
            case 9:
                bedrockPong.gameType = infos[8];
            case 8:
                bedrockPong.subMotd = infos[7];
            case 7:
                try {
                    bedrockPong.serverId = Long.parseLong(infos[6]);
                } catch (NumberFormatException e) {
                    // ignore
                }
            case 6:
                try {
                    bedrockPong.maximumPlayerCount = Integer.parseInt(infos[5]);
                } catch (NumberFormatException e) {
                    // ignore
                }
            case 5:
                try {
                    bedrockPong.playerCount = Integer.parseInt(infos[4]);
                } catch (NumberFormatException e) {
                    // ignore
                }
            case 4:
                bedrockPong.version = infos[3];
            case 3:
                try {
                    bedrockPong.protocolVersion = Integer.parseInt(infos[2]);
                } catch (NumberFormatException e) {
                    // ignore
                }
            case 2:
                bedrockPong.motd = infos[1];
            case 1:
                bedrockPong.edition = infos[0];
        }
        return bedrockPong;
    }

    byte[] toRakNet() {
        StringJoiner joiner = new StringJoiner(";", "", ";")
                .add(this.edition)
                .add(toString(this.motd))
                .add(Integer.toString(this.protocolVersion))
                .add(toString(this.version))
                .add(Integer.toString(this.playerCount))
                .add(Integer.toString(this.maximumPlayerCount))
                .add(Long.toString(this.serverId))
                .add(toString(this.subMotd))
                .add(toString(this.gameType))
                .add(this.nintendoLimited ? "0" : "1")
                .add(Integer.toString(this.ipv4Port))
                .add(Integer.toString(this.ipv6Port));
        if (this.extras != null) {
            for (String extra : this.extras) {
                joiner.add(extra);
            }
        }
        return joiner.toString().getBytes(StandardCharsets.UTF_8);
    }

    private static String toString(String string) {
        return string == null ? "" : string;
    }
}
