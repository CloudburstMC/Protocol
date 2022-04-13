package org.cloudburstmc.protocol.bedrock.codec.v419;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.AvailableCommandsSerializer_v388;
import org.cloudburstmc.protocol.bedrock.codec.v408.Bedrock_v408;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.AnimateEntitySerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.CameraShakeSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ContainerCloseSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.CorrectPlayerMovePredictionSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ItemComponentSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ItemStackResponseSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.MotionPredictionHintsSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.MoveEntityDeltaSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.MovePlayerSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.PlayerAuthInputSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.PlayerFogSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ResourcePackStackSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.SetEntityDataSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.StartGameSerializer_v419;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.UpdateAttributesSerializer_v419;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.packet.AnimateEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.AvailableCommandsPacket;
import org.cloudburstmc.protocol.bedrock.packet.CameraShakePacket;
import org.cloudburstmc.protocol.bedrock.packet.ContainerClosePacket;
import org.cloudburstmc.protocol.bedrock.packet.CorrectPlayerMovePredictionPacket;
import org.cloudburstmc.protocol.bedrock.packet.ItemComponentPacket;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent1Packet;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent2Packet;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.MotionPredictionHintsPacket;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityDeltaPacket;
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayerFogPacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackStackPacket;
import org.cloudburstmc.protocol.bedrock.packet.SetEntityDataPacket;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.bedrock.packet.UpdateAttributesPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v419 extends Bedrock_v408 {

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = Bedrock_v408.COMMAND_PARAMS.toBuilder()
            .shift(14, 1) // From FILE_PATH, move up 1
            .shift(30, 1) // From STRING, move up 1
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v408.SOUND_EVENTS.toBuilder()
            .replace(317, SoundEvent.EQUIP_NETHERITE)
            .insert(318, SoundEvent.UNDEFINED)
            .build();

    public static BedrockCodec CODEC = Bedrock_v408.CODEC.toBuilder()
            .protocolVersion(419)
            .minecraftVersion("1.16.100")
            .helper(() -> new BedrockCodecHelper_v419(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES))
            .updateSerializer(ResourcePackStackPacket.class, ResourcePackStackSerializer_v419.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v419.INSTANCE)
            .updateSerializer(MovePlayerPacket.class, MovePlayerSerializer_v419.INSTANCE)
            .updateSerializer(UpdateAttributesPacket.class, UpdateAttributesSerializer_v419.INSTANCE)
            .updateSerializer(SetEntityDataPacket.class, SetEntityDataSerializer_v419.INSTANCE)
            .updateSerializer(ContainerClosePacket.class, ContainerCloseSerializer_v419.INSTANCE)
            .updateSerializer(MoveEntityDeltaPacket.class, MoveEntityDeltaSerializer_v419.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v419.INSTANCE)
            .updateSerializer(ItemStackResponsePacket.class, ItemStackResponseSerializer_v419.INSTANCE)
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v388(COMMAND_PARAMS))
            .registerPacket(MotionPredictionHintsPacket.class, MotionPredictionHintsSerializer_v419.INSTANCE, 157)
            .registerPacket(AnimateEntityPacket.class, AnimateEntitySerializer_v419.INSTANCE, 158)
            .registerPacket(CameraShakePacket.class, CameraShakeSerializer_v419.INSTANCE, 159)
            .registerPacket(PlayerFogPacket.class, PlayerFogSerializer_v419.INSTANCE, 160)
            .registerPacket(CorrectPlayerMovePredictionPacket.class, CorrectPlayerMovePredictionSerializer_v419.INSTANCE, 161)
            .registerPacket(ItemComponentPacket.class, ItemComponentSerializer_v419.INSTANCE, 162)
            .build();
}
