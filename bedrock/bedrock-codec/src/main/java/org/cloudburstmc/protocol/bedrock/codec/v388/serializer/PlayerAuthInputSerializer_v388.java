package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.ClientPlayMode;
import org.cloudburstmc.protocol.bedrock.data.InputMode;
import org.cloudburstmc.protocol.bedrock.data.PlayerAuthInputData;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerAuthInputSerializer_v388 implements BedrockPacketSerializer<PlayerAuthInputPacket> {

    public static final PlayerAuthInputSerializer_v388 INSTANCE = new PlayerAuthInputSerializer_v388();

    protected static final InputMode[] INPUT_MODES = InputMode.values();
    protected static final ClientPlayMode[] CLIENT_PLAY_MODES = ClientPlayMode.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        Vector3f rotation = packet.getRotation();
        buffer.writeFloatLE(rotation.getX());
        buffer.writeFloatLE(rotation.getY());
        helper.writeVector3f(buffer, packet.getPosition());
        buffer.writeFloatLE(packet.getMotion().getX());
        buffer.writeFloatLE(packet.getMotion().getY());
        buffer.writeFloatLE(rotation.getZ());
        long flagValue = 0;
        for (PlayerAuthInputData data : packet.getInputData()) {
            flagValue |= (1L << data.ordinal());
        }
        VarInts.writeUnsignedLong(buffer, flagValue);
        VarInts.writeUnsignedInt(buffer, packet.getInputMode().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getPlayMode().ordinal());
        writeInteractionModel(buffer, helper, packet);

        if (packet.getPlayMode() == ClientPlayMode.REALITY) {
            helper.writeVector3f(buffer, packet.getVrGazeDirection());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        float x = buffer.readFloatLE();
        float y = buffer.readFloatLE();
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(Vector2f.from(buffer.readFloatLE(), buffer.readFloatLE()));
        float z = buffer.readFloatLE();
        packet.setRotation(Vector3f.from(x, y, z));
        long flagValue = VarInts.readUnsignedLong(buffer);
        Set<PlayerAuthInputData> flags = packet.getInputData();
        for (PlayerAuthInputData flag : PlayerAuthInputData.values()) {
            if ((flagValue & (1L << flag.ordinal())) != 0) {
                flags.add(flag);
            }
        }
        packet.setInputMode(INPUT_MODES[VarInts.readUnsignedInt(buffer)]);
        packet.setPlayMode(CLIENT_PLAY_MODES[VarInts.readUnsignedInt(buffer)]);
        readInteractionModel(buffer, helper, packet);

        if (packet.getPlayMode() == ClientPlayMode.REALITY) {
            packet.setVrGazeDirection(helper.readVector3f(buffer));
        }
    }

    protected void readInteractionModel(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
    }

    protected void writeInteractionModel(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
    }
}
