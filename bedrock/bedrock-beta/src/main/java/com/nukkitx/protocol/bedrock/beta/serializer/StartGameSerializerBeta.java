package com.nukkitx.protocol.bedrock.beta.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v465.serializer.StartGameSerializer_v465;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializerBeta extends StartGameSerializer_v465 {
    public static final StartGameSerializerBeta INSTANCE = new StartGameSerializerBeta();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
        super.serialize(buffer, helper, packet, session);

        helper.writeTag(buffer, packet.getPlayerPropertyData());
        buffer.writeLongLE(packet.getBlockRegistryChecksum());
        helper.writeUuid(buffer, packet.getWorldTemplateId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
        super.deserialize(buffer, helper, packet, session);

        packet.setPlayerPropertyData(helper.readTag(buffer));
        packet.setBlockRegistryChecksum(buffer.readLongLE());
        packet.setWorldTemplateId(helper.readUuid(buffer));
    }

    @Override
    protected long readSeed(ByteBuf buf) {
        return buf.readLongLE();
    }

    @Override
    protected void writeSeed(ByteBuf buf, long seed) {
        buf.writeLongLE(seed);
    }
}
