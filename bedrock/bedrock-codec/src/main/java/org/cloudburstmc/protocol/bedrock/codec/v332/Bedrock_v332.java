package org.cloudburstmc.protocol.bedrock.codec.v332;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.AvailableCommandsSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.Bedrock_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.AddPaintingSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.EventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.NetworkStackLatencySerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.ResourcePacksInfoSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.SpawnParticleEffectSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.StartGameSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.TextSerializer_v332;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.packet.AddPaintingPacket;
import org.cloudburstmc.protocol.bedrock.packet.AvailableCommandsPacket;
import org.cloudburstmc.protocol.bedrock.packet.EventPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.NetworkStackLatencyPacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.cloudburstmc.protocol.bedrock.packet.SpawnParticleEffectPacket;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v332 extends Bedrock_v313 {

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = Bedrock_v313.COMMAND_PARAMS.toBuilder()
            .update(14, 15, CommandParam.FILE_PATH)
            .update(18, 19, CommandParam.INT_RANGE)
            .shift(26, 2)
            .build();

    protected static final TypeMap<EntityData> ENTITY_DATA = Bedrock_v313.ENTITY_DATA.toBuilder()
            .insert(94, EntityData.AREA_EFFECT_CLOUD_DURATION)
            .insert(95, EntityData.AREA_EFFECT_CLOUD_SPAWN_TIME)
            .insert(96, EntityData.AREA_EFFECT_CLOUD_CHANGE_RATE)
            .insert(97, EntityData.AREA_EFFECT_CLOUD_CHANGE_ON_PICKUP)
            .insert(98, EntityData.AREA_EFFECT_CLOUD_COUNT)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v313.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_BLOCK + 12, LevelEvent.CAULDRON_FILL_LAVA)
            .insert(LEVEL_EVENT_BLOCK + 13, LevelEvent.CAULDRON_TAKE_LAVA)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v313.CODEC.toBuilder()
            .protocolVersion(332)
            .minecraftVersion("1.9.0")
            .helper(() -> new BedrockCodecHelper_v332(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES))
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v332.INSTANCE)
            .updateSerializer(TextPacket.class, TextSerializer_v332.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v332.INSTANCE)
            .updateSerializer(AddPaintingPacket.class, AddPaintingSerializer_v332.INSTANCE)
            .updateSerializer(EventPacket.class, EventSerializer_v332.INSTANCE)
            .updateSerializer(NetworkStackLatencyPacket.class, NetworkStackLatencySerializer_v332.INSTANCE)
            .updateSerializer(SpawnParticleEffectPacket.class, SpawnParticleEffectSerializer_v332.INSTANCE)
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v291(COMMAND_PARAMS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .registerPacket(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS), 123)
            .build();
}
