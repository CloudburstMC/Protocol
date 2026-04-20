package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtList;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtType;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v898.Bedrock_v898;
import org.cloudburstmc.protocol.bedrock.codec.v898.serializer.EducationStartGameSerializer_v898;
import org.cloudburstmc.protocol.bedrock.data.AuthoritativeMovementMode;
import org.cloudburstmc.protocol.bedrock.data.ChatRestrictionLevel;
import org.cloudburstmc.protocol.bedrock.data.GamePublishSetting;
import org.cloudburstmc.protocol.bedrock.data.GameType;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.SpawnBiomeType;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EducationStartGameSerializerTest {
    private static final BedrockCodecHelper CODEC_HELPER = Bedrock_v898.EDUCATION_CODEC.createHelper();
    private static final EducationStartGameSerializer_v898 SERIALIZER = EducationStartGameSerializer_v898.INSTANCE;

    @Test
    public void testEducationFieldsSurviveRoundTrip() {
        StartGamePacket packet = new StartGamePacket();
        populateRequiredFields(packet);

        packet.setEducationReferrerId("referrer-xyz");
        packet.setEducationCreatorWorldId("world-abc");
        packet.setEducationCreatorId("creator-123");

        ByteBuf buf = Unpooled.buffer();
        SERIALIZER.serialize(buf, CODEC_HELPER, packet);

        StartGamePacket out = new StartGamePacket();
        SERIALIZER.deserialize(buf, CODEC_HELPER, out);

        assertEquals("referrer-xyz", out.getEducationReferrerId());
        assertEquals("world-abc", out.getEducationCreatorWorldId());
        assertEquals("creator-123", out.getEducationCreatorId());
    }

    @SuppressWarnings("deprecation") // authoritativeMovementMode is deprecated but still written by the v898 serializer chain
    private static void populateRequiredFields(StartGamePacket packet) {
        packet.setPlayerGameType(GameType.SURVIVAL);
        packet.setPlayerPosition(Vector3f.ZERO);
        packet.setRotation(Vector2f.ZERO);
        packet.setLevelGameType(GameType.SURVIVAL);
        packet.setDefaultSpawn(Vector3i.ZERO);
        packet.setXblBroadcastMode(GamePublishSetting.NO_MULTI_PLAY);
        packet.setPlatformBroadcastMode(GamePublishSetting.NO_MULTI_PLAY);
        packet.setDefaultPlayerPermission(PlayerPermission.MEMBER);
        packet.setLevelId("test-level-id");
        packet.setLevelName("Test Level");
        packet.setPremiumWorldTemplateId("");
        packet.setMultiplayerCorrelationId("");
        packet.setVanillaVersion("1.21.130");
        packet.setBlockPalette(new NbtList<>(NbtType.COMPOUND, Collections.emptyList()));
        packet.setForceExperimentalGameplay(OptionalBoolean.empty());
        packet.setChatRestrictionLevel(ChatRestrictionLevel.NONE);
        packet.setAuthoritativeMovementMode(AuthoritativeMovementMode.SERVER_WITH_REWIND);
        packet.setSpawnBiomeType(SpawnBiomeType.DEFAULT);
        packet.setCustomBiomeName("");
        packet.setEducationProductionId("");
        packet.setServerEngine("");
        packet.setPlayerPropertyData(NbtMap.EMPTY);
        packet.setWorldTemplateId(new UUID(0, 0));
        packet.setServerId("");
        packet.setWorldId("");
        packet.setScenarioId("");
        packet.setOwnerId("");
    }
}
