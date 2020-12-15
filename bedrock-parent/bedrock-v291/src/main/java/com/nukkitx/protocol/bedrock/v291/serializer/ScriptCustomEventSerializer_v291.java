package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ScriptCustomEventPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScriptCustomEventSerializer_v291 implements BedrockPacketSerializer<ScriptCustomEventPacket> {
    public static final ScriptCustomEventSerializer_v291 INSTANCE = new ScriptCustomEventSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ScriptCustomEventPacket packet) {
        helper.writeString(buffer, packet.getEventName());
        helper.writeString(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ScriptCustomEventPacket packet) {
        packet.setEventName(helper.readString(buffer));
        packet.setData(helper.readString(buffer));
    }
}

