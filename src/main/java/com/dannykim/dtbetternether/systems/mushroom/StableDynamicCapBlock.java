package com.dannykim.dtbetternether.systems.mushroom;
import com.ferreusveritas.dynamictreesplus.block.mushroom.CapProperties;
import com.ferreusveritas.dynamictreesplus.block.mushroom.DynamicCapBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
public final class StableDynamicCapBlock extends DynamicCapBlock {
    public StableDynamicCapBlock(final CapProperties capProperties,final Properties properties){super(capProperties,properties);}
    @Override public void tick(final BlockState state,final ServerLevel level,final BlockPos pos,final RandomSource random){}
}
