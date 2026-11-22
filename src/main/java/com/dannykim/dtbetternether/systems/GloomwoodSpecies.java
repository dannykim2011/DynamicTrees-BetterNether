package com.dannykim.dtbetternether.systems;

import com.dtteam.dynamictrees.api.registry.TypedRegistry;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictrees.worldgen.DynamicTreeGenerationContext;
import com.dtteam.dynamictrees.worldgen.feature.DynamicTreeFeature;
import net.minecraft.resources.Identifier;

public final class GloomwoodSpecies extends Species {
    public static final TypedRegistry.EntryType<Species> TYPE =
            Species.createDefaultType(GloomwoodSpecies::new);

    public GloomwoodSpecies(final Identifier name,
                                  final Family family,
                                  final LeavesProperties leavesProperties) {
        super(name, family, leavesProperties);
    }

    @Override
    public boolean generate(final DynamicTreeGenerationContext context) {
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
        final var scaled = new DynamicTreeGenerationContext(context.levelContext(), this, context.originPos(),
                context.rootPos(), context.biome(), context.facing(), radius, context.isWorldGen());
        return super.generate(scaled);
    }
}
