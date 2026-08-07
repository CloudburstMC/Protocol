package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v924.Bedrock_v924;
import org.cloudburstmc.protocol.bedrock.codec.v924.serializer.ClientboundDataStoreSerializer_v924;
import org.cloudburstmc.protocol.bedrock.data.datastore.DataStoreChange;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundDataStorePacket;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataStoreSerializationTest {

    private static final BedrockCodecHelper HELPER = Bedrock_v924.CODEC.createHelper();

    @Test
    public void testDynamicValueRoundTrip() {
        // A custom_form-shaped seed exercising every tag type at once.
        Map<String, Object> slot = new LinkedHashMap<>();
        slot.put("slider_visible", true);
        slot.put("label", "Slider");
        slot.put("value", 5.0d);
        slot.put("minValue", 0.0d);
        slot.put("maxValue", 10.0d);

        Map<String, Object> label = new LinkedHashMap<>();
        label.put("label_visible", true);
        label.put("text", Collections.singletonMap("rawtext",
                Arrays.asList(Collections.singletonMap("translate", "gui.ok"))));

        Map<String, Object> layout = new LinkedHashMap<>();
        layout.put("0", slot);
        layout.put("1", label);
        layout.put("length", 2L);

        Map<String, Object> tree = new LinkedHashMap<>();
        tree.put("title", "Round trip");
        tree.put("layout", layout);
        tree.put("empty", null);

        assertEquals(tree, roundTrip(tree));
    }

    @Test
    public void testNullDynamicValueRoundTrip() {
        assertNull(roundTrip(null));
    }

    @Test
    public void testScalarDynamicValueRoundTrip() {
        assertEquals(true, roundTrip(true));
        assertEquals(42L, roundTrip(42L));
        assertEquals(1.5d, roundTrip(1.5d));
        assertEquals("text", roundTrip("text"));
        assertEquals(Arrays.asList(1L, 2.5d, "three"), roundTrip(Arrays.asList(1L, 2.5d, "three")));
    }

    @Test
    public void testHostileListLengthDoesNotPreAllocate() {
        // A forged length prefix must not translate into an up-front allocation; the loop then
        // fails on buffer underflow instead of exhausting memory.
        ByteBuf buffer = Unpooled.buffer();
        VarInts.writeUnsignedInt(buffer, 1); // update count
        VarInts.writeUnsignedInt(buffer, 1); // action type: change
        HELPER.writeString(buffer, "minecraft");
        HELPER.writeString(buffer, "custom_form_data_1");
        buffer.writeIntLE(1); // property update count
        buffer.writeIntLE(5); // list tag
        VarInts.writeUnsignedInt(buffer, Integer.MAX_VALUE);
        try {
            ClientboundDataStorePacket decoded = new ClientboundDataStorePacket();
            assertThrows(Exception.class,
                    () -> ClientboundDataStoreSerializer_v924.INSTANCE.deserialize(buffer, HELPER, decoded));
        } finally {
            buffer.release();
        }
    }

    @Test
    public void testDynamicValueTypeTags() {
        // A symmetric round trip would not catch swapped tags, so pin the values on the wire.
        assertEquals(0, writtenTypeTag(null));
        assertEquals(1, writtenTypeTag(true));
        assertEquals(2, writtenTypeTag(42L));
        assertEquals(3, writtenTypeTag(1.5d));
        assertEquals(4, writtenTypeTag("text"));
        assertEquals(5, writtenTypeTag(Arrays.asList("a", "b")));
        assertEquals(6, writtenTypeTag(Collections.singletonMap("key", "value")));
    }

    private Object roundTrip(Object value) {
        ByteBuf buffer = serialize(value);
        try {
            ClientboundDataStorePacket decoded = new ClientboundDataStorePacket();
            ClientboundDataStoreSerializer_v924.INSTANCE.deserialize(buffer, HELPER, decoded);
            return ((DataStoreChange) decoded.getUpdates().get(0)).getNewValue();
        } finally {
            buffer.release();
        }
    }

    private int writtenTypeTag(Object value) {
        ByteBuf buffer = serialize(value);
        try {
            VarInts.readUnsignedInt(buffer); // update count
            VarInts.readUnsignedInt(buffer); // action type
            HELPER.readString(buffer); // data store name
            HELPER.readString(buffer); // property
            buffer.readIntLE(); // property update count
            return buffer.readIntLE();
        } finally {
            buffer.release();
        }
    }

    private ByteBuf serialize(Object value) {
        DataStoreChange change = new DataStoreChange();
        change.setDataStoreName("minecraft");
        change.setProperty("custom_form_data_1");
        change.setUpdateCount(1);
        change.setNewValue(value);

        ClientboundDataStorePacket packet = new ClientboundDataStorePacket();
        packet.getUpdates().add(change);

        ByteBuf buffer = Unpooled.buffer();
        ClientboundDataStoreSerializer_v924.INSTANCE.serialize(buffer, HELPER, packet);
        return buffer;
    }
}
