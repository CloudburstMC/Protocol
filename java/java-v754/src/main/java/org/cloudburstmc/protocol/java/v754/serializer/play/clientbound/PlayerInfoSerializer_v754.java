package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.GameType;
import org.cloudburstmc.protocol.java.data.profile.GameProfile;
import org.cloudburstmc.protocol.java.packet.play.clientbound.PlayerInfoPacket;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerInfoSerializer_v754 implements JavaPacketSerializer<PlayerInfoPacket> {
    public static final PlayerInfoSerializer_v754 INSTANCE = new PlayerInfoSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, PlayerInfoPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getAction().ordinal());
        helper.writeArray(buffer, packet.getEntries(), ((buf, entry) -> {
            switch (packet.getAction()) {
                case ADD_PLAYER:
                    helper.writeGameProfile(buffer, entry.getProfile(), true);
                    helper.writeVarInt(buffer, entry.getGameType().ordinal());
                    helper.writeVarInt(buffer, entry.getLatency());
                    helper.writeOptional(buffer, entry.getDisplayName(), helper::writeComponent);
                    break;
                case UPDATE_GAME_MODE:
                    helper.writeUUID(buffer, entry.getProfile().getId());
                    helper.writeVarInt(buffer, entry.getGameType().ordinal());
                    break;
                case UPDATE_LATENCY:
                    helper.writeUUID(buffer, entry.getProfile().getId());
                    helper.writeVarInt(buffer, entry.getLatency());
                    break;
                case UPDATE_DISPLAY_NAME:
                    helper.writeUUID(buffer, entry.getProfile().getId());
                    helper.writeOptional(buffer, entry.getDisplayName(), helper::writeComponent);
                    break;
                case REMOVE_PLAYER:
                    helper.writeUUID(buffer, entry.getProfile().getId());
                    break;
            }
        }));
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, PlayerInfoPacket packet) throws PacketSerializeException {
        packet.setAction(PlayerInfoPacket.Action.getById(helper.readVarInt(buffer)));
        PlayerInfoPacket.Entry[] entries = helper.readArray(buffer, new PlayerInfoPacket.Entry[0], buf -> {
            switch (packet.getAction()) {
                case ADD_PLAYER:
                    GameProfile profile = helper.readGameProfile(buffer, true);
                    GameType gameType = GameType.getById(helper.readVarInt(buffer));
                    int latency = helper.readVarInt(buffer);
                    Component displayName = helper.readOptional(buffer, helper::readComponent);
                    return new PlayerInfoPacket.Entry(latency, gameType, profile, displayName);
                case UPDATE_GAME_MODE:
                    UUID id = helper.readUUID(buffer);
                    gameType = GameType.getById(helper.readVarInt(buffer));
                    return new PlayerInfoPacket.Entry(-1, gameType, new GameProfile(id), null);
                case UPDATE_LATENCY:
                    id = helper.readUUID(buffer);
                    latency = helper.readVarInt(buffer);
                    return new PlayerInfoPacket.Entry(latency, null, new GameProfile(id), null);
                case UPDATE_DISPLAY_NAME:
                    id = helper.readUUID(buffer);
                    displayName = helper.readOptional(buffer, helper::readComponent);
                    return new PlayerInfoPacket.Entry(-1, null, new GameProfile(id), displayName);
                case REMOVE_PLAYER:
                    id = helper.readUUID(buffer);
                    return new PlayerInfoPacket.Entry(-1, null, new GameProfile(id), null);
                default:
                    throw new RuntimeException("Unable to find entry for action " + packet.getAction());
            }
        });
        for (PlayerInfoPacket.Entry entry : entries) {
            packet.getEntries().add(entry);
        }
    }
}
