package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.UpdateTagsPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTagsSerializer_v754 implements JavaPacketSerializer<UpdateTagsPacket> {
    public static final UpdateTagsSerializer_v754 INSTANCE = new UpdateTagsSerializer_v754();

    private static final int TAG_TYPE_COUNT = 4;

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, UpdateTagsPacket packet) throws PacketSerializeException {
        for (Map<String, int[]> tags : packet.getTags().values()) {
            helper.writeArray(buffer, tags.entrySet(), (buf, tag) -> {
                helper.writeString(buffer, tag.getKey());
                helper.writeIntArray(buffer, tag.getValue(), helper::writeVarInt);
            });
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, UpdateTagsPacket packet) throws PacketSerializeException {
        List<Map<String, int[]>> allTags = new ObjectArrayList<>();
        for (int i = 0; i < TAG_TYPE_COUNT; i++) {
            Map<String, int[]> tags = new HashMap<>();
            helper.readArray(buffer, allTags, (buf) -> {
                String name = helper.readString(buffer);
                int[] entries = helper.readIntArray(buffer, helper::readVarInt);
                tags.put(name, entries);
                return tags;
            });
        }
        allTags.forEach(map -> packet.getTags().put(StringUtil.EMPTY_STRING, map));
    }
}
