package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v844.serializer.ServerboundPackSettingChangeSerializer_v844;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundPackSettingChangePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.List;

public class ServerboundPackSettingChangeSerializer_v2193 extends ServerboundPackSettingChangeSerializer_v844 {

    public static final ServerboundPackSettingChangeSerializer_v2193 INSTANCE = new ServerboundPackSettingChangeSerializer_v2193();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundPackSettingChangePacket packet) {
        helper.writeUuid(buffer, packet.getPackId());
        helper.writeString(buffer, packet.getPackSettingName());

        Object value = packet.getPackSettingValue();

        int type = value instanceof Float ? 0 : value instanceof Boolean ? 1 : value instanceof String ? 2 : value instanceof List ? 3 : -1;

        VarInts.writeUnsignedInt(buffer, type);

        switch (type) {
            case 0:
                buffer.writeFloatLE((float) value);
                break;
            case 1:
                buffer.writeBoolean((boolean) value);
                break;
            case 2:
                helper.writeString(buffer, (String) value);
                break;
            case 3:
                helper.writeArray(buffer, (List) value, helper::writeString);
                break;
            default:
                throw new IllegalStateException("Invalid pack setting type");
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundPackSettingChangePacket packet) {
        packet.setPackId(helper.readUuid(buffer));
        packet.setPackSettingName(helper.readString(buffer));

        int type = VarInts.readUnsignedInt(buffer);

        switch (type) {
            case 0:
                packet.setPackSettingValue(buffer.readFloatLE());
                break;
            case 1:
                packet.setPackSettingValue(buffer.readBoolean());
                break;
            case 2:
                packet.setPackSettingValue(helper.readString(buffer));
                break;
            case 3:
                List<String> list = new ArrayList<>();
                helper.readArray(buffer, list, helper::readString);
                packet.setPackSettingValue(list);
                break;
            default:
                throw new IllegalStateException("Invalid pack setting type");
        }
    }
}
