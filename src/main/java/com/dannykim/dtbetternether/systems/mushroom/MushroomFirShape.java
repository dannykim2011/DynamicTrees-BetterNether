package com.dannykim.dtbetternether.systems.mushroom;

import net.minecraft.resources.ResourceLocation;

/**
 * Stacked shelf profile: broad lower shelves and progressively smaller upper
 * shelves reproduce the original 3-7 block Mushroom Fir.
 */
final class MushroomFirShape extends ProfiledMushroomShape {
    MushroomFirShape(final ResourceLocation name) { super(name); }
    @Override protected int maximumAge() { return 4; }
    @Override protected int depth(final int radius, final int age) {
        return Math.max(0, age - radius) * 2;
    }
    @Override protected float chanceToAge() { return 0.9F; }
}
