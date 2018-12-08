package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
 * Used to trigger Note blocks, Chests and End Gateways
 *
 * ------- Note Block --------
 * transactionType: (Instrument)
 *   - 0 (Piano)
 *   - 1 (Base Drum)
 *   - 2 (Sticks)
 *   - 3 (Drum)
 *   - 4 (Bass)
 * data: 0-15
 * ---------------------------
 *
 * ------- Chest Block -------
 * transactionType: 1 (Chest open/closed)
 * data: 0 or 1
 * ---------------------------
 *
 * ------- End Gateway -------
 * transactionType: 1 (Cool down)
 * data: n/a
 * ---------------------------
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlockEventPacket extends BedrockPacket {
    private Vector3i blockPosition;
    private int eventType;
    private int eventData;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
