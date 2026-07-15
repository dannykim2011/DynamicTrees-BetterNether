package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dannykim.dtbetternether.worldgen.FeatureTypeCanceller;
import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import java.util.Set;

public final class ModFeatureCancellers {
    public static final FeatureCanceller BETTERNETHER_TREES = new FeatureTypeCanceller(
            DynamicTreesBetterNether.location("betternether_trees"),
            Set.of(
                    ResourceLocation.tryBuild("betternether", "anchor_tree"),
                    ResourceLocation.tryBuild("betternether", "anchor_tree_branch"),
                    ResourceLocation.tryBuild("betternether", "anchor_tree_root"),
                    ResourceLocation.tryBuild("betternether", "old_willow_tree"),
                    ResourceLocation.tryBuild("betternether", "rubeus_tree"),
                    ResourceLocation.tryBuild("betternether", "rubeus_bush"),
                    ResourceLocation.tryBuild("betternether", "sakura_tree"),
                    ResourceLocation.tryBuild("betternether", "sakura_bush"),
                    ResourceLocation.tryBuild("betternether", "wart_tree"),
                    ResourceLocation.tryBuild("betternether", "wart_bush"),
                    ResourceLocation.tryBuild("betternether", "willow_tree"),
                    ResourceLocation.tryBuild("betternether", "willow_bush")
            )
    );
    public static final FeatureCanceller BETTERNETHER_FUNGI = new FeatureTypeCanceller(
            DynamicTreesBetterNether.location("betternether_fungi"),
            Set.of(
                    ResourceLocation.tryBuild("betternether", "mushroom_fir"),
                    ResourceLocation.tryBuild("betternether", "big_red_mushroom"),
                    ResourceLocation.tryBuild("betternether", "big_brown_mushroom"),
                    ResourceLocation.tryBuild("bclib", "template"),
                    ResourceLocation.tryBuild("minecraft", "block_column")
            )
    );
    public static final FeatureCanceller BETTERNETHER_TEMPLATE_TREES = new FeatureTypeCanceller(
            DynamicTreesBetterNether.location("betternether_template_trees"),
            Set.of(
                    ResourceLocation.tryBuild("bclib", "template")
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
