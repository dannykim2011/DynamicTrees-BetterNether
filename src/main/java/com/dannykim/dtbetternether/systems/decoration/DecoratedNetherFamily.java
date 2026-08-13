package com.dannykim.dtbetternether.systems.decoration;

import com.dtteam.dynamictrees.api.registry.TypedRegistry;
import com.dtteam.dynamictrees.block.branch.BasicBranchBlock;
import com.dtteam.dynamictrees.block.branch.BranchBlock;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.family.NetherFungusFamily;
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
