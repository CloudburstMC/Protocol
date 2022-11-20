package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class BedrockPacketWrapper extends AbstractReferenceCounted {
    private int packetId;
    private int senderSubClientId;
    private int targetSubClientId;
    private BedrockPacket packet;
    private ByteBuf packetBuffer;

    @Override
    protected void deallocate() {
        ReferenceCountUtil.release(this.packet);
        ReferenceCountUtil.release(this.packetBuffer);
    }

    @Override
    public BedrockPacketWrapper touch(Object hint) {
        ReferenceCountUtil.touch(this.packet);
        ReferenceCountUtil.touch(this.packetBuffer);
        return this;
    }
}
