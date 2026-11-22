package com.dannykim.dtbetternether.systems.growthlogic;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public final class CrimsonGlowingLogic extends GrowthLogicKit {
    public CrimsonGlowingLogic(final ResourceLocation name) {
        super(name);
    }

    // Paths start at the soil. Each step follows the stem or supports a shroomlight cluster.
    // The four rows correspond to BetterNether crimson_glow_tree_01 through _04.
    private static final String[][] FORMS = {
            {"UUUSUUUWUUUU", "UUUSUUUWUUSEU"},
            {"UUUNUUEUUU"},
            {"UUUNUUEUEUEUUUU", "UUUNUUWSUUSUU"},
            {"UUUUUUU"}
    };

    @Override
    public int[] populateDirectionProbabilityMap(final GrowthLogicKitConfiguration cfg,
                                                 final DirectionManipulationContext ctx) {
        final int[] weights = new int[6];
        final var signal = ctx.signal();
        final int seed = Long.hashCode(signal.rootPos.asLong() * 0x9E3779B97F4A7C15L);
        final String[] paths = FORMS[seed & 3];
        final int rotation = (seed >>> 2) & 3;
        // Shift the first bend and vary the tip height without moving the fork on either arm.
        final int stemOffset = ((seed >>> 4) & 3) - 1;
        final int crownOffset = ((seed >>> 6) & 3) - 1;
        for (final String path : paths) {
            int x = 0, y = 0, z = 0;
            for (int i = 0; i < path.length() + stemOffset + crownOffset; i++) {
                final int index = i - stemOffset;
                final char axis = index < 0 || index >= path.length() ? 'U' : path.charAt(index);
                Direction step = switch (axis) {
                    case 'N' -> Direction.NORTH;
                    case 'S' -> Direction.SOUTH;
                    case 'E' -> Direction.EAST;
                    case 'W' -> Direction.WEST;
                    default -> Direction.UP;
                };
                if (step != Direction.UP) {
                    if ((seed & 256) != 0 && step.getAxis() == Direction.Axis.X) step = step.getOpposite();
                    for (int turn = 0; turn < rotation; turn++) step = step.getClockWise();
                }
                if (x == signal.delta.getX() && y == signal.delta.getY() && z == signal.delta.getZ()) {
                    weights[step.ordinal()]++;
                    break;
                }
                x += step.getStepX();
                y += step.getStepY();
                z += step.getStepZ();
            }
        }
        // An exhausted signal cannot continue past the terminal crown on a later growth pulse.
        if (java.util.Arrays.stream(weights).sum() == 0) {
            signal.energy = 0;
            weights[Direction.UP.ordinal()] = 1;
        }
        return weights;
    }
}
