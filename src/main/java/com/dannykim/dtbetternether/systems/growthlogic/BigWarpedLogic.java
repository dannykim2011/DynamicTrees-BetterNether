package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

/**
 * BetterNether's old warped woods use five floor template structures.  The
 * largest template is about 15x20x15 and the smaller ones sit around 7-13
 * blocks tall, with a heavy warped-wart canopy, a thick straight stem, warped
 * hyphae side arms, shroomlights, and sparse black vines.  This logic keeps a
 * strong central leader until the canopy height, then opens into broad,
 * slightly uneven shelves instead of making a vanilla-like blob.
 */
public final class BigWarpedLogic extends GrowthLogicKit {
    public BigWarpedLogic(final Identifier name) {
        super(name);
    }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());

        if (spread <= 1 && y < 11) {
            map[Direction.UP.ordinal()] = 34;
            if (y > 4 && y % 4 == 0) {
                for (final Direction d : Direction.Plane.HORIZONTAL) {
                    map[d.ordinal()] = 3;
                }
            }
        } else if (spread <= 2 && y < 18) {
            map[Direction.UP.ordinal()] = 24;
            if (y >= 8) {
                for (final Direction d : Direction.Plane.HORIZONTAL) {
                    map[d.ordinal()] = y % 3 == 0 ? 9 : 5;
                }
            }
        } else if (y >= 10 && y < 22 && spread < 9) {
            map[Direction.UP.ordinal()] = spread < 4 ? 8 : 2;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = spread < 7 ? 11 : 4;
            }
            if (spread > 5) {
                map[Direction.DOWN.ordinal()] = 2;
            }
        } else if (ctx.signal().dir.getAxis().isHorizontal()) {
            map[ctx.signal().dir.ordinal()] = spread < 11 ? 8 : 0;
            map[Direction.UP.ordinal()] = spread < 8 ? 2 : 0;
            map[Direction.DOWN.ordinal()] = y > 14 && spread > 6 ? 5 : 0;
        }

        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
