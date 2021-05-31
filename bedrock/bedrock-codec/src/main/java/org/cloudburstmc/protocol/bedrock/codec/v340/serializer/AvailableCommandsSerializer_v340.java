package org.cloudburstmc.protocol.bedrock.codec.v340.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.AvailableCommandsSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.command.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.List;

public class AvailableCommandsSerializer_v340 extends AvailableCommandsSerializer_v291 {

    public AvailableCommandsSerializer_v340(TypeMap<CommandParam> paramTypeMap) {
        super(paramTypeMap);
    }

    @Override
    protected void writeParameter(ByteBuf buffer, BedrockCodecHelper helper, CommandParamData param, List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes) {
        super.writeParameter(buffer, helper, param, enums, softEnums, postFixes);

        byte options = 0;

        if (param.getOptions() != null) {
            for (CommandParamOption option : param.getOptions()) {
                options |= 1 << option.ordinal();
            }
        }
        buffer.writeByte(options);
    }

    @Override
    protected CommandParamData.Builder readParameter(ByteBuf buffer, BedrockCodecHelper helper) {
        String parameterName = helper.readString(buffer);
        CommandSymbolData type = CommandSymbolData.deserialize(buffer.readIntLE());
        boolean optional = buffer.readBoolean();
        byte options = buffer.readByte();

        return new CommandParamData.Builder(parameterName, type, optional, options);
    }
}