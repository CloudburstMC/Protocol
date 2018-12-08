package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StructureBlockUpdatePacket extends BedrockPacket {
    private Vector3i blockPosition;
    private Type structureType;
    // Structure Editor Data start
    private String customName;
    private String metadata;
    private Vector3i structureOffset;
    private Vector3i structureSize;
    private boolean includingEntities;
    private boolean ignoringBlocks;
    private boolean includingPlayers;
    private boolean showingAir;
    // Structure Settings start
    private float integrity;
    private int seed;
    private int mirror;
    private int rotation;
    private boolean ignoringEntities;
    private boolean ignoringStructureBlocks;
    private Vector3i bbMin;
    private Vector3i bbMax;
    // Structure Settings end
    // Structure Editor Data end
    private boolean boundingBoxVisible;
    private boolean powered;

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
