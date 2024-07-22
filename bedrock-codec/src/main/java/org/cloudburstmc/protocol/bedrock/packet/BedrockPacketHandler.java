package org.cloudburstmc.protocol.bedrock.packet;

import org.cloudburstmc.protocol.common.PacketHandler;
import org.cloudburstmc.protocol.common.PacketSignal;

public interface BedrockPacketHandler extends PacketHandler {

    default PacketSignal handlePacket(BedrockPacket packet) {
        return packet.handle(this);
    }

    default void onDisconnect(String reason) {
    }

    default PacketSignal handle(AdventureSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AnimatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AnvilDamagePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AvailableEntityIdentifiersPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(BlockEntityDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(BlockPickRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(BookEditPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ClientCacheBlobStatusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ClientCacheMissResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ClientCacheStatusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ClientToServerHandshakePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CommandBlockUpdatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CommandRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CompletedUsingItemPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ContainerClosePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CraftingEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EducationSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EmotePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EntityEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EntityFallPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EntityPickRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(FilterTextPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(InteractPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(InventoryContentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(InventorySlotPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(InventoryTransactionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ItemFrameDropItemPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LabTablePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LecternUpdatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LevelEventGenericPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LevelSoundEvent1Packet packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LevelSoundEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LoginPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MapInfoRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MobArmorEquipmentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MobEquipmentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ModalFormResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MoveEntityAbsolutePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MovePlayerPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MultiplayerSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(NetworkStackLatencyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PhotoTransferPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerActionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerAuthInputPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerHotbarPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerInputPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerSkinPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PurchaseReceiptPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RequestChunkRadiusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ResourcePackChunkRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ResourcePackClientResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RiderJumpPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ServerSettingsRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetDefaultGameTypePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetLocalPlayerAsInitializedPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetPlayerGameTypePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SubClientLoginPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AddBehaviorTreePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AddEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AddHangingEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AddItemEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AddPaintingPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AddPlayerPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AvailableCommandsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(BlockEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(BossEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CameraPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ChangeDimensionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ChunkRadiusUpdatedPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ClientboundMapItemDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CommandOutputPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ContainerOpenPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ContainerSetDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CraftingDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(DisconnectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ExplodePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LevelChunkPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(GameRulesChangedPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(GuiDataPickItemPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(HurtArmorPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AutomationClientConnectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LevelEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MapCreateLockedCopyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MobEffectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ModalFormRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MoveEntityDeltaPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(NetworkSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(NpcRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(OnScreenTextureAnimationPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerListPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlaySoundPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayStatusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RemoveEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RemoveObjectivePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ResourcePackChunkDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ResourcePackDataInfoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ResourcePacksInfoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ResourcePackStackPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RespawnPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ScriptCustomEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ServerSettingsResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ServerToClientHandshakePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetCommandsEnabledPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetDifficultyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetDisplayObjectivePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetEntityDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetEntityLinkPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetEntityMotionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetHealthPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetLastHurtByPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetScoreboardIdentityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetScorePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetSpawnPositionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetTimePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SettingsCommandPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetTitlePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ShowCreditsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ShowProfilePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ShowStoreOfferPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SimpleEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SpawnExperienceOrbPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SpawnParticleEffectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(StartGamePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(StopSoundPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(StructureBlockUpdatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(StructureTemplateDataRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(StructureTemplateDataResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(TakeItemEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(TextPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(TickSyncPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(TransferPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateAttributesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateBlockPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateBlockPropertiesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateBlockSyncedPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateEquipPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateSoftEnumPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateTradePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(BiomeDefinitionListPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LevelSoundEvent2Packet packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(NetworkChunkPublisherUpdatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(VideoStreamConnectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CodeBuilderPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EmoteListPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ItemStackRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ItemStackResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerArmorDamagePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerEnchantOptionsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CreativeContentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdatePlayerGameTypePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PositionTrackingDBServerBroadcastPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PositionTrackingDBClientRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PacketViolationWarningPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(DebugInfoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(MotionPredictionHintsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AnimateEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CameraShakePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CorrectPlayerMovePredictionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerFogPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ItemComponentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ClientboundDebugRendererPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SyncEntityPropertyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AddVolumeEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RemoveVolumeEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(NpcDialoguePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SimulationTypePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EduUriResourcePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CreatePhotoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateSubChunkBlocksPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SubChunkPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SubChunkRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PhotoInfoRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(PlayerStartItemCooldownPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ScriptMessagePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CodeBuilderSourcePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(TickingAreasLoadStatusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(DimensionDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AgentActionEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ChangeMobPropertyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(LessonProgressPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RequestAbilityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RequestPermissionsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ToastRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateAbilitiesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateAdventureSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(DeathInfoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(EditorNetworkPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(FeatureRegistryPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ServerStatsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RequestNetworkSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(GameTestRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(GameTestResultsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UpdateClientInputLocksPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ClientCheatAbilityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CameraPresetsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CameraInstructionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(UnlockedRecipesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CompressedBiomeDefinitionListPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(TrimDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(OpenSignPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AgentAnimationPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(RefreshEntitlementsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ToggleCrafterSlotRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetPlayerInventoryOptionsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(SetHudPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(AwardAchievementPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ClientboundCloseFormPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ServerboundLoadingScreenPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(JigsawStructureDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(CurrentStructureFeaturePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    default PacketSignal handle(ServerboundDiagnosticsPacket packet) {
        return PacketSignal.UNHANDLED;
    }
}
