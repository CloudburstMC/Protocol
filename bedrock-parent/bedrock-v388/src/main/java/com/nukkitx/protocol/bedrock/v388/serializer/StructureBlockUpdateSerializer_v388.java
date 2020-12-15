package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.structure.StructureBlockType;
import com.nukkitx.protocol.bedrock.data.structure.StructureEditorData;
import com.nukkitx.protocol.bedrock.data.structure.StructureRedstoneSaveMode;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.v361.serializer.StructureBlockUpdateSerializer_v361;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StructureBlockUpdateSerializer_v388 extends StructureBlockUpdateSerializer_v361 {
    public static final StructureBlockUpdateSerializer_v388 INSTANCE = new StructureBlockUpdateSerializer_v388();

    @Override
    protected StructureEditorData readEditorData(ByteBuf buffer, BedrockPacketHelper helper) {
        String name = helper.readString(buffer);
        String dataField = helper.readString(buffer);
        boolean includingPlayers = buffer.readBoolean();
        boolean boundingBoxVisible = buffer.readBoolean();
        StructureBlockType type = StructureBlockType.from(VarInts.readInt(buffer));
        StructureSettings settings = helper.readStructureSettings(buffer);
        StructureRedstoneSaveMode redstoneSaveMode = StructureRedstoneSaveMode.from(VarInts.readInt(buffer));
        return new StructureEditorData(name, dataField, includingPlayers, boundingBoxVisible, type, settings,
                redstoneSaveMode);
    }

    @Override
    protected void writeEditorData(ByteBuf buffer, BedrockPacketHelper helper, StructureEditorData data) {
        super.writeEditorData(buffer, helper, data);

        VarInts.writeInt(buffer, data.getRedstoneSaveMode().ordinal());
    }
}
