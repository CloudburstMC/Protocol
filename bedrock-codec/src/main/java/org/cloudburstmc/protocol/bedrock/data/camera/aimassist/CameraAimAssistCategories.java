package org.cloudburstmc.protocol.bedrock.data.camera.aimassist;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
public class CameraAimAssistCategories {
    private String identifier;
    private final List<Category> categories = new ObjectArrayList<>();

    @Data
    public static class Category {
        private String name;
        private Priorities priorities = Priorities.EMPTY;

        @Data
        public static class Priorities {
            public static final Priorities EMPTY = new Priorities();

            private final List<Entity> entities = new ObjectArrayList<>();
            private final List<Block> blocks = new ObjectArrayList<>();
            private Integer entityDefault;
            private Integer blockDefault;

            @Value
            public static class Entity {
                String identifier;
                int entityDefault;
            }

            @Value
            public static class Block {
                String identifier;
                int blockDefault;
            }
        }
    }
}