package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongObjectPair;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v340.serializer.AvailableCommandsSerializer_v340;
import org.cloudburstmc.protocol.bedrock.data.command.*;
import org.cloudburstmc.protocol.bedrock.packet.AvailableCommandsPacket;
import org.cloudburstmc.protocol.common.util.SequencedHashSet;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AvailableCommandsSerializer_v388 extends AvailableCommandsSerializer_v340 {

    private static final CommandEnumConstraint[] CONSTRAINTS = CommandEnumConstraint.values();

    public AvailableCommandsSerializer_v388(TypeMap<CommandParam> paramTypeMap) {
        super(paramTypeMap);
    }

    protected static long key(int enumIndex, int valueIndex) {
        return (((long) enumIndex) << 32) | (valueIndex & 0xffffffffL);
    }

    protected static int getEnumIndex(long key) {
        return (int) (key >> 32);
    }

    protected static int getValueIndex(long key) {
        return (int) key;
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AvailableCommandsPacket packet) {
        SequencedHashSet<String> enumValues = new SequencedHashSet<>();
        SequencedHashSet<String> postFixes = new SequencedHashSet<>();
        SequencedHashSet<CommandEnumData> enums = new SequencedHashSet<>();
        SequencedHashSet<CommandEnumData> softEnums = new SequencedHashSet<>();
        SequencedHashSet<LongObjectPair<Set<CommandEnumConstraint>>> enumConstraints = new SequencedHashSet<>();

        // Get all enum values
        for (CommandData data : packet.getCommands()) {
            if (data.getAliases() != null) {
                enumValues.addAll(data.getAliases().getValues().keySet());
                enums.add(data.getAliases());
            }

            for (CommandParamData[] overload : data.getOverloads()) {
                for (CommandParamData parameter : overload) {
                    CommandEnumData commandEnumData = parameter.getEnumData();
                    if (commandEnumData != null) {
                        if (commandEnumData.isSoft()) {
                            softEnums.add(commandEnumData);
                        } else {
                            enums.add(commandEnumData);
                            int enumIndex = enums.indexOf(commandEnumData);
                            commandEnumData.getValues().forEach((key, constraints) -> {
                                enumValues.add(key);
                                if (!constraints.isEmpty()) {
                                    int valueIndex = enumValues.indexOf(key);
                                    enumConstraints.add(LongObjectPair.of(key(enumIndex, valueIndex), constraints));
                                }
                            });
                        }
                    }

                    String postfix = parameter.getPostfix();
                    if (postfix != null) {
                        postFixes.add(postfix);
                    }
                }
            }
        }

        helper.writeArray(buffer, enumValues, helper::writeString);
        helper.writeArray(buffer, postFixes, helper::writeString);

        this.writeEnums(buffer, helper, enumValues, enums);

        helper.writeArray(buffer, packet.getCommands(), (buf, command) -> {
            this.writeCommand(buffer, helper, command, enums, softEnums, postFixes);
        });

        helper.writeArray(buffer, softEnums, helper::writeCommandEnum);

        helper.writeArray(buffer, enumConstraints, this::writeEnumConstraint);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AvailableCommandsPacket packet) {
        SequencedHashSet<String> enumValues = new SequencedHashSet<>();
        SequencedHashSet<String> postFixes = new SequencedHashSet<>();
        SequencedHashSet<CommandEnumData> enums = new SequencedHashSet<>();
        SequencedHashSet<CommandEnumData> softEnums = new SequencedHashSet<>();
        Set<Runnable> delayedTasks = new HashSet<>();

        helper.readArray(buffer, enumValues, helper::readString);
        helper.readArray(buffer, postFixes, helper::readString);

        this.readEnums(buffer, helper, enumValues, enums);

        helper.readArray(buffer, packet.getCommands(), (buf, aHelper) ->
                this.readCommand(buf, aHelper, enums, softEnums, postFixes, delayedTasks));

        helper.readArray(buffer, softEnums, buf -> helper.readCommandEnum(buffer, true));

        this.readConstraints(buffer, helper, enums, enumValues);

        delayedTasks.forEach(Runnable::run);
    }

    protected void writeEnumConstraint(ByteBuf buffer, BedrockCodecHelper helper, LongObjectPair<Set<CommandEnumConstraint>> pair) {
        buffer.writeIntLE(getEnumIndex(pair.keyLong()));
        buffer.writeIntLE(getValueIndex(pair.keyLong()));
        helper.writeArray(buffer, pair.value(), (buf, constraint) -> buf.writeByte(constraint.ordinal()));
    }

    protected void readConstraints(ByteBuf buffer, BedrockCodecHelper helper, List<CommandEnumData> enums,
                                   List<String> enumValues) {
        int count = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < count; i++) {
            CommandEnumData enumData = enums.get(buffer.readIntLE());
            String key = enumValues.get(buffer.readIntLE());
            Set<CommandEnumConstraint> constraints = enumData.getValues().get(key);
            helper.readArray(buffer, constraints, buf -> CONSTRAINTS[buf.readUnsignedByte()]);
        }
    }
}