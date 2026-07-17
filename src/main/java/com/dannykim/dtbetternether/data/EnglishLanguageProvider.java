package com.dannykim.dtbetternether.data;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(final PackOutput output) {
        super(output, DynamicTreesBetterNether.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("item.dtbetternether.anchor_tree_seed", "Anchor Tree Nut");
        add("item.dtbetternether.mushroom_fir_seed", "Mushroom Fir Spore");
        add("item.dtbetternether.nether_sakura_seed", "Nether Sakura Seed");
        add("item.dtbetternether.nether_brown_mushroom_seed", "Nether Brown Mushroom Spore");
        add("item.dtbetternether.nether_cactus_seed", "Nether Cactus Seed");
        add("item.dtbetternether.nether_red_mushroom_seed", "Nether Red Mushroom Spore");
        add("item.dtbetternether.old_willow_seed", "Old Nether Willow Inflorescence");
        add("item.dtbetternether.rubeus_seed", "Rubeus Cone");
        add("item.dtbetternether.wart_seed", "Wart Spore Gall");
        add("item.dtbetternether.willow_seed", "Nether Willow Inflorescence");
    }
}
