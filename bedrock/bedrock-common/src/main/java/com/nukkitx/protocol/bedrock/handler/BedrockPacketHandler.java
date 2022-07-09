package com.nukkitx.protocol.bedrock.handler;

import com.nukkitx.protocol.PacketHandler;
import com.nukkitx.protocol.bedrock.packet.AddBehaviorTreePacket;
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AddHangingEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AddItemEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AddPaintingPacket;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import com.nukkitx.protocol.bedrock.packet.AddVolumeEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.AgentActionEventPacket;
import com.nukkitx.protocol.bedrock.packet.AnimateEntityPacket;
import com.nukkitx.protocol.bedrock.packet.AnimatePacket;
import com.nukkitx.protocol.bedrock.packet.AnvilDamagePacket;
import com.nukkitx.protocol.bedrock.packet.AutomationClientConnectPacket;
import com.nukkitx.protocol.bedrock.packet.AvailableCommandsPacket;
import com.nukkitx.protocol.bedrock.packet.AvailableEntityIdentifiersPacket;
import com.nukkitx.protocol.bedrock.packet.BiomeDefinitionListPacket;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import com.nukkitx.protocol.bedrock.packet.BlockPickRequestPacket;
import com.nukkitx.protocol.bedrock.packet.BookEditPacket;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import com.nukkitx.protocol.bedrock.packet.CameraPacket;
import com.nukkitx.protocol.bedrock.packet.CameraShakePacket;
import com.nukkitx.protocol.bedrock.packet.ChangeDimensionPacket;
import com.nukkitx.protocol.bedrock.packet.ChangeMobPropertyPacket;
import com.nukkitx.protocol.bedrock.packet.ChunkRadiusUpdatedPacket;
import com.nukkitx.protocol.bedrock.packet.ClientCacheBlobStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ClientCacheMissResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ClientCacheStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import com.nukkitx.protocol.bedrock.packet.ClientboundDebugRendererPacket;
import com.nukkitx.protocol.bedrock.packet.ClientboundMapItemDataPacket;
import com.nukkitx.protocol.bedrock.packet.CodeBuilderPacket;
import com.nukkitx.protocol.bedrock.packet.CodeBuilderSourcePacket;
import com.nukkitx.protocol.bedrock.packet.CommandBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.packet.CommandOutputPacket;
import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import com.nukkitx.protocol.bedrock.packet.CompletedUsingItemPacket;
import com.nukkitx.protocol.bedrock.packet.ContainerClosePacket;
import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import com.nukkitx.protocol.bedrock.packet.ContainerSetDataPacket;
import com.nukkitx.protocol.bedrock.packet.CorrectPlayerMovePredictionPacket;
import com.nukkitx.protocol.bedrock.packet.CraftingDataPacket;
import com.nukkitx.protocol.bedrock.packet.CraftingEventPacket;
import com.nukkitx.protocol.bedrock.packet.CreatePhotoPacket;
import com.nukkitx.protocol.bedrock.packet.CreativeContentPacket;
import com.nukkitx.protocol.bedrock.packet.DeathInfoPacket;
import com.nukkitx.protocol.bedrock.packet.DebugInfoPacket;
import com.nukkitx.protocol.bedrock.packet.DimensionDataPacket;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import com.nukkitx.protocol.bedrock.packet.EduUriResourcePacket;
import com.nukkitx.protocol.bedrock.packet.EducationSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.EmoteListPacket;
import com.nukkitx.protocol.bedrock.packet.EmotePacket;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import com.nukkitx.protocol.bedrock.packet.EntityFallPacket;
import com.nukkitx.protocol.bedrock.packet.EntityPickRequestPacket;
import com.nukkitx.protocol.bedrock.packet.EventPacket;
import com.nukkitx.protocol.bedrock.packet.ExplodePacket;
import com.nukkitx.protocol.bedrock.packet.FilterTextPacket;
import com.nukkitx.protocol.bedrock.packet.GameRulesChangedPacket;
import com.nukkitx.protocol.bedrock.packet.GuiDataPickItemPacket;
import com.nukkitx.protocol.bedrock.packet.HurtArmorPacket;
import com.nukkitx.protocol.bedrock.packet.InteractPacket;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.packet.ItemComponentPacket;
import com.nukkitx.protocol.bedrock.packet.ItemFrameDropItemPacket;
import com.nukkitx.protocol.bedrock.packet.ItemStackRequestPacket;
import com.nukkitx.protocol.bedrock.packet.ItemStackResponsePacket;
import com.nukkitx.protocol.bedrock.packet.LabTablePacket;
import com.nukkitx.protocol.bedrock.packet.LecternUpdatePacket;
import com.nukkitx.protocol.bedrock.packet.LessonProgressPacket;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import com.nukkitx.protocol.bedrock.packet.LevelEventGenericPacket;
import com.nukkitx.protocol.bedrock.packet.LevelEventPacket;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEvent1Packet;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEvent2Packet;
import com.nukkitx.protocol.bedrock.packet.LevelSoundEventPacket;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.MapCreateLockedCopyPacket;
import com.nukkitx.protocol.bedrock.packet.MapInfoRequestPacket;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket;
import com.nukkitx.protocol.bedrock.packet.MobEquipmentPacket;
import com.nukkitx.protocol.bedrock.packet.ModalFormRequestPacket;
import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import com.nukkitx.protocol.bedrock.packet.MotionPredictionHintsPacket;
import com.nukkitx.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import com.nukkitx.protocol.bedrock.packet.MoveEntityDeltaPacket;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import com.nukkitx.protocol.bedrock.packet.MultiplayerSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.NetworkChunkPublisherUpdatePacket;
import com.nukkitx.protocol.bedrock.packet.NetworkSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.NetworkStackLatencyPacket;
import com.nukkitx.protocol.bedrock.packet.NpcDialoguePacket;
import com.nukkitx.protocol.bedrock.packet.NpcRequestPacket;
import com.nukkitx.protocol.bedrock.packet.OnScreenTextureAnimationPacket;
import com.nukkitx.protocol.bedrock.packet.PacketViolationWarningPacket;
import com.nukkitx.protocol.bedrock.packet.PhotoInfoRequestPacket;
import com.nukkitx.protocol.bedrock.packet.PhotoTransferPacket;
import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerArmorDamagePacket;
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerEnchantOptionsPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerFogPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerHotbarPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerInputPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerStartItemCooldownPacket;
import com.nukkitx.protocol.bedrock.packet.PositionTrackingDBClientRequestPacket;
import com.nukkitx.protocol.bedrock.packet.PositionTrackingDBServerBroadcastPacket;
import com.nukkitx.protocol.bedrock.packet.PurchaseReceiptPacket;
import com.nukkitx.protocol.bedrock.packet.RemoveEntityPacket;
import com.nukkitx.protocol.bedrock.packet.RemoveObjectivePacket;
import com.nukkitx.protocol.bedrock.packet.RemoveVolumeEntityPacket;
import com.nukkitx.protocol.bedrock.packet.RequestAbilityPacket;
import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import com.nukkitx.protocol.bedrock.packet.RequestPermissionsPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkDataPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkRequestPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import com.nukkitx.protocol.bedrock.packet.RiderJumpPacket;
import com.nukkitx.protocol.bedrock.packet.ScriptCustomEventPacket;
import com.nukkitx.protocol.bedrock.packet.ScriptMessagePacket;
import com.nukkitx.protocol.bedrock.packet.ServerSettingsRequestPacket;
import com.nukkitx.protocol.bedrock.packet.ServerSettingsResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;
import com.nukkitx.protocol.bedrock.packet.SetCommandsEnabledPacket;
import com.nukkitx.protocol.bedrock.packet.SetDefaultGameTypePacket;
import com.nukkitx.protocol.bedrock.packet.SetDifficultyPacket;
import com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityLinkPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket;
import com.nukkitx.protocol.bedrock.packet.SetHealthPacket;
import com.nukkitx.protocol.bedrock.packet.SetLastHurtByPacket;
import com.nukkitx.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;
import com.nukkitx.protocol.bedrock.packet.SetPlayerGameTypePacket;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import com.nukkitx.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import com.nukkitx.protocol.bedrock.packet.SetSpawnPositionPacket;
import com.nukkitx.protocol.bedrock.packet.SetTimePacket;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import com.nukkitx.protocol.bedrock.packet.SettingsCommandPacket;
import com.nukkitx.protocol.bedrock.packet.ShowCreditsPacket;
import com.nukkitx.protocol.bedrock.packet.ShowProfilePacket;
import com.nukkitx.protocol.bedrock.packet.ShowStoreOfferPacket;
import com.nukkitx.protocol.bedrock.packet.SimpleEventPacket;
import com.nukkitx.protocol.bedrock.packet.SimulationTypePacket;
import com.nukkitx.protocol.bedrock.packet.SpawnExperienceOrbPacket;
import com.nukkitx.protocol.bedrock.packet.SpawnParticleEffectPacket;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.packet.StopSoundPacket;
import com.nukkitx.protocol.bedrock.packet.StructureBlockUpdatePacket;
import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataRequestPacket;
import com.nukkitx.protocol.bedrock.packet.StructureTemplateDataResponsePacket;
import com.nukkitx.protocol.bedrock.packet.SubChunkPacket;
import com.nukkitx.protocol.bedrock.packet.SubChunkRequestPacket;
import com.nukkitx.protocol.bedrock.packet.SubClientLoginPacket;
import com.nukkitx.protocol.bedrock.packet.SyncEntityPropertyPacket;
import com.nukkitx.protocol.bedrock.packet.TakeItemEntityPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import com.nukkitx.protocol.bedrock.packet.TickSyncPacket;
import com.nukkitx.protocol.bedrock.packet.TickingAreasLoadStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ToastRequestPacket;
import com.nukkitx.protocol.bedrock.packet.TransferPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateAbilitiesPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateAttributesPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPropertiesPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockSyncedPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateEquipPacket;
import com.nukkitx.protocol.bedrock.packet.UpdatePlayerGameTypePacket;
import com.nukkitx.protocol.bedrock.packet.UpdateSoftEnumPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateSubChunkBlocksPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateTradePacket;
import com.nukkitx.protocol.bedrock.packet.VideoStreamConnectPacket;

public interface BedrockPacketHandler extends PacketHandler {

    default boolean handle(AdventureSettingsPacket packet) {
        return false;
    }

    default boolean handle(AnimatePacket packet) {
        return false;
    }

    default boolean handle(AnvilDamagePacket packet) {
        return false;
    }

    default boolean handle(AvailableEntityIdentifiersPacket packet) {
        return false;
    }

    default boolean handle(BlockEntityDataPacket packet) {
        return false;
    }

    default boolean handle(BlockPickRequestPacket packet) {
        return false;
    }

    default boolean handle(BookEditPacket packet) {
        return false;
    }

    default boolean handle(ClientCacheBlobStatusPacket packet) {
        return false;
    }

    default boolean handle(ClientCacheMissResponsePacket packet) {
        return false;
    }

    default boolean handle(ClientCacheStatusPacket packet) {
        return false;
    }

    default boolean handle(ClientToServerHandshakePacket packet) {
        return false;
    }

    default boolean handle(CommandBlockUpdatePacket packet) {
        return false;
    }

    default boolean handle(CommandRequestPacket packet) {
        return false;
    }

    default boolean handle(CompletedUsingItemPacket packet) {
        return false;
    }

    default boolean handle(ContainerClosePacket packet) {
        return false;
    }

    default boolean handle(CraftingEventPacket packet) {
        return false;
    }

    default boolean handle(EducationSettingsPacket packet) {
        return false;
    }

    default boolean handle(EmotePacket packet) {
        return false;
    }

    default boolean handle(EntityEventPacket packet) {
        return false;
    }

    default boolean handle(EntityFallPacket packet) {
        return false;
    }

    default boolean handle(EntityPickRequestPacket packet) {
        return false;
    }

    default boolean handle(EventPacket packet) {
        return false;
    }

    default boolean handle(FilterTextPacket packet) {
        return false;
    }

    default boolean handle(InteractPacket packet) {
        return false;
    }

    default boolean handle(InventoryContentPacket packet) {
        return false;
    }

    default boolean handle(InventorySlotPacket packet) {
        return false;
    }

    default boolean handle(InventoryTransactionPacket packet) {
        return false;
    }

    default boolean handle(ItemFrameDropItemPacket packet) {
        return false;
    }

    default boolean handle(LabTablePacket packet) {
        return false;
    }

    default boolean handle(LecternUpdatePacket packet) {
        return false;
    }

    default boolean handle(LevelEventGenericPacket packet) {
        return false;
    }

    default boolean handle(LevelSoundEvent1Packet packet) {
        return false;
    }

    default boolean handle(LevelSoundEventPacket packet) {
        return false;
    }

    default boolean handle(LoginPacket packet) {
        return false;
    }

    default boolean handle(MapInfoRequestPacket packet) {
        return false;
    }

    default boolean handle(MobArmorEquipmentPacket packet) {
        return false;
    }

    default boolean handle(MobEquipmentPacket packet) {
        return false;
    }

    default boolean handle(ModalFormResponsePacket packet) {
        return false;
    }

    default boolean handle(MoveEntityAbsolutePacket packet) {
        return false;
    }

    default boolean handle(MovePlayerPacket packet) {
        return false;
    }

    default boolean handle(MultiplayerSettingsPacket packet) {
        return false;
    }

    default boolean handle(NetworkStackLatencyPacket packet) {
        return false;
    }

    default boolean handle(PhotoTransferPacket packet) {
        return false;
    }

    default boolean handle(PlayerActionPacket packet) {
        return false;
    }

    default boolean handle(PlayerAuthInputPacket packet) {
        return false;
    }

    default boolean handle(PlayerHotbarPacket packet) {
        return false;
    }

    default boolean handle(PlayerInputPacket packet) {
        return false;
    }

    default boolean handle(PlayerSkinPacket packet) {
        return false;
    }

    default boolean handle(PurchaseReceiptPacket packet) {
        return false;
    }

    default boolean handle(RequestChunkRadiusPacket packet) {
        return false;
    }

    default boolean handle(ResourcePackChunkRequestPacket packet) {
        return false;
    }

    default boolean handle(ResourcePackClientResponsePacket packet) {
        return false;
    }

    default boolean handle(RiderJumpPacket packet) {
        return false;
    }

    default boolean handle(ServerSettingsRequestPacket packet) {
        return false;
    }

    default boolean handle(SetDefaultGameTypePacket packet) {
        return false;
    }

    default boolean handle(SetLocalPlayerAsInitializedPacket packet) {
        return false;
    }

    default boolean handle(SetPlayerGameTypePacket packet) {
        return false;
    }

    default boolean handle(SubClientLoginPacket packet) {
        return false;
    }

    default boolean handle(AddBehaviorTreePacket packet) {
        return false;
    }

    default boolean handle(AddEntityPacket packet) {
        return false;
    }

    default boolean handle(AddHangingEntityPacket packet) {
        return false;
    }

    default boolean handle(AddItemEntityPacket packet) {
        return false;
    }

    default boolean handle(AddPaintingPacket packet) {
        return false;
    }

    default boolean handle(AddPlayerPacket packet) {
        return false;
    }

    default boolean handle(AvailableCommandsPacket packet) {
        return false;
    }

    default boolean handle(BlockEventPacket packet) {
        return false;
    }

    default boolean handle(BossEventPacket packet) {
        return false;
    }

    default boolean handle(CameraPacket packet) {
        return false;
    }

    default boolean handle(ChangeDimensionPacket packet) {
        return false;
    }

    default boolean handle(ChunkRadiusUpdatedPacket packet) {
        return false;
    }

    default boolean handle(ClientboundMapItemDataPacket packet) {
        return false;
    }

    default boolean handle(CommandOutputPacket packet) {
        return false;
    }

    default boolean handle(ContainerOpenPacket packet) {
        return false;
    }

    default boolean handle(ContainerSetDataPacket packet) {
        return false;
    }

    default boolean handle(CraftingDataPacket packet) {
        return false;
    }

    default boolean handle(DisconnectPacket packet) {
        return false;
    }

    default boolean handle(ExplodePacket packet) {
        return false;
    }

    default boolean handle(LevelChunkPacket packet) {
        return false;
    }

    default boolean handle(GameRulesChangedPacket packet) {
        return false;
    }

    default boolean handle(GuiDataPickItemPacket packet) {
        return false;
    }

    default boolean handle(HurtArmorPacket packet) {
        return false;
    }

    default boolean handle(AutomationClientConnectPacket packet) {
        return false;
    }

    default boolean handle(LevelEventPacket packet) {
        return false;
    }

    default boolean handle(MapCreateLockedCopyPacket packet) {
        return false;
    }

    default boolean handle(MobEffectPacket packet) {
        return false;
    }

    default boolean handle(ModalFormRequestPacket packet) {
        return false;
    }

    default boolean handle(MoveEntityDeltaPacket packet) {
        return false;
    }

    default boolean handle(NetworkSettingsPacket packet) {
        return false;
    }

    default boolean handle(NpcRequestPacket packet) {
        return false;
    }

    default boolean handle(OnScreenTextureAnimationPacket packet) {
        return false;
    }

    default boolean handle(PlayerListPacket packet) {
        return false;
    }

    default boolean handle(PlaySoundPacket packet) {
        return false;
    }

    default boolean handle(PlayStatusPacket packet) {
        return false;
    }

    default boolean handle(RemoveEntityPacket packet) {
        return false;
    }

    default boolean handle(RemoveObjectivePacket packet) {
        return false;
    }

    default boolean handle(ResourcePackChunkDataPacket packet) {
        return false;
    }

    default boolean handle(ResourcePackDataInfoPacket packet) {
        return false;
    }

    default boolean handle(ResourcePacksInfoPacket packet) {
        return false;
    }

    default boolean handle(ResourcePackStackPacket packet) {
        return false;
    }

    default boolean handle(RespawnPacket packet) {
        return false;
    }

    default boolean handle(ScriptCustomEventPacket packet) {
        return false;
    }

    default boolean handle(ServerSettingsResponsePacket packet) {
        return false;
    }

    default boolean handle(ServerToClientHandshakePacket packet) {
        return false;
    }

    default boolean handle(SetCommandsEnabledPacket packet) {
        return false;
    }

    default boolean handle(SetDifficultyPacket packet) {
        return false;
    }

    default boolean handle(SetDisplayObjectivePacket packet) {
        return false;
    }

    default boolean handle(SetEntityDataPacket packet) {
        return false;
    }

    default boolean handle(SetEntityLinkPacket packet) {
        return false;
    }

    default boolean handle(SetEntityMotionPacket packet) {
        return false;
    }

    default boolean handle(SetHealthPacket packet) {
        return false;
    }

    default boolean handle(SetLastHurtByPacket packet) {
        return false;
    }

    default boolean handle(SetScoreboardIdentityPacket packet) {
        return false;
    }

    default boolean handle(SetScorePacket packet) {
        return false;
    }

    default boolean handle(SetSpawnPositionPacket packet) {
        return false;
    }

    default boolean handle(SetTimePacket packet) {
        return false;
    }

    default boolean handle(SettingsCommandPacket packet) {
        return false;
    }

    default boolean handle(SetTitlePacket packet) {
        return false;
    }

    default boolean handle(ShowCreditsPacket packet) {
        return false;
    }

    default boolean handle(ShowProfilePacket packet) {
        return false;
    }

    default boolean handle(ShowStoreOfferPacket packet) {
        return false;
    }

    default boolean handle(SimpleEventPacket packet) {
        return false;
    }

    default boolean handle(SpawnExperienceOrbPacket packet) {
        return false;
    }

    default boolean handle(SpawnParticleEffectPacket packet) {
        return false;
    }

    default boolean handle(StartGamePacket packet) {
        return false;
    }

    default boolean handle(StopSoundPacket packet) {
        return false;
    }

    default boolean handle(StructureBlockUpdatePacket packet) {
        return false;
    }

    default boolean handle(StructureTemplateDataRequestPacket packet) {
        return false;
    }

    default boolean handle(StructureTemplateDataResponsePacket packet) {
        return false;
    }

    default boolean handle(TakeItemEntityPacket packet) {
        return false;
    }

    default boolean handle(TextPacket packet) {
        return false;
    }

    default boolean handle(TickSyncPacket packet) {
        return false;
    }

    default boolean handle(TransferPacket packet) {
        return false;
    }

    default boolean handle(UpdateAttributesPacket packet) {
        return false;
    }

    default boolean handle(UpdateBlockPacket packet) {
        return false;
    }

    default boolean handle(UpdateBlockPropertiesPacket packet) {
        return false;
    }

    default boolean handle(UpdateBlockSyncedPacket packet) {
        return false;
    }

    default boolean handle(UpdateEquipPacket packet) {
        return false;
    }

    default boolean handle(UpdateSoftEnumPacket packet) {
        return false;
    }

    default boolean handle(UpdateTradePacket packet) {
        return false;
    }

    default boolean handle(BiomeDefinitionListPacket packet) {
        return false;
    }

    default boolean handle(LevelSoundEvent2Packet packet) {
        return false;
    }

    default boolean handle(NetworkChunkPublisherUpdatePacket packet) {
        return false;
    }

    default boolean handle(VideoStreamConnectPacket packet) {
        return false;
    }

    default boolean handle(CodeBuilderPacket packet) {
        return false;
    }

    default boolean handle(EmoteListPacket packet) {
        return false;
    }

    default boolean handle(ItemStackRequestPacket packet) {
        return false;
    }

    default boolean handle(ItemStackResponsePacket packet) {
        return false;
    }

    default boolean handle(PlayerArmorDamagePacket packet) {
        return false;
    }

    default boolean handle(PlayerEnchantOptionsPacket packet) {
        return false;
    }

    default boolean handle(CreativeContentPacket packet) {
        return false;
    }

    default boolean handle(UpdatePlayerGameTypePacket packet) {
        return false;
    }

    default boolean handle(PositionTrackingDBServerBroadcastPacket packet) {
        return false;
    }

    default boolean handle(PositionTrackingDBClientRequestPacket packet) {
        return false;
    }

    default boolean handle(PacketViolationWarningPacket packet) {
        return false;
    }

    default boolean handle(DebugInfoPacket packet) {
        return false;
    }

    default boolean handle(MotionPredictionHintsPacket packet) {
        return false;
    }

    default boolean handle(AnimateEntityPacket packet) {
        return false;
    }

    default boolean handle(CameraShakePacket packet) {
        return false;
    }

    default boolean handle(CorrectPlayerMovePredictionPacket packet) {
        return false;
    }

    default boolean handle(PlayerFogPacket packet) {
        return false;
    }

    default boolean handle(ItemComponentPacket packet) {
        return false;
    }

    default boolean handle(ClientboundDebugRendererPacket packet) {
        return false;
    }

    default boolean handle(SyncEntityPropertyPacket packet) {
        return false;
    }

    default boolean handle(AddVolumeEntityPacket packet) {
        return false;
    }

    default boolean handle(RemoveVolumeEntityPacket packet) {
        return false;
    }

    default boolean handle(NpcDialoguePacket packet) {
        return false;
    }

    default boolean handle(SimulationTypePacket packet) {
        return false;
    }

    default boolean handle(EduUriResourcePacket packet) {
        return false;
    }

    default boolean handle(CreatePhotoPacket packet) {
        return false;
    }

    default boolean handle(UpdateSubChunkBlocksPacket packet) {
        return false;
    }

    default boolean handle(SubChunkPacket packet) {
        return false;
    }

    default boolean handle(SubChunkRequestPacket packet) {
        return false;
    }

    default boolean handle(PhotoInfoRequestPacket packet) {
        return false;
    }

    default boolean handle(PlayerStartItemCooldownPacket packet) {
        return false;
    }

    default boolean handle(ScriptMessagePacket packet) {
        return false;
    }

    default boolean handle(CodeBuilderSourcePacket packet) {
        return false;
    }

    default boolean handle(TickingAreasLoadStatusPacket packet) {
        return false;
    }

    default boolean handle(DimensionDataPacket packet) {
        return false;
    }

    default boolean handle(AgentActionEventPacket packet) {
        return false;
    }

    default boolean handle(ChangeMobPropertyPacket packet) {
        return false;
    }

    default boolean handle(LessonProgressPacket packet) {
        return false;
    }

    default boolean handle(RequestAbilityPacket packet) {
        return false;
    }

    default boolean handle(RequestPermissionsPacket packet) {
        return false;
    }

    default boolean handle(ToastRequestPacket packet) {
        return false;
    }

    default boolean handle(UpdateAbilitiesPacket packet) {
        return false;
    }

    default boolean handle(DeathInfoPacket packet) {
        return false;
    }
}
