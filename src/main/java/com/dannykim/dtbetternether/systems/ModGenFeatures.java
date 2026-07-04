package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dannykim.dtbetternether.systems.genfeature.WillowVinesGenFeature;
import com.dtteam.dynamictrees.api.registry.Registry;
import com.dtteam.dynamictrees.systems.genfeature.GenFeature;

public final class ModGenFeatures {
    public static final GenFeature WILLOW_VINES =
            new WillowVinesGenFeature(DynamicTreesBetterNether.location("willow_vines"));

    private ModGenFeatures() {
    }

    public static void register(final Registry<GenFeature> registry) {
        registry.register(WILLOW_VINES);
    }
}
