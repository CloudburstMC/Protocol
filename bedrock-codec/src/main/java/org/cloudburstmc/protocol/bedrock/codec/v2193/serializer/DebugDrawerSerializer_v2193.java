package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.DebugDrawerSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.data.debugshape.*;

import java.awt.Color;

public class DebugDrawerSerializer_v2193 extends DebugDrawerSerializer_v1001 {

    public static final DebugDrawerSerializer_v2193 INSTANCE = new DebugDrawerSerializer_v2193();

    @Override
    protected DebugText readDebugText(ByteBuf buffer, BedrockCodecHelper helper, DebugText text) {
        text.setText(helper.readString(buffer));
        text.setUseRotation(buffer.readBoolean());
        text.setBackgroundColor(helper.readOptional(buffer, null, (buf, h) -> new Color(buf.readIntLE(), true)));
        text.setLineGapHeight(buffer.readFloatLE()); // new
        text.setDepthTest(buffer.readBoolean());
        text.setShowBackface(buffer.readBoolean());
        text.setShowTextBackface(buffer.readBoolean());
        return text;
    }

    @Override
    protected void writeDebugText(ByteBuf buffer, BedrockCodecHelper helper, DebugText text) {
        helper.writeString(buffer, text.getText());
        buffer.writeBoolean(text.isUseRotation());
        helper.writeOptionalNull(buffer, text.getBackgroundColor(), (buf, h, c) -> buf.writeIntLE(c.getRGB()));
        buffer.writeFloatLE(text.getLineGapHeight()); // new
        buffer.writeBoolean(text.isDepthTest());
        buffer.writeBoolean(text.isShowBackface());
        buffer.writeBoolean(text.isShowTextBackface());
    }
}
