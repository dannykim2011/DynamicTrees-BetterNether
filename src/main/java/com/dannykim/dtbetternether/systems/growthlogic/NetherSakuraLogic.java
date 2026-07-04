package com.dannykim.dtbetternether.systems.growthlogic;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/** Long narrow stem with a tapered, downward-looking conical crown. */
public final class NetherSakuraLogic extends GrowthLogicKit {
    public NetherSakuraLogic(final ResourceLocation name) { super(name); }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());
        if (y < 15 && spread == 0) {
            map[Direction.UP.ordinal()] = 24;
        } else if (spread < 2 && y < 24) {
            map[Direction.UP.ordinal()] = 10;
            for (final Direction d : Direction.Plane.HORIZONTAL) map[d.ordinal()] = 5;
        } else if (ctx.signal().dir.getAxis().isHorizontal()) {
            map[ctx.signal().dir.ordinal()] = spread < 5 ? 10 : 0;
            map[Direction.DOWN.ordinal()] = y > 10 ? 4 : 0;
        } else if (ctx.signal().dir == Direction.DOWN && y > 4) {
            map[Direction.DOWN.ordinal()] = 9;
        }
        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
