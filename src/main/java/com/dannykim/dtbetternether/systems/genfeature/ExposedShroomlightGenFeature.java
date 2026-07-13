package com.dannykim.dtbetternether.systems.genfeature;

import com.dtteam.dynamictrees.api.configuration.ConfigurationProperty;
import com.dtteam.dynamictrees.systems.genfeature.GenFeature;
import com.dtteam.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.dtteam.dynamictrees.systems.genfeature.context.PostGenerationContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * Replaces a few exterior wart-cap leaf blocks with shroomlights after JoCode
 * generation.  The normal Dynamic Trees shroomlight feature searches branch
 * pits, which can hide lights inside BetterNether's wide generated caps; this
 * keeps the light blocks visible without altering JoCodes or growth logic.
 */
public final class ExposedShroomlightGenFeature extends GenFeature {
    public static final ConfigurationProperty<Block> TARGET_LEAVES =
            ConfigurationProperty.block("target_leaves");
    public static final ConfigurationProperty<Block> SHROOMLIGHT_BLOCK =
            ConfigurationProperty.block("shroomlight");

    public ExposedShroomlightGenFeature(final ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(TARGET_LEAVES, SHROOMLIGHT_BLOCK, PLACE_CHANCE, MAX_COUNT, MAX_HEIGHT);
    }

    @Override
    protected GenFeatureConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(TARGET_LEAVES, Blocks.NETHER_WART_BLOCK)
                .with(SHROOMLIGHT_BLOCK, Blocks.SHROOMLIGHT)
                .with(PLACE_CHANCE, 0.85f)
                .with(MAX_COUNT, 4)
                .with(MAX_HEIGHT, 30);
    }

    @Override
    protected boolean postGenerate(final GenFeatureConfiguration configuration, final PostGenerationContext context) {
        if (!context.isWorldGen() && context.random().nextFloat() > configuration.get(PLACE_CHANCE)) {
            return false;
        }

        final List<BlockPos> candidates = this.findCandidates(configuration, context.level(), context.pos(), context.radius());
        if (candidates.isEmpty()) {
            return false;
        }

        final int sizeMax = Mth.clamp((context.radius() + 1) / 2, 1, configuration.get(MAX_COUNT));
        final int targetCount = 1 + context.random().nextInt(sizeMax);
        int placed = 0;

        while (!candidates.isEmpty() && placed < targetCount) {
            final BlockPos pos = candidates.remove(context.random().nextInt(candidates.size()));
            if (this.isCandidate(configuration, context.level(), pos, context.pos())) {
                context.level().setBlock(pos, configuration.get(SHROOMLIGHT_BLOCK).defaultBlockState(), 2);
                placed++;
            }
        }

        return placed > 0;
    }

    private List<BlockPos> findCandidates(final GenFeatureConfiguration configuration,
                                          final LevelAccessor level,
                                          final BlockPos rootPos,
                                          final int radius) {
        final List<BlockPos> candidates = new ArrayList<>();
        final int horizontalRange = Mth.clamp(radius + 6, 8, 18);
        final int maxHeight = configuration.get(MAX_HEIGHT);

        for (int y = 3; y <= maxHeight; y++) {
            for (int x = -horizontalRange; x <= horizontalRange; x++) {
                for (int z = -horizontalRange; z <= horizontalRange; z++) {
                    final BlockPos pos = rootPos.offset(x, y, z);
                    if (this.isCandidate(configuration, level, pos, rootPos)) {
                        candidates.add(pos.immutable());
                    }
                }
            }
        }

        return candidates;
    }

    private boolean isCandidate(final GenFeatureConfiguration configuration,
                                final LevelAccessor level,
                                final BlockPos pos,
                                final BlockPos rootPos) {
        final BlockState state = level.getBlockState(pos);
        final Block targetLeaves = configuration.get(TARGET_LEAVES);
        if (!state.is(targetLeaves)) {
            return false;
        }
        if (pos.distManhattan(rootPos) < 4) {
            return false;
        }
        return this.hasExteriorFace(level, pos);
    }

    private boolean hasExteriorFace(final LevelAccessor level, final BlockPos pos) {
        for (final Direction direction : Direction.values()) {
            if (direction == Direction.DOWN) {
                continue;
            }
            if (level.isEmptyBlock(pos.relative(direction))) {
                return true;
            }
        }
        return false;
    }
}
