package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.command.CommandParamData;
import com.nukkitx.protocol.bedrock.data.command.CommandParamOption;
import com.nukkitx.protocol.bedrock.data.command.CommandSymbolData;
import com.nukkitx.protocol.bedrock.v291.serializer.AvailableCommandsSerializer_v291;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableCommandsSerializer_v340 extends AvailableCommandsSerializer_v291 {
    public static final AvailableCommandsSerializer_v340 INSTANCE = new AvailableCommandsSerializer_v340();

    @Override
    protected void writeParameter(ByteBuf buffer, BedrockPacketHelper helper, CommandParamData param, List<CommandEnumData> enums, List<CommandEnumData> softEnums, List<String> postFixes) {
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
    protected CommandParamData.Builder readParameter(ByteBuf buffer, BedrockPacketHelper helper) {
        String parameterName = helper.readString(buffer);
        CommandSymbolData type = CommandSymbolData.deserialize(buffer.readIntLE());
        boolean optional = buffer.readBoolean();
        byte options = buffer.readByte();

        return new CommandParamData.Builder(parameterName, type, optional, options);
    }
}