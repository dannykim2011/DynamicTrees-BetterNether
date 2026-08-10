package com.dannykim.dtbetternether.systems.growthlogic;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

/**
 * BetterNether template-derived growth for crimson glow trees.
 *
 * The original crimson_glow_tree_01..04 templates are not capped fungi. They
 * are short crimson stem/hyphae skeletons with shroomlights as the leaf mass:
 * wood is concentrated at y=0..8 and already leans outward from the bottom.
 * Shroomlights begin at y=5..7, peak at y=7..10, and taper to a small knot by
 * y=13. The shape is therefore a low diagonal crimson skeleton with glowing
 * shroomlight tips, not a broad wart canopy.
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

        if (y < 3 && spread <= 2) {
            map[Direction.UP.ordinal()] = spread == 0 ? 30 : 20;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = spread == 0 ? 8 : radialRibBias(d, x, z, 9);
            }
            if (horizontal && spread < 2) {
                map[ctx.signal().dir.ordinal()] += 8;
            }
        } else if (y < 7 && spread <= 4) {
            map[Direction.UP.ordinal()] = spread <= 1 ? 18 : 8;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = radialRibBias(d, x, z, spread <= 2 ? 15 : 6);
            }
            if (horizontal) {
                map[ctx.signal().dir.ordinal()] += spread < 4 ? 18 : 4;
            }
        } else if (y < 10 && spread <= 4) {
            map[Direction.UP.ordinal()] = spread <= 1 ? 7 : 0;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = spread <= 2 ? radialRibBias(d, x, z, 18) : radialRibBias(d, x, z, 5);
            }
            if (horizontal && spread < 4) {
                map[ctx.signal().dir.ordinal()] += 14;
            }
        } else if (y < 14 && spread <= 3) {
            map[Direction.UP.ordinal()] = spread == 0 ? 4 : 0;
            for (final Direction d : Direction.Plane.HORIZONTAL) {
                map[d.ordinal()] = spread <= 2 ? radialRibBias(d, x, z, 4) : 0;
            }
            if (horizontal && spread < 3) {
                map[ctx.signal().dir.ordinal()] += 4;
            }
        }

        map[ctx.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }

    private static int radialRibBias(final Direction direction, final int x, final int z, final int base) {
        if (direction == Direction.EAST && x > 0) return base + 3;
        if (direction == Direction.WEST && x < 0) return base + 3;
        if (direction == Direction.SOUTH && z > 0) return base + 3;
        if (direction == Direction.NORTH && z < 0) return base + 3;
        if (x == 0 && z == 0) return base;
        return Math.max(1, base - 4);
    }
}
