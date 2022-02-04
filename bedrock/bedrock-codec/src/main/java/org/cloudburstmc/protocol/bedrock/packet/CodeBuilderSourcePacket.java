package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderCategoryType;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderOperationType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CodeBuilderSourcePacket implements BedrockPacket {

    private CodeBuilderOperationType operation;
    private CodeBuilderCategoryType category;
    private String value;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CODE_BUILDER_SOURCE_PACKET;
    }
}
