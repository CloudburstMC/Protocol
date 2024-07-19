package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectPool;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.util.PacketFlag;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class BedrockBatchWrapper extends AbstractReferenceCounted {
    private static final ObjectPool<BedrockBatchWrapper> RECYCLER = ObjectPool.newPool(BedrockBatchWrapper::new);
    private final ObjectPool.Handle<BedrockBatchWrapper> handle;

    private ByteBuf compressed;
    private CompressionAlgorithm algorithm;

    private ByteBuf uncompressed;
    private List<BedrockPacketWrapper> packets = new ObjectArrayList<>();

    private boolean modified;
    private Set<PacketFlag> flags = new ObjectOpenHashSet<>();

    private BedrockBatchWrapper(ObjectPool.Handle<BedrockBatchWrapper> handle) {
        this.handle = handle;
    }

    public static BedrockBatchWrapper newInstance() {
        return newInstance(null, null);
    }

    public static BedrockBatchWrapper newInstance(ByteBuf compressed, ByteBuf uncompressed) {
        BedrockBatchWrapper batch = RECYCLER.get();
        if (!batch.packets.isEmpty() || !batch.flags.isEmpty()) {
            throw new IllegalStateException("Batch was not deallocated");
        }

        batch.compressed = compressed;
        batch.uncompressed = uncompressed;
        batch.setRefCnt(1);
        return batch;
    }

    public static BedrockBatchWrapper create(int subClientId, BedrockPacket... packets) {
        BedrockBatchWrapper batch = BedrockBatchWrapper.newInstance();
        for (BedrockPacket packet : packets) {
            batch.getPackets().add(BedrockPacketWrapper.create(0, subClientId, 0, packet, null));
        }
        return batch;
    }

    @Override
    protected void deallocate() {
        this.packets.forEach(ReferenceCountUtil::safeRelease);
        ReferenceCountUtil.safeRelease(this.uncompressed);
        ReferenceCountUtil.safeRelease(this.compressed);
        this.compressed = null;
        this.uncompressed = null;
        this.packets.clear();
        this.modified = false;
        this.algorithm = null;
        this.flags.clear();
        this.handle.recycle(this);
    }

    public void addPacket(BedrockPacketWrapper wrapper) {
        this.packets.add(wrapper);
        this.modify();

        if (!wrapper.getFlags().isEmpty()) {
            for (PacketFlag flag : wrapper.getFlags()) {
                if (flag.canInherit()) {
                    this.flags.add(flag);
                }
            }
        }
    }

    public void modify() {
        this.modified = true;
    }

    public void setCompressed(ByteBuf compressed) {
        if (this.compressed != null) {
            this.compressed.release();
        }

        this.compressed = compressed;
        if (compressed == null) {
            this.algorithm = null;
        }
    }

    public void setCompressed(ByteBuf compressed, CompressionAlgorithm algorithm) {
        if (this.compressed != null) {
            this.compressed.release();
        }

        this.compressed = compressed;
        this.algorithm = algorithm;
    }

    public void setUncompressed(ByteBuf uncompressed) {
        if (this.uncompressed != null) {
            this.uncompressed.release();
        }
        this.uncompressed = uncompressed;
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
    public ReferenceCounted touch(Object o) {
        return this;
    }

    @Override
    public BedrockBatchWrapper retain() {
        return (BedrockBatchWrapper) super.retain();
    }

    @Override
    public BedrockBatchWrapper retain(int increment) {
        return (BedrockBatchWrapper) super.retain(increment);
    }
}
