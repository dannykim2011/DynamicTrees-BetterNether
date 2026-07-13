package com.dannykim.dtbetternether;

import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DynamicTreesBetterNether.MOD_ID)
public final class DynamicTreesBetterNether {
    public static final String MOD_ID = "dtbetternether";

    public DynamicTreesBetterNether() {
        RegistryHandler.setup(MOD_ID);
        if (ModList.get().isLoaded("dynamictreesplus")) {
            FMLJavaModLoadingContext.get().getModEventBus().register(
                    com.dannykim.dtbetternether.systems.mushroom.DTPlusRegistries.class
            );
        }
    }

    public static ResourceLocation location(final String path) {
        return ResourceLocation.tryBuild(MOD_ID, path);
    }
}
