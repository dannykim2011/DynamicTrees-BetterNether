package com.dannykim.dtbetternether.systems.growthlogic;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.PositionalSpeciesContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public final class GloomwoodLogic extends GrowthLogicKit {
    public GloomwoodLogic(final ResourceLocation name) {
        super(name);
    }

    // BetterNether's ForkingTrunk chooses a height in one of three ranges and forks halfway up.
    private static int targetHeight(final BlockPos root) {
        final int seed = Long.hashCode(root.asLong() * 0x9E3779B97F4A7C15L);
        return switch (Math.floorMod(seed, 3)) {
            case 0 -> 3 + Math.floorMod(seed >>> 3, 3);
            case 1 -> 7 + Math.floorMod(seed >>> 3, 4);
            default -> 11 + Math.floorMod(seed >>> 3, 6);
        };
    }

    @Override
    public float getEnergy(final GrowthLogicKitConfiguration cfg, final PositionalSpeciesContext ctx) {
        // Allow a few horizontal steps on each ascending arm.
        return targetHeight(ctx.pos()) + 5;
    }

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                 final DirectionManipulationContext ctx) {
        final var signal = ctx.signal();
        final int height = targetHeight(signal.rootPos);
        final int y = signal.delta.getY();
        final int[] weights = super.populateDirectionProbabilityMap(cfg, ctx);
        weights[Direction.DOWN.ordinal()] = 0;
        if (y < height / 2) {
            for (Direction side : Direction.Plane.HORIZONTAL) weights[side.ordinal()] = 0;
        } else if (y >= height) {
            weights[Direction.UP.ordinal()] = 0;
        } else if (signal.delta.getX() == 0 && signal.delta.getZ() == 0) {
            for (Direction side : Direction.Plane.HORIZONTAL) weights[side.ordinal()] *= 4;
        } else {
            weights[Direction.UP.ordinal()] *= 3;
        }
        return weights;
    }
}
