package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureTemplateRequestOperation;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class StructureTemplateDataRequestPacket implements BedrockPacket {
    private String name;
    private Vector3i position;
    private StructureSettings settings;
    private StructureTemplateRequestOperation operation;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.STRUCTURE_TEMPLATE_DATA_EXPORT_REQUEST;
    }

    @Override
    public StructureTemplateDataRequestPacket clone() {
        try {
            return (StructureTemplateDataRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

