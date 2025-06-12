package org.cloudburstmc.protocol.bedrock.codec.v818.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.DebugShape;
import org.cloudburstmc.protocol.bedrock.packet.ServerScriptDebugDrawerPacket;
import org.cloudburstmc.protocol.common.util.TriConsumer;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ServerScriptDebugDrawerSerializer_v818 implements BedrockPacketSerializer<ServerScriptDebugDrawerPacket> {

    public static final ServerScriptDebugDrawerSerializer_v818 INSTANCE = new ServerScriptDebugDrawerSerializer_v818();

    protected static final DebugShape.Type[] SHAPE_TYPES = DebugShape.Type.values();

    protected static final TriConsumer<ByteBuf, BedrockCodecHelper, Vector3f> WRITE_VECTOR3F =
            (buffer, helper, vector3f) -> helper.writeVector3f(buffer, vector3f);
    protected static final BiConsumer<ByteBuf, Color> WRITE_COLOR =
            (buffer, color) -> buffer.writeIntLE(color.getRGB());
    protected static final TriConsumer<ByteBuf, BedrockCodecHelper, String> WRITE_STRING =
            (buffer, helper, text) -> helper.writeString(buffer, text);
    protected static final BiFunction<ByteBuf, BedrockCodecHelper, Vector3f> READ_VECTOR3F =
            (buffer, helper) -> helper.readVector3f(buffer);
    protected static final Function<ByteBuf, Color> READ_COLOR =
            buffer -> new Color(buffer.readIntLE());
    protected static final BiFunction<ByteBuf, BedrockCodecHelper, String> READ_STRING =
            (buffer, helper) -> helper.readString(buffer);

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerScriptDebugDrawerPacket packet) {
        helper.writeArray(buffer, packet.getShapes(), this::writeShape);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerScriptDebugDrawerPacket packet) {
        helper.readArray(buffer, packet.getShapes(), this::readShape);
    }

    protected void writeShape(ByteBuf buffer, BedrockCodecHelper helper, DebugShape shape) {
        VarInts.writeUnsignedLong(buffer, shape.getId());
        helper.writeOptionalNull(buffer, shape.getType(), (buf, type) -> buf.writeByte(type.ordinal()));
        helper.writeOptionalNull(buffer, shape.getPosition(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getScale(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getRotation(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getTotalTimeLeft(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getColor(), WRITE_COLOR);
        helper.writeOptionalNull(buffer, shape.getText(), WRITE_STRING);
        helper.writeOptionalNull(buffer, shape.getBoxBounds(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getLineEndPosition(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getArrowHeadLength(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getArrowHeadRadius(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getSegments(), ByteBuf::writeByte);
    }

    protected DebugShape readShape(ByteBuf buffer, BedrockCodecHelper helper) {
        long id = VarInts.readUnsignedLong(buffer);
        DebugShape.Type type = helper.readOptional(buffer, null,
                (buf, aHelper) -> SHAPE_TYPES[buf.readUnsignedByte()]);
        Vector3f position = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float scale = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Vector3f rotation = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float totalTimeLeft = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Color color = helper.readOptional(buffer, null, READ_COLOR);
        String text = helper.readOptional(buffer, null, READ_STRING);
        Vector3f boxBounds = helper.readOptional(buffer, null, READ_VECTOR3F);
        Vector3f lineEndPosition = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float arrowHeadLength = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float arrowHeadRadius = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Integer segments = helper.readOptional(buffer, null, buf -> (int) buf.readUnsignedByte());
        return new DebugShape(id, type, position, scale, rotation, totalTimeLeft, color, text,
                boxBounds, lineEndPosition, arrowHeadLength, arrowHeadRadius, segments);
    }
}
