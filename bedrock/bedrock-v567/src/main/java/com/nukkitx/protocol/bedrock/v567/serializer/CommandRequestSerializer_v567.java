package com.nukkitx.protocol.bedrock.v567.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import com.nukkitx.protocol.bedrock.v291.serializer.CommandRequestSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommandRequestSerializer_v567 extends CommandRequestSerializer_v291 {

    public static final CommandRequestSerializer_v567 INSTANCE = new CommandRequestSerializer_v567();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CommandRequestPacket packet) {
        super.serialize(buffer, helper, packet);

        VarInts.writeInt(buffer, packet.getVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CommandRequestPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setVersion(VarInts.readInt(buffer));
    }
}
