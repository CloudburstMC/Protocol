package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.AuthoritativeMovementMode;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v361.serializer.StartGameSerializer_v361;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartGameSerializer_v388 extends StartGameSerializer_v361 {
    public static final StartGameSerializer_v388 INSTANCE = new StartGameSerializer_v388();

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
        buffer.writeBoolean(packet.getAuthoritativeMovementMode() != AuthoritativeMovementMode.CLIENT);
        buffer.writeLongLE(packet.getCurrentTick());
        VarInts.writeInt(buffer, packet.getEnchantmentSeed());

        // cache palette for fast writing
        helper.writeTag(buffer, packet.getBlockPalette());

        helper.writeArray(buffer, packet.getItemEntries(), (buf, h, entry) -> {
            h.writeString(buf, entry.getIdentifier());
            buf.writeShortLE(entry.getId());
        });

        helper.writeString(buffer, packet.getMultiplayerCorrelationId());

    }

    @SuppressWarnings("unchecked")
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
        packet.setAuthoritativeMovementMode(buffer.readBoolean() ? AuthoritativeMovementMode.SERVER : AuthoritativeMovementMode.CLIENT);
        packet.setCurrentTick(buffer.readLongLE());
        packet.setEnchantmentSeed(VarInts.readInt(buffer));

        packet.setBlockPalette(helper.readTag(buffer));

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

        packet.setVanillaVersion(helper.readString(buffer));
    }

    @Override
    protected void writeLevelSettings(ByteBuf buffer, BedrockPacketHelper helper, StartGamePacket packet) {
        super.writeLevelSettings(buffer, helper, packet);

        helper.writeString(buffer, packet.getVanillaVersion());
    }
}
