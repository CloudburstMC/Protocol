package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.ClientboundDebugRendererType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ClientboundDebugRendererPacket extends BedrockPacket {

    private ClientboundDebugRendererType debugMarkerType;

    /**
     * Only used if {@link #debugMarkerType} is set to {@link ClientboundDebugRendererType#ADD_DEBUG_MARKER_CUBE}.
     */
    private String markerText;
    /**
     * Only used if {@link #debugMarkerType} is set to {@link ClientboundDebugRendererType#ADD_DEBUG_MARKER_CUBE}.
     */
    private Vector3f markerPosition;
    /**
     * Only used if {@link #debugMarkerType} is set to {@link ClientboundDebugRendererType#ADD_DEBUG_MARKER_CUBE}.
     */
    private float markerColorRed;
    /**
     * Only used if {@link #debugMarkerType} is set to {@link ClientboundDebugRendererType#ADD_DEBUG_MARKER_CUBE}.
     */
    private float markerColorGreen;
    /**
     * Only used if {@link #debugMarkerType} is set to {@link ClientboundDebugRendererType#ADD_DEBUG_MARKER_CUBE}.
     */
    private float markerColorBlue;
    /**
     * Only used if {@link #debugMarkerType} is set to {@link ClientboundDebugRendererType#ADD_DEBUG_MARKER_CUBE}.
     */
    private float markerColorAlpha;
    /**
     * Only used if {@link #debugMarkerType} is set to {@link ClientboundDebugRendererType#ADD_DEBUG_MARKER_CUBE}.
     * In milliseconds.
     */
    private long markerDuration;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_DEBUG_RENDERER;
    }

}
