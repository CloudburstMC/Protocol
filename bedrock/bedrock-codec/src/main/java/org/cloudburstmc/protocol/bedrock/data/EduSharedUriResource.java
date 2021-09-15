package org.cloudburstmc.protocol.bedrock.data;

import lombok.Value;

@Value
public class EduSharedUriResource {
    public static final EduSharedUriResource EMPTY = new EduSharedUriResource("", "");

    String buttonName;
    String linkUri;
}
