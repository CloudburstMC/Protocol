package org.cloudburstmc.protocol.bedrock.codec.v431;

import com.nukkitx.nbt.NBTInputStream;
import com.nukkitx.nbt.NBTOutputStream;
import com.nukkitx.nbt.NbtMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import org.cloudburstmc.protocol.bedrock.codec.v428.BedrockCodecHelper_v428;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventorySource;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.CraftResultsDeprecatedStackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.bedrock.util.LittleEndianByteBufInputStream;
import org.cloudburstmc.protocol.bedrock.util.LittleEndianByteBufOutputStream;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class BedrockCodecHelper_v431 extends BedrockCodecHelper_v428 {
    public static final BedrockCodecHelper_v431 INSTANCE = new BedrockCodecHelper_v431();

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(1064, LevelEvent.SOUND_POINTED_DRIPSTONE_LAND);
        this.addLevelEvent(1065, LevelEvent.SOUND_DYE_USED);
        this.addLevelEvent(1066, LevelEvent.SOUND_INK_SACE_USED);

        this.addLevelEvent(2028, LevelEvent.PARTICLE_DRIPSTONE_DRIP);
        this.addLevelEvent(2029, LevelEvent.PARTICLE_FIZZ_EFFECT);
        this.addLevelEvent(2030, LevelEvent.PARTICLE_WAX_ON);
        this.addLevelEvent(2031, LevelEvent.PARTICLE_WAX_OFF);
        this.addLevelEvent(2032, LevelEvent.PARTICLE_SCRAPE);
        this.addLevelEvent(2033, LevelEvent.PARTICLE_ELECTRIC_SPARK);

        int legacy = 0x4000;
        this.addLevelEvent(29 + legacy, LevelEvent.PARTICLE_STALACTITE_DRIP_WATER);
        this.addLevelEvent(30 + legacy, LevelEvent.PARTICLE_STALACTITE_DRIP_LAVA);
        this.addLevelEvent(31 + legacy, LevelEvent.PARTICLE_FALLING_DUST);
        this.addLevelEvent(32 + legacy, LevelEvent.PARTICLE_MOB_SPELL);
        this.addLevelEvent(33 + legacy, LevelEvent.PARTICLE_MOB_SPELL_AMBIENT);
        this.addLevelEvent(34 + legacy, LevelEvent.PARTICLE_MOB_SPELL_INSTANTANEOUS);
        this.addLevelEvent(35 + legacy, LevelEvent.PARTICLE_INK);
        this.addLevelEvent(36 + legacy, LevelEvent.PARTICLE_SLIME);
        this.addLevelEvent(37 + legacy, LevelEvent.PARTICLE_RAIN_SPLASH);
        this.addLevelEvent(38 + legacy, LevelEvent.PARTICLE_VILLAGER_ANGRY);
        this.addLevelEvent(39 + legacy, LevelEvent.PARTICLE_VILLAGER_HAPPY);
        this.addLevelEvent(40 + legacy, LevelEvent.PARTICLE_ENCHANTMENT_TABLE);
        this.addLevelEvent(41 + legacy, LevelEvent.PARTICLE_TRACKING_EMITTER);
        this.addLevelEvent(42 + legacy, LevelEvent.PARTICLE_NOTE);
        this.addLevelEvent(43 + legacy, LevelEvent.PARTICLE_WITCH_SPELL);
        this.addLevelEvent(44 + legacy, LevelEvent.PARTICLE_CARROT);
        this.addLevelEvent(45 + legacy, LevelEvent.PARTICLE_MOB_APPEARANCE);
        this.addLevelEvent(46 + legacy, LevelEvent.PARTICLE_END_ROD);
        this.addLevelEvent(47 + legacy, LevelEvent.PARTICLE_DRAGONS_BREATH);
        this.addLevelEvent(48 + legacy, LevelEvent.PARTICLE_SPIT);
        this.addLevelEvent(49 + legacy, LevelEvent.PARTICLE_TOTEM);
        this.addLevelEvent(50 + legacy, LevelEvent.PARTICLE_FOOD);
        this.addLevelEvent(51 + legacy, LevelEvent.PARTICLE_FIREWORKS_STARTER);
        this.addLevelEvent(52 + legacy, LevelEvent.PARTICLE_FIREWORKS_SPARK);
        this.addLevelEvent(53 + legacy, LevelEvent.PARTICLE_FIREWORKS_OVERLAY);
        this.addLevelEvent(54 + legacy, LevelEvent.PARTICLE_BALLOON_GAS);
        this.addLevelEvent(55 + legacy, LevelEvent.PARTICLE_COLORED_FLAME);
        this.addLevelEvent(56 + legacy, LevelEvent.PARTICLE_SPARKLER);
        this.addLevelEvent(57 + legacy, LevelEvent.PARTICLE_CONDUIT);
        this.addLevelEvent(58 + legacy, LevelEvent.PARTICLE_BUBBLE_COLUMN_UP);
        this.addLevelEvent(59 + legacy, LevelEvent.PARTICLE_BUBBLE_COLUMN_DOWN);
        this.addLevelEvent(60 + legacy, LevelEvent.PARTICLE_SNEEZE);
        this.addLevelEvent(61 + legacy, LevelEvent.PARTICLE_SHULKER_BULLET);
        this.addLevelEvent(62 + legacy, LevelEvent.PARTICLE_BLEACH);
        this.addLevelEvent(63 + legacy, LevelEvent.PARTICLE_DRAGON_DESTROY_BLOCK);
        this.addLevelEvent(64 + legacy, LevelEvent.PARTICLE_MYCELIUM_DUST);
        this.addLevelEvent(65 + legacy, LevelEvent.PARTICLE_FALLING_RED_DUST);
        this.addLevelEvent(66 + legacy, LevelEvent.PARTICLE_CAMPFIRE_SMOKE);
        this.addLevelEvent(67 + legacy, LevelEvent.PARTICLE_TALL_CAMPFIRE_SMOKE);
        this.addLevelEvent(68 + legacy, LevelEvent.PARTICLE_RISING_DRAGONS_BREATH);
        this.addLevelEvent(69 + legacy, LevelEvent.PARTICLE_DRAGONS_BREATH);
        this.addLevelEvent(70 + legacy, LevelEvent.PARTICLE_BLUE_FLAME);
        this.addLevelEvent(71 + legacy, LevelEvent.PARTICLE_SOUL);
        this.addLevelEvent(72 + legacy, LevelEvent.PARTICLE_OBSIDIAN_TEAR);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(332, SoundEvent.POINTED_DRIPSTONE_CAULDRON_DRIP_LAVA);
        this.addSoundEvent(333, SoundEvent.POINTED_DRIPSTONE_CAULDRON_DRIP_WATER);
        this.addSoundEvent(334, SoundEvent.POINTED_DRIPSTONE_DRIP_LAVA);
        this.addSoundEvent(335, SoundEvent.POINTED_DRIPSTONE_DRIP_WATER);
        this.addSoundEvent(336, SoundEvent.CAVE_VINES_PICK_BERRIES);
        this.addSoundEvent(337, SoundEvent.BIG_DRIPLEAF_TILT_DOWN);
        this.addSoundEvent(338, SoundEvent.BIG_DRIPLEAF_TILT_UP);
        this.addSoundEvent(339, SoundEvent.UNDEFINED);
    }

    @Override
    public ItemData readItemInstance(ByteBuf buffer) {
        int id = VarInts.readInt(buffer);
        if (id == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }
        int count = buffer.readUnsignedShortLE();
        int damage = VarInts.readUnsignedInt(buffer);
        int blockRuntimeId = VarInts.readInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace;
        String[] canBreak;

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));
        try (LittleEndianByteBufInputStream stream = new LittleEndianByteBufInputStream(buf);
             NBTInputStream nbtStream = new NBTInputStream(stream)) {
            int nbtSize = stream.readShort();

            if (nbtSize > 0) {
                compoundTag = (NbtMap) nbtStream.readTag();
            } else if (nbtSize == -1) {
                int tagCount = stream.readUnsignedByte();
                if (tagCount != 1) throw new IllegalArgumentException("Expected 1 tag but got " + tagCount);
                compoundTag = (NbtMap) nbtStream.readTag();
            }

            canPlace = new String[stream.readInt()];
            for (int i = 0; i < canPlace.length; i++) {
                canPlace[i] = stream.readUTF();
            }
            canBreak = new String[stream.readInt()];
            for (int i = 0; i < canBreak.length; i++) {
                canBreak[i] = stream.readUTF();
            }

            if (this.isBlockingItem(id)) {
                blockingTicks = stream.readLong();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read item user data", e);
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left\n{}", buf.readableBytes(), ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
        }

        return ItemData.builder()
                .id(id)
                .damage(damage)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockRuntimeId(blockRuntimeId)
                .build();
    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        int id = VarInts.readInt(buffer);
        if (id == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }
        int count = buffer.readUnsignedShortLE();
        int damage = VarInts.readUnsignedInt(buffer);

        boolean hasNetId = buffer.readBoolean();
        int netId = 0;
        if (hasNetId) {
            netId = VarInts.readInt(buffer);
        }

        int blockRuntimeId = VarInts.readInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace;
        String[] canBreak;

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));
        try (LittleEndianByteBufInputStream stream = new LittleEndianByteBufInputStream(buf);
             NBTInputStream nbtStream = new NBTInputStream(stream)) {
            int nbtSize = stream.readShort();

            if (nbtSize > 0) {
                compoundTag = (NbtMap) nbtStream.readTag();
            } else if (nbtSize == -1) {
                int tagCount = stream.readUnsignedByte();
                if (tagCount != 1) throw new IllegalArgumentException("Expected 1 tag but got " + tagCount);
                compoundTag = (NbtMap) nbtStream.readTag();
            }

            canPlace = new String[stream.readInt()];
            for (int i = 0; i < canPlace.length; i++) {
                canPlace[i] = stream.readUTF();
            }
            canBreak = new String[stream.readInt()];
            for (int i = 0; i < canBreak.length; i++) {
                canBreak[i] = stream.readUTF();
            }

            if (this.isBlockingItem(id)) {
                blockingTicks = stream.readLong();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read item user data", e);
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left\n{}", buf.readableBytes(), ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
        }

        return ItemData.builder()
                .id(id)
                .damage(damage)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockRuntimeId(blockRuntimeId)
                .usingNetId(hasNetId)
                .netId(netId)
                .build();
    }

    @Override
    public ItemData readNetItem(ByteBuf buffer) {
        return this.readItem(buffer);
    }

    @Override
    public void writeItemInstance(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        // Write id
        int id = item.getId();
        if (id == 0) {
            // We don't need to write anything extra.
            buffer.writeByte(0);
            return;
        }
        VarInts.writeInt(buffer, id);

        // Write damage and count
        buffer.writeShortLE(item.getCount());
        VarInts.writeUnsignedInt(buffer, item.getDamage());
        VarInts.writeInt(buffer, item.getBlockRuntimeId());

        ByteBuf userDataBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        try (LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream(userDataBuf);
             NBTOutputStream nbtStream = new NBTOutputStream(stream)) {
            if (item.getTag() != null) {
                stream.writeShort(-1);
                stream.writeByte(1); // Hardcoded in current version
                nbtStream.writeTag(item.getTag());
            } else {
                userDataBuf.writeShortLE(0);
            }

            String[] canPlace = item.getCanPlace();
            stream.writeInt(canPlace.length);
            for (String aCanPlace : canPlace) {
                stream.writeUTF(aCanPlace);
            }

            String[] canBreak = item.getCanBreak();
            stream.writeInt(canBreak.length);
            for (String aCanBreak : canBreak) {
                stream.writeUTF(aCanBreak);
            }

            if (this.isBlockingItem(id)) {
                stream.writeLong(item.getBlockingTicks());
            }

            VarInts.writeUnsignedInt(buffer, userDataBuf.readableBytes());
            buffer.writeBytes(userDataBuf);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write item user data", e);
        } finally {
            userDataBuf.release();
        }
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        // Write id
        int id = item.getId();
        if (id == 0) {
            // We don't need to write anything extra.
            buffer.writeByte(0);
            return;
        }
        VarInts.writeInt(buffer, id);

        // Write damage and count
        buffer.writeShortLE(item.getCount());
        VarInts.writeUnsignedInt(buffer, item.getDamage());

        buffer.writeBoolean(item.isUsingNetId());
        if (item.isUsingNetId()) {
            VarInts.writeInt(buffer, item.getNetId());
        }

        VarInts.writeInt(buffer, item.getBlockRuntimeId());

        ByteBuf userDataBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        try (LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream(userDataBuf);
             NBTOutputStream nbtStream = new NBTOutputStream(stream)) {
            if (item.getTag() != null) {
                stream.writeShort(-1);
                stream.writeByte(1); // Hardcoded in current version
                nbtStream.writeTag(item.getTag());
            } else {
                userDataBuf.writeShortLE(0);
            }

            String[] canPlace = item.getCanPlace();
            stream.writeInt(canPlace.length);
            for (String aCanPlace : canPlace) {
                stream.writeUTF(aCanPlace);
            }

            String[] canBreak = item.getCanBreak();
            stream.writeInt(canBreak.length);
            for (String aCanBreak : canBreak) {
                stream.writeUTF(aCanBreak);
            }

            if (this.isBlockingItem(id)) {
                stream.writeLong(item.getBlockingTicks());
            }

            VarInts.writeUnsignedInt(buffer, userDataBuf.readableBytes());
            buffer.writeBytes(userDataBuf);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write item user data", e);
        } finally {
            userDataBuf.release();
        }
    }

    @Override
    public void writeNetItem(ByteBuf buffer, ItemData item) {
        this.writeItem(buffer, item);
    }

    @Override
    public boolean readInventoryActions(ByteBuf buffer, List<InventoryActionData> actions) {
        this.readArray(buffer, actions, (buf, helper) -> {
            InventorySource source = helper.readSource(buf);
            int slot = VarInts.readUnsignedInt(buf);
            ItemData fromItem = helper.readItem(buf);
            ItemData toItem = helper.readItem(buf);

            return new InventoryActionData(source, slot, fromItem, toItem);
        });
        return false;
    }

    @Override
    public void writeInventoryActions(ByteBuf buffer, List<InventoryActionData> actions,
                                      boolean hasNetworkIds) {
        this.writeArray(buffer, actions, (buf, helper, action) -> {
            helper.writeSource(buf, action.getSource());
            VarInts.writeUnsignedInt(buf, action.getSlot());
            helper.writeItem(buf, action.getFromItem());
            helper.writeItem(buf, action.getToItem());
        });
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type) {
        if (type == StackRequestActionType.CRAFT_RESULTS_DEPRECATED) {
            return new CraftResultsDeprecatedStackRequestActionData(
                    this.readArray(byteBuf, new ItemData[0], buf -> this.readItemInstance(buf)),
                    byteBuf.readByte()
            );
        } else {
            return super.readRequestActionData(byteBuf, type);
        }
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action) {
        if (action.getType() == StackRequestActionType.CRAFT_RESULTS_DEPRECATED) {
            this.writeArray(byteBuf, ((CraftResultsDeprecatedStackRequestActionData) action).getResultItems(), (buf2, item) -> this.writeItemInstance(buf2, item));
            byteBuf.writeByte(((CraftResultsDeprecatedStackRequestActionData) action).getTimesCrafted());
        } else {
            super.writeRequestActionData(byteBuf, action);
        }
    }
}
