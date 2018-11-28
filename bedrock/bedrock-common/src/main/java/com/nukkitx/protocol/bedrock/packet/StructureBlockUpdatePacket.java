package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StructureBlockUpdatePacket extends BedrockPacket {
    protected Vector3i blockPosition;
    protected Type structureType;
    // Structure Editor Data start
    protected String customName;
    protected String metadata;
    protected Vector3i structureOffset;
    protected Vector3i structureSize;
    protected boolean includingEntities;
    protected boolean ignoringBlocks;
    protected boolean includingPlayers;
    protected boolean showingAir;
    // Structure Settings start
    protected float integrity;
    protected int seed;
    protected int mirror;
    protected int rotation;
    protected boolean ignoringEntities;
    protected boolean ignoringStructureBlocks;
    protected Vector3i bbMin;
    protected Vector3i bbMax;
    // Structure Settings end
    // Structure Editor Data end
    protected boolean boundingBoxVisible;
    protected boolean powered;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Type {
        NONE,
        SAVE,
        LOAD,
    }
}
