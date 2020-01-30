package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.CommandPermission;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket.Flag.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdventureSettingsSerializer_v340 implements PacketSerializer<AdventureSettingsPacket> {
    public static final AdventureSettingsSerializer_v340 INSTANCE = new AdventureSettingsSerializer_v340();

    private static final CommandPermission[] COMMAND_PERMISSIONS = CommandPermission.values();
    private static final PlayerPermission[] PLAYER_PERMISSIONS = PlayerPermission.values();

    private static final AdventureSettingsPacket.Flag[] FLAGS_1 = {IMMUTABLE_WORLD, NO_PVP, NO_PVM, null, NO_MVP, MAY_FLY, NO_CLIP, WORLD_BUILDER, FLYING};
    private static final AdventureSettingsPacket.Flag[] FLAGS_2 = {MINE, DOORS_AND_SWITCHES, OPEN_CONTAINERS, ATTACK_PLAYERS, ATTACK_MOBS, OP, null, BUILD, SET_DEFAULT};

    private static final TObjectIntMap<AdventureSettingsPacket.Flag> FLAGS_TO_BIT_1 = new TObjectIntHashMap<>();
    private static final TObjectIntMap<AdventureSettingsPacket.Flag> FLAGS_TO_BIT_2 = new TObjectIntHashMap<>();

    static {
        FLAGS_TO_BIT_1.put(IMMUTABLE_WORLD, 0x1);
        FLAGS_TO_BIT_1.put(NO_PVP, 0x2);
        FLAGS_TO_BIT_1.put(NO_PVM, 0x4);
        FLAGS_TO_BIT_1.put(NO_MVP, 0x10);
        FLAGS_TO_BIT_1.put(AUTO_JUMP, 0x20);
        FLAGS_TO_BIT_1.put(MAY_FLY, 0x40);
        FLAGS_TO_BIT_1.put(NO_CLIP, 0x80);
        FLAGS_TO_BIT_1.put(WORLD_BUILDER, 0x100);
        FLAGS_TO_BIT_1.put(FLYING, 0x200);
        FLAGS_TO_BIT_1.put(MUTE, 0x400);

        FLAGS_TO_BIT_2.put(MINE, 0x1);
        FLAGS_TO_BIT_2.put(DOORS_AND_SWITCHES, 0x2);
        FLAGS_TO_BIT_2.put(OPEN_CONTAINERS, 0x04);
        FLAGS_TO_BIT_2.put(ATTACK_PLAYERS, 0x8);
        FLAGS_TO_BIT_2.put(ATTACK_MOBS, 0x10);
        FLAGS_TO_BIT_2.put(OP, 0x20);
        FLAGS_TO_BIT_2.put(TELEPORT, 0x80);
        FLAGS_TO_BIT_2.put(BUILD, 0x100);
        FLAGS_TO_BIT_2.put(SET_DEFAULT, 0x200);
    }


    @Override
    public void serialize(ByteBuf buffer, AdventureSettingsPacket packet) {
        int flags1 = 0;
        int flags2 = 0;
        for (AdventureSettingsPacket.Flag flag : packet.getFlags()) {
            if (FLAGS_TO_BIT_1.containsKey(flag)) {
                flags1 |= FLAGS_TO_BIT_1.get(flag);
            } else if (FLAGS_TO_BIT_2.containsKey(flag)) {
                flags2 |= FLAGS_TO_BIT_2.get(flag);
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
    public void deserialize(ByteBuf buffer, AdventureSettingsPacket packet) {
        int flags1 = VarInts.readUnsignedInt(buffer);
        packet.setCommandPermission(COMMAND_PERMISSIONS[VarInts.readUnsignedInt(buffer)]);
        int flags2 = VarInts.readUnsignedInt(buffer);
        packet.setPlayerPermission(PLAYER_PERMISSIONS[VarInts.readUnsignedInt(buffer)]);
        VarInts.readUnsignedInt(buffer); // useless
        packet.setUniqueEntityId(buffer.readLongLE());

        Set<AdventureSettingsPacket.Flag> flags = packet.getFlags();
        for (int i = 0; i < FLAGS_1.length; i++) {
            if ((flags1 & (1 << i)) != 0) {
                flags.add(FLAGS_1[i]);
            }
        }

        for (int i = 0; i < FLAGS_2.length; i++) {
            if ((flags2 & (1 << i)) != 0) {
                flags.add(FLAGS_2[i]);
            }
        }
    }
}
