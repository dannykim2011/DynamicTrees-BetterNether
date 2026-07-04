package com.dannykim.dtbetternether.data;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public final class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(final PackOutput output) {
        super(output, DynamicTreesBetterNether.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("item.dtbetternether.anchor_tree_seed", "Anchor Tree Nut");
        add("item.dtbetternether.nether_sakura_seed", "Nether Sakura Seed");
        add("item.dtbetternether.rubeus_seed", "Rubeus Cone");
        add("item.dtbetternether.wart_seed", "Wart Spore Gall");
        add("item.dtbetternether.willow_seed", "Nether Willow Inflorescence");
    }
}
