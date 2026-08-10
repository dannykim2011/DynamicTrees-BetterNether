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
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;

public final class CrimsonGlowingWallDecorationsGenFeature extends GenFeature {
    public static final ConfigurationProperty<Block> TARGET_LEAVES =
            ConfigurationProperty.block("target_leaves");
    public static final ConfigurationProperty<Block> TARGET_BRANCH =
            ConfigurationProperty.block("target_branch");
    public static final ConfigurationProperty<Block> WALL_MOSS =
            ConfigurationProperty.block("wall_moss");
    public static final ConfigurationProperty<Block> WALL_MUSHROOM =
            ConfigurationProperty.block("wall_mushroom");

    public CrimsonGlowingWallDecorationsGenFeature(final ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(TARGET_LEAVES, TARGET_BRANCH, WALL_MOSS, WALL_MUSHROOM, PLACE_CHANCE, MAX_COUNT, MAX_HEIGHT);
    }

    @Override
    protected GenFeatureConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(TARGET_LEAVES, Blocks.SHROOMLIGHT)
                .with(TARGET_BRANCH, Blocks.CRIMSON_STEM)
                .with(WALL_MOSS, Blocks.AIR)
                .with(WALL_MUSHROOM, Blocks.AIR)
                .with(PLACE_CHANCE, 1.0F)
                .with(MAX_COUNT, 28)
                .with(MAX_HEIGHT, 18);
    }

    @Override
    protected boolean postGenerate(final GenFeatureConfiguration configuration, final PostGenerationContext context) {
        if (!context.isWorldGen() && context.random().nextFloat() > configuration.get(PLACE_CHANCE)) {
            return false;
        }

        final List<Candidate> candidates = this.findCandidates(
                configuration,
                context.level(),
                context.pos(),
                context.radius()
        );
        if (candidates.isEmpty()) {
            return false;
        }

        final int mossTarget = Mth.clamp(configuration.get(MAX_COUNT), 8, 34);
        final int mushroomTarget = Math.max(2, mossTarget / 6);
        int mossPlaced = 0;
        int mushroomPlaced = 0;

        while (!candidates.isEmpty() && mossPlaced < mossTarget) {
            final Candidate candidate = candidates.remove(context.random().nextInt(candidates.size()));
            if (!context.level().isEmptyBlock(candidate.pos())) {
                continue;
            }
            final Block block = mushroomPlaced < mushroomTarget && context.random().nextFloat() < 0.22F
                    ? configuration.get(WALL_MUSHROOM)
                    : configuration.get(WALL_MOSS);
            if (block == Blocks.AIR) {
                continue;
            }
            context.level().setBlock(candidate.pos(), withFacing(block.defaultBlockState(), candidate.facing()), Block.UPDATE_ALL);
            if (block == configuration.get(WALL_MUSHROOM)) {
                mushroomPlaced++;
            } else {
                mossPlaced++;
            }
        }

        return mossPlaced > 0 || mushroomPlaced > 0;
    }

    private List<Candidate> findCandidates(final GenFeatureConfiguration configuration,
                                           final LevelAccessor level,
                                           final BlockPos rootPos,
                                           final int radius) {
        final List<Candidate> candidates = new ArrayList<>();
        final int horizontalRange = Mth.clamp(radius + 6, 8, 18);
        final int maxHeight = configuration.get(MAX_HEIGHT);

        for (int y = 1; y <= maxHeight; y++) {
            for (int x = -horizontalRange; x <= horizontalRange; x++) {
                for (int z = -horizontalRange; z <= horizontalRange; z++) {
                    final BlockPos support = rootPos.offset(x, y, z);
                    if (!isSupport(configuration, level.getBlockState(support))) {
                        continue;
                    }
                    for (final Direction direction : Direction.Plane.HORIZONTAL) {
                        final BlockPos pos = support.relative(direction);
                        if (level.isEmptyBlock(pos)) {
                            candidates.add(new Candidate(pos.immutable(), direction));
                        }
                    }
                }
            }
        }
        return candidates;
    }

    private static boolean isSupport(final GenFeatureConfiguration configuration, final BlockState state) {
        return state.is(configuration.get(TARGET_LEAVES)) || state.is(configuration.get(TARGET_BRANCH));
    }

    private static BlockState withFacing(final BlockState state, final Direction direction) {
        for (final Property<?> property : state.getProperties()) {
            if ("facing".equals(property.getName())) {
                return setUnchecked(state, property, direction);
            }
        }
        return state;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static BlockState setUnchecked(final BlockState state, final Property property, final Object value) {
        return state.setValue(property, (Comparable) value);
    }

    private record Candidate(BlockPos pos, Direction facing) {
    }
}
