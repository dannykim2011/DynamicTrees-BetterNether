package com.dannykim.dtbetternether.systems.genfeature;

import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.ferreusveritas.dynamictrees.systems.genfeature.VinesGenFeature;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * BetterNether's willow branch is a decorative hanging block, not a vanilla
 * growing-plant head, so its tip must be placed without an age property.
 */
public final class WillowVinesGenFeature extends VinesGenFeature {
    public WillowVinesGenFeature(final ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void addVerticalVines(
            final GenFeatureConfiguration configuration,
            final LevelAccessor level,
            final Species species,
            final BlockPos rootPos,
            final BlockPos branchPos,
            final SafeChunkBounds safeBounds,
            final boolean worldGen
    ) {
        final BlockPos vinePos = CoordUtils.getRayTraceFruitPos(level, species, rootPos, branchPos, safeBounds);
        if (vinePos == BlockPos.ZERO || !safeBounds.inBounds(vinePos, true)) {
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
