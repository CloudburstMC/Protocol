package org.cloudburstmc.protocol.java.handler;

import org.cloudburstmc.protocol.java.packet.play.*;

public interface JavaPlayPacketHandler extends JavaPacketHandler {

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

    default boolean handle(DisconnectPacket packet) {
        return false;
    }

    default boolean handle(ChatPacket packet) {
        return false;
    }
}
