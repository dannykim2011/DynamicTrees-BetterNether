package com.dannykim.dtbetternether.systems.mushroom;
import com.dtteam.dynamictreesplus.block.mushroom.CapProperties;
import com.dtteam.dynamictreesplus.block.mushroom.DynamicCapBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
public final class StableDynamicCapBlock extends DynamicCapBlock {
    public StableDynamicCapBlock(final Identifier name,final CapProperties capProperties,final Properties properties){super(name,capProperties,properties);}
    @Override public void tick(final BlockState state,final ServerLevel level,final BlockPos pos,final RandomSource random){}
}
