package com.nukkitx.protocol.bedrock.v527.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.ee.LessonAction;
import com.nukkitx.protocol.bedrock.packet.LessonProgressPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonProgressSerializer_v527 implements BedrockPacketSerializer<LessonProgressPacket> {
    public static final LessonProgressSerializer_v527 INSTANCE = new LessonProgressSerializer_v527();

    private static final LessonAction[] ACTIONS = LessonAction.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LessonProgressPacket packet) {
        VarInts.writeInt(buffer, packet.getAction().ordinal());
        VarInts.writeInt(buffer, packet.getScore());
        helper.writeString(buffer, packet.getActivityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LessonProgressPacket packet) {
        packet.setAction(ACTIONS[VarInts.readInt(buffer)]);
        packet.setScore(VarInts.readInt(buffer));
        packet.setActivityId(helper.readString(buffer));
    }
}
