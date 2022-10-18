package com.nukkitx.protocol.bedrock.v557.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.SetEntityDataSerializer_v291;
import io.netty.buffer.ByteBuf;

public class SetEntityDataSerializer_v557 extends SetEntityDataSerializer_v291 {

    public static final SetEntityDataSerializer_v557 INSTANCE = new SetEntityDataSerializer_v557();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeEntityProperties(buffer, packet.getProperties()); // added
        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetEntityDataPacket packet) {
        super.deserialize(buffer, helper, packet);

        helper.readEntityProperties(buffer, packet.getProperties()); // added
        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}
