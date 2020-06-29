package com.nukkitx.protocol.bedrock.v363;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlags;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.structure.StructureMirror;
import com.nukkitx.protocol.bedrock.data.structure.StructureRotation;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.v354.BedrockPacketHelper_v354;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v363 extends BedrockPacketHelper_v354 {

}
