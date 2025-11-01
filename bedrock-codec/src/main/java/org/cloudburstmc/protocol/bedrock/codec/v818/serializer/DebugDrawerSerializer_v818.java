package org.cloudburstmc.protocol.bedrock.codec.v818.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.debugshape.*;
import org.cloudburstmc.protocol.bedrock.packet.DebugDrawerPacket;
import org.cloudburstmc.protocol.common.util.TriConsumer;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DebugDrawerSerializer_v818 implements BedrockPacketSerializer<DebugDrawerPacket> {

    public static final DebugDrawerSerializer_v818 INSTANCE = new DebugDrawerSerializer_v818();

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
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, DebugDrawerPacket packet) {
        helper.writeArray(buffer, packet.getShapes(), this::writeShape);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, DebugDrawerPacket packet) {
        helper.readArray(buffer, packet.getShapes(), this::readShape);
    }

    protected void writeShape(ByteBuf buffer, BedrockCodecHelper helper, DebugShape shape) {
        VarInts.writeUnsignedLong(buffer, shape.getId());
        writeCommonShapeData(buffer, helper, shape);

        switch (shape.getType()) {
            case ARROW:
                DebugArrow arrow = (DebugArrow) shape;
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, arrow.getArrowEndPosition(), WRITE_VECTOR3F);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadLength(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadRadius(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadSegments(), ByteBuf::writeByte);
                break;
            case BOX:
                DebugBox box = (DebugBox) shape;
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, box.getBoxBounds(), WRITE_VECTOR3F);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                break;
            case CIRCLE:
                DebugCircle circle = (DebugCircle) shape;
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, circle.getSegments(), ByteBuf::writeByte);
                break;
            case LINE:
                DebugLine line = (DebugLine) shape;
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, line.getLineEndPosition(), WRITE_VECTOR3F);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                break;
            case SPHERE:
                DebugSphere sphere = (DebugSphere) shape;
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, sphere.getSegments(), ByteBuf::writeByte);
                break;
            case TEXT:
                DebugText text = (DebugText) shape;
                helper.writeOptionalNull(buffer, text.getText(), WRITE_STRING);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                break;
        }
    }

    protected void writeCommonShapeData(ByteBuf buffer, BedrockCodecHelper helper, DebugShape shape) {
        helper.writeOptionalNull(buffer, shape.getType(), (buf, type) -> buf.writeByte(type.ordinal()));
        helper.writeOptionalNull(buffer, shape.getPosition(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getScale(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getRotation(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getTotalTimeLeft(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getColor(), WRITE_COLOR);
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

        if (type == null) {
            return new DebugShape(id);
        }

        switch (type) {
            case ARROW:
                return new DebugArrow(id, 0, position, scale, rotation, totalTimeLeft, color, lineEndPosition, arrowHeadLength, arrowHeadRadius, segments);
            case BOX:
                return new DebugBox(id, 0, position, scale, rotation, totalTimeLeft, color, boxBounds);
            case CIRCLE:
                return new DebugCircle(id, 0, position, scale, rotation, totalTimeLeft, color, segments);
            case LINE:
                return new DebugLine(id, 0, position, scale, rotation, totalTimeLeft, color, lineEndPosition);
            case SPHERE:
                return new DebugSphere(id, 0, position, scale, rotation, totalTimeLeft, color, segments);
            case TEXT:
                return new DebugText(id, 0, position, scale, rotation, totalTimeLeft, color, text);
            default:
                throw new IllegalStateException("Unknown debug shape type");
        }
    }
}
