package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

@Data
@EqualsAndHashCode(callSuper = false)
public class BedrockPacketWrapper extends AbstractReferenceCounted {
    private int packetId;
    private int senderId;
    private int clientId;
    private BedrockPacket packet;
    private ByteBuf packetBuffer;

    @Override
    protected void deallocate() {
        ReferenceCountUtil.release(packetBuffer);
    }

    @Override
    public BedrockPacketWrapper touch(Object hint) {
        ReferenceCountUtil.release(packet);
        ReferenceCountUtil.release(packetBuffer);
        return this;
    }
}
