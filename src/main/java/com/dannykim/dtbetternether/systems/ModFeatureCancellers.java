package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dannykim.dtbetternether.worldgen.FeatureTypeCanceller;
import com.dtteam.dynamictrees.api.registry.Registry;
import com.dtteam.dynamictrees.api.worldgen.FeatureCanceller;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.ModList;

import java.util.Set;

public final class ModFeatureCancellers {
    public static final FeatureCanceller BETTERNETHER_TREES = new FeatureTypeCanceller(
            DynamicTreesBetterNether.location("betternether_trees"),
            Set.of(
                    Identifier.fromNamespaceAndPath("betternether", "anchor_tree"),
                    Identifier.fromNamespaceAndPath("betternether", "anchor_tree_branch"),
                    Identifier.fromNamespaceAndPath("betternether", "anchor_tree_root"),
                    Identifier.fromNamespaceAndPath("betternether", "old_willow_tree"),
                    Identifier.fromNamespaceAndPath("betternether", "rubeus_tree"),
                    Identifier.fromNamespaceAndPath("betternether", "rubeus_bush"),
                    Identifier.fromNamespaceAndPath("betternether", "sakura_tree"),
                    Identifier.fromNamespaceAndPath("betternether", "sakura_bush"),
                    Identifier.fromNamespaceAndPath("betternether", "wart_tree"),
                    Identifier.fromNamespaceAndPath("betternether", "wart_bush"),
                    Identifier.fromNamespaceAndPath("betternether", "willow_tree"),
                    Identifier.fromNamespaceAndPath("betternether", "willow_bush")
            )
    );
    public static final FeatureCanceller BETTERNETHER_FUNGI = new FeatureTypeCanceller(
            DynamicTreesBetterNether.location("betternether_fungi"),
            Set.of(
                    Identifier.fromNamespaceAndPath("betternether", "mushroom_fir"),
                    Identifier.fromNamespaceAndPath("betternether", "big_red_mushroom"),
                    Identifier.fromNamespaceAndPath("betternether", "big_brown_mushroom"),
                    Identifier.fromNamespaceAndPath("wover", "template"),
                    Identifier.fromNamespaceAndPath("minecraft", "block_column")
            )
    );
    public static final FeatureCanceller BETTERNETHER_TEMPLATE_TREES = new FeatureTypeCanceller(
            DynamicTreesBetterNether.location("betternether_template_trees"),
            Set.of(
                    Identifier.fromNamespaceAndPath("wover", "template")
            )
    );
    private ModFeatureCancellers() {
    }

    public static void register(final Registry<FeatureCanceller> registry) {
        registry.register(BETTERNETHER_TREES);
        registry.register(BETTERNETHER_TEMPLATE_TREES);
        if (ModList.get().isLoaded("dynamictreesplus")) {
            registry.register(BETTERNETHER_FUNGI);
        }
    }
}
