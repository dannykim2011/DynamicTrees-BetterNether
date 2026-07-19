package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

public final class MushroomFirLogic extends GrowthLogicKit {
    public MushroomFirLogic(final Identifier name) {
        super(name);
    }

    @Override
    public int[] populateDirectionProbabilityMap(
            final GrowthLogicKitConfiguration configuration,
            final DirectionManipulationContext context
    ) {
        final int[] map = new int[6];
        final int y = context.signal().delta.getY();
        final int spread = Math.abs(context.signal().delta.getX()) + Math.abs(context.signal().delta.getZ());
        if (spread == 0 && y < 7) {
            map[Direction.UP.ordinal()] = 22;
            if (y >= 2) {
                final int side = y < 5 ? 7 : 3;
                for (final Direction direction : Direction.Plane.HORIZONTAL) {
                    map[direction.ordinal()] = side;
                }
            }
        } else if (context.signal().dir.getAxis().isHorizontal()) {
            map[context.signal().dir.ordinal()] = spread < (y < 5 ? 2 : 1) ? 9 : 0;
        }
        map[context.signal().dir.getOpposite().ordinal()] = 0;
        return map;
    }
}
