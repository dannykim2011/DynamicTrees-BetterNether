package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dtteam.dynamictrees.event.RegistryEvent;
import com.dtteam.dynamictrees.api.worldgen.FeatureCanceller;
import com.dtteam.dynamictrees.systems.genfeature.GenFeature;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.block.soil.SoilProperties;
import com.dtteam.dynamictrees.data.GatherDataHelper;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictrees.treepack.Resources;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
public final class DTBetterNetherRegistries {
    private DTBetterNetherRegistries() {}

    @SubscribeEvent
    public static void registerGrowthLogic(final RegistryEvent<GrowthLogicKit> event) {
        if (!event.isEntryOfType(GrowthLogicKit.class)) return;
        ModGrowthLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerGenFeatures(final RegistryEvent<GenFeature> event) {
        if (!event.isEntryOfType(GenFeature.class)) return;
        ModGenFeatures.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerFeatureCancellers(final RegistryEvent<FeatureCanceller> event) {
        if (!event.isEntryOfType(FeatureCanceller.class)) return;
        ModFeatureCancellers.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        Resources.MANAGER.gatherData();
        if (event instanceof GatherDataEvent.Client clientEvent) {
            GatherDataHelper.gatherClientData(DynamicTreesBetterNether.MOD_ID, clientEvent,
                    SoilProperties.REGISTRY, Family.REGISTRY, Species.REGISTRY,
                    LeavesProperties.REGISTRY);
        } else if (event instanceof GatherDataEvent.Server serverEvent) {
            GatherDataHelper.gatherServerData(DynamicTreesBetterNether.MOD_ID, serverEvent,
                    SoilProperties.REGISTRY, Family.REGISTRY, Species.REGISTRY,
                    LeavesProperties.REGISTRY);
        }
    }
}
