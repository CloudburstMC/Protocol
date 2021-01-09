package org.cloudburstmc.protocol.java.handler;

import org.cloudburstmc.protocol.java.packet.play.CustomPayloadPacket;
import org.cloudburstmc.protocol.java.packet.play.clientbound.*;
import org.cloudburstmc.protocol.java.packet.play.ContainerClosePacket;
import org.cloudburstmc.protocol.java.packet.play.serverbound.MovePlayerPacket;

public interface JavaPlayPacketHandler extends JavaPacketHandler {

    // Clientbound packets
    default boolean handle(AddEntityPacket packet) {
        return false;
    }

    default boolean handle(AddExperienceOrbPacket packet) {
        return false;
    }

    default boolean handle(AddMobPacket packet) {
        return false;
    }

    default boolean handle(AddPaintingPacket packet) {
        return false;
    }

    default boolean handle(AddPlayerPacket packet) {
        return false;
    }

    default boolean handle(AnimatePacket packet) {
        return false;
    }

    default boolean handle(AwardStatsPacket packet) {
        return false;
    }

    default boolean handle(BlockBreakAckPacket packet) {
        return false;
    }

    default boolean handle(BlockDestructionPacket packet) {
        return false;
    }

    default boolean handle(BlockEntityDataPacket packet) {
        return false;
    }

    default boolean handle(BlockEventPacket packet) {
        return false;
    }

    default boolean handle(BlockUpdatePacket packet) {
        return false;
    }

    default boolean handle(BossEventPacket packet) {
        return false;
    }

    default boolean handle(ChangeDifficultyPacket packet) {
        return false;
    }

    default boolean handle(ChatPacket packet) {
        return false;
    }

    default boolean handle(CommandSuggestionsPacket packet) {
        return false;
    }

    default boolean handle(CommandsPacket packet) {
        return false;
    }

    default boolean handle(ContainerAckPacket packet) {
        return false;
    }

    default boolean handle(ContainerSetContentPacket packet) {
        return false;
    }

    default boolean handle(ContainerSetDataPacket packet) {
        return false;
    }

    default boolean handle(ContainerSetSlotPacket packet) {
        return false;
    }

    default boolean handle(CooldownPacket packet) {
        return false;
    }

    default boolean handle(DisconnectPacket packet) {
        return false;
    }

    default boolean handle(LoginPacket packet) {
        return false;
    }

    default boolean handle(PlayerPositionPacket packet) {
        return false;
    }

    // Serverbound packets
    default boolean handle(AcceptTeleportationPacket packet) {
        return false;
    }

    default boolean handle(MovePlayerPacket packet) {
        return false;
    }

    default boolean handle(ContainerClosePacket packet) {
        return false;
    }

    // Bidirectional packets
    default boolean handle(CustomPayloadPacket packet) {
        return false;
    }
}
