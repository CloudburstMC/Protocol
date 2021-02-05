package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.data.structure.StructureTemplateRequestOperation;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.math.vector.Vector3i;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class StructureTemplateDataRequestPacket extends BedrockPacket {
    private String name;
    private Vector3i position;
    private StructureSettings settings;
    private StructureTemplateRequestOperation operation;

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.STRUCTURE_TEMPLATE_DATA_EXPORT_REQUEST;
    }
}
