package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.Advancement;
import org.cloudburstmc.protocol.java.data.DisplayInfo;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;
import org.cloudburstmc.protocol.java.packet.play.clientbound.UpdateAdvancementsPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAdvancementsSerializer_v754 implements JavaPacketSerializer<UpdateAdvancementsPacket> {
    public static final UpdateAdvancementsSerializer_v754 INSTANCE = new UpdateAdvancementsSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, UpdateAdvancementsPacket packet) throws PacketSerializeException {
        buffer.writeBoolean(packet.isReset());
        helper.writeArray(buffer, packet.getAdded(), (buf, advancement) -> {
            helper.writeKey(buffer, advancement.getId());
            helper.writeOptional(buffer, advancement.getParentId(), helper::writeKey);
            helper.writeOptional(buffer, advancement.getDisplayInfo(), (buf1, displayInfo) -> {
                helper.writeComponent(buffer, displayInfo.getTitle());
                helper.writeComponent(buffer, displayInfo.getDescription());
                helper.writeItemStack(buffer, displayInfo.getIcon());
                helper.writeVarInt(buffer, displayInfo.getFrameType().ordinal());
                int flags = 0;
                if (displayInfo.getBackgroundTexture() != null) {
                    flags |= 0x01;
                }
                if (displayInfo.isToast()) {
                    flags |= 0x02;
                }

                if (displayInfo.isHidden()) {
                    flags |= 0x04;
                }
                buffer.writeInt(flags);
                if (displayInfo.getBackgroundTexture() != null) {
                    helper.writeKey(buffer, displayInfo.getBackgroundTexture());
                }
                helper.writeVector2f(buffer, displayInfo.getPos());
            });
            helper.writeArray(buffer, advancement.getCriteria(), helper::writeKey);
            helper.writeArray(buffer, advancement.getRequirements(), (buf1, requirement) -> helper.writeArray(buffer, requirement, helper::writeKey));
        });

        helper.writeArray(buffer, packet.getRemoved(), helper::writeKey);
        helper.writeArray(buffer, packet.getProgress().entrySet(), (buf, entry) -> {
            helper.writeKey(buffer, entry.getKey());
            Object2LongMap<Key> progress = entry.getValue();
            helper.writeArray(buffer, progress.object2LongEntrySet(), (buf1, criteria) -> {
                helper.writeKey(buffer, criteria.getKey());
                if (criteria.getLongValue() == -1L) {
                    buffer.writeBoolean(true);
                    buffer.writeLong(criteria.getLongValue());
                } else {
                    buffer.writeBoolean(false);
                }
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, UpdateAdvancementsPacket packet) throws PacketSerializeException {
        packet.setReset(buffer.readBoolean());
        packet.setAdded(new ObjectArrayList<>(
                helper.readArray(buffer, new Advancement[0], (buf) -> {
                    Key id = helper.readKey(buffer);
                    Key parentId = helper.readOptional(buffer, helper::readKey);
                    DisplayInfo displayInfo = helper.readOptional(buffer, (buf1) -> {
                        Component title = helper.readComponent(buffer);
                        Component description = helper.readComponent(buffer);
                        ItemStack icon = helper.readItemStack(buffer);
                        DisplayInfo.FrameType type = DisplayInfo.FrameType.getById(helper.readVarInt(buffer));

                        int flags = buffer.readInt();
                        boolean hasBackgroundTexture = (flags & 0x01) == 0x01;
                        boolean toast = (flags & 0x02) == 0x02;
                        boolean hidden = (flags & 0x04) == 0x04;

                        Key backgroundTexture = hasBackgroundTexture ? helper.readKey(buffer) : null;
                        Vector2f pos = helper.readVector2f(buffer);
                        return new DisplayInfo(title, description, icon, backgroundTexture, type, toast, hidden, pos);
                    });
                    List<Key> criteria = new ObjectArrayList<>(helper.readArray(buffer, new Key[0], helper::readKey));
                    List<Key[]> requirements = new ObjectArrayList<>(helper.readArray(buffer, new Key[0][0], (buf1) -> helper.readArray(buffer, new Key[0], helper::readKey)));
                    return new Advancement(parentId, id, displayInfo, criteria, requirements);
                })
        ));
        packet.setRemoved(new ObjectArrayList<>(helper.readArray(buffer, new Key[0], helper::readKey)));
        Map<Key, Object2LongMap<Key>> progressMap = new HashMap<>();
        for (int i = 0; i < helper.readVarInt(buffer); i++) {
            Key id = helper.readKey(buffer);
            Object2LongMap<Key> progress = new Object2LongOpenHashMap<>();
            for (int j = 0; j < helper.readVarInt(buffer); j++) {
                Key criteriaId = helper.readKey(buffer);
                long date = buffer.readBoolean() ? buffer.readLong() : -1;
                progress.put(criteriaId, date);
            }
            progressMap.put(id, progress);
        }
        packet.setProgress(progressMap);
    }
}
