package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPropertiesPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateBlockPropertiesSerializer_v361 implements BedrockPacketSerializer<UpdateBlockPropertiesPacket> {
    public static final UpdateBlockPropertiesSerializer_v361 INSTANCE = new UpdateBlockPropertiesSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockPropertiesPacket packet) {
        helper.writeTag(buffer, packet.getProperties());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateBlockPropertiesPacket packet) {
        packet.setProperties(helper.readTag(buffer));
    }
}
