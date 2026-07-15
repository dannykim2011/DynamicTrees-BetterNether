package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/**
 * Template-derived growth for BetterNether's crimson glow trees.
 *
 * The original crimson_glow_tree_01..04 structures are small static templates:
 * roughly 5-11 blocks wide, 10-14 blocks tall, with 48-141 total structure
 * blocks.  Shroomlights are not a broad wart canopy; they are about one
 * quarter of the template and appear mostly from y=5 to y=13, clustered around
 * the ends of short crimson stem/hyphae ribs.  The trunk material usually ends
 * by y=8, while the glow mass tapers upward after y=10.
 *
 * This kit therefore grows a compact upright core first, then sends short
 * radial ribs through the glow band and deliberately terminates them instead
 * of continuing into a pine shelf or warped-fungus cap.
 */
public final class CrimsonGlowingLogic extends GrowthLogicKit {
    public CrimsonGlowingLogic(final ResourceLocation name) {
        super(name);
    }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                  final DirectionManipulationContext ctx) {
        final int[] map = new int[6];
        final int y = ctx.signal().delta.getY();
        final int x = ctx.signal().delta.getX();
        final int z = ctx.signal().delta.getZ();
        final int spread = Math.abs(x) + Math.abs(z);
        final boolean horizontal = ctx.signal().dir.getAxis().isHorizontal();

        if (y < 4 && spread <= 1) {
            // y=0..4 in the templates is almost entirely crimson stem/hyphae,
            // with only small side irregularities.  Keep a narrow, slightly
            // offset leader.
            map[Direction.UP.ordinal()] = 34;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = y >= 2 ? 4 : 1;
            }
        } else if (y < 6 && spread <= 3) {
            // The glow band starts around y=5-7.  Start four-way radial ribs
            // but keep enough upward pressure to reach the 10-14 block height.
            map[Direction.UP.ordinal()] = spread == 0 ? 18 : 7;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = outwardBias(d, x, z, 17);
            }
            if (horizontal) {
                map[ctx.signal().dir.ordinal()] += 8;
            }
        } else if (y < 9 && spread <= 4) {
            // Main shroomlight mass.  The original has dense glow/moss around
            // short branch ends, so horizontal continuation is favored only
            // until spread 3-4 and upward growth is sharply reduced away from
            // the core.
            map[Direction.UP.ordinal()] = spread <= 1 ? 6 : 0;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = outwardBias(d, x, z, spread <= 2 ? 19 : 7);
            }
            if (horizontal) {
                map[ctx.signal().dir.ordinal()] += spread < 4 ? 12 : 0;
            }
        } else if (y < 11 && spread <= 3) {
            // Tapering top knot.  This keeps the last shroomlights above the
            // ribs but prevents a large mushroom cap.
            map[Direction.UP.ordinal()] = spread <= 1 ? 3 : 0;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = spread <= 2 ? outwardBias(d, x, z, 7) : 0;
            }
        } else if (horizontal && y < 10 && spread <= 4) {
            // Existing ribs are allowed to end cleanly.  A near-zero map here
            // is intentional: leaves are shroomlights, so branch termination is
            // what makes the glow appear on tips rather than as a sheet.
            map[ctx.signal().dir.ordinal()] = spread < 4 ? 6 : 0;
            map[Direction.UP.ordinal()] = spread < 2 ? 1 : 0;
        }

        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }

    private static int outwardBias(final Direction direction, final int x, final int z, final int base) {
        if (direction == Direction.EAST && x > 0) return base + 3;
        if (direction == Direction.WEST && x < 0) return base + 3;
        if (direction == Direction.SOUTH && z > 0) return base + 3;
        if (direction == Direction.NORTH && z < 0) return base + 3;
        if (x == 0 && z == 0) return base;
        return Math.max(1, base - 4);
    }
}
