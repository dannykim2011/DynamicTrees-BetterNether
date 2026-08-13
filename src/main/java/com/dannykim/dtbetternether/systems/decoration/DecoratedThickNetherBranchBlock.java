package com.dannykim.dtbetternether.systems.decoration;

import com.dtteam.dynamictrees.block.branch.BranchBlock;
import com.dtteam.dynamictrees.block.branch.ThickBranchBlock;
import com.dtteam.dynamictrees.tree.species.Species;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;

public class DecoratedThickNetherBranchBlock extends ThickBranchBlock {
    public DecoratedThickNetherBranchBlock(final Identifier name, final Properties properties) {
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
        DecoratedNetherBranchBlock.dropDecorations(level, cutPos, endPoints, destroyedLeaves, tool);
    }
}
