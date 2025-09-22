package org.cloudburstmc.protocol.bedrock.codec.v390.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import static org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket.Action;
import static org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket.Entry;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerListSerializer_v390 implements BedrockPacketSerializer<PlayerListPacket> {
    public static final PlayerListSerializer_v390 INSTANCE = new PlayerListSerializer_v390();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerListPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

        if (packet.getAction() == Action.ADD) {
            for (Entry entry : packet.getEntries()) {
                this.writeEntryBase(buffer, helper, entry);
            }

            for (Entry entry : packet.getEntries()) {
                buffer.writeBoolean(entry.isTrustedSkin());
            }
        } else {
            for (Entry entry : packet.getEntries()) {
                helper.writeUuid(buffer, entry.getUuid());
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerListPacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        int length = VarInts.readUnsignedInt(buffer);

        if (action == Action.ADD) {
            for (int i = 0; i < length; i++) {
                packet.getEntries().add(this.readEntryBase(buffer, helper));
            }

            for (int i = 0; i < length && buffer.isReadable(); i++) {
                packet.getEntries().get(i).setTrustedSkin(buffer.readBoolean());
            }
        } else {
            for (int i = 0; i < length; i++) {
                packet.getEntries().add(new Entry(helper.readUuid(buffer)));
            }
        }
    }

    protected void writeEntryBase(ByteBuf buffer, BedrockCodecHelper helper, Entry entry) {
        helper.writeUuid(buffer, entry.getUuid());

        VarInts.writeLong(buffer, entry.getEntityId());
        helper.writeString(buffer, entry.getName());
        helper.writeString(buffer, entry.getXuid());
        helper.writeString(buffer, entry.getPlatformChatId());
        buffer.writeIntLE(entry.getBuildPlatform());
        helper.writeSkin(buffer, entry.getSkin());
        buffer.writeBoolean(entry.isTeacher());
        buffer.writeBoolean(entry.isHost());
    }

    protected Entry readEntryBase(ByteBuf buffer, BedrockCodecHelper helper) {
        Entry entry = new Entry(helper.readUuid(buffer));
        entry.setEntityId(VarInts.readLong(buffer));
        entry.setName(helper.readString(buffer));
        entry.setXuid(helper.readString(buffer));
        entry.setPlatformChatId(helper.readString(buffer));
        entry.setBuildPlatform(buffer.readIntLE());
        entry.setSkin(helper.readSkin(buffer));
        entry.setTeacher(buffer.readBoolean());
        entry.setHost(buffer.readBoolean());
        return entry;
    }
}
