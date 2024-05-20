package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderCategoryType;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderCodeStatus;
import org.cloudburstmc.protocol.bedrock.data.CodeBuilderOperationType;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CodeBuilderSourcePacket implements BedrockPacket {

    private CodeBuilderOperationType operation;
    private CodeBuilderCategoryType category;
    /**
     * @deprecated since v685
     */
    private String value;
    /**
     * @since v685
     */
    private CodeBuilderCodeStatus codeStatus;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CODE_BUILDER_SOURCE;
    }
}
