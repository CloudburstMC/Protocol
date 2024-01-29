package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
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
    private int headerLength;
    private BedrockPacket packet;
    private ByteBuf packetBuffer;

    public BedrockPacketWrapper(int packetId, int senderSubClientId, int targetSubClientId, BedrockPacket packet, ByteBuf packetBuffer) {
        this.packetId = packetId;
        this.senderSubClientId = senderSubClientId;
        this.targetSubClientId = targetSubClientId;
        this.packet = packet;
        this.packetBuffer = packetBuffer;
    }

    @Override
    protected void deallocate() {
        ReferenceCountUtil.safeRelease(this.packet);
        ReferenceCountUtil.safeRelease(this.packetBuffer);
    }

    @Override
    public BedrockPacketWrapper touch(Object hint) {
        ReferenceCountUtil.touch(this.packet);
        ReferenceCountUtil.touch(this.packetBuffer);
        return this;
    }

    @Override
    public BedrockPacketWrapper retain() {
        return (BedrockPacketWrapper) super.retain();
    }
}
