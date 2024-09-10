package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Used to trigger an entity animation on the specified runtime IDs to the client that receives it.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AnimateEntityPacket implements BedrockPacket {

    /**
     * Name of the to play on the entities specified in {@link #runtimeEntityIds}
     *
     * @param animation entity animation
     * @return entity animation
     */
    private String animation;

    /**
     * The entity state to move to when the animation has finished playing.
     *
     * @param nextState state after animation has finished
     * @return state after animation has finished
     */
    private String nextState;

    /**
     * Expression to check if the animation needs to stop.
     *
     * @param stopExpression molang expression (???)
     * @return molang expression (???)
     */
    private String stopExpression;

    /**
     * The molang stop expression version
     *
     * @param stopExpressionVersion the stop expression version
     * @return molang stop expression version
     *
     * @since v465
     */
    private int stopExpressionVersion;


    /**
     * Name of the animation controller to use.
     *
     * @param controller controller name
     * @return controller name
     */
    private String controller;

    /**
     * Time taken to blend out of the specified animation.
     *
     * @param blendOutTime time
     * @return time
     */
    private float blendOutTime;

    /**
     * Entity runtime IDs to run the animation on when sent to the client.
     *
     * @param runtimeEntityIds runtime entity IDs list
     * @return runtime entity IDs list
     */
    private final LongList runtimeEntityIds = new LongArrayList();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ANIMATE_ENTITY;
    }

    @Override
    public AnimateEntityPacket clone() {
        try {
            return (AnimateEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

