package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/** Large 3-7 stem ancient willow with broad spherical terminal crowns. */
public final class OldWillowLogic extends GrowthLogicKit {
    public OldWillowLogic(final ResourceLocation name) { super(name); }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());
        if (y < 3 && spread < 2) {
            map[Direction.UP.ordinal()] = 4;
            for (final Direction d : Direction.Plane.HORIZONTAL) map[d.ordinal()] = 11;
        } else if (ctx.signal().dir.getAxis().isHorizontal() && spread < 15) {
            map[ctx.signal().dir.ordinal()] = 14;
            map[Direction.UP.ordinal()] = y < 16 ? 8 : 2;
            if (spread > 8) map[Direction.DOWN.ordinal()] = 4;
        } else if (ctx.signal().dir == Direction.DOWN && y > 2) {
            map[Direction.DOWN.ordinal()] = 10;
        }
        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
