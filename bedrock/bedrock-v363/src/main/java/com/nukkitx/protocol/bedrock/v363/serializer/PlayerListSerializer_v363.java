package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ImageData;
import com.nukkitx.protocol.bedrock.data.SerializedSkin;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Action;
import static com.nukkitx.protocol.bedrock.packet.PlayerListPacket.Entry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerListSerializer_v363 implements PacketSerializer<PlayerListPacket> {
    public static final PlayerListSerializer_v363 INSTANCE = new PlayerListSerializer_v363();


    @Override
    public void serialize(ByteBuf buffer, PlayerListPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

        for (Entry entry : packet.getEntries()) {
            BedrockUtils.writeUuid(buffer, entry.getUuid());

            if (packet.getAction() == Action.ADD) {
                VarInts.writeLong(buffer, entry.getEntityId());
                BedrockUtils.writeString(buffer, entry.getName());
                SerializedSkin skin = entry.getSkin();
                BedrockUtils.writeString(buffer, skin.getSkinId());
                skin.getSkinData().checkLegacySkinSize();
                BedrockUtils.writeByteArray(buffer, skin.getSkinData().getImage());
                skin.getCapeData().checkLegacyCapeSize();
                BedrockUtils.writeByteArray(buffer, skin.getCapeData().getImage());
                BedrockUtils.writeString(buffer, skin.getGeometryName());
                BedrockUtils.writeString(buffer, skin.getGeometryData());
                BedrockUtils.writeString(buffer, entry.getXuid());
                BedrockUtils.writeString(buffer, entry.getPlatformChatId());
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerListPacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            Entry entry = new Entry(BedrockUtils.readUuid(buffer));

            if (action == Action.ADD) {
                entry.setEntityId(VarInts.readLong(buffer));
                entry.setName(BedrockUtils.readString(buffer));
                String skinId = BedrockUtils.readString(buffer);
                ImageData skinData = ImageData.of(BedrockUtils.readByteArray(buffer));
                ImageData capeData = ImageData.of(64, 32, BedrockUtils.readByteArray(buffer));
                String geometryName = BedrockUtils.readString(buffer);
                String geometryData = BedrockUtils.readString(buffer);
                entry.setSkin(SerializedSkin.of(skinId, skinData, capeData, geometryName, geometryData, false));
                entry.setXuid(BedrockUtils.readString(buffer));
                entry.setPlatformChatId(BedrockUtils.readString(buffer));
            }
            packet.getEntries().add(entry);
        }
    }
}
