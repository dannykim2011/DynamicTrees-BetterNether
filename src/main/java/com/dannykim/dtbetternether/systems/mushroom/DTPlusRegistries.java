package com.dannykim.dtbetternether.systems.mushroom;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dtteam.dynamictrees.event.RegistryEvent;
import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictreesplus.block.mushroom.CapProperties;
import com.dtteam.dynamictreesplus.systems.mushroomlogic.shapekits.MushroomShapeKit;
import net.neoforged.bus.api.SubscribeEvent;

/** This class is loaded only when Dynamic Trees Plus is present. */
public final class DTPlusRegistries {
    private static final MushroomShapeKit RED =
            new NetherRedMushroomShape(DynamicTreesBetterNether.location("nether_red_mushroom"));
    private static final MushroomShapeKit BROWN =
            new NetherBrownMushroomShape(DynamicTreesBetterNether.location("nether_brown_mushroom"));

    private DTPlusRegistries() {}

    @SubscribeEvent
    public static void register(final RegistryEvent<MushroomShapeKit> event) {
        if (!event.isEntryOfType(MushroomShapeKit.class)) return;
        event.getRegistry().registerAll(RED, BROWN);
    }

    @SubscribeEvent
    public static void registerFamilyTypes(final TypeRegistryEvent<Family> event) {
        if (event.isEntryOfType(Family.class)) {
            event.registerType(DynamicTreesBetterNether.location("protected_mushroom"), ProtectedMushroomFamily.TYPE);
        }
    }

    @SubscribeEvent
    public static void registerCapTypes(final TypeRegistryEvent<CapProperties> event) {
        if (event.isEntryOfType(CapProperties.class)) {
            event.registerType(DynamicTreesBetterNether.location("stable_cap"), StableCapProperties.TYPE);
        }
    }
}
