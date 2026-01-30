package org.cloudburstmc.protocol.bedrock.packet;

import org.cloudburstmc.protocol.common.PacketHandler;
import org.cloudburstmc.protocol.common.PacketSignal;

public interface BedrockPacketHandler extends PacketHandler {

    default PacketSignal handlePacket(BedrockPacket packet) {
        // This call is kept for backwards compatible
        return packet.handle(this);
    }

    default void onDisconnect(CharSequence reason) {
    }

    @Deprecated
    default PacketSignal handle(AdventureSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AnimatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AnvilDamagePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AvailableEntityIdentifiersPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(BlockEntityDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(BlockPickRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(BookEditPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientCacheBlobStatusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientCacheMissResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientCacheStatusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientToServerHandshakePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CommandBlockUpdatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CommandRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CompletedUsingItemPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ContainerClosePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CraftingEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EducationSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EmotePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EntityEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EntityFallPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EntityPickRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(FilterTextPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(InteractPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(InventoryContentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(InventorySlotPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(InventoryTransactionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ItemFrameDropItemPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LabTablePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LecternUpdatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LevelEventGenericPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LevelSoundEvent1Packet packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LevelSoundEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LoginPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MapInfoRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MobArmorEquipmentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MobEquipmentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ModalFormResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MoveEntityAbsolutePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MovePlayerPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MultiplayerSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(NetworkStackLatencyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PhotoTransferPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerActionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerAuthInputPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerHotbarPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerInputPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerSkinPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PurchaseReceiptPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RequestChunkRadiusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ResourcePackChunkRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ResourcePackClientResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RiderJumpPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerSettingsRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetDefaultGameTypePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetLocalPlayerAsInitializedPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetPlayerGameTypePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SubClientLoginPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AddBehaviorTreePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AddEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AddHangingEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AddItemEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AddPaintingPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AddPlayerPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AvailableCommandsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(BlockEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(BossEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ChangeDimensionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ChunkRadiusUpdatedPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundMapItemDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CommandOutputPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ContainerOpenPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ContainerSetDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CraftingDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(DisconnectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ExplodePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LevelChunkPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(GameRulesChangedPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(GuiDataPickItemPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(HurtArmorPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AutomationClientConnectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LevelEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MapCreateLockedCopyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MobEffectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ModalFormRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MoveEntityDeltaPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(NetworkSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(NpcRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(OnScreenTextureAnimationPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerListPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlaySoundPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayStatusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RemoveEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RemoveObjectivePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ResourcePackChunkDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ResourcePackDataInfoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ResourcePacksInfoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ResourcePackStackPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RespawnPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ScriptCustomEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerSettingsResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerToClientHandshakePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetCommandsEnabledPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetDifficultyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetDisplayObjectivePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetEntityDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetEntityLinkPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetEntityMotionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetHealthPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetLastHurtByPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetScoreboardIdentityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetScorePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetSpawnPositionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetTimePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SettingsCommandPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetTitlePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ShowCreditsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ShowProfilePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ShowStoreOfferPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SimpleEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SpawnExperienceOrbPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SpawnParticleEffectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(StartGamePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(StopSoundPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(StructureBlockUpdatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(StructureTemplateDataRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(StructureTemplateDataResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(TakeItemEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(TextPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(TickSyncPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(TransferPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateAttributesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateBlockPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateBlockPropertiesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateBlockSyncedPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateEquipPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateSoftEnumPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateTradePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(BiomeDefinitionListPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LevelSoundEvent2Packet packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(NetworkChunkPublisherUpdatePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(VideoStreamConnectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CodeBuilderPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EmoteListPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ItemStackRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ItemStackResponsePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerArmorDamagePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerEnchantOptionsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CreativeContentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdatePlayerGameTypePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PositionTrackingDBServerBroadcastPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PositionTrackingDBClientRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PacketViolationWarningPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(DebugInfoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MotionPredictionHintsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AnimateEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraShakePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CorrectPlayerMovePredictionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerFogPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ItemComponentPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundDebugRendererPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SyncEntityPropertyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AddVolumeEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RemoveVolumeEntityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(NpcDialoguePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SimulationTypePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EduUriResourcePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CreatePhotoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateSubChunkBlocksPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SubChunkPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SubChunkRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PhotoInfoRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerStartItemCooldownPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ScriptMessagePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CodeBuilderSourcePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(TickingAreasLoadStatusPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(DimensionDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AgentActionEventPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ChangeMobPropertyPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(LessonProgressPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RequestAbilityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RequestPermissionsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ToastRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateAbilitiesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateAdventureSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(DeathInfoPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(EditorNetworkPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(FeatureRegistryPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerStatsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RequestNetworkSettingsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(GameTestRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(GameTestResultsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateClientInputLocksPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientCheatAbilityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraPresetsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraInstructionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UnlockedRecipesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CompressedBiomeDefinitionListPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(TrimDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(OpenSignPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AgentAnimationPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(RefreshEntitlementsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ToggleCrafterSlotRequestPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetPlayerInventoryOptionsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetHudPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(AwardAchievementPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundCloseFormPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerboundLoadingScreenPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(JigsawStructureDataPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CurrentStructureFeaturePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerboundDiagnosticsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraAimAssistPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ContainerRegistryCleanupPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MovementEffectPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(SetMovementAuthorityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraAimAssistPresetsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraAimAssistInstructionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(MovementPredictionSyncPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(UpdateClientOptionsPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerVideoCapturePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerUpdateEntityOverridesPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(PlayerLocationPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundControlSchemeSetPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(DebugDrawerPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerboundPackSettingChangePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(GraphicsParameterOverridePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerPlayerPostMovePositionPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ServerboundDataStorePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundDataStorePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraAimAssistActorPriorityPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(CameraSplinePacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundDataDrivenUICloseAllScreensPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundDataDrivenUIReloadPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundDataDrivenUIShowScreenPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(ClientboundTextureShiftPacket packet) {
        return PacketSignal.UNHANDLED;
    }

    @Deprecated
    default PacketSignal handle(VoxelShapesPacket packet) {
        return PacketSignal.UNHANDLED;
    }
}
