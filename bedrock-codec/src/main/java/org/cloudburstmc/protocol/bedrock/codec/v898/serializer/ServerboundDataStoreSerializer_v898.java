package org.cloudburstmc.protocol.bedrock.codec.v898.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDataStorePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerboundDataStoreSerializer_v898 implements BedrockPacketSerializer<ServerboundDataStorePacket> {

    public static final ServerboundDataStoreSerializer_v898 INSTANCE = new ServerboundDataStoreSerializer_v898();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDataStorePacket packet) {
        helper.writeString(buffer, packet.getUpdate().getDataStoreName());
        helper.writeString(buffer, packet.getUpdate().getProperty());
        helper.writeString(buffer, packet.getUpdate().getPath());

        Object value = packet.getUpdate().getData();

        int type = value instanceof Number ? 0 : value instanceof Boolean ? 1 : value instanceof String ? 2 : -1;

        VarInts.writeUnsignedInt(buffer, type);

        switch (type) {
            case 0:
                buffer.writeDoubleLE(((Number) value).doubleValue());
                break;
            case 1:
                buffer.writeBoolean((boolean) value);
                break;
            case 2:
                helper.writeString(buffer, (String) value);
                break;
            default:
                throw new IllegalStateException("Invalid data store data type");
        }

        buffer.writeIntLE(packet.getUpdate().getUpdateCount());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDataStorePacket packet) {
        packet.getUpdate().setDataStoreName(helper.readStringMaxLen(buffer, 1000));
        packet.getUpdate().setProperty(helper.readStringMaxLen(buffer, 1000));
        packet.getUpdate().setPath(helper.readStringMaxLen(buffer, 1000));

        int type = VarInts.readUnsignedInt(buffer);

        switch (type) {
            case 0:
                packet.getUpdate().setData(buffer.readDoubleLE());
                break;
            case 1:
                packet.getUpdate().setData(buffer.readBoolean());
                break;
            case 2:
                packet.getUpdate().setData(helper.readStringMaxLen(buffer, 5000));
                break;
            default:
                throw new IllegalStateException("Invalid data store data type: " + type);
        }

        packet.getUpdate().setUpdateCount((int) buffer.readUnsignedIntLE());
    }
}
