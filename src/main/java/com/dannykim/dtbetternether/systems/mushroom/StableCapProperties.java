package com.dannykim.dtbetternether.systems.mushroom;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictreesplus.block.mushroom.CapProperties;
import com.ferreusveritas.dynamictreesplus.block.mushroom.DynamicCapBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
public final class StableCapProperties extends CapProperties {
    public static final TypedRegistry.EntryType<CapProperties> TYPE=TypedRegistry.newType(StableCapProperties::new);
    public StableCapProperties(final ResourceLocation name){super(name);}
    @Override protected DynamicCapBlock createDynamicCap(final BlockBehaviour.Properties properties){return new StableDynamicCapBlock(this,properties);}
}
