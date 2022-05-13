package com.nukkitx.protocol.bedrock.beta.serializer;

import com.nukkitx.nbt.NbtType;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v503.serializer.StartGameSerializer_v503;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializerBeta extends StartGameSerializer_v503 {
    public static final StartGameSerializerBeta INSTANCE = new StartGameSerializerBeta();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeTag(buffer, packet.getPlayerPropertyData());
        helper.writeUuid(buffer, packet.getWorldTemplateId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
        super.deserialize(buffer, helper, packet);

        packet.setPlayerPropertyData(helper.readTag(buffer));
        packet.setWorldTemplateId(helper.readUuid(buffer));
    }
}
