package com.nukkitx.protocol.bedrock.v544.serializer;

import com.nukkitx.math.vector.Vector2i;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.MapInfoRequestPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.MapInfoRequestSerializer_v291;
import io.netty.buffer.ByteBuf;

public class MapInfoRequestSerializer_v544 extends MapInfoRequestSerializer_v291 {

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MapInfoRequestPacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeArray(buffer, packet.getRequestedPixels(), ByteBuf::writeIntLE, (buf, aHelper, pixel) -> {
            buf.writeIntLE(pixel.getX());
            buf.writeShortLE(pixel.getY());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MapInfoRequestPacket packet) {
        super.deserialize(buffer, helper, packet);

        helper.readArray(buffer, packet.getRequestedPixels(), ByteBuf::readUnsignedIntLE, (buf, aHelper) -> {
            int x = buf.readIntLE();
            int y = buf.readUnsignedShortLE();
            return Vector2i.from(x, y);
        });
    }
}
