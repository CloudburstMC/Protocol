package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BossEventPacket extends BedrockPacket {
    private long bossUniqueEntityId;
    private Action action;
    private long playerUniqueEntityId;
    private String title;
    private float healthPercentage;
    private int darkenSky;
    private int color;
    private int overlay;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BOSS_EVENT;
    }

    public enum Action {
        /**
         * Shows the bossbar to the player.
         */
        SHOW,
        /**
         * Registers a player to a boss fight.
         */
        REGISTER_PLAYER,
        /**
         * Removes the bossbar from the client.
         */
        HIDE,
        /**
         * Unregisters a player from a boss fight.
         */
        UNREGISTER_PLAYER,
        /**
         * Appears not to be implemented. Currently bar percentage only appears to change in response to the target entity's health.
         */
        HEALTH_PERCENTAGE,
        /**
         * Also appears to not be implemented. Title clientside sticks as the target entity's nametag, or their entity transactionType name if not set.
         */
        TITLE,
        /**
         * Darken the sky when the boss bar is shown.
         */
        DARKEN_SKY,
        /**
         * Not implemented :( Intended to alter bar appearance, but these currently produce no effect on clientside whatsoever.
         */
        OVERLAY
    }
}
