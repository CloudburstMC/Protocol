package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.AnimatePacket;
import com.nukkitx.protocol.bedrock.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;

public class AnimatePacket_v291 extends AnimatePacket {
    private static final TIntHashBiMap<Action> types = new TIntHashBiMap<>();

    static {
        types.put(0, Action.NO_ACTION);
        types.put(1, Action.SWING);
        types.put(3, Action.WAKE_UP);
        types.put(4, Action.CRITICAL_HIT);
        types.put(5, Action.MAGIC_CRITICAL_HIT);
        types.put(128, Action.ROW_RIGHT);
        types.put(129, Action.ROW_LEFT);
    }

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, types.get(action));
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        if (action == Action.ROW_LEFT || action == Action.ROW_RIGHT) {
            buffer.writeFloatLE(rowingTime);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        action = types.get(VarInts.readInt(buffer));
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        if (action == Action.ROW_LEFT || action == Action.ROW_RIGHT) {
            rowingTime = buffer.readFloatLE();
        }
    }
}
