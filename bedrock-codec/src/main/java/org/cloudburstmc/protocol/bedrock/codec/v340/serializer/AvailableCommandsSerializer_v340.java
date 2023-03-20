package org.cloudburstmc.protocol.bedrock.codec.v340.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.AvailableCommandsSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamOption;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AvailableCommandsSerializer_v340 extends AvailableCommandsSerializer_v291 {

    public AvailableCommandsSerializer_v340(TypeMap<CommandParam> paramTypeMap) {
        super(paramTypeMap);
    }

    @Override
    protected void writeParameter(ByteBuf buffer, BedrockCodecHelper helper, CommandParamData param,
                                  List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes) {
        super.writeParameter(buffer, helper, param, enums, softEnums, postFixes);

        byte options = 0;
        for (CommandParamOption option : param.getOptions()) {
            options |= 1 << option.ordinal();
        }
        buffer.writeByte(options);
    }

    @Override
    protected CommandParamData readParameter(ByteBuf buffer, BedrockCodecHelper helper, List<CommandEnumData> enums,
                                             List<String> postfixes, Set<Consumer<List<CommandEnumData>>> softEnumParameters) {
        CommandParamData param = super.readParameter(buffer, helper, enums, postfixes, softEnumParameters);

        Set<CommandParamOption> options = param.getOptions();
        int optionsBits = buffer.readUnsignedByte();

        for (CommandParamOption option : OPTIONS) {
            if ((optionsBits & 1 << option.ordinal()) != 0) {
                options.add(option);
            }
        }
        return param;
    }
}
