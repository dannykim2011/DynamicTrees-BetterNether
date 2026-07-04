package com.dannykim.dtbetternether.systems.mushroom;

import net.minecraft.resources.ResourceLocation;

/** Very broad, shallow brown shelves, matching the 13-21 block template widths. */
final class NetherBrownMushroomShape extends ProfiledMushroomShape {
    NetherBrownMushroomShape(final ResourceLocation name) { super(name); }
    @Override protected int maximumAge() { return 8; }
    @Override protected int depth(final int radius, final int age) {
        return radius == age ? 2 : (radius >= age - 2 ? 1 : 0);
    }
    @Override protected float chanceToAge() { return 0.85F; }
}
