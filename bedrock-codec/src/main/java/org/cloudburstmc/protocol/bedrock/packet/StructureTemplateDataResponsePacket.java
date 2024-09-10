package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureTemplateResponseType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class StructureTemplateDataResponsePacket implements BedrockPacket {
    private String name;
    private boolean save;
    private NbtMap tag;
    private StructureTemplateResponseType type;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.STRUCTURE_TEMPLATE_DATA_EXPORT_RESPONSE;
    }

    @Override
    public StructureTemplateDataResponsePacket clone() {
        try {
            return (StructureTemplateDataResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

