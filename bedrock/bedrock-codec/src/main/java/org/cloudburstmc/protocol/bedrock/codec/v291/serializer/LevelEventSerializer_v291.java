package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor
public class LevelEventSerializer_v291 implements BedrockPacketSerializer<LevelEventPacket> {

    private final TypeMap<LevelEventType> typeMap;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelEventPacket packet) {
        VarInts.writeInt(buffer, typeMap.getId(packet.getType()));
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelEventPacket packet) {
        int eventId = VarInts.readInt(buffer);
        packet.setType(typeMap.getType(eventId));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setData(VarInts.readInt(buffer));
    }
}
