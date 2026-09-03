package com.dannykim.dtbetternether.systems.mushroom;

import com.dtteam.dynamictrees.api.registry.TypedRegistry;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictreesplus.block.mushroom.CapProperties;
import com.dtteam.dynamictreesplus.tree.HugeMushroomSpecies;
import net.minecraft.resources.ResourceLocation;

public final class ProtectedMushroomSpecies extends HugeMushroomSpecies {
    public static final TypedRegistry.EntryType<Species> TYPE =
            HugeMushroomSpecies.createDefaultMushroomType(ProtectedMushroomSpecies::new);
    public ProtectedMushroomSpecies(final ResourceLocation name, final Family family, final CapProperties capProperties) {
        super(name, family, capProperties);
    }
}
