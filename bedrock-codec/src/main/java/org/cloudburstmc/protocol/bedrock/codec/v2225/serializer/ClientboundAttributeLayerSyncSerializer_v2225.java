package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2193.serializer.ClientboundAttributeLayerSyncSerializer_v2193;
import org.cloudburstmc.protocol.bedrock.data.attributelayer.*;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.List;

public class ClientboundAttributeLayerSyncSerializer_v2225 extends ClientboundAttributeLayerSyncSerializer_v2193 {

    public static final ClientboundAttributeLayerSyncSerializer_v2225 INSTANCE = new ClientboundAttributeLayerSyncSerializer_v2225();


    @Override
    protected void writeUpdateAttributeLayers(ByteBuf buf, BedrockCodecHelper helper, UpdateAttributeLayersData data) {
        List<AttributeLayerData> layers = data.getAttributeLayers();
        helper.writeArray(buf, layers, (b, layer) -> {
            helper.writeString(b, layer.getLayerName());
            //revert noise
            VarInts.writeInt(b, layer.getDimension());
            writeAttributeLayerSettings(b, helper, layer.getSettings());
            helper.writeArray(b, layer.getAttributes(), (bb, attr) ->
                    writeEnvironmentAttribute(bb, helper, attr));
        });
    }

    @Override
    protected UpdateAttributeLayersData readUpdateAttributeLayers(ByteBuf buf, BedrockCodecHelper helper) {
        List<AttributeLayerData> layers = new ArrayList<>();
        helper.readArray(buf, layers, b -> {
            String name = helper.readStringMaxLen(buf, 128);
            //revert noise
            int dim = VarInts.readInt(b);
            AttributeLayerSettings settings = readAttributeLayerSettings(b, helper);

            List<EnvironmentAttributeData> attrs = new ArrayList<>();
            helper.readArray(b, attrs, bb -> readEnvironmentAttribute(bb, helper), 1024);

            return new AttributeLayerData(name, null, dim, settings, attrs);
        }, 512);
        return new UpdateAttributeLayersData(layers);
    }

    @Override
    protected void writeEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper, EnvironmentAttributeData e) {
        helper.writeString(buf, e.getAttributeName());

        if (e.isNoiseTransition()) {
            VarInts.writeUnsignedInt(buf, 2); // NoiseTransitionAttributeData

            writeAttributeData(buf, helper, e.getFrom());
            writeAttributeData(buf, helper, e.getTo());

            VarInts.writeUnsignedInt(buf, e.getTotalTransitionTicks());
            VarInts.writeUnsignedInt(buf, e.getCurrentTransitionTicks());

            VarInts.writeInt(buf, e.getEasing().ordinal());

            helper.writeString(buf, e.getClockName());

            VarInts.writeUnsignedInt(buf, e.getLocalTransitionTicks());

            helper.writeString(buf, e.getNoiseName());

            buf.writeByte(e.getNoiseAlignment().getType().ordinal());
            VarInts.writeUnsignedInt(buf, e.getNoiseAlignment().getValue());
        } else if (e.getFrom() != null && e.getTo() != null) {
            VarInts.writeUnsignedInt(buf, 1); // TransitionAttributeData

            writeAttributeData(buf, helper, e.getFrom());
            writeAttributeData(buf, helper, e.getTo());

            VarInts.writeUnsignedInt(buf, e.getTotalTransitionTicks());
            VarInts.writeUnsignedInt(buf, e.getCurrentTransitionTicks());

            VarInts.writeInt(buf, e.getEasing().ordinal());

            helper.writeString(buf, e.getClockName());
        } else {
            VarInts.writeUnsignedInt(buf, 0); // ConstantAttributeData
            writeAttributeData(buf, helper, e.getAttribute());
        }
    }

    @Override
    protected EnvironmentAttributeData readEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper) {
        String name = helper.readStringMaxLen(buf, 128);

        int type = VarInts.readUnsignedInt(buf);

        if (type == 2) {
            AttributeData from = readAttributeData(buf, helper);
            AttributeData to = readAttributeData(buf, helper);

            int totalTransitionTicks = VarInts.readUnsignedInt(buf);
            int currentTransitionTicks = VarInts.readUnsignedInt(buf);

            CameraEase easing = CameraEase.values()[VarInts.readInt(buf)];

            String clockName = helper.readString(buf);

            int localTransitionTicks = VarInts.readUnsignedInt(buf);

            String noiseName = helper.readString(buf);

            NoiseAlignment na = new NoiseAlignment(NoiseAlignment.Type.values()[buf.readUnsignedByte()], VarInts.readUnsignedInt(buf));

            return new EnvironmentAttributeData(name, from, null, to, currentTransitionTicks, totalTransitionTicks, easing, clockName, localTransitionTicks, true, noiseName, na);
        } else if (type == 1) {
            AttributeData from = readAttributeData(buf, helper);
            AttributeData to = readAttributeData(buf, helper);

            int totalTransitionTicks = VarInts.readUnsignedInt(buf);
            int currentTransitionTicks = VarInts.readUnsignedInt(buf);

            CameraEase easing = CameraEase.values()[VarInts.readInt(buf)];

            String clockName = helper.readString(buf);

            return new EnvironmentAttributeData(name, from, null, to, currentTransitionTicks, totalTransitionTicks, easing, clockName, 0, false, null, null);
        } if (type == 0) {
            AttributeData attribute = readAttributeData(buf, helper);
            return new EnvironmentAttributeData(name, null, attribute, null, 0, 0, null, null, 0, false, null, null);
        } else {
            throw new IllegalArgumentException(type + " is not oneOf<ConstantAttributeData, TransitionAttributeData, NoiseTransitionAttributeData>");
        }
    }
}
