package com.dannykim.dtbetternether.systems.mushroom;

import net.minecraft.resources.ResourceLocation;

/** Tall domed red caps, matching the 7-20 block original template set. */
final class NetherRedMushroomShape extends ProfiledMushroomShape {
    NetherRedMushroomShape(final ResourceLocation name) { super(name); }
    @Override protected int maximumAge() { return 7; }
    @Override protected int depth(final int radius, final int age) {
        final float ratio = radius / (float) Math.max(age, 1);
        return Math.round(ratio * ratio * 4.0F);
    }
    @Override protected float chanceToAge() { return 0.78F; }
}
