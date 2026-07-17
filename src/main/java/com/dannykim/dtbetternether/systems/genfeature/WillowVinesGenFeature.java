package com.dannykim.dtbetternether.systems.genfeature;

import com.dtteam.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.dtteam.dynamictrees.systems.genfeature.VinesGenFeature;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictrees.utility.CoordUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Places BetterNether willow branches as hanging vines without applying the
 * {@code age} property required by vanilla growing-plant tip blocks.
 */
public final class WillowVinesGenFeature extends VinesGenFeature {
    public WillowVinesGenFeature(final Identifier registryName) {
        super(registryName);
    }

    @Override
    protected void addVerticalVines(
            final GenFeatureConfiguration configuration,
            final LevelAccessor level,
            final Species species,
            final BlockPos rootPos,
            final BlockPos branchPos,
            final boolean worldGen
    ) {
        final BlockPos vinePos = CoordUtils.getRayTraceFruitPos(level, species, rootPos, branchPos, worldGen);
        if (vinePos == BlockPos.ZERO) {
            return;
        }

        final BlockState bodyState = configuration.get(BLOCK).defaultBlockState();
        final BlockState tipState = configuration.getAsOptional(TIP_BLOCK)
                .map(block -> block.defaultBlockState())
                .orElse(bodyState);

        this.placeVines(
                level,
                vinePos,
                bodyState,
                configuration.get(MAX_LENGTH),
                tipState,
                configuration.get(VINE_TYPE),
                worldGen
        );
    }
}
