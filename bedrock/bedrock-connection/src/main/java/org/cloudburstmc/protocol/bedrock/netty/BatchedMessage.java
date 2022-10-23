package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;

import java.util.List;

public class BatchedMessage extends AbstractReferenceCounted {

    private final List<ByteBuf> messages;

    public BatchedMessage(List<ByteBuf> messages) {
        this.messages = messages;
    }

    public List<ByteBuf> getMessages() {
        return messages;
    }

    public int size() {
        int size = messages.size() * 5; // header size
        for (ByteBuf message : messages) {
            size += message.readableBytes();
        }
        return size;
    }

    @Override
    protected void deallocate() {
        for (ByteBuf message : messages) {
            message.release();
        }
    }

    @Override
    public BatchedMessage touch(Object hint) {
        for (ByteBuf message : messages) {
            message.touch(hint);
        }
        return this;
    }
}
