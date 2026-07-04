package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dannykim.dtbetternether.worldgen.FeatureTypeCanceller;
import com.dtteam.dynamictrees.api.registry.Registry;
import com.dtteam.dynamictrees.api.worldgen.FeatureCanceller;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public final class ModFeatureCancellers {
    public static final FeatureCanceller BETTERNETHER_TREES = new FeatureTypeCanceller(
            DynamicTreesBetterNether.location("betternether_trees"),
            Set.of(
                    ResourceLocation.fromNamespaceAndPath("betternether", "anchor_tree"),
                    ResourceLocation.fromNamespaceAndPath("betternether", "old_willow"),
                    ResourceLocation.fromNamespaceAndPath("betternether", "rubeus_tree"),
                    ResourceLocation.fromNamespaceAndPath("betternether", "sakura_tree"),
                    ResourceLocation.fromNamespaceAndPath("betternether", "wart_tree"),
                    ResourceLocation.fromNamespaceAndPath("betternether", "willow_tree")
            )
    );

    public static final FeatureCanceller BETTERNETHER_FUNGI = new FeatureTypeCanceller(
            DynamicTreesBetterNether.location("betternether_fungi"),
            Set.of(
                    ResourceLocation.fromNamespaceAndPath("betternether", "big_brown_mushroom"),
                    ResourceLocation.fromNamespaceAndPath("betternether", "mushroom_fir"),
                    ResourceLocation.fromNamespaceAndPath("minecraft", "block_column"),
                    ResourceLocation.fromNamespaceAndPath("wover", "template")
            )
    );

    private ModFeatureCancellers() {
    }

    public static void register(final Registry<FeatureCanceller> registry) {
        registry.registerAll(BETTERNETHER_TREES, BETTERNETHER_FUNGI);
    }
}
