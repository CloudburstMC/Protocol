package org.cloudburstmc.protocol.bedrock.codec.v944.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.attributelayer.*;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundAttributeLayerSyncPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundAttributeLayerSyncSerializer_v944 implements BedrockPacketSerializer<ClientboundAttributeLayerSyncPacket> {

    public static final ClientboundAttributeLayerSyncSerializer_v944 INSTANCE = new ClientboundAttributeLayerSyncSerializer_v944();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundAttributeLayerSyncPacket packet) {
        AttributeLayerSyncPayload data = packet.getData();

        if (data instanceof UpdateAttributeLayersData) {
            VarInts.writeUnsignedInt(buffer, 0);
            writeUpdateAttributeLayers(buffer, helper, (UpdateAttributeLayersData) data);
        } else if (data instanceof UpdateAttributeLayerSettingsData) {
            VarInts.writeUnsignedInt(buffer, 1);
            writeUpdateAttributeLayerSettings(buffer, helper, (UpdateAttributeLayerSettingsData) data);
        } else if (data instanceof UpdateEnvironmentAttributesData) {
            VarInts.writeUnsignedInt(buffer, 2);
            writeUpdateEnvironmentAttributes(buffer, helper, (UpdateEnvironmentAttributesData) data);
        } else if (data instanceof RemoveEnvironmentAttributesData) {
            VarInts.writeUnsignedInt(buffer, 3);
            writeRemoveEnvironmentAttributes(buffer, helper, (RemoveEnvironmentAttributesData) data);
        } else {
            throw new IllegalArgumentException("Not oneOf<UpdateAttributeLayersData, UpdateAttributeLayerSettingsData, UpdateEnvironmentAttributesData, RemoveEnvironmentAttributesData>");
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundAttributeLayerSyncPacket packet) {
        int type = VarInts.readUnsignedInt(buffer);

        switch (type) {
            case 0:
                packet.setData(readUpdateAttributeLayers(buffer, helper));
                return;
            case 1:
                packet.setData(readUpdateAttributeLayerSettings(buffer, helper));
                return;
            case 2:
                packet.setData(readUpdateEnvironmentAttributes(buffer, helper));
                return;
            case 3:
                packet.setData(readRemoveEnvironmentAttributes(buffer, helper));
                return;
        }

        throw new IllegalArgumentException(type + " is not oneOf<UpdateAttributeLayersData, UpdateAttributeLayerSettingsData, UpdateEnvironmentAttributesData, RemoveEnvironmentAttributesData>");
    }

    protected void writeUpdateAttributeLayers(ByteBuf buf, BedrockCodecHelper helper, UpdateAttributeLayersData data) {
        List<AttributeLayerData> layers = data.getAttributeLayers();
        helper.writeArray(buf, layers, (b, layer) -> {
            helper.writeString(b, layer.getLayerName());
            VarInts.writeInt(b, layer.getDimension());
            writeAttributeLayerSettings(b, helper, layer.getSettings());
            helper.writeArray(b, layer.getAttributes(), (bb, attr) ->
                    writeEnvironmentAttribute(bb, helper, attr));
        });
    }

    protected UpdateAttributeLayersData readUpdateAttributeLayers(ByteBuf buf, BedrockCodecHelper helper) {
        List<AttributeLayerData> layers = new ArrayList<>();
        helper.readArray(buf, layers, b -> {
            String name = helper.readStringMaxLen(buf, 128);
            int dim = VarInts.readInt(b);
            AttributeLayerSettings settings = readAttributeLayerSettings(b, helper);

            List<EnvironmentAttributeData> attrs = new ArrayList<>();
            helper.readArray(b, attrs, bb -> readEnvironmentAttribute(bb, helper), 1024);

            return new AttributeLayerData(name, null, dim, settings, attrs);
        }, 512);
        return new UpdateAttributeLayersData(layers);
    }

    private void writeUpdateAttributeLayerSettings(ByteBuf buf, BedrockCodecHelper helper, UpdateAttributeLayerSettingsData data) {
        helper.writeString(buf, data.getLayerName());
        VarInts.writeInt(buf, data.getDimension());
        writeAttributeLayerSettings(buf, helper, data.getSettings());
    }

    private UpdateAttributeLayerSettingsData readUpdateAttributeLayerSettings(ByteBuf buf, BedrockCodecHelper helper) {
        String name = helper.readStringMaxLen(buf, 128);
        int dim = VarInts.readInt(buf);
        AttributeLayerSettings settings = readAttributeLayerSettings(buf, helper);
        return new UpdateAttributeLayerSettingsData(name, dim, settings);
    }

    private void writeUpdateEnvironmentAttributes(ByteBuf buf, BedrockCodecHelper helper, UpdateEnvironmentAttributesData data) {
        helper.writeString(buf, data.getLayerName());
        VarInts.writeInt(buf, data.getDimension());
        helper.writeArray(buf, data.getAttributes(), (b, attr) ->
                writeEnvironmentAttribute(b, helper, attr));
    }

    private UpdateEnvironmentAttributesData readUpdateEnvironmentAttributes(ByteBuf buf, BedrockCodecHelper helper) {
        String name = helper.readStringMaxLen(buf, 128);
        int dim = VarInts.readInt(buf);
        List<EnvironmentAttributeData> attrs = new ArrayList<>();
        helper.readArray(buf, attrs, b -> readEnvironmentAttribute(b, helper), 1024);
        return new UpdateEnvironmentAttributesData(name, dim, attrs);
    }

    private void writeRemoveEnvironmentAttributes(ByteBuf buf, BedrockCodecHelper helper, RemoveEnvironmentAttributesData data) {
        helper.writeString(buf, data.getLayerName());
        VarInts.writeInt(buf, data.getDimension());
        helper.writeArray(buf, data.getAttributes(), helper::writeString);
    }

    private RemoveEnvironmentAttributesData readRemoveEnvironmentAttributes(ByteBuf buf, BedrockCodecHelper helper) {
        String name = helper.readStringMaxLen(buf, 128);
        int dim = VarInts.readInt(buf);
        List<String> attrs = new ArrayList<>();
        helper.readArray(buf, attrs, helper::readString, 1024);
        return new RemoveEnvironmentAttributesData(name, dim, attrs);
    }

    protected void writeAttributeLayerSettings(ByteBuf buf, BedrockCodecHelper helper, AttributeLayerSettings s) {
        buf.writeIntLE(s.getPriority());
        writeWeight(buf, helper, s.getWeight());
        buf.writeBoolean(s.isEnabled());
        buf.writeBoolean(s.isTransitionsPaused());
    }

    protected AttributeLayerSettings readAttributeLayerSettings(ByteBuf buf, BedrockCodecHelper helper) {
        int priority = buf.readIntLE();
        AttributeLayerSettings.Weight weight = readWeight(buf, helper);
        boolean enabled = buf.readBoolean();
        boolean paused = buf.readBoolean();
        return new AttributeLayerSettings(priority, weight, enabled, paused);
    }

    protected void writeWeight(ByteBuf buf, BedrockCodecHelper helper, AttributeLayerSettings.Weight w) {
        if (w instanceof AttributeLayerSettings.FloatWeight) {
            VarInts.writeUnsignedInt(buf, 0);
            buf.writeFloatLE(((AttributeLayerSettings.FloatWeight) w).getValue());
        } else if (w instanceof AttributeLayerSettings.StringWeight) {
            VarInts.writeUnsignedInt(buf, 1);
            helper.writeString(buf, ((AttributeLayerSettings.StringWeight) w).getValue());
        } else {
            throw new IllegalArgumentException("Unknown Weight: " + w);
        }
    }

    protected AttributeLayerSettings.Weight readWeight(ByteBuf buf, BedrockCodecHelper helper) {
        int type = VarInts.readUnsignedInt(buf);
        switch (type) {
            case 0:
                return new AttributeLayerSettings.FloatWeight(buf.readFloatLE());
            case 1:
                return new AttributeLayerSettings.StringWeight(helper.readString(buf));
        }
        throw new IllegalArgumentException("Unknown Weight type: " + type);
    }

    protected void writeEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper, EnvironmentAttributeData e) {
        helper.writeString(buf, e.getAttributeName());
        helper.writeOptionalNull(buf, e.getFrom(), (b, attr) -> writeAttributeData(b, helper, attr));
        writeAttributeData(buf, helper, e.getAttribute());
        helper.writeOptionalNull(buf, e.getTo(), (b, attr) -> writeAttributeData(b, helper, attr));
        buf.writeIntLE(e.getCurrentTransitionTicks());
        buf.writeIntLE(e.getTotalTransitionTicks());
        helper.writeString(buf, e.getEasing().getSerializeName());
    }

    protected EnvironmentAttributeData readEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper) {
        String name = helper.readStringMaxLen(buf, 128);

        AttributeData from = helper.readOptional(buf, null, b -> readAttributeData(b, helper));
        AttributeData attribute = readAttributeData(buf, helper);
        AttributeData to = helper.readOptional(buf, null, b -> readAttributeData(b, helper));

        int currentTicks = (int) buf.readUnsignedIntLE();
        int totalTicks = (int) buf.readUnsignedIntLE();

        CameraEase easing = CameraEase.fromName(helper.readString(buf));

        return new EnvironmentAttributeData(name, from, attribute, to, currentTicks, totalTicks, easing, null, 0, false, null, null);
    }

    private static final List<String> BOOL_OPERATIONS = Arrays.asList("override", "alpha_blend", "and", "nand", "or", "nor", "xor", "xnor");
    private static final List<String> FLOAT_OPERATIONS = Arrays.asList("override", "alpha_blend", "add", "subtract", "multiply", "minimum", "maximum");
    private static final List<String> COLOR_OPERATIONS = Arrays.asList("override", "alpha_blend", "add", "subtract", "multiply");

    protected void writeAttributeData(ByteBuf buf, BedrockCodecHelper helper, AttributeData data) {
        if (data instanceof BoolAttributeData) {
            BoolAttributeData at = (BoolAttributeData) data;
            VarInts.writeUnsignedInt(buf, 0);
            buf.writeBoolean(at.isValue());
            helper.writeString(buf, BOOL_OPERATIONS.get(at.getOperation().ordinal()));
        } else if (data instanceof FloatAttributeData) {
            FloatAttributeData at = (FloatAttributeData) data;
            VarInts.writeUnsignedInt(buf, 1);
            buf.writeFloatLE(at.getValue());
            helper.writeString(buf, FLOAT_OPERATIONS.get(at.getOperation().ordinal()));
            helper.writeOptionalNull(buf, at.getConstraintMin(), ByteBuf::writeFloatLE);
            helper.writeOptionalNull(buf, at.getConstraintMax(), ByteBuf::writeFloatLE);
        } else if (data instanceof ColorAttributeData) {
            ColorAttributeData at = (ColorAttributeData) data;
            VarInts.writeUnsignedInt(buf, 2);
            writeColor255(buf, helper, at.getValue());
            helper.writeString(buf, COLOR_OPERATIONS.get(at.getOperation().ordinal()));
        } else {
            throw new IllegalArgumentException("Unknown AttributeData: " + data);
        }
    }

    protected AttributeData readAttributeData(ByteBuf buf, BedrockCodecHelper helper) {
        int type = VarInts.readUnsignedInt(buf);
        switch (type) {
            case 0:
                return new BoolAttributeData(
                    buf.readBoolean(),
                    BoolAttributeData.Operation.values()[BOOL_OPERATIONS.indexOf(helper.readString(buf))]
                );
            case 1:
                return new FloatAttributeData(
                    buf.readFloatLE(),
                    FloatAttributeData.Operation.values()[FLOAT_OPERATIONS.indexOf(helper.readString(buf))],
                    helper.readOptional(buf, null, ByteBuf::readFloatLE),
                    helper.readOptional(buf, null, ByteBuf::readFloatLE)
                );
            case 2:
                return new ColorAttributeData(
                    readColor255(buf, helper),
                    ColorAttributeData.Operation.values()[COLOR_OPERATIONS.indexOf(helper.readString(buf))]
                );
        }
        throw new IllegalArgumentException("Unknown AttributeData type: " + type);
    }

    private void writeColor255(ByteBuf buf, BedrockCodecHelper helper, ColorAttributeData.Color255RGBA c) {
        if (c instanceof ColorAttributeData.StringColor) {
            VarInts.writeUnsignedInt(buf, 0);
            helper.writeString(buf, ((ColorAttributeData.StringColor) c).getValue());
        } else if (c instanceof ColorAttributeData.ArrayColor) {
            VarInts.writeUnsignedInt(buf, 1);
            int[] v = ((ColorAttributeData.ArrayColor) c).getValue();
            for (int i = 0; i < 4; i++) {
                buf.writeIntLE(v[i]);
            }
        } else {
            throw new IllegalArgumentException("Unknown Color255RGBA: " + c);
        }
    }

    private ColorAttributeData.Color255RGBA readColor255(ByteBuf buf, BedrockCodecHelper helper) {
        int type = VarInts.readUnsignedInt(buf);
        switch (type) {
            case 0:
                return new ColorAttributeData.StringColor(helper.readString(buf));
            case 1:
                int[] v = new int[4];
                for (int i = 0; i < 4; i++) {
                    v[i] = buf.readIntLE();
                }
                return new ColorAttributeData.ArrayColor(v);
        }
        throw new IllegalArgumentException("Unknown Color255RGBA type: " + type);
    }
}
