package com.nukkitx.protocol.bedrock.beta.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.ChatRestrictionLevel;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v534.serializer.StartGameSerializer_v534;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializerBeta extends StartGameSerializer_v534 {

    public static final StartGameSerializerBeta INSTANCE = new StartGameSerializerBeta();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
        super.serialize(buffer, helper, packet, session);

        buffer.writeBoolean(packet.isClientSideGenerationEnabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
        super.deserialize(buffer, helper, packet, session);

        packet.setClientSideGenerationEnabled(buffer.readBoolean());
    }

    @Override
    protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.writeLevelSettings(buffer, helper, packet);

        buffer.writeByte(packet.getChatRestrictionLevel().ordinal());
        buffer.writeBoolean(packet.isDisablingPlayerInteractions());
    }

    @Override
    protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.readLevelSettings(buffer, helper, packet);

        packet.setChatRestrictionLevel(ChatRestrictionLevel.values()[buffer.readByte()]);
        packet.setDisablingPlayerInteractions(buffer.readBoolean());
    }
}
