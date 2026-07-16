package com.dannykim.dtbetternether;

import com.dannykim.dtbetternether.systems.DTBetterNetherRegistries;
import com.dtteam.dynamictrees.registry.NeoForgeRegistryHandler;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(DynamicTreesBetterNether.MOD_ID)
public final class DynamicTreesBetterNether {
    public static final String MOD_ID = "dtbetternether";

    public DynamicTreesBetterNether(final IEventBus modEventBus, final ModContainer modContainer) {
        modEventBus.register(DTBetterNetherRegistries.class);
        NeoForgeRegistryHandler.setup(MOD_ID, modEventBus);
    }

    public static Identifier location(final String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
