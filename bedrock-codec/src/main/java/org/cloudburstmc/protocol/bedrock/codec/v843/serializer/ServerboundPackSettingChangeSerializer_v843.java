package org.cloudburstmc.protocol.bedrock.codec.v843.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundPackSettingChangePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerboundPackSettingChangeSerializer_v843 implements BedrockPacketSerializer<ServerboundPackSettingChangePacket> {

    public static final ServerboundPackSettingChangeSerializer_v843 INSTANCE = new ServerboundPackSettingChangeSerializer_v843();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundPackSettingChangePacket packet) {
        helper.writeUuid(buffer, packet.getPackId());
        helper.writeString(buffer, packet.getPackSettingName());

        Object value = packet.getPackSettingValue();

        int type = value instanceof Float ? 0 : value instanceof Boolean ? 1 : value instanceof String ? 2 : -1;

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
            default:
                throw new IllegalStateException("Invalid pack setting type");
        }
    }
}
