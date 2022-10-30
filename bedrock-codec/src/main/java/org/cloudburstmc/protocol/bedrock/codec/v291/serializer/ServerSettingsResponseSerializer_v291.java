package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerSettingsResponsePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerSettingsResponseSerializer_v291 implements BedrockPacketSerializer<ServerSettingsResponsePacket> {
    public static final ServerSettingsResponseSerializer_v291 INSTANCE = new ServerSettingsResponseSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerSettingsResponsePacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getFormId());
        helper.writeString(buffer, packet.getFormData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerSettingsResponsePacket packet) {
        packet.setFormId(VarInts.readUnsignedInt(buffer));
        packet.setFormData(helper.readString(buffer));
    }
}
