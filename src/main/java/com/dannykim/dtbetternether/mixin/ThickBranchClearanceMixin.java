package com.dannykim.dtbetternether.mixin;

import com.dannykim.dtbetternether.systems.ModGenFeatures;
import com.ferreusveritas.dynamictrees.block.branch.ThickBranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.TrunkShellBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ThickBranchBlock.class, remap = false)
public abstract class ThickBranchClearanceMixin {
    // DT queries each required shell cell, then commits the footprint only if every cell fits.
    @Inject(method = "getReplaceability", at = @At("RETURN"), cancellable = true)
    private void dtbetternether$allowShell(final LevelAccessor level, final BlockPos pos,
                                            final BlockPos core, final CallbackInfoReturnable<Enum<?>> result) {
        final Enum<?> classification = result.getReturnValue();
        final var branch = (ThickBranchBlock) (Object) this;
        for (final var feature : branch.getFamily().getCommonSpecies().getGenFeatures()) {
            if (feature.getGenFeature() == ModGenFeatures.BASE_TRUNK_CLEARANCE) {
                final var state = level.getBlockState(pos);
                final boolean blocking = classification.name().equals("BLOCKING");
                final boolean worldGenSoil = branch.getFamily().getCommonSpecies()
                        .isAcceptableSoilForWorldgen(level, pos, state);
                if (!blocking && !worldGenSoil) return;
                if (!state.getFluidState().isEmpty() || state.getBlock() instanceof TrunkShellBlock) return;
                ModGenFeatures.BASE_TRUNK_CLEARANCE.rememberRemovedBlock(level, pos);
                if (!blocking) return;
                // The return enum is package-private in DT.
                result.setReturnValue(Enum.valueOf(classification.getDeclaringClass(), "REPLACEABLE"));
                return;
            }
        }
    }
}
