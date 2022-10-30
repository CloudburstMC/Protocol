package org.cloudburstmc.protocol.bedrock.codec.v527.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.StartGameSerializer_v465;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

@NoArgsConstructor
public class StartGameSerializer_v527 extends StartGameSerializer_v465 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeTag(buffer, packet.getPlayerPropertyData());
        buffer.writeLongLE(packet.getBlockRegistryChecksum());
        helper.writeUuid(buffer, packet.getWorldTemplateId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setPlayerPropertyData(helper.readTag(buffer, NbtMap.class));
        packet.setBlockRegistryChecksum(buffer.readLongLE());
        packet.setWorldTemplateId(helper.readUuid(buffer));
    }

    // Re-added the previous version's protocol changes to this serializer due to the
    // player property data coming before the block registry checksum.
    @Override
    protected long readSeed(ByteBuf buf) {
        return buf.readLongLE();
    }

    @Override
    protected void writeSeed(ByteBuf buf, long seed) {
        buf.writeLongLE(seed);
    }
}