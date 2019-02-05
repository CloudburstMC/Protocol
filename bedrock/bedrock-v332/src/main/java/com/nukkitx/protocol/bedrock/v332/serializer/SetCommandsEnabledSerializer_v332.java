package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.SetCommandsEnabledPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetCommandsEnabledSerializer_v332 implements PacketSerializer<SetCommandsEnabledPacket> {
    public static final SetCommandsEnabledSerializer_v332 INSTANCE = new SetCommandsEnabledSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, SetCommandsEnabledPacket packet) {
        buffer.writeBoolean(packet.isCommandsEnabled());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetCommandsEnabledPacket packet) {
        packet.setCommandsEnabled(buffer.readBoolean());
    }
}
