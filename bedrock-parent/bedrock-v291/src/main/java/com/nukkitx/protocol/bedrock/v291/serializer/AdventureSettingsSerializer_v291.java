package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.AdventureSetting;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.nukkitx.protocol.bedrock.data.AdventureSetting.*;

@SuppressWarnings("PointlessBitwiseExpression")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdventureSettingsSerializer_v291 implements BedrockPacketSerializer<AdventureSettingsPacket> {
    public static final AdventureSettingsSerializer_v291 INSTANCE = new AdventureSettingsSerializer_v291();

    private static final CommandPermission[] COMMAND_PERMISSIONS = CommandPermission.values();
    private static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();

    private static final AdventureSetting[] FLAGS_1 = {WORLD_IMMUTABLE, NO_PVM, NO_MVP, null, SHOW_NAME_TAGS, AUTO_JUMP, MAY_FLY, NO_CLIP, WORLD_BUILDER, FLYING, MUTED};
    private static final AdventureSetting[] FLAGS_2 = {MINE, DOORS_AND_SWITCHES, OPEN_CONTAINERS, ATTACK_PLAYERS, ATTACK_MOBS, OPERATOR, null, TELEPORT, BUILD, DEFAULT_LEVEL_PERMISSIONS};

    private static final Object2IntMap<AdventureSetting> FLAGS_TO_BIT_1 = new Object2IntOpenHashMap<>();
    private static final Object2IntMap<AdventureSetting> FLAGS_TO_BIT_2 = new Object2IntOpenHashMap<>();

    static {
        FLAGS_TO_BIT_1.put(WORLD_IMMUTABLE, (1 << 0));
        FLAGS_TO_BIT_1.put(NO_PVM, (1 << 1));
        FLAGS_TO_BIT_1.put(NO_MVP, (1 << 2));

        FLAGS_TO_BIT_1.put(SHOW_NAME_TAGS, (1 << 4));
        FLAGS_TO_BIT_1.put(AUTO_JUMP, (1 << 5));
        FLAGS_TO_BIT_1.put(MAY_FLY, (1 << 6));
        FLAGS_TO_BIT_1.put(NO_CLIP, (1 << 7));
        FLAGS_TO_BIT_1.put(WORLD_BUILDER, (1 << 8));
        FLAGS_TO_BIT_1.put(FLYING, (1 << 9));
        FLAGS_TO_BIT_1.put(MUTED, (1 << 10));

        // Permissions flags
        FLAGS_TO_BIT_2.put(MINE, (1 << 0));
        FLAGS_TO_BIT_2.put(DOORS_AND_SWITCHES, (1 << 1));
        FLAGS_TO_BIT_2.put(OPEN_CONTAINERS, (1 << 2));
        FLAGS_TO_BIT_2.put(ATTACK_PLAYERS, (1 << 3));
        FLAGS_TO_BIT_2.put(ATTACK_MOBS, (1 << 4));
        FLAGS_TO_BIT_2.put(OPERATOR, (1 << 5));

        FLAGS_TO_BIT_2.put(TELEPORT, (1 << 7));
        FLAGS_TO_BIT_2.put(BUILD, (1 << 8));
        FLAGS_TO_BIT_2.put(DEFAULT_LEVEL_PERMISSIONS, (1 << 9));
    }


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AdventureSettingsPacket packet) {
        int flags1 = 0;
        int flags2 = 0;
        for (AdventureSetting setting : packet.getSettings()) {
            if (FLAGS_TO_BIT_1.containsKey(setting)) {
                flags1 |= FLAGS_TO_BIT_1.getInt(setting);
            } else if (FLAGS_TO_BIT_2.containsKey(setting)) {
                flags2 |= FLAGS_TO_BIT_2.getInt(setting);
            }
        }
        VarInts.writeUnsignedInt(buffer, flags1);
        VarInts.writeUnsignedInt(buffer, packet.getCommandPermission().ordinal());
        VarInts.writeUnsignedInt(buffer, flags2);
        VarInts.writeUnsignedInt(buffer, packet.getPlayerPermission().ordinal());
        VarInts.writeUnsignedInt(buffer, 0); // Useless
        buffer.writeLongLE(packet.getUniqueEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AdventureSettingsPacket packet) {
        int flags1 = VarInts.readUnsignedInt(buffer);
        packet.setCommandPermission(COMMAND_PERMISSIONS[VarInts.readUnsignedInt(buffer)]);
        int flags2 = VarInts.readUnsignedInt(buffer);
        packet.setPlayerPermission(PLAYER_PERMISSIONS[VarInts.readUnsignedInt(buffer)]);
        VarInts.readUnsignedInt(buffer); // useless
        packet.setUniqueEntityId(buffer.readLongLE());

        Set<AdventureSetting> settings = packet.getSettings();

        readFlags(flags1, FLAGS_1, settings);
        readFlags(flags2, FLAGS_2, settings);
    }

    protected static void readFlags(int flags, AdventureSetting[] mappings, Set<AdventureSetting> settings) {
        for (int i = 0; i < mappings.length; i++) {
            AdventureSetting setting = mappings[i];
            if (setting != null && (flags & (1 << i)) != 0) {
                settings.add(setting);
            }
        }
    }
}
