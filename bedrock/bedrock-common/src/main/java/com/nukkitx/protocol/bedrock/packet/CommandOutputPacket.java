package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.CommandOutputMessage;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class CommandOutputPacket extends BedrockPacket {
    protected final List<CommandOutputMessage> messages = new ArrayList<>();
    protected CommandOriginData commandOriginData;
    protected int outputType;
    protected int successCount;
    protected String data;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
