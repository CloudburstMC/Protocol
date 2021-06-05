package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.cloudburstmc.protocol.common.util.TriConsumer;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Collection;
import java.util.function.BiFunction;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePacksInfoSerializer_v291 implements BedrockPacketSerializer<ResourcePacksInfoPacket> {
    public static final ResourcePacksInfoSerializer_v291 INSTANCE = new ResourcePacksInfoSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        writeArrayShortLE(buffer, packet.getBehaviorPackInfos(), helper, this::writeEntry);
        writeArrayShortLE(buffer, packet.getResourcePackInfos(), helper, this::writeEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        readArrayShortLE(buffer, packet.getBehaviorPackInfos(), helper, this::readEntry);
        readArrayShortLE(buffer, packet.getResourcePackInfos(), helper, this::readEntry);
    }

    protected ResourcePacksInfoPacket.Entry readEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        String packId = helper.readString(buffer);
        String packVersion = helper.readString(buffer);
        long packSize = buffer.readLongLE();
        String contentKey = helper.readString(buffer);
        String subPackName = helper.readString(buffer);
        String contentId = helper.readString(buffer);
        return new ResourcePacksInfoPacket.Entry(packId, packVersion, packSize, contentKey, subPackName, contentId, false, false);
    }

    protected void writeEntry(ByteBuf buffer, BedrockCodecHelper helper, ResourcePacksInfoPacket.Entry entry) {
        requireNonNull(entry, "ResourcePacketInfoPacket entry was null");

        helper.writeString(buffer, entry.getPackId());
        helper.writeString(buffer, entry.getPackVersion());
        buffer.writeLongLE(entry.getPackSize());
        helper.writeString(buffer, entry.getContentKey());
        helper.writeString(buffer, entry.getSubPackName());
        helper.writeString(buffer, entry.getContentId());
    }

    protected <T> void readArrayShortLE(ByteBuf buffer, Collection<T> array, BedrockCodecHelper helper,
                                        BiFunction<ByteBuf, BedrockCodecHelper, T> function) {
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            array.add(function.apply(buffer, helper));
        }
    }

    protected <T> void writeArrayShortLE(ByteBuf buffer, Collection<T> array, BedrockCodecHelper helper,
                                         TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer) {
        VarInts.writeUnsignedInt(buffer, array.size());
        for (T val : array) {
            consumer.accept(buffer, helper, val);
        }
    }
}
