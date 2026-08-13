package com.dannykim.dtbetternether.systems.mushroom;

import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictreesplus.block.mushroom.DynamicCapCenterBlock;
import com.ferreusveritas.dynamictreesplus.block.mushroom.MushroomBranchBlock;
import com.ferreusveritas.dynamictreesplus.systems.mushroomlogic.context.MushroomCapContext;
import com.ferreusveritas.dynamictreesplus.tree.HugeMushroomSpecies;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import java.util.*;

public final class ProtectedMushroomBranchBlock extends MushroomBranchBlock {
    public ProtectedMushroomBranchBlock(final ResourceLocation name, final Properties properties) { super(name, properties); }
    @Override public void destroyMushroomCap(final Level level, final BlockPos cutPos, final Species species,
            final ItemStack tool, final List<BlockPos> endPoints, final Map<BlockPos, BlockState> destroyedCaps,
            final List<BranchBlock.ItemStackPos> drops) {
        final Map<BlockPos, BlockState> protectedCaps = hideStandingCapOverlaps(level, species, endPoints);
        try { super.destroyMushroomCap(level, cutPos, species, tool, endPoints, destroyedCaps, drops); }
        finally { protectedCaps.forEach((pos, state) -> level.setBlock(pos, state, Block.UPDATE_CLIENTS)); }
    }
    private Map<BlockPos, BlockState> hideStandingCapOverlaps(final Level level, final Species species, final List<BlockPos> endPoints) {
        final Map<BlockPos, BlockState> protectedCaps = new HashMap<>();
        if (level.isClientSide() || !(species instanceof HugeMushroomSpecies mushroomSpecies)) return protectedCaps;
        final Set<BlockPos> felledCenters = new HashSet<>(), felledShape = new HashSet<>();
        for (final BlockPos endPoint : endPoints) {
            final BlockPos center = endPoint.above().immutable(); final int age = DynamicCapCenterBlock.getCapAge(level, center);
            if (age >= 0) { felledCenters.add(center); felledShape.addAll(mushroomSpecies.getMushroomShapeKit().getShapeCluster(new MushroomCapContext(level, center, mushroomSpecies, age))); }
        }
        if (felledShape.isEmpty()) return protectedCaps;
        int minX=Integer.MAX_VALUE,minY=Integer.MAX_VALUE,minZ=Integer.MAX_VALUE,maxX=Integer.MIN_VALUE,maxY=Integer.MIN_VALUE,maxZ=Integer.MIN_VALUE;
        for(final BlockPos pos:felledShape){minX=Math.min(minX,pos.getX());minY=Math.min(minY,pos.getY());minZ=Math.min(minZ,pos.getZ());maxX=Math.max(maxX,pos.getX());maxY=Math.max(maxY,pos.getY());maxZ=Math.max(maxZ,pos.getZ());}
        final Block capCenter=mushroomSpecies.getCapProperties().getDynamicCapCenterBlock().orElse(null); if(capCenter==null)return protectedCaps;
        final Set<BlockPos> standingShape=new HashSet<>();
        for(int x=minX-8;x<=maxX+8;x++)for(int y=minY-8;y<=maxY+8;y++)for(int z=minZ-8;z<=maxZ+8;z++){
            final BlockPos center=new BlockPos(x,y,z);
            if(felledCenters.contains(center)||level.getBlockState(center).getBlock()!=capCenter||!(level.getBlockState(center.below()).getBlock() instanceof BranchBlock))continue;
            final int age=DynamicCapCenterBlock.getCapAge(level,center);if(age>=0)standingShape.addAll(mushroomSpecies.getMushroomShapeKit().getShapeCluster(new MushroomCapContext(level,center,mushroomSpecies,age)));
        }
        standingShape.retainAll(felledShape);standingShape.removeAll(felledCenters);
        for(final BlockPos pos:standingShape){final BlockState state=level.getBlockState(pos);if(!state.isAir()){protectedCaps.put(pos.immutable(),state);level.setBlock(pos,Blocks.AIR.defaultBlockState(),Block.UPDATE_CLIENTS);}}
        return protectedCaps;
    }
}
