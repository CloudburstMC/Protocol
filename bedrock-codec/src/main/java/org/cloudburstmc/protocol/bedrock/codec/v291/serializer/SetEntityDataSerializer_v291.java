package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SetEntityDataPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetEntityDataSerializer_v291 implements BedrockPacketSerializer<SetEntityDataPacket> {
    public static final SetEntityDataSerializer_v291 INSTANCE = new SetEntityDataSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetEntityDataPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeEntityData(buffer, packet.getMetadata());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetEntityDataPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        helper.readEntityData(buffer, packet.getMetadata());
    }
}
