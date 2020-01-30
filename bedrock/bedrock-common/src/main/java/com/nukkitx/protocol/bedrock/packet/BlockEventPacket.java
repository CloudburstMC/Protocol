package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Used to trigger Note blocks, Chests and End Gateways
 *
 * <h2>Examples</h2>
 *
 * <h3>Note Block</h3>
 * <blockquote>
 *     eventType: (Instrument)
 *     <ul>
 *         <li>0 (Piano)</li>
 *         <li>1 (Base Drum)</li>
 *         <li>2 (Sticks)</li>
 *         <li>3 (Drum)</li>
 *         <li>4 (Bass)</li>
 *     </ul>
 *     data: 0-15
 * </blockquote>
 *
 * <h3>Chest Block</h3>
 * <blockquote>
 *     eventType: 1 (Chest open/closed)<br>
 *     data: 0 or 1
 * </blockquote>
 *
 * <h3>End Gateway</h3>
 * <blockquote>
 *     eventType: 1 (Cool down)<br>
 *     data: n/a
 * </blockquote>
 *
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class BlockEventPacket extends BedrockPacket {

    /**
     * Position to execute block event.
     *
     * @param blockPosition block event position
     * @return block event position
     */
    private Vector3i blockPosition;

    /**
     * Block event type to execute
     *
     * @param eventType block event type
     * @return block event type
     */
    private int eventType;

    /**
     * Data used by event (if applicable)
     *
     * @param eventData data for event
     * @return data for event
     */
    private int eventData;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BLOCK_EVENT;
    }
}
