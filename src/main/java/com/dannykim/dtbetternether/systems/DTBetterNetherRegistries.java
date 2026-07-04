package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dannykim.dtbetternether.data.EnglishLanguageProvider;
import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = DynamicTreesBetterNether.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class DTBetterNetherRegistries {
    private DTBetterNetherRegistries() {}

    @SubscribeEvent
    public static void registerGrowthLogic(final RegistryEvent<GrowthLogicKit> event) {
        ModGrowthLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerGenFeatures(final RegistryEvent<GenFeature> event) {
        ModGenFeatures.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerFeatureCancellers(final RegistryEvent<FeatureCanceller> event) {
        ModFeatureCancellers.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeClient(),
                new EnglishLanguageProvider(event.getGenerator().getPackOutput())
        );
    }
}
