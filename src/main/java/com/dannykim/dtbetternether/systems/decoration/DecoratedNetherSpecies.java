package com.dannykim.dtbetternether.systems.decoration;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.NetherFungusSpecies;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class DecoratedNetherSpecies extends NetherFungusSpecies {
    public static final TypedRegistry.EntryType<Species> TYPE =
            Species.createDefaultType(DecoratedNetherSpecies::new);

    public DecoratedNetherSpecies(final ResourceLocation name,
                                  final Family family,
                                  final LeavesProperties leavesProperties) {
        super(name, family, leavesProperties);
    }

    public static boolean isFellingDecoration(final BlockState state) {
        final ResourceLocation key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if ("minecraft".equals(key.getNamespace())) {
            return switch (key.getPath()) {
                case "shroomlight", "weeping_vines", "weeping_vines_plant",
                     "twisting_vines", "twisting_vines_plant" -> true;
                default -> false;
            };
        }
        return "betternether".equals(key.getNamespace()) && switch (key.getPath()) {
            case "wall_moss", "wall_mushroom_red" -> true;
            default -> false;
        };
    }
}
