package org.cloudburstmc.protocol.java.handler;

import org.cloudburstmc.protocol.java.packet.play.CustomPayloadPacket;
import org.cloudburstmc.protocol.java.packet.play.KeepAlivePacket;
import org.cloudburstmc.protocol.java.packet.play.SetCarriedItemPacket;
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

    default boolean handle(CustomSoundPacket packet) {
        return false;
    }

    default boolean handle(DisconnectPacket packet) {
        return false;
    }

    default boolean handle(EntityEventPacket packet) {
        return false;
    }

    default boolean handle(ExplodePacket packet) {
        return false;
    }

    default boolean handle(ForgetLevelChunkPacket packet) {
        return false;
    }

    default boolean handle(GameEventPacket packet) {
        return false;
    }

    default boolean handle(HorseScreenOpenPacket packet) {
        return false;
    }

    default boolean handle(KeepAlivePacket packet) {
        return false;
    }

    default boolean handle(LevelChunkPacket packet) {
        return false;
    }

    default boolean handle(LevelEventPacket packet) {
        return false;
    }

    default boolean handle(LevelParticlesPacket packet) {
        return false;
    }

    default boolean handle(LightUpdatePacket packet) {
        return false;
    }

    default boolean handle(LoginPacket packet) {
        return false;
    }

    default boolean handle(MapItemDataPacket packet) {
        return false;
    }

    default boolean handle(MerchantOffersPacket packet) {
        return false;
    }

    default boolean handle(MoveEntityPacket.Pos packet) {
        return false;
    }

    default boolean handle(MoveEntityPacket.PosRot packet) {
        return false;
    }

    default boolean handle(MoveEntityPacket.Rot packet) {
        return false;
    }

    default boolean handle(MoveVehiclePacket packet) {
        return false;
    }

    default boolean handle(OpenBookPacket packet) {
        return false;
    }

    default boolean handle(OpenScreenPacket packet) {
        return false;
    }

    default boolean handle(OpenSignEditorPacket packet) {
        return false;
    }

    default boolean handle(PlaceGhostRecipePacket packet) {
        return false;
    }

    default boolean handle(PlayerAbilitiesPacket packet) {
        return false;
    }

    default boolean handle(PlayerCombatPacket packet) {
        return false;
    }

    default boolean handle(PlayerInfoPacket packet) {
        return false;
    }

    default boolean handle(PlayerLookAtPacket packet) {
        return false;
    }

    default boolean handle(PlayerPositionPacket packet) {
        return false;
    }

    default boolean handle(RecipePacket packet) {
        return false;
    }

    default boolean handle(RemoveEntitiesPacket packet) {
        return false;
    }

    default boolean handle(RemoveMobEffectPacket packet) {
        return false;
    }

    default boolean handle(ResourcePackPacket packet) {
        return false;
    }

    default boolean handle(RespawnPacket packet) {
        return false;
    }

    default boolean handle(RotateHeadPacket packet) {
        return false;
    }

    default boolean handle(SectionBlocksUpdatePacket packet) {
        return false;
    }

    default boolean handle(SelectAdvancementsTabPacket packet) {
        return false;
    }

    default boolean handle(SetBorderPacket packet) {
        return false;
    }

    default boolean handle(SetCameraPacket packet) {
        return false;
    }

    default boolean handle(SetCarriedItemPacket packet) {
        return false;
    }

    default boolean handle(SetChunkCacheCenterPacket packet) {
        return false;
    }

    default boolean handle(SetChunkCacheRadiusPacket packet) {
        return false;
    }

    default boolean handle(SetDefaultSpawnPositionPacket packet) {
        return false;
    }

    default boolean handle(SetDisplayObjectivePacket packet) {
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
