package com.nukkitx.protocol.genoa.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class GenoaUpdateBlockPacket extends BedrockPacket {

    public int VarInt1;
    public int UnsignedVarInt1;
    public int VarInt2;
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
