package com.dannykim.dtbetternether.systems.decoration;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.branch.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.family.NetherFungusFamily;
import net.minecraft.resources.ResourceLocation;

public class DecoratedNetherFamily extends NetherFungusFamily {
    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(DecoratedNetherFamily::new);

    public DecoratedNetherFamily(final ResourceLocation name) {
        super(name);
    }

    @Override
    protected BranchBlock createBranchBlock(final ResourceLocation name) {
        final BasicBranchBlock branch = this.isThick()
                ? new DecoratedThickNetherBranchBlock(name, this.getProperties())
                : new DecoratedNetherBranchBlock(name, this.getProperties());
        if (this.isFireProof()) {
            branch.setFireSpreadSpeed(0).setFlammability(0);
        }
        return branch;
    }
}
