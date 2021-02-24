package com.nukkitx.protocol.genoa.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class GenoaUpdateBlockPacket extends BedrockPacket {

    public Vector3i blockPos;
    public int UnsignedVarInt2;
    public int UnsignedVarInt3;
    public int UnsignedVarInt4; // Either this or the next two ones if bool is true
    public float Float1;
    public boolean CheckForFloat;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.GENOA_UPDATE_BLOCK;
    }
}
