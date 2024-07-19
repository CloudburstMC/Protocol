package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectPool;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.util.PacketFlag;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class BedrockPacketWrapper extends AbstractReferenceCounted {
    private static final ObjectPool<BedrockPacketWrapper> RECYCLER = ObjectPool.newPool(BedrockPacketWrapper::new);
    private final ObjectPool.Handle<BedrockPacketWrapper> handle;

    private int packetId;
    private int senderSubClientId;
    private int targetSubClientId;
    private int headerLength;
    private BedrockPacket packet;
    private ByteBuf packetBuffer;
    private Set<PacketFlag> flags = new ObjectOpenHashSet<>();

    public static BedrockPacketWrapper create(int packetId, int senderSubClientId, int targetSubClientId, BedrockPacket packet, ByteBuf packetBuffer) {
        BedrockPacketWrapper wrapper = RECYCLER.get();
        if (wrapper.packet != null || wrapper.packetBuffer != null) {
            throw new IllegalStateException("BedrockPacketWrapper was not deallocated");
        }

        wrapper.packetId = packetId;
        wrapper.senderSubClientId = senderSubClientId;
        wrapper.targetSubClientId = targetSubClientId;
        wrapper.packet = packet;
        wrapper.packetBuffer = packetBuffer;

        wrapper.setRefCnt(1);
        return wrapper;
    }

    public static BedrockPacketWrapper create() {
        BedrockPacketWrapper wrapper = RECYCLER.get();
        if (wrapper.packet != null || wrapper.packetBuffer != null) {
            throw new IllegalStateException("BedrockPacketWrapper was not deallocated");
        }

        wrapper.setRefCnt(1);
        return wrapper;
    }

    private BedrockPacketWrapper(ObjectPool.Handle<BedrockPacketWrapper> handle) {
        this.handle = handle;
    }

    public void setFlag(PacketFlag flag) {
        this.flags.add(flag);
    }

    public boolean hasFlag(PacketFlag flag) {
        return this.flags.contains(flag);
    }

    public void unsetFlag(PacketFlag flag) {
        this.flags.remove(flag);
    }

    @Override
    protected void deallocate() {
        ReferenceCountUtil.safeRelease(this.packet);
        ReferenceCountUtil.safeRelease(this.packetBuffer);
        this.packetId = 0;
        this.senderSubClientId = 0;
        this.targetSubClientId = 0;
        this.headerLength = 0;
        this.packet = null;
        this.packetBuffer = null;
        this.flags.clear();
        this.handle.recycle(this);
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
