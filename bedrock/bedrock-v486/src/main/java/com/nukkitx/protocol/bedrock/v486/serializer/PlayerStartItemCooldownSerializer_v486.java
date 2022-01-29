package com.nukkitx.protocol.bedrock.v486.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PlayerStartItemCooldownPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerStartItemCooldownSerializer_v486 implements BedrockPacketSerializer<PlayerStartItemCooldownPacket> {

    public static final PlayerStartItemCooldownSerializer_v486 INSTANCE = new PlayerStartItemCooldownSerializer_v486();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerStartItemCooldownPacket packet) {
        helper.writeString(buffer, packet.getItemCategory());
        VarInts.writeInt(buffer, packet.getCooldownDuration());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerStartItemCooldownPacket packet) {
        packet.setItemCategory(helper.readString(buffer));
        packet.setCooldownDuration(VarInts.readInt(buffer));
    }
}
