package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.PlayerUpdateEntityOverridesSerializer_v786;
import org.cloudburstmc.protocol.bedrock.packet.PlayerUpdateEntityOverridesPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class PlayerUpdateEntityOverridesSerializer_v2168 extends PlayerUpdateEntityOverridesSerializer_v786 {

    public static final PlayerUpdateEntityOverridesSerializer_v2168 INSTANCE = new PlayerUpdateEntityOverridesSerializer_v2168();

    private static final String[] TYPE_NAMES = {
            "clearoverrides",
            "removeoverride",
            "setintoverride",
            "setfloatoverride"
    };

    private static String typeName(int ordinal) {
        if (ordinal < 0 || ordinal >= TYPE_NAMES.length) {
            throw new IllegalStateException("Unknown entity override type: " + ordinal);
        }
        return TYPE_NAMES[ordinal];
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerUpdateEntityOverridesPacket packet) {
        VarInts.writeLong(buffer, packet.getEntityUniqueId());
        VarInts.writeUnsignedInt(buffer, packet.getPropertyIndex());
        VarInts.writeUnsignedInt(buffer, packet.getUpdateType().ordinal());
        helper.writeString(buffer, typeName(packet.getUpdateType().ordinal()));
        if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_INT_OVERRIDE)) {
            buffer.writeIntLE(packet.getIntValue());
        } else if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_FLOAT_OVERRIDE)) {
            buffer.writeFloatLE(packet.getFloatValue());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerUpdateEntityOverridesPacket packet) {
        packet.setEntityUniqueId(VarInts.readLong(buffer));
        packet.setPropertyIndex(VarInts.readUnsignedInt(buffer));

        int type = VarInts.readUnsignedInt(buffer);
        String name = helper.readString(buffer);
        if (!typeName(type).equals(name)) {
            throw new IllegalStateException("type != type name (expected " + typeName(type) + ", got " + name + ")");
        }

        packet.setUpdateType(PlayerUpdateEntityOverridesPacket.UpdateType.values()[type]);
        if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_INT_OVERRIDE)) {
            packet.setIntValue(buffer.readIntLE());
        } else if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_FLOAT_OVERRIDE)) {
            packet.setFloatValue(buffer.readFloatLE());
        }
    }
}