package org.cloudburstmc.protocol.bedrock.codec.v859.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.GraphicsOverrideParameterType;
import org.cloudburstmc.protocol.bedrock.packet.GraphicsParameterOverridePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GraphicsParameterOverrideSerializer_v859 implements BedrockPacketSerializer<GraphicsParameterOverridePacket> {

    public static final GraphicsParameterOverrideSerializer_v859 INSTANCE = new GraphicsParameterOverrideSerializer_v859();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, GraphicsParameterOverridePacket packet) {
        helper.writeArray(buffer, packet.getValues().entrySet(), (buf, aHelper, entry) -> {
            buf.writeFloatLE(entry.getKey());
            helper.writeVector3f(buf, entry.getValue());
        });
        helper.writeString(buffer, packet.getBiomeIdentifier());
        buffer.writeByte(packet.getParameterType().ordinal());
        buffer.writeBoolean(packet.isReset());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, GraphicsParameterOverridePacket packet) {
        Map<Float, Vector3f> values = new HashMap<>();
        int length = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < length; i++) {
            float key = buffer.readFloatLE();
            Vector3f value = helper.readVector3f(buffer);
            values.put(key, value);
        }
        packet.setValues(values);
        packet.setBiomeIdentifier(helper.readString(buffer));
        packet.setParameterType(GraphicsOverrideParameterType.values()[buffer.readUnsignedByte()]);
        packet.setReset(buffer.readBoolean());
    }
}
