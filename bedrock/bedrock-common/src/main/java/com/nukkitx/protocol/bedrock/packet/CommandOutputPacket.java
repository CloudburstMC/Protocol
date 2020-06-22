package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOutputMessage;
import com.nukkitx.protocol.bedrock.data.command.CommandOutputType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class CommandOutputPacket extends BedrockPacket {
    private final List<CommandOutputMessage> messages = new ObjectArrayList<>();
    private CommandOriginData commandOriginData;
    private CommandOutputType type;
    private int successCount;
    private String data;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.COMMAND_OUTPUT;
    }
}
