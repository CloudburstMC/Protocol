package org.cloudburstmc.protocol.bedrock.codec.v898.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

/**
 * Education Edition variant of the v898 StartGamePacket serializer.
 * <p>
 * Minecraft Education Edition 1.21.132 (protocol 898) expects three additional
 * string fields at the end of LevelSettings, after {@code ownerId}. Without
 * these fields, the Education client's deserializer reads into subsequent
 * packet data and disconnects.
 * <p>
 * These fields are not present in standard Bedrock protocol 898.
 */
public class EducationStartGameSerializer_v898 extends StartGameSerializer_v898 {

    public static final EducationStartGameSerializer_v898 INSTANCE = new EducationStartGameSerializer_v898();

    @Override
    protected void writeLevelSettings(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.writeLevelSettings(buffer, helper, packet);
        helper.writeString(buffer, packet.getEducationReferrerId());
        helper.writeString(buffer, packet.getEducationCreatorWorldId());
        helper.writeString(buffer, packet.getEducationCreatorId());
    }

    @Override
    protected void readLevelSettings(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.readLevelSettings(buffer, helper, packet);
        packet.setEducationReferrerId(helper.readString(buffer));
        packet.setEducationCreatorWorldId(helper.readString(buffer));
        packet.setEducationCreatorId(helper.readString(buffer));
    }
}
