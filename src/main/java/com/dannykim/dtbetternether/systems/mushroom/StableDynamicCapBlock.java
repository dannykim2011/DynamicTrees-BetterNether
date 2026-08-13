package com.dannykim.dtbetternether.systems.mushroom;

import com.dtteam.dynamictreesplus.block.mushroom.CapProperties;
import com.dtteam.dynamictreesplus.block.mushroom.DynamicCapBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public final class StableDynamicCapBlock extends DynamicCapBlock {
    public StableDynamicCapBlock(final CapProperties capProperties, final Properties properties) { super(capProperties, properties); }
    @Override public void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        // Felling removes the generated shape directly; hand-made holes do not collapse neighboring rings.
    }
}
