package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ServerSettingsResponsePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerSettingsResponseSerializer_v291 implements BedrockPacketSerializer<ServerSettingsResponsePacket> {
    public static final ServerSettingsResponseSerializer_v291 INSTANCE = new ServerSettingsResponseSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ServerSettingsResponsePacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getFormId());
        helper.writeString(buffer, packet.getFormData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ServerSettingsResponsePacket packet) {
        packet.setFormId(VarInts.readUnsignedInt(buffer));
        packet.setFormData(helper.readString(buffer));
    }
}
