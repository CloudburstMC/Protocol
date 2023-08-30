package org.cloudburstmc.protocol.bedrock.codec.v575.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtMapBuilder;
import org.cloudburstmc.nbt.NbtType;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraPreset;
import org.cloudburstmc.protocol.bedrock.packet.CameraPresetsPacket;

import java.util.List;

public class CameraPresetsSerializer_v575 implements BedrockPacketSerializer<CameraPresetsPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CameraPresetsPacket packet) {
        List<NbtMap> presets = new ObjectArrayList<>();
        for (CameraPreset preset : packet.getPresets()) {
            NbtMapBuilder builder = NbtMap.builder()
                    .putString("identifier", preset.getIdentifier())
                    .putString("inherit_from", preset.getParentPreset());

            if (preset.getPos() != null) {
                builder.putFloat("pos_x", preset.getPos().getX());
                builder.putFloat("pos_y", preset.getPos().getY());
                builder.putFloat("pos_z", preset.getPos().getZ());
            }

            if (preset.getYaw() != null) {
                builder.putFloat("rot_y", preset.getYaw());
            }

            if (preset.getPitch() != null) {
                builder.putFloat("rot_x", preset.getPitch());
            }
            presets.add(builder.build());
        }

        helper.writeTag(buffer, NbtMap.builder()
                .putList("presets", NbtType.COMPOUND, presets)
                .build());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CameraPresetsPacket packet) {
        NbtMap tag = helper.readTag(buffer, NbtMap.class);

        List<NbtMap> list = tag.getList("presets", NbtType.COMPOUND);
        for (NbtMap presetTag : list) {
            CameraPreset preset = new CameraPreset();
            preset.setIdentifier(presetTag.getString("identifier"));
            preset.setParentPreset(presetTag.getString("inherit_from"));

            if (presetTag.containsKey("pos_x", NbtType.FLOAT) || presetTag.containsKey("pos_y", NbtType.FLOAT) || presetTag.containsKey("pos_z", NbtType.FLOAT)) {
                float x = presetTag.containsKey("pos_x", NbtType.FLOAT) ? presetTag.getFloat("pos_x") : 0;
                float y = presetTag.containsKey("pos_y", NbtType.FLOAT) ? presetTag.getFloat("pos_y") : 0;
                float z = presetTag.containsKey("pos_z", NbtType.FLOAT) ? presetTag.getFloat("pos_z") : 0;
                preset.setPos(Vector3f.from(x, y, z));
            }

            if (presetTag.containsKey("rot_y", NbtType.FLOAT)) {
                preset.setYaw(presetTag.getFloat("rot_y"));
            }

            if (presetTag.containsKey("rot_x", NbtType.FLOAT)) {
                preset.setPitch(presetTag.getFloat("rot_x"));
            }
            packet.getPresets().add(preset);
        }
    }
}
