package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/** Short 5-7 block bole, four flat arms and hanging terminal twigs. */
public final class WillowLogic extends GrowthLogicKit {
    public WillowLogic(final ResourceLocation name) { super(name); }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());
        if (y < 5 && spread == 0) {
            map[Direction.UP.ordinal()] = 24;
        } else if (spread < 2) {
            for (final Direction d : Direction.Plane.HORIZONTAL) map[d.ordinal()] = 12;
            map[Direction.UP.ordinal()] = 2;
        } else if (ctx.signal().dir.getAxis().isHorizontal() && spread < 6) {
            map[ctx.signal().dir.ordinal()] = 14;
            map[ctx.signal().dir.getClockWise().ordinal()] = 3;
            map[ctx.signal().dir.getCounterClockWise().ordinal()] = 3;
            if (spread >= 3) map[Direction.DOWN.ordinal()] = 6;
        } else if (ctx.signal().dir == Direction.DOWN && y > 1) {
            map[Direction.DOWN.ordinal()] = 10;
        }
        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
