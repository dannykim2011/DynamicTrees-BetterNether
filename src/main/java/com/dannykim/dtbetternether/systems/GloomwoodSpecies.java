package com.dannykim.dtbetternether.systems;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.worldgen.GenerationContext;
import com.ferreusveritas.dynamictrees.worldgen.DynamicTreeFeature;
import net.minecraft.resources.ResourceLocation;

public final class GloomwoodSpecies extends Species {
    public static final TypedRegistry.EntryType<Species> TYPE =
            Species.createDefaultType(GloomwoodSpecies::new);

    public GloomwoodSpecies(final ResourceLocation name,
                                  final Family family,
                                  final LeavesProperties leavesProperties) {
        super(name, family, leavesProperties);
    }

    @Override
    public boolean generate(final GenerationContext context) {
        // The three JoCode pools contain trees with 3-5, 7-10 and 11-16 blocks of wood.
        int clearance = 0;
        while (clearance < 18 && DynamicTreeFeature.validTreePos(context.level(), context.rootPos().above(clearance + 1))) {
            clearance++;
        }
        int radius = context.radius();
        if (clearance < 12) {
            radius = Math.min(radius, 3);
        } else if (clearance < 18 || (radius >= 6 && context.random().nextInt(3) == 0)) {
            radius = Math.min(radius, 5);
        }
        if (radius == context.radius()) return super.generate(context);
        final var scaled = new GenerationContext(context.levelContext(), this, context.originPos(),
                context.rootPos(), context.biome(), context.facing(), radius, context.safeBounds());
        return super.generate(scaled);
    }
}
