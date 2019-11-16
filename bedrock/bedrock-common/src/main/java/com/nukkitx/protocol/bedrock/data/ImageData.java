package com.nukkitx.protocol.bedrock.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(exclude = {"image"})
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ImageData {
    public static final ImageData EMPTY = new ImageData(0, 0, new byte[0]);

    private static final int PIXEL_SIZE = 4;

    private static final int SINGLE_SKIN_SIZE = 64 * 32 * PIXEL_SIZE;
    private static final int DOUBLE_SKIN_SIZE = 64 * 64 * PIXEL_SIZE;
    private static final int SKIN_128_64_SIZE = 128 * 64 * PIXEL_SIZE;
    private static final int SKIN_128_128_SIZE = 128 * 128 * PIXEL_SIZE;

    private final long width;
    private final long height;
    private final byte[] image;

    public static ImageData of(long width, long height, byte[] image) {
        Objects.requireNonNull(image, "image");
        return new ImageData(width, height, image);
    }

    public static ImageData of(byte[] image) {
        Objects.requireNonNull(image, "image");
        switch (image.length) {
            case 0:
                return EMPTY;
            case SINGLE_SKIN_SIZE:
                return new ImageData(64, 32, image);
            case DOUBLE_SKIN_SIZE:
                return new ImageData(64, 64, image);
            case SKIN_128_64_SIZE:
                return new ImageData(128, 64, image);
            case SKIN_128_128_SIZE:
                return new ImageData(128, 128, image);
            default:
                throw new IllegalArgumentException("Invalid legacy skin");
        }
    }

    public void checkLegacySkinSize() {
        switch (image.length) {
            case SINGLE_SKIN_SIZE:
            case DOUBLE_SKIN_SIZE:
            case SKIN_128_64_SIZE:
            case SKIN_128_128_SIZE:
                return;
            default:
                throw new IllegalArgumentException("Invalid legacy skin");
        }
    }

    public void checkLegacyCapeSize() {
        if (image.length != 0 && image.length != SINGLE_SKIN_SIZE) {
            throw new IllegalArgumentException("Invalid legacy cape");
        }
    }
}
