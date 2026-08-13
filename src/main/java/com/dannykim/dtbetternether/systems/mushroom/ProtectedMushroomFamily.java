package com.dannykim.dtbetternether.systems.mushroom;
import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.branch.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictreesplus.tree.HugeMushroomFamily;
import net.minecraft.resources.ResourceLocation;
public final class ProtectedMushroomFamily extends HugeMushroomFamily {
    public static final TypedRegistry.EntryType<Family> TYPE=TypedRegistry.newType(ProtectedMushroomFamily::new);
    public ProtectedMushroomFamily(final ResourceLocation name){super(name);}
    @Override protected BranchBlock createBranchBlock(final ResourceLocation name){final BasicBranchBlock branch=new ProtectedMushroomBranchBlock(name,this.getProperties());if(this.isFireProof())branch.setFireSpreadSpeed(0).setFlammability(0);return branch;}
}
