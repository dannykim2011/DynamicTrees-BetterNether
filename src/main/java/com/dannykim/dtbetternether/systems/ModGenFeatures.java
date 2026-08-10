package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dannykim.dtbetternether.systems.genfeature.CrimsonGlowingWallDecorationsGenFeature;
import com.dannykim.dtbetternether.systems.genfeature.ExposedShroomlightGenFeature;
import com.dannykim.dtbetternether.systems.genfeature.WillowVinesGenFeature;
import com.dtteam.dynamictrees.api.registry.Registry;
import com.dtteam.dynamictrees.systems.genfeature.GenFeature;

public final class ModGenFeatures {
    public static final GenFeature WILLOW_VINES =
            new WillowVinesGenFeature(DynamicTreesBetterNether.location("willow_vines"));
    public static final GenFeature EXPOSED_SHROOMLIGHT =
            new ExposedShroomlightGenFeature(DynamicTreesBetterNether.location("exposed_shroomlight"));
    public static final GenFeature CRIMSON_GLOWING_WALL_DECORATIONS =
            new CrimsonGlowingWallDecorationsGenFeature(DynamicTreesBetterNether.location("crimson_glowing_wall_decorations"));

    private ModGenFeatures() {
    }

    public static void register(final Registry<GenFeature> registry) {
        registry.register(WILLOW_VINES);
        registry.register(EXPOSED_SHROOMLIGHT);
        registry.register(CRIMSON_GLOWING_WALL_DECORATIONS);
    }
}
