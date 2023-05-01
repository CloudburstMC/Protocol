package org.cloudburstmc.protocol.bedrock.codec.v361.serializer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.nbt.NbtType;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventGenericPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor
public class LevelEventGenericSerializer_v361 implements BedrockPacketSerializer<LevelEventGenericPacket> {

    private final TypeMap<LevelEventType> typeMap;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelEventGenericPacket packet) {
        VarInts.writeInt(buffer, typeMap.getId(packet.getType()));
        helper.writeTagValue(buffer, packet.getTag());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelEventGenericPacket packet) {
        int eventId = VarInts.readInt(buffer);
        packet.setType(typeMap.getType(eventId));
        packet.setTag(helper.readTagValue(buffer, NbtType.COMPOUND));
    }
}
