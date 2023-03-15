package com.nukkitx.protocol.bedrock.v534.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.UpdateAbilitiesPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAbilitiesSerializer_v534 implements BedrockPacketSerializer<UpdateAbilitiesPacket> {
    public static final UpdateAbilitiesSerializer_v534 INSTANCE = new UpdateAbilitiesSerializer_v534();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAbilitiesPacket packet) {
        helper.writePlayerAbilities(buffer, helper, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateAbilitiesPacket packet) {
        helper.readPlayerAbilities(buffer, helper, packet);
    }
}