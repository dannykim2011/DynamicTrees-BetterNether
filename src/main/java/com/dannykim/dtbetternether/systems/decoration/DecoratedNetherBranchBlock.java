package com.dannykim.dtbetternether.systems.decoration;

import com.dtteam.dynamictrees.block.branch.BasicBranchBlock;
import com.dtteam.dynamictrees.block.branch.BranchBlock;
import com.dtteam.dynamictrees.tree.species.Species;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DecoratedNetherBranchBlock extends BasicBranchBlock {
    private static final int MAX_DECORATIONS = 512;

    public DecoratedNetherBranchBlock(final ResourceLocation name, final Properties properties) {
        super(name, properties);
    }

    @Override
    public void destroyLeaves(final Level level,
                              final BlockPos cutPos,
                              final Species species,
                              final ItemStack tool,
                              final List<BlockPos> endPoints,
                              final Map<BlockPos, BlockState> destroyedLeaves,
                              final List<BranchBlock.ItemStackPos> drops) {
        super.destroyLeaves(level, cutPos, species, tool, endPoints, destroyedLeaves, drops);
        dropDecorations(level, cutPos, endPoints, destroyedLeaves, tool);
    }

    static void dropDecorations(final Level level,
                                final BlockPos cutPos,
                                final List<BlockPos> endPoints,
                                final Map<BlockPos, BlockState> destroyedLeaves,
                                final ItemStack tool) {
        final ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        final Set<BlockPos> visited = new HashSet<>();

        int minX = cutPos.getX();
        int minY = cutPos.getY();
        int minZ = cutPos.getZ();
        int maxX = minX;
        int maxY = minY;
        int maxZ = minZ;

        for (final BlockPos endPoint : endPoints) {
            minX = Math.min(minX, endPoint.getX());
            minY = Math.min(minY, endPoint.getY());
            minZ = Math.min(minZ, endPoint.getZ());
            maxX = Math.max(maxX, endPoint.getX());
            maxY = Math.max(maxY, endPoint.getY());
            maxZ = Math.max(maxZ, endPoint.getZ());
        }

        for (final BlockPos relPos : new ArrayList<>(destroyedLeaves.keySet())) {
            final BlockPos pos = cutPos.offset(relPos.getX(), relPos.getY(), relPos.getZ());
            for (final Direction direction : Direction.values()) {
                queue.add(pos.relative(direction));
            }
        }

        int collected = dropConnected(level, queue, visited, tool, 0);

        for (int x = minX - 3; x <= maxX + 3 && collected < MAX_DECORATIONS; x++) {
            for (int y = minY - 10; y <= maxY + 3 && collected < MAX_DECORATIONS; y++) {
                for (int z = minZ - 3; z <= maxZ + 3 && collected < MAX_DECORATIONS; z++) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    final BlockState state = level.getBlockState(pos);
                    if (DecoratedNetherSpecies.isFellingDecoration(state) && !state.canSurvive(level, pos)) {
                        queue.add(pos);
                        collected = dropConnected(level, queue, visited, tool, collected);
                    }
                }
            }
        }
    }

    private static int dropConnected(final Level level,
                                     final ArrayDeque<BlockPos> queue,
                                     final Set<BlockPos> visited,
                                     final ItemStack tool,
                                     int collected) {
        while (!queue.isEmpty() && collected < MAX_DECORATIONS) {
            final BlockPos pos = queue.removeFirst().immutable();
            if (!visited.add(pos)) {
                continue;
            }
            final BlockState state = level.getBlockState(pos);
            if (!DecoratedNetherSpecies.isFellingDecoration(state)) {
                continue;
            }
            Block.dropResources(state, level, pos, null, null, tool);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            collected++;
            for (final Direction direction : Direction.values()) {
                queue.add(pos.relative(direction));
            }
        }
        return collected;
    }
}
