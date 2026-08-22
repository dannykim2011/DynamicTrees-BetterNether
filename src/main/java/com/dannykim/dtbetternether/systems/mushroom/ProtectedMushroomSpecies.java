package com.dannykim.dtbetternether.systems.mushroom;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.entity.FallingTreeEntity;
import com.ferreusveritas.dynamictrees.entity.animation.AnimationHandler;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictreesplus.block.mushroom.CapProperties;
import com.ferreusveritas.dynamictreesplus.tree.HugeMushroomSpecies;
import net.minecraft.resources.ResourceLocation;

public final class ProtectedMushroomSpecies extends HugeMushroomSpecies {
    public static final TypedRegistry.EntryType<Species> TYPE =
            HugeMushroomSpecies.createDefaultMushroomType(ProtectedMushroomSpecies::new);
    public ProtectedMushroomSpecies(final ResourceLocation name, final Family family, final CapProperties capProperties) {
        super(name, family, capProperties);
    }
    @Override public AnimationHandler selectAnimationHandler(final FallingTreeEntity entity) {
        return new MushroomFellingAnimationHandler(super.selectAnimationHandler(entity));
    }
}
