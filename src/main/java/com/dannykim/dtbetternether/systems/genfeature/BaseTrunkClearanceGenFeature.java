package com.dannykim.dtbetternether.systems.genfeature;

import com.dtteam.dynamictrees.block.branch.BranchBlock;
import com.dtteam.dynamictrees.systems.genfeature.GenFeature;
import com.dtteam.dynamictrees.systems.genfeature.GenFeatureConfiguration;
import com.dtteam.dynamictrees.systems.genfeature.context.PostGenerationContext;
import com.dtteam.dynamictrees.systems.genfeature.context.PreGenerationContext;
import com.dtteam.dynamictrees.systems.genfeature.context.PostGrowContext;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.LevelAccessor;


public final class BaseTrunkClearanceGenFeature extends GenFeature {
    private record Removed(BlockState state, BlockState below) {}
    private record ClearedBlocks(WeakReference<LevelAccessor> level, BlockPos root, Map<BlockPos, Removed> positions) {}
    private final ThreadLocal<ClearedBlocks> pending = new ThreadLocal<>();

    public BaseTrunkClearanceGenFeature(final Identifier registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
    }

    @Override
    protected BlockPos preGenerate(GenFeatureConfiguration configuration, PreGenerationContext context) {
        pending.remove();
        if (!context.isWorldGen()) return context.pos();
        final var level = context.level();
        final BlockPos root = context.pos();
        pending.set(new ClearedBlocks(new WeakReference<>(level), root.immutable(), new HashMap<>()));
        return root;
    }

    public void rememberRemovedBlock(LevelAccessor level, BlockPos pos) {
        ClearedBlocks previous = pending.get();
        if (previous == null || previous.level().get() != level || !level.hasChunkAt(pos)) return;
        BlockState state = level.getBlockState(pos);
        if (!state.isAir() && Block.isShapeFullBlock(state.getCollisionShape(level, pos))) {
            previous.positions().putIfAbsent(pos.immutable(), new Removed(state, level.getBlockState(pos.below())));
        }
    }

    private boolean restoreSurface(PostGenerationContext context) {
        final ClearedBlocks previous = pending.get();
        pending.remove();
        if (previous == null || previous.level().get() != context.level() || !previous.root().equals(context.pos())
                || !(context.level() instanceof WorldGenLevel world)) return false;
        boolean changed = false;
        final Map<Long, BlockPos> lowest = new HashMap<>();
        final Map<Long, BlockPos> highest = new HashMap<>();
        for (BlockPos pos : previous.positions().keySet()) {
            long column = BlockPos.asLong(pos.getX(), 0, pos.getZ());
            if (!lowest.containsKey(column) || pos.getY() < lowest.get(column).getY()) lowest.put(column, pos);
            if (!highest.containsKey(column) || pos.getY() > highest.get(column).getY()) highest.put(column, pos);
        }
        for (long column : lowest.keySet()) {
            BlockPos removed = lowest.get(column);
            Removed original = previous.positions().get(removed);
            BlockPos target = removed.below();
            BlockState cover = world.getBlockState(removed);
            if (cover.equals(original.state()) || !world.getBlockState(target).equals(original.below())
                    || Block.isFaceFull(cover.getShape(world, removed), Direction.DOWN)) continue;
            BlockState replacement = previous.positions().get(highest.get(column)).state();
            changed |= world.setBlock(target, replacement, Block.UPDATE_CLIENTS);
        }
        return changed;
    }

    @Override
    protected boolean postGenerate(final GenFeatureConfiguration configuration,
                                   final PostGenerationContext context) {
        boolean changed = reconcileTrunk(context.level(), context.pos().above());
        return restoreSurface(context) || changed;
    }

    @Override
    protected boolean postGrow(final GenFeatureConfiguration configuration, final PostGrowContext context) {
        return reconcileTrunk(context.level(), context.treePos());
    }

    private static boolean reconcileTrunk(final LevelAccessor level, final BlockPos base) {
        if (!(level.getBlockState(base).getBlock() instanceof BranchBlock trunk)) return false;
        BlockPos top = base;
        while (level.getBlockState(top.above()).is(trunk)) top = top.above();

        int aboveRadius = 0;
        boolean changed = false;
        for (BlockPos pos = top; pos.getY() >= base.getY(); pos = pos.below()) {
            int radius = trunk.getRadius(level.getBlockState(pos));
            if (aboveRadius > 8 && aboveRadius > radius) {
                int actual = trunk.setRadius(level, pos, aboveRadius, null);
                changed |= radius != actual;
                radius = actual;
            }
            aboveRadius = radius;
        }
        return changed;
    }
}
