package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v924.serializer.DebugDrawerSerializer_v924;
import org.cloudburstmc.protocol.bedrock.data.debugshape.*;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.awt.Color;

public class DebugDrawerSerializer_v975 extends DebugDrawerSerializer_v924 {

    public static final DebugDrawerSerializer_v975 INSTANCE = new DebugDrawerSerializer_v975();

    @Override
    protected void writeCommonShapeData(ByteBuf buffer, BedrockCodecHelper helper, DebugShape shape) {
        VarInts.writeUnsignedLong(buffer, shape.getId());
        helper.writeOptionalNull(buffer, shape.getType(), (buf, type) -> buf.writeByte(type.ordinal()));
        helper.writeOptionalNull(buffer, shape.getPosition(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getScale(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getRotation(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getTotalTimeLeft(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getMaximumRenderDistance(), ByteBuf::writeFloatLE); // new
        helper.writeOptionalNull(buffer, shape.getColor(), WRITE_COLOR);

        helper.writeOptionalNull(buffer, shape.getDimension(), VarInts::writeInt);
        helper.writeOptionalNull(buffer, shape.getAttachedToEntityId(), VarInts::writeLong);
    }

    @Override
    protected void writeShape(ByteBuf buffer, BedrockCodecHelper helper, DebugShape shape) {
        if (shape.getType() != DebugShape.Type.TEXT) {
            super.writeShape(buffer, helper, shape);
            return;
        }

        writeCommonShapeData(buffer, helper, shape);

        VarInts.writeUnsignedInt(buffer, toPayloadType(DebugShape.Type.TEXT));

        writeDebugText(buffer, helper, (DebugText) shape);
    }

    @Override
    protected DebugShape readShape(ByteBuf buffer, BedrockCodecHelper helper) {
        long id = VarInts.readUnsignedLong(buffer);

        DebugShape.Type type = helper.readOptional(buffer, null, (buf, aHelper) -> SHAPE_TYPES[buf.readUnsignedByte()]);
        Vector3f position = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float scale = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Vector3f rotation = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float totalTimeLeft = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float maximumRenderDistance = helper.readOptional(buffer, null, ByteBuf::readFloatLE); // new
        Color color = helper.readOptional(buffer, null, READ_COLOR);
        Integer dimension = helper.readOptional(buffer, -1, VarInts::readInt);
        Long attachedToEntityId = helper.readOptional(buffer, null, VarInts::readLong);
        VarInts.readUnsignedInt(buffer); // Unused payload type

        if (type == null) {
            return new DebugShape(id, dimension);
        }

        DebugShape shape;
        switch (type) {
            case ARROW:
                shape = new DebugArrow();
                break;
            case BOX:
                shape = new DebugBox();
                break;
            case CIRCLE:
                shape = new DebugCircle();
                break;
            case LINE:
                shape = new DebugLine();
                break;
            case SPHERE:
                shape = new DebugSphere();
                break;
            case TEXT:
                shape = new DebugText();
                break;
            default:
                throw new IllegalStateException("Unknown debug shape type");
        }

        shape.setId(id);
        shape.setDimension(dimension);
        shape.setPosition(position);
        shape.setScale(scale);
        shape.setRotation(rotation);
        shape.setTotalTimeLeft(totalTimeLeft);
        shape.setColor(color);
        shape.setAttachedToEntityId(attachedToEntityId);
        shape.setMaximumRenderDistance(maximumRenderDistance);

        switch (type) {
            case ARROW:
                DebugArrow arrow = (DebugArrow) shape;
                arrow.setArrowEndPosition(helper.readOptional(buffer, null, READ_VECTOR3F));
                arrow.setArrowHeadLength(helper.readOptional(buffer, null, ByteBuf::readFloatLE));
                arrow.setArrowHeadRadius(helper.readOptional(buffer, null, ByteBuf::readFloatLE));
                arrow.setArrowHeadSegments(helper.readOptional(buffer, null, buf -> (int) buf.readUnsignedByte()));
                return arrow;
            case BOX:
                DebugBox box = (DebugBox) shape;
                box.setBoxBounds(helper.readVector3f(buffer));
                return box;
            case CIRCLE:
                DebugCircle circle = (DebugCircle) shape;
                circle.setSegments((int) buffer.readUnsignedByte());
                return circle;
            case LINE:
                DebugLine line = (DebugLine) shape;
                line.setLineEndPosition(helper.readVector3f(buffer));
                return line;
            case SPHERE:
                DebugSphere sphere = (DebugSphere) shape;
                sphere.setSegments((int) buffer.readUnsignedByte());
                return sphere;
            case TEXT:
                return readDebugText(buffer, helper, (DebugText) shape);
            default:
                throw new IllegalStateException("Unknown debug shape type");
        }
    }

    protected DebugText readDebugText(ByteBuf buffer, BedrockCodecHelper helper, DebugText text) {
        text.setText(helper.readString(buffer));
        text.setUseRotation(buffer.readBoolean());
        text.setBackgroundColor(helper.readOptional(buffer, null, (buf, h) -> new Color(buf.readIntLE(), true)));
        text.setDepthTest(buffer.readBoolean());
        text.setShowBackface(buffer.readBoolean());
        text.setShowTextBackface(buffer.readBoolean());
        return text;
    }

    protected void writeDebugText(ByteBuf buffer, BedrockCodecHelper helper, DebugText text) {
        helper.writeString(buffer, text.getText());
        buffer.writeBoolean(text.isUseRotation());
        helper.writeOptionalNull(buffer, text.getBackgroundColor(), (buf, h, c) -> buf.writeIntLE(c.getRGB()));
        buffer.writeBoolean(text.isDepthTest());
        buffer.writeBoolean(text.isShowBackface());
        buffer.writeBoolean(text.isShowTextBackface());
    }
}
