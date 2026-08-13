package com.dannykim.dtbetternether.systems.mushroom;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictreesplus.block.mushroom.CapProperties;
import com.ferreusveritas.dynamictreesplus.systems.mushroomlogic.shapekits.MushroomShapeKit;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/** This class is loaded only when Dynamic Trees Plus is present. */
public final class DTPlusRegistries {
    private static final MushroomShapeKit RED =
            new NetherRedMushroomShape(DynamicTreesBetterNether.location("nether_red_mushroom"));
    private static final MushroomShapeKit BROWN =
            new NetherBrownMushroomShape(DynamicTreesBetterNether.location("nether_brown_mushroom"));

    private DTPlusRegistries() {}

    @SubscribeEvent
    public static void register(final RegistryEvent<MushroomShapeKit> event) {
        event.getRegistry().registerAll(RED, BROWN);
    }

    @SubscribeEvent
    public static void registerFamilyTypes(final TypeRegistryEvent<Family> event) {
        event.registerType(DynamicTreesBetterNether.location("protected_mushroom"), ProtectedMushroomFamily.TYPE);
    }

    @SubscribeEvent
    public static void registerCapTypes(final TypeRegistryEvent<CapProperties> event) {
        event.registerType(DynamicTreesBetterNether.location("stable_cap"), StableCapProperties.TYPE);
    }
}
