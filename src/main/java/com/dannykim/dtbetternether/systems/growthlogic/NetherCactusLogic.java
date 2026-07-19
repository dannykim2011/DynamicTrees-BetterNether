package com.dannykim.dtbetternether.systems.growthlogic;

import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKitConfiguration;
import com.dtteam.dynamictrees.systems.growthlogic.context.DirectionManipulationContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

public final class NetherCactusLogic extends GrowthLogicKit {
    public NetherCactusLogic(final Identifier name) {
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

        if (spread == 0 && y < 8) {
            map[Direction.UP.ordinal()] = 38;
            if (y > 4 && y % 4 == 0) {
                for (final Direction direction : Direction.Plane.HORIZONTAL) {
                    map[direction.ordinal()] = 1;
                }
            }
        } else if (context.signal().dir.getAxis().isHorizontal()) {
            map[context.signal().dir.ordinal()] = spread < 1 && y > 5 ? 4 : 0;
            map[Direction.UP.ordinal()] = y < 11 ? 18 : 0;
        } else {
            map[Direction.UP.ordinal()] = y < 11 ? 24 : 0;
        }

        if (context.signal().dir != Direction.DOWN) {
            map[context.signal().dir.getOpposite().ordinal()] = 0;
        }
        return map;
    }
}
