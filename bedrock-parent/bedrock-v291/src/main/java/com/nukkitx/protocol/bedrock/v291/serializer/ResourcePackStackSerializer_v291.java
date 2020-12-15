package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourcePackStackSerializer_v291 implements BedrockPacketSerializer<ResourcePackStackPacket> {
    public static final ResourcePackStackSerializer_v291 INSTANCE = new ResourcePackStackSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        helper.writeArray(buffer, packet.getBehaviorPacks(), this::writeEntry);
        helper.writeArray(buffer, packet.getResourcePacks(), this::writeEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        helper.readArray(buffer, packet.getBehaviorPacks(), this::readEntry);
        helper.readArray(buffer, packet.getResourcePacks(), this::readEntry);
    }

    public ResourcePackStackPacket.Entry readEntry(ByteBuf buffer, BedrockPacketHelper helper) {
        String packId = helper.readString(buffer);
        String packVersion = helper.readString(buffer);
        String subPackName = helper.readString(buffer);
        return new ResourcePackStackPacket.Entry(packId, packVersion, subPackName);
    }

    public void writeEntry(ByteBuf buffer, BedrockPacketHelper helper, ResourcePackStackPacket.Entry entry) {
        requireNonNull(entry, "ResourcePackStackPacket entry is null");

        helper.writeString(buffer, entry.getPackId());
        helper.writeString(buffer, entry.getPackVersion());
        helper.writeString(buffer, entry.getSubPackName());
    }
}
