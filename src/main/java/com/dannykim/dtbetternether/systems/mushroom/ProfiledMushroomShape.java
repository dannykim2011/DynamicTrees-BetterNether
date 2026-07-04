package com.dannykim.dtbetternether.systems.mushroom;

import com.ferreusveritas.dynamictreesplus.block.mushroom.DynamicCapCenterBlock;
import com.ferreusveritas.dynamictreesplus.systems.mushroomlogic.MushroomShapeConfiguration;
import com.ferreusveritas.dynamictreesplus.systems.mushroomlogic.context.MushroomCapContext;
import com.ferreusveritas.dynamictreesplus.systems.mushroomlogic.shapekits.MushroomShapeKit;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

abstract class ProfiledMushroomShape extends MushroomShapeKit {
    protected ProfiledMushroomShape(final ResourceLocation name) { super(name); }

    @Override
    public final void generateMushroomCap(final MushroomShapeConfiguration cfg, final MushroomCapContext ctx) {
        apply(ctx, Mode.PLACE);
    }

    @Override
    public final void clearMushroomCap(final MushroomShapeConfiguration cfg, final MushroomCapContext ctx) {
        apply(ctx, Mode.CLEAR);
    }

    @Override
    public final List<BlockPos> getShapeCluster(final MushroomShapeConfiguration cfg, final MushroomCapContext ctx) {
        return apply(ctx, Mode.GET);
    }

    private List<BlockPos> apply(final MushroomCapContext ctx, final Mode mode) {
        final List<BlockPos> result = new LinkedList<>();
        final DynamicCapCenterBlock cap = ctx.species().getCapProperties().getDynamicCapCenterBlock().orElse(null);
        if (cap == null) return result;
        final int age = Math.min(ctx.age(), maximumAge());
        for (int r = 1; r <= age; r++) {
            final BlockPos centre = ctx.pos().below(depth(r, age));
            final boolean rim = r == age || depth(r, age) != depth(Math.min(age, r + 1), age);
            if (mode == Mode.PLACE && !cap.placeRing(ctx.level(), centre, r, age, rim, r == age)) break;
            if (mode == Mode.CLEAR) cap.clearRing(ctx.level(), centre, r);
            if (mode == Mode.GET) result.addAll(cap.getRing(ctx.level(), centre, r));
        }
        result.add(ctx.pos());
        return result;
    }

    @Override public final int getMaxCapAge(final MushroomShapeConfiguration cfg) { return maximumAge(); }
    @Override public final float getChanceToAge(final MushroomShapeConfiguration cfg) { return chanceToAge(); }
    protected abstract int maximumAge();
    protected abstract int depth(int radius, int age);
    protected float chanceToAge() { return 0.75F; }
    private enum Mode { PLACE, CLEAR, GET }
}
