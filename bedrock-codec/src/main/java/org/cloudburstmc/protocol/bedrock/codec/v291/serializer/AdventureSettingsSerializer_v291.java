package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.AdventureSetting;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.bedrock.packet.AdventureSettingsPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Set;

@SuppressWarnings("PointlessBitwiseExpression")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdventureSettingsSerializer_v291 implements BedrockPacketSerializer<AdventureSettingsPacket> {
    public static final AdventureSettingsSerializer_v291 INSTANCE = new AdventureSettingsSerializer_v291();

    private static final CommandPermission[] COMMAND_PERMISSIONS = CommandPermission.values();
    private static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();

    private static final AdventureSetting[] FLAGS_1 = {AdventureSetting.WORLD_IMMUTABLE, AdventureSetting.NO_PVM, AdventureSetting.NO_MVP, null, AdventureSetting.SHOW_NAME_TAGS, AdventureSetting.AUTO_JUMP, AdventureSetting.MAY_FLY, AdventureSetting.NO_CLIP, AdventureSetting.WORLD_BUILDER, AdventureSetting.FLYING, AdventureSetting.MUTED};
    private static final AdventureSetting[] FLAGS_2 = {AdventureSetting.MINE, AdventureSetting.DOORS_AND_SWITCHES, AdventureSetting.OPEN_CONTAINERS, AdventureSetting.ATTACK_PLAYERS, AdventureSetting.ATTACK_MOBS, AdventureSetting.OPERATOR, null, AdventureSetting.TELEPORT, AdventureSetting.BUILD, AdventureSetting.DEFAULT_LEVEL_PERMISSIONS};

    private static final Object2IntMap<AdventureSetting> FLAGS_TO_BIT_1 = new Object2IntOpenHashMap<>();
    private static final Object2IntMap<AdventureSetting> FLAGS_TO_BIT_2 = new Object2IntOpenHashMap<>();

    static {
        FLAGS_TO_BIT_1.put(AdventureSetting.WORLD_IMMUTABLE, (1 << 0));
        FLAGS_TO_BIT_1.put(AdventureSetting.NO_PVM, (1 << 1));
        FLAGS_TO_BIT_1.put(AdventureSetting.NO_MVP, (1 << 2));

        FLAGS_TO_BIT_1.put(AdventureSetting.SHOW_NAME_TAGS, (1 << 4));
        FLAGS_TO_BIT_1.put(AdventureSetting.AUTO_JUMP, (1 << 5));
        FLAGS_TO_BIT_1.put(AdventureSetting.MAY_FLY, (1 << 6));
        FLAGS_TO_BIT_1.put(AdventureSetting.NO_CLIP, (1 << 7));
        FLAGS_TO_BIT_1.put(AdventureSetting.WORLD_BUILDER, (1 << 8));
        FLAGS_TO_BIT_1.put(AdventureSetting.FLYING, (1 << 9));
        FLAGS_TO_BIT_1.put(AdventureSetting.MUTED, (1 << 10));

        // Permissions flags
        FLAGS_TO_BIT_2.put(AdventureSetting.MINE, (1 << 0));
        FLAGS_TO_BIT_2.put(AdventureSetting.DOORS_AND_SWITCHES, (1 << 1));
        FLAGS_TO_BIT_2.put(AdventureSetting.OPEN_CONTAINERS, (1 << 2));
        FLAGS_TO_BIT_2.put(AdventureSetting.ATTACK_PLAYERS, (1 << 3));
        FLAGS_TO_BIT_2.put(AdventureSetting.ATTACK_MOBS, (1 << 4));
        FLAGS_TO_BIT_2.put(AdventureSetting.OPERATOR, (1 << 5));

        FLAGS_TO_BIT_2.put(AdventureSetting.TELEPORT, (1 << 7));
        FLAGS_TO_BIT_2.put(AdventureSetting.BUILD, (1 << 8));
        FLAGS_TO_BIT_2.put(AdventureSetting.DEFAULT_LEVEL_PERMISSIONS, (1 << 9));
    }


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AdventureSettingsPacket packet) {
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
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AdventureSettingsPacket packet) {
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
