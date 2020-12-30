package org.cloudburstmc.protocol.java.data.entity;

import lombok.Data;

// TODO: More descriptive and understandable values: https://wiki.vg/Protocol#Statistics
@Data
public class Statistic {
    private int categoryId;
    private int statisticId;
    private int value;
}
