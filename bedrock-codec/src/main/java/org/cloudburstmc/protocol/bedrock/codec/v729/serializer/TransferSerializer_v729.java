package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.TransferSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.TransferPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferSerializer_v729 extends TransferSerializer_v291 {

    public static final TransferSerializer_v729 INSTANCE = new TransferSerializer_v729();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TransferPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isReloadWorld());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TransferPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setReloadWorld(buffer.readBoolean());
    }
}
