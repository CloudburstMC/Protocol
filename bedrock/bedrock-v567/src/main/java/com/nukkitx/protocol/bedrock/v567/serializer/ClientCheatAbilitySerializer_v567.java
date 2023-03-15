package com.nukkitx.protocol.bedrock.v567.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ClientCheatAbilityPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientCheatAbilitySerializer_v567 implements BedrockPacketSerializer<ClientCheatAbilityPacket> {

    public static final ClientCheatAbilitySerializer_v567 INSTANCE = new ClientCheatAbilitySerializer_v567();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ClientCheatAbilityPacket packet) {
        helper.writePlayerAbilities(buffer, helper, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ClientCheatAbilityPacket packet) {
        helper.readPlayerAbilities(buffer, helper, packet);
    }
}
