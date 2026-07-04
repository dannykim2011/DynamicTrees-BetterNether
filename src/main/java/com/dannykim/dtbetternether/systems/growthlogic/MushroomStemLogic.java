package com.dannykim.dtbetternether.systems.growthlogic;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public final class MushroomStemLogic extends GrowthLogicKit {
    private final int up;
    private final int side;

    public MushroomStemLogic(final ResourceLocation name, final int up, final int side) {
        super(name);
        this.up = up;
        this.side = side;
    }

    @Override
    public int[] populateDirectionProbabilityMap(
            final GrowthLogicKitConfiguration configuration,
            final DirectionManipulationContext context
    ) {
        final int[] map = new int[6];
        map[Direction.UP.ordinal()] = up;
        if (context.signal().delta.getY() > 1) {
            for (final Direction direction : Direction.Plane.HORIZONTAL) {
                map[direction.ordinal()] = side;
            }
        }
        map[context.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
