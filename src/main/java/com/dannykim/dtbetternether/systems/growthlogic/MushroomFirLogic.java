package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

/** A tiny 3-7 block fungal spire with large lower and small upper side shelves. */
public final class MushroomFirLogic extends GrowthLogicKit {
    public MushroomFirLogic(final Identifier name) { super(name); }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());
        if (spread == 0 && y < 7) {
            map[Direction.UP.ordinal()] = 22;
            if (y >= 2) {
                final int side = y < 5 ? 7 : 3;
                for (final Direction d : Direction.Plane.HORIZONTAL) map[d.ordinal()] = side;
            }
        } else if (ctx.signal().dir.getAxis().isHorizontal()) {
            map[ctx.signal().dir.ordinal()] = spread < (y < 5 ? 2 : 1) ? 9 : 0;
        }
        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
