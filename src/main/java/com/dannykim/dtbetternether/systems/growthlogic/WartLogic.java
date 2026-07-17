package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

/** Thick 5-9 block column ending in the original blocky cross-shaped wart head. */
public final class WartLogic extends GrowthLogicKit {
    public WartLogic(final Identifier name) { super(name); }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());
        if (y < 6 && spread == 0) {
            map[Direction.UP.ordinal()] = 24;
        } else if (y < 9 && spread < 2) {
            map[Direction.UP.ordinal()] = 8;
            for (final Direction d : Direction.Plane.HORIZONTAL) map[d.ordinal()] = 7;
        } else if (ctx.signal().dir.getAxis().isHorizontal()) {
            map[ctx.signal().dir.ordinal()] = spread < 4 ? 10 : 0;
            map[Direction.UP.ordinal()] = spread < 3 ? 3 : 0;
        }
        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
