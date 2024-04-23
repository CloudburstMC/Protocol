package org.cloudburstmc.protocol.bedrock.codec.v567;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v557.BedrockCodecHelper_v557;
import org.cloudburstmc.protocol.bedrock.codec.v560.Bedrock_v560;
import org.cloudburstmc.protocol.bedrock.codec.v567.serializer.ClientCheatAbilitySerializer_v567;
import org.cloudburstmc.protocol.bedrock.codec.v567.serializer.CommandRequestSerializer_v567;
import org.cloudburstmc.protocol.bedrock.codec.v567.serializer.CraftingDataSerializer_v567;
import org.cloudburstmc.protocol.bedrock.codec.v567.serializer.StartGameSerializer_v567;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v567 extends Bedrock_v560 {


    public static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v560.SOUND_EVENTS.toBuilder()
            .replace(458, SoundEvent.INSERT)
            .insert(459, SoundEvent.PICKUP)
            .insert(460, SoundEvent.INSERT_ENCHANTED)
            .insert(461, SoundEvent.PICKUP_ENCHANTED)
            .insert(462, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v560.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(567)
            .minecraftVersion("1.19.60")
            .helper(() -> new BedrockCodecHelper_v557(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(StartGamePacket.class, new StartGameSerializer_v567())
            .updateSerializer(CommandRequestPacket.class, new CommandRequestSerializer_v567())
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(CraftingDataPacket.class, new CraftingDataSerializer_v567())
            .registerPacket(ClientCheatAbilityPacket::new, new ClientCheatAbilitySerializer_v567(), 197, PacketRecipient.CLIENT)
            .build();
}
