package org.cloudburstmc.protocol.bedrock.codec.v440.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SyncEntityPropertyPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SyncEntityPropertySerializer_v440 implements BedrockPacketSerializer<SyncEntityPropertyPacket> {

    public static final SyncEntityPropertySerializer_v440 INSTANCE = new SyncEntityPropertySerializer_v440();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SyncEntityPropertyPacket packet) {
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SyncEntityPropertyPacket packet) {
        packet.setData(helper.readTag(buffer, NbtMap.class));
    }
}
