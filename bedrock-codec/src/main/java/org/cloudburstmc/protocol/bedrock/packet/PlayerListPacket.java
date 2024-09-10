package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerListPacket implements BedrockPacket {
    private final List<Entry> entries = new ObjectArrayList<>();
    private Action action;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_LIST;
    }

    public enum Action {
        ADD,
        REMOVE
    }


    @Data
    @ToString(doNotUseGetters = true)
    @EqualsAndHashCode(doNotUseGetters = true)
    public final static class Entry {
        private final UUID uuid;
        private long entityId;
        private String name;
        private String xuid;
        private String platformChatId;
        private int buildPlatform;
        private SerializedSkin skin;
        private boolean teacher;
        private boolean host;
        private boolean trustedSkin;
        private boolean subClient;
    }

    @Override
    public PlayerListPacket clone() {
        try {
            return (PlayerListPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

