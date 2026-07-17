package com.dannykim.dtbetternether.client;

import com.mojang.blaze3d.platform.NativeImage;

final class FilledTextureProcessor {
    private FilledTextureProcessor() {
    }

    static NativeImage fillTransparentPixels(final NativeImage source) {
        final int width = source.getWidth();
        final int height = source.getHeight();
        final NativeImage result = new NativeImage(width, height, false);
        final int fallback = firstOpaque(source);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int pixel = source.getPixelRGBA(x, y);
                if (alpha(pixel) > 0) {
                    result.setPixelRGBA(x, y, opaque(pixel));
                } else {
                    result.setPixelRGBA(x, y, nearestFilledPixel(source, x, y, fallback));
                }
            }
        }

        return result;
    }

    private static int nearestFilledPixel(final NativeImage source, final int centerX, final int centerY, final int fallback) {
        final int width = source.getWidth();
        final int height = source.getHeight();
        final int maxRadius = Math.max(width, height);

        for (int radius = 1; radius <= maxRadius; radius++) {
            int best = 0;
            int bestScore = Integer.MIN_VALUE;

            for (int y = Math.max(0, centerY - radius); y <= Math.min(height - 1, centerY + radius); y++) {
                for (int x = Math.max(0, centerX - radius); x <= Math.min(width - 1, centerX + radius); x++) {
                    if (Math.abs(x - centerX) != radius && Math.abs(y - centerY) != radius) {
                        continue;
                    }
                    final int pixel = source.getPixelRGBA(x, y);
                    if (alpha(pixel) <= 0) {
                        continue;
                    }
                    final int score = brownScore(pixel);
                    if (score > bestScore) {
                        best = pixel;
                        bestScore = score;
                    }
                }
            }

            if (bestScore != Integer.MIN_VALUE) {
                return opaque(best);
            }
        }

        return fallback;
    }

    private static int firstOpaque(final NativeImage source) {
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                final int pixel = source.getPixelRGBA(x, y);
                if (alpha(pixel) > 0) {
                    return opaque(pixel);
                }
            }
        }
        return 0xFFFFFFFF;
    }

    private static int brownScore(final int pixel) {
        final int red = pixel & 0xFF;
        final int green = pixel >> 8 & 0xFF;
        final int blue = pixel >> 16 & 0xFF;
        return red * 3 + green * 2 - blue * 3;
    }

    private static int alpha(final int pixel) {
        return pixel >>> 24 & 0xFF;
    }

    private static int opaque(final int pixel) {
        return pixel | 0xFF000000;
    }
}
