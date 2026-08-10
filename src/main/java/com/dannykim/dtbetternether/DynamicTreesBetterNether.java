package com.dannykim.dtbetternether;

import com.dannykim.dtbetternether.systems.DTBetterNetherRegistries;
import com.dtteam.dynamictrees.registry.NeoForgeRegistryHandler;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(DynamicTreesBetterNether.MOD_ID)
public final class DynamicTreesBetterNether {
    public static final String MOD_ID = "dtbetternether";

    public DynamicTreesBetterNether(final IEventBus modEventBus, final ModContainer modContainer) {
        modEventBus.register(DTBetterNetherRegistries.class);
        if (ModList.get().isLoaded("dynamictreesplus")) {
            modEventBus.register(com.dannykim.dtbetternether.systems.mushroom.DTPlusRegistries.class);
        }
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            modEventBus.addListener(com.dannykim.dtbetternether.client.FilledTextureSource::register);
        }
        NeoForgeRegistryHandler.setup(MOD_ID, modEventBus);
    }

    public static Identifier location(final String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

}
