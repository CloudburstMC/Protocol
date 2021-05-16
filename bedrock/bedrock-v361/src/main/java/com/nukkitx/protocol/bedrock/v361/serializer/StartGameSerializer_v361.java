package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.nbt.NbtList;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtType;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v332.serializer.StartGameSerializer_v332;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v361 extends StartGameSerializer_v332 {
    public static final StartGameSerializer_v361 INSTANCE = new StartGameSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getPlayerGameType().ordinal());
        helper.writeVector3f(buffer, packet.getPlayerPosition());
        helper.writeVector2f(buffer, packet.getRotation());

        this.writeLevelSettings(buffer, helper, packet);

        helper.writeString(buffer, packet.getLevelId());
        helper.writeString(buffer, packet.getLevelName());
        helper.writeString(buffer, packet.getPremiumWorldTemplateId());
        buffer.writeBoolean(packet.isTrial());
        buffer.writeLongLE(packet.getCurrentTick());
        VarInts.writeInt(buffer, packet.getEnchantmentSeed());

        List<NbtMap> palette = packet.getBlockPalette();
        VarInts.writeUnsignedInt(buffer, palette.size());
        for (NbtMap entry : palette) {
            NbtMap blockTag = entry.getCompound("block");
            helper.writeString(buffer, blockTag.getString("name"));
            buffer.writeShortLE(entry.getShort("meta"));
            buffer.writeShortLE(entry.getShort("id"));
        }

        helper.writeArray(buffer, packet.getItemEntries(), (buf, entry) -> {
            helper.writeString(buf, entry.getIdentifier());
            buf.writeShortLE(entry.getId());
        });

        helper.writeString(buffer, packet.getMultiplayerCorrelationId());

    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet, BedrockSession session) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlayerGameType(GameType.from(VarInts.readInt(buffer)));
        packet.setPlayerPosition(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector2f(buffer));

        this.readLevelSettings(buffer, helper, packet);

        packet.setLevelId(helper.readString(buffer));
        packet.setLevelName(helper.readString(buffer));
        packet.setPremiumWorldTemplateId(helper.readString(buffer));
        packet.setTrial(buffer.readBoolean());
        packet.setCurrentTick(buffer.readLongLE());
        packet.setEnchantmentSeed(VarInts.readInt(buffer));

        int paletteLength = VarInts.readUnsignedInt(buffer);
        List<NbtMap> palette = new ObjectArrayList<>(paletteLength);
        for (int i = 0; i < paletteLength; i++) {
            palette.add(NbtMap.builder()
                    .putCompound("block", NbtMap.builder()
                            .putString("name", helper.readString(buffer))
                            .build())
                    .putShort("meta", buffer.readShortLE())
                    .putShort("id", buffer.readShortLE())
                    .build());
        }
        packet.setBlockPalette(new NbtList<>(NbtType.COMPOUND, palette));

        helper.readArray(buffer, packet.getItemEntries(), session, (buf, packetHelper, aSession) -> {
            String identifier = packetHelper.readString(buf);
            short id = buf.readShortLE();
            if (identifier.equals(packetHelper.getBlockingItemIdentifier())) {
                aSession.getHardcodedBlockingId().set(id);
            }
            return new StartGamePacket.ItemEntry(identifier, id);
        });

        packet.setMultiplayerCorrelationId(helper.readString(buffer));
    }

    @Override
    protected void readLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.readLevelSettings(buffer, helper, packet);

        packet.setOnlySpawningV1Villagers(buffer.readBoolean());
    }

    @Override
    protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.writeLevelSettings(buffer, helper, packet);

        buffer.writeBoolean(packet.isOnlySpawningV1Villagers());
    }
}
