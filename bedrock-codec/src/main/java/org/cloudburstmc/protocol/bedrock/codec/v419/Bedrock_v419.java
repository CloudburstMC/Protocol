package org.cloudburstmc.protocol.bedrock.codec.v419;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.AvailableCommandsSerializer_v388;
import org.cloudburstmc.protocol.bedrock.codec.v408.Bedrock_v408;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v419 extends Bedrock_v408 {

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = Bedrock_v408.COMMAND_PARAMS.toBuilder()
            .shift(7, 1)
            .shift(30, 1)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v408.SOUND_EVENTS.toBuilder()
            .replace(317, SoundEvent.EQUIP_NETHERITE)
            .insert(318, SoundEvent.UNDEFINED)
            .build();

    public static BedrockCodec CODEC = Bedrock_v408.CODEC.toBuilder()
            .protocolVersion(419)
            .minecraftVersion("1.16.100")
            .helper(() -> new BedrockCodecHelper_v419(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES))
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
            .registerPacket(MotionPredictionHintsPacket::new, MotionPredictionHintsSerializer_v419.INSTANCE, 157, PacketRecipient.CLIENT)
            .registerPacket(AnimateEntityPacket::new, AnimateEntitySerializer_v419.INSTANCE, 158, PacketRecipient.CLIENT)
            .registerPacket(CameraShakePacket::new, CameraShakeSerializer_v419.INSTANCE, 159, PacketRecipient.CLIENT)
            .registerPacket(PlayerFogPacket::new, PlayerFogSerializer_v419.INSTANCE, 160, PacketRecipient.CLIENT)
            .registerPacket(CorrectPlayerMovePredictionPacket::new, CorrectPlayerMovePredictionSerializer_v419.INSTANCE, 161, PacketRecipient.CLIENT)
            .registerPacket(ItemComponentPacket::new, ItemComponentSerializer_v419.INSTANCE, 162, PacketRecipient.CLIENT)
            .build();
}
