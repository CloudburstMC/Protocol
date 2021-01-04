package org.cloudburstmc.protocol.java.packet.play;

import com.nukkitx.nbt.NbtMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.JavaPacket;
import org.cloudburstmc.protocol.java.handler.JavaPlayPacketHandler;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;
import org.cloudburstmc.protocol.java.packet.type.JavaPlayPacketType;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class LoginPacket extends JavaPacket<JavaPlayPacketHandler> {
    private int entityId;
    private boolean hardcore;
    private int gamemode;
    private int previousGamemode;
    private Key[] dimensions;
    @ToString.Exclude private NbtMap dimensionCodec;
    @ToString.Exclude private NbtMap dimension;
    private Key dimensionName;
    private long seedHash;
    private int maxPlayers;
    private int chunkRadius;
    private boolean reducedDebugInfo;
    private boolean enableRespawnScreen;
    private boolean debug;
    private boolean flat;

    @Override
    public boolean handle(JavaPlayPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public JavaPacketType getPacketType() {
        return JavaPlayPacketType.LOGIN_S2C;
    }
}
