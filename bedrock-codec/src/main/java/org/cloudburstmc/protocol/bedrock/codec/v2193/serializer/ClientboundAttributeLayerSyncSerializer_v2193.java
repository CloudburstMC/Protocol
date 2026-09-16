package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.ClientboundAttributeLayerSyncSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.data.attributelayer.*;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.common.util.VarInts;

public class ClientboundAttributeLayerSyncSerializer_v2193 extends ClientboundAttributeLayerSyncSerializer_v1001 {

    public static final ClientboundAttributeLayerSyncSerializer_v2193 INSTANCE = new ClientboundAttributeLayerSyncSerializer_v2193();

    @Override
    protected void writeEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper, EnvironmentAttributeData e) {
        super.writeEnvironmentAttribute(buf, helper, e);

        buf.writeByte(e.getNoiseAlignment().getType().ordinal());
        VarInts.writeUnsignedInt(buf, e.getNoiseAlignment().getValue());
    }

    @Override
    protected EnvironmentAttributeData readEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper) {
        String name = helper.readStringMaxLen(buf, 128);

        AttributeData from = helper.readOptional(buf, null, b -> readAttributeData(b, helper));
        AttributeData attribute = readAttributeData(buf, helper);
        AttributeData to = helper.readOptional(buf, null, b -> readAttributeData(b, helper));

        int currentTicks = (int) buf.readUnsignedIntLE();
        int totalTicks = (int) buf.readUnsignedIntLE();

        CameraEase easing = CameraEase.fromName(helper.readString(buf));

        int localTransitionTicks = (int) buf.readUnsignedIntLE();
        boolean noiseTransition = buf.readBoolean();

        NoiseAlignment na = new NoiseAlignment(NoiseAlignment.Type.values()[buf.readUnsignedByte()], VarInts.readUnsignedInt(buf));

        return new EnvironmentAttributeData(name, from, attribute, to, currentTicks, totalTicks, easing, localTransitionTicks, noiseTransition, na);
    }
}
