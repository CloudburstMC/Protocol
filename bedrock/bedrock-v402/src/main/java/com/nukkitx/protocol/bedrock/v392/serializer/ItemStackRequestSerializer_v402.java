package com.nukkitx.protocol.bedrock.v392.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ItemData;
import com.nukkitx.protocol.bedrock.data.ItemStackAction;
import com.nukkitx.protocol.bedrock.packet.ItemStackRequestPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemStackRequestSerializer_v402 implements PacketSerializer<ItemStackRequestPacket> {

    public static final ItemStackRequestSerializer_v402 INSTANCE = new ItemStackRequestSerializer_v402();

    @Override
    public void serialize(ByteBuf buffer, ItemStackRequestPacket packet) {
        BedrockUtils.writeArray(buffer, packet.getRequests(), (buf, requests) -> {
            VarInts.writeInt(buf, requests.getRequestId());

            BedrockUtils.writeArray(buf, requests.getActions(), (byteBuf, action) -> {
                switch (action.getType()) {
                    case 0:
                    case 1:
                    case 2:
                        byteBuf.writeByte(action.getBaseByte0());
                        byteBuf.writeByte(action.getBaseByte1());
                        byteBuf.writeByte(action.getBaseByte2());
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        byteBuf.writeByte(action.getFlagsByte0());
                        byteBuf.writeByte(action.getFlagsByte1());
                        VarInts.writeInt(byteBuf, action.getFlagsVarInt0());
                        break;
                    case 3:
                        byteBuf.writeByte(action.getBaseByte0());
                        byteBuf.writeByte(action.getBaseByte1());
                        byteBuf.writeByte(action.getBaseByte2());
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        byteBuf.writeBoolean(action.isBool0());
                        break;
                    case 4:
                    case 5:
                        byteBuf.writeByte(action.getBaseByte0());
                        byteBuf.writeByte(action.getBaseByte1());
                        byteBuf.writeByte(action.getBaseByte2());
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        break;
                    case 6:
                        byteBuf.writeByte(action.getByte0());
                        break;
                    case 8:
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        VarInts.writeInt(byteBuf, action.getVarInt1());
                        break;
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                        VarInts.writeInt(byteBuf, action.getVarInt0());
                        break;
                    case 17:
                        BedrockUtils.writeArray(byteBuf, action.getItems(), BedrockUtils::writeItemData);
                        break;
                }
            });
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, ItemStackRequestPacket packet) {
        BedrockUtils.readArray(buffer, packet.getRequests(), buf -> {
            int unknownVarInt0 = VarInts.readInt(buf);
            List<ItemStackAction> actions = new ArrayList<>();

            BedrockUtils.readArray(buf, actions, byteBuf -> {
                byte type = byteBuf.readByte();

                boolean bool0 = false;
                byte byte0 = 0;
                int varInt0 = 0;
                int varInt1 = 0;
                byte baseByte0 = 0;
                byte baseByte1 = 0;
                byte baseByte2 = 0;
                int baseVarInt0 = 0;
                byte flagsByte0 = 0;
                byte flagsByte1 = 0;
                int flagsVarInt0 = 0;
                List<ItemData> items = null;

                switch (type) {
                    case 0:
                    case 1:
                    case 2:
                        baseByte0 = byteBuf.readByte();
                        baseByte1 = byteBuf.readByte();
                        baseByte2 = byteBuf.readByte();
                        baseVarInt0 = VarInts.readInt(byteBuf);
                        flagsByte0 = byteBuf.readByte();
                        flagsByte1 = byteBuf.readByte();
                        flagsVarInt0 = VarInts.readInt(byteBuf);
                        break;
                    case 3:
                        baseByte0 = byteBuf.readByte();
                        baseByte1 = byteBuf.readByte();
                        baseByte2 = byteBuf.readByte();
                        baseVarInt0 = VarInts.readInt(byteBuf);
                        bool0 = byteBuf.readBoolean();
                        break;
                    case 4:
                    case 5:
                        baseByte0 = byteBuf.readByte();
                        baseByte1 = byteBuf.readByte();
                        baseByte2 = byteBuf.readByte();
                        baseVarInt0 = VarInts.readInt(byteBuf);
                        break;
                    case 6:
                        byte0 = byteBuf.readByte();
                        break;
                    case 8:
                        varInt0 = VarInts.readInt(byteBuf);
                        varInt1 = VarInts.readInt(byteBuf);
                        break;
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                        varInt0 = VarInts.readUnsignedInt(byteBuf);
                        break;
                    case 17:
                        items = new ArrayList<>();
                        BedrockUtils.readArray(byteBuf, items, BedrockUtils::readItemData);
                        break;
                }
                return new ItemStackAction(type, bool0, byte0, varInt0, varInt1, baseByte0, baseByte1, baseByte2,
                        baseVarInt0, flagsByte0, flagsByte1, flagsVarInt0, items);
            });
            return new ItemStackRequestPacket.Request(unknownVarInt0, actions);
        });
    }
}
