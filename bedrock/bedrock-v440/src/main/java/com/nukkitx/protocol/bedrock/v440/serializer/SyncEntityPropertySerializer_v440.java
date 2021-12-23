package com.nukkitx.protocol.bedrock.v440.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.SyncEntityPropertyPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SyncEntityPropertySerializer_v440 implements BedrockPacketSerializer<SyncEntityPropertyPacket> {

    public static final SyncEntityPropertySerializer_v440 INSTANCE = new SyncEntityPropertySerializer_v440();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SyncEntityPropertyPacket packet) {
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SyncEntityPropertyPacket packet) {
        packet.setData(helper.readTag(buffer));
    }
}
