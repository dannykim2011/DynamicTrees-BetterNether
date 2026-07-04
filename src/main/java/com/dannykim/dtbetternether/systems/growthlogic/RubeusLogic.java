package com.dannykim.dtbetternether.systems.growthlogic;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/** Three to seven curved radial stems, each carrying its own compact crown. */
public final class RubeusLogic extends GrowthLogicKit {
    public RubeusLogic(final ResourceLocation name) { super(name); }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());
        if (y < 3 && spread < 2) {
            map[Direction.UP.ordinal()] = 4;
            for (final Direction d : Direction.Plane.HORIZONTAL) map[d.ordinal()] = 10;
        } else if (ctx.signal().dir.getAxis().isHorizontal() && spread < 11) {
            map[ctx.signal().dir.ordinal()] = 13;
            map[Direction.UP.ordinal()] = y < 13 ? 8 : 1;
        } else {
            map[Direction.UP.ordinal()] = y < 15 ? 5 : 0;
        }
        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
