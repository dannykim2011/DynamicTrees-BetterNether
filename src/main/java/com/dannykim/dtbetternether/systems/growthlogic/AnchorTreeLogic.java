package com.dannykim.dtbetternether.systems.growthlogic;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/**
 * Massive Nether-height trunk with repeated 3-7 arm rings. Horizontal arms
 * arch outward, then send hanging anchors down from their terminal crowns.
 */
public final class AnchorTreeLogic extends GrowthLogicKit {
    public AnchorTreeLogic(final ResourceLocation name) { super(name); }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());
        if (spread < 2 && y < 64) {
            map[Direction.UP.ordinal()] = 26;
            if (y >= 12 && y % 10 <= 1) {
                for (final Direction d : Direction.Plane.HORIZONTAL) map[d.ordinal()] = 8;
            }
        } else if (ctx.signal().dir.getAxis().isHorizontal() && spread < 18) {
            map[ctx.signal().dir.ordinal()] = 16;
            map[Direction.UP.ordinal()] = spread < 10 ? 5 : 1;
            if (spread > 10) map[Direction.DOWN.ordinal()] = 5;
        } else if (ctx.signal().dir == Direction.DOWN && y > 3) {
            map[Direction.DOWN.ordinal()] = 14;
            if (y % 6 == 0) {
                for (final Direction d : Direction.Plane.HORIZONTAL) map[d.ordinal()] = 2;
            }
        }
        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
