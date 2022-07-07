package org.cloudburstmc.protocol.bedrock.codec.v534.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.UpdateAdventureSettingsPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAdventureSettingsSerializer_v534 implements BedrockPacketSerializer<UpdateAdventureSettingsPacket> {
    public static final UpdateAdventureSettingsSerializer_v534 INSTANCE = new UpdateAdventureSettingsSerializer_v534();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAdventureSettingsPacket packet) {
        buffer.writeBoolean(packet.isNoPvM());
        buffer.writeBoolean(packet.isNoMvP());
        buffer.writeBoolean(packet.isImmutableWorld());
        buffer.writeBoolean(packet.isShowNameTags());
        buffer.writeBoolean(packet.isAutoJump());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateAdventureSettingsPacket packet) {
        packet.setNoPvM(buffer.readBoolean());
        packet.setNoMvP(buffer.readBoolean());
        packet.setImmutableWorld(buffer.readBoolean());
        packet.setShowNameTags(buffer.readBoolean());
        packet.setAutoJump(buffer.readBoolean());
    }
}
