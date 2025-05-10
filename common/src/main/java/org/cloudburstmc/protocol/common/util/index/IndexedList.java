package org.cloudburstmc.protocol.common.util.index;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class IndexedList<T> implements Indexable<List<T>> {

    private final List<T> values;
    private final int[] indices;
    private List<T> cached;

    public IndexedList(List<T> values, int[] indices) {
        this.values = Objects.requireNonNull(values, "values cannot be null");
        this.indices = Objects.requireNonNull(indices, "indices cannot be null");
    }

    @Override
    public List<T> get() {
        if (cached != null) {
            return cached;
        }
        if (indices.length == 0) {
            return Collections.emptyList();
        }
        List<T> indexedValues = new ObjectArrayList<>(indices.length);
        for (int index : indices) {
            indexedValues.add(values.get(index));
        }
        return cached = indexedValues;
    }
}
