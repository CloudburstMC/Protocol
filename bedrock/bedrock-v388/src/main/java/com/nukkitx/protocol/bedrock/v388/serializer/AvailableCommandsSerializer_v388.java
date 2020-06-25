package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.command.*;
import com.nukkitx.protocol.bedrock.packet.AvailableCommandsPacket;
import com.nukkitx.protocol.bedrock.v340.serializer.AvailableCommandsSerializer_v340;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableCommandsSerializer_v388 extends AvailableCommandsSerializer_v340 {
    public static final AvailableCommandsSerializer_v388 INSTANCE = new AvailableCommandsSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AvailableCommandsPacket packet) {
        super.serialize(buffer, helper, packet);

        // Constraints - @TODO
        //helper.writeArray(buffer, packet.getConstraints(), (buf, data) -> helper.writeCommandEnumConstraints(buf, enums, enumValues));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AvailableCommandsPacket packet) {
        List<String> enumValues = new ObjectArrayList<>();
        List<String> postFixes = new ObjectArrayList<>();
        List<CommandEnumData> enums = new ObjectArrayList<>();
        List<CommandData.Builder> commands = new ObjectArrayList<>();
        List<CommandEnumData> softEnums = new ObjectArrayList<>();

        helper.readArray(buffer, enumValues, helper::readString);
        helper.readArray(buffer, postFixes, helper::readString);

        this.readEnums(buffer, helper, enumValues, enums);

        helper.readArray(buffer, commands, this::readCommand);

        helper.readArray(buffer, softEnums, buf -> helper.readCommandEnum(buffer, true));


        // Generate command data
        for (CommandData.Builder command : commands) {
            byte flags = command.getFlags();
            List<CommandData.Flag> flagList = new ObjectArrayList<>();
            for (int i = 0; i < 6; i++) {
                if ((flags & (1 << i)) != 0) {
                    flagList.add(FLAGS[i]);
                }
            }
            int aliasesIndex = command.getAliases();
            CommandEnumData aliases = aliasesIndex == -1 ? null : enums.get(aliasesIndex);

            CommandParamData.Builder[][] overloadBuilders = command.getOverloads();
            CommandParamData[][] overloads = new CommandParamData[overloadBuilders.length][];
            for (int i = 0; i < overloadBuilders.length; i++) {
                overloads[i] = new CommandParamData[overloadBuilders[i].length];
                for (int i2 = 0; i2 < overloadBuilders[i].length; i2++) {
                    CommandParamData.Builder param = overloadBuilders[i][i2];
                    String name = param.getName();
                    CommandSymbolData type = param.getType();
                    boolean optional = param.isOptional();
                    byte optionsByte = param.getOptions();

                    String postfix = null;
                    CommandEnumData enumData = null;
                    CommandParamType paramType = null;
                    if (type.isPostfix()) {
                        postfix = postFixes.get(type.getValue());
                    } else {
                        if (type.isCommandEnum()) {
                            enumData = enums.get(type.getValue());
                        } else if (type.isSoftEnum()) {
                            enumData = softEnums.get(type.getValue());
                        } else {
                            paramType = helper.getCommandParam(type.getValue());
                        }
                    }

                    List<CommandParamOption> options = new ObjectArrayList<>();
                    for (int idx = 0; idx < 8; idx++) {
                        if ((optionsByte & (1 << idx)) != 0) {
                            options.add(OPTIONS[idx]);
                        }
                    }

                    overloads[i][i2] = new CommandParamData(name, optional, enumData, paramType, postfix, options);
                }
            }

            packet.getCommands().add(new CommandData(command.getName(), command.getDescription(),
                    flagList, command.getPermission(), aliases, overloads));
        }

        // Constraints - @TODO
        //helper.readArray(buffer, packet.getConstraints(), buf -> helper.readCommandEnumConstraints(buf, enums, enumValues));
    }
}