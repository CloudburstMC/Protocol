package org.cloudburstmc.protocol.bedrock.codec.v582.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.TrimMaterial;
import org.cloudburstmc.protocol.bedrock.data.TrimPattern;
import org.cloudburstmc.protocol.bedrock.packet.TrimDataPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrimDataSerializer_v582 implements BedrockPacketSerializer<TrimDataPacket> {
    public static final TrimDataSerializer_v582 INSTANCE = new TrimDataSerializer_v582();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TrimDataPacket packet) {
        helper.writeArray(buffer, packet.getPatterns(), (buf, pattern) -> {
            helper.writeString(buf, pattern.getItemName());
            helper.writeString(buf, pattern.getPatternId());
        });
        helper.writeArray(buffer, packet.getMaterials(), (buf, pattern) -> {
            helper.writeString(buf, pattern.getMaterialId());
            helper.writeString(buf, pattern.getColor());
            helper.writeString(buf, pattern.getItemName());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TrimDataPacket packet) {
        helper.readArray(buffer, packet.getPatterns(), buf -> {
            String name = helper.readString(buf);
            String id = helper.readString(buf);
            return new TrimPattern(name, id);
        });
        helper.readArray(buffer, packet.getMaterials(), buf -> {
            String id = helper.readString(buf);
            String color = helper.readString(buf);
            String name = helper.readString(buf);
            return new TrimMaterial(id, color, name);
        });
    }
}
