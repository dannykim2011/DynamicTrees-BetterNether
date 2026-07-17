package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

/**
 * BetterNether crimson pine has five template variants.  The large variants
 * are broad 12-17 block crowns with a 13-18 block height, while small variants
 * keep the same conifer-like stacked silhouette.  Most canopy blocks are
 * nether wart, with crimson stem/hyphae ribs, shroomlights, and weeping vines.
 * This logic keeps a tall leader and emits repeated narrowing horizontal
 * shelves, so the result reads as a crimson pine instead of a generic fungus.
 */
public final class CrimsonPineLogic extends GrowthLogicKit {
    public CrimsonPineLogic(final Identifier name) {
        super(name);
    }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int spread = Math.abs(ctx.signal().delta.getX()) + Math.abs(ctx.signal().delta.getZ());

        if (spread <= 1 && y < 18) {
            map[Direction.UP.ordinal()] = 33;
            if (y > 4 && y % 3 == 0) {
                final int shelf = Math.max(3, 12 - y / 3);
                for (final Direction d : Direction.Plane.HORIZONTAL) {
                    map[d.ordinal()] = shelf;
                }
            }
        } else if (spread <= 2 && y < 22) {
            map[Direction.UP.ordinal()] = 18;
            if (y > 7 && y % 3 != 1) {
                final int shelf = Math.max(2, 10 - y / 4);
                for (final Direction d : Direction.Plane.HORIZONTAL) {
                    map[d.ordinal()] = shelf;
                }
            }
        } else if (ctx.signal().dir.getAxis().isHorizontal()) {
            final int maxSpread = Math.max(3, 10 - y / 4);
            map[ctx.signal().dir.ordinal()] = spread < maxSpread ? 13 : 0;
            map[Direction.UP.ordinal()] = spread < 4 ? 3 : 0;
            map[Direction.DOWN.ordinal()] = y > 8 && spread > 2 ? 3 : 0;
        } else {
            map[Direction.UP.ordinal()] = y < 22 ? 8 : 0;
        }

        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
