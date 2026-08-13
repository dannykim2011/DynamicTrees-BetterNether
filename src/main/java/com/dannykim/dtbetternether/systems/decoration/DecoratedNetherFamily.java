package com.dannykim.dtbetternether.systems.decoration;

import com.dtteam.dynamictrees.api.registry.TypedRegistry;
import com.dtteam.dynamictrees.block.branch.BasicBranchBlock;
import com.dtteam.dynamictrees.block.branch.BranchBlock;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.family.NetherFungusFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DecoratedNetherFamily extends NetherFungusFamily {
    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(DecoratedNetherFamily::new);

    public DecoratedNetherFamily(final Identifier name) {
        super(name);
    }

    @Override
    protected BranchBlock createBranch(final Identifier name, final BlockBehaviour.Properties properties) {
        final BasicBranchBlock branch = this.isThick()
                ? new DecoratedThickNetherBranchBlock(name, properties)
                : new DecoratedNetherBranchBlock(name, properties);
        if (this.isFireProof()) {
            branch.setFireSpreadSpeed(0).setFlammability(0);
        }
        return branch;
    }
}
