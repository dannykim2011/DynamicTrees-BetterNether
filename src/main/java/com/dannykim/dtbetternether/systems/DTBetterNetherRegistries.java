package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dtteam.dynamictrees.event.RegistryEvent;
import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import com.dannykim.dtbetternether.systems.decoration.DecoratedNetherFamily;
import com.dannykim.dtbetternether.systems.decoration.DecoratedNetherSpecies;
import com.dtteam.dynamictrees.api.worldgen.FeatureCanceller;
import com.dtteam.dynamictrees.systems.genfeature.GenFeature;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.species.Species;
import net.neoforged.bus.api.SubscribeEvent;
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
    public static void registerFamilyTypes(final TypeRegistryEvent<Family> event) {
        if (!event.isEntryOfType(Family.class)) return;
        event.registerType(DynamicTreesBetterNether.location("decorated_nether_fungus"), DecoratedNetherFamily.TYPE);
    }

    @SubscribeEvent
    public static void registerSpeciesTypes(final TypeRegistryEvent<Species> event) {
        if (!event.isEntryOfType(Species.class)) return;
        event.registerType(DynamicTreesBetterNether.location("decorated_nether_fungus"), DecoratedNetherSpecies.TYPE);
    }

}
