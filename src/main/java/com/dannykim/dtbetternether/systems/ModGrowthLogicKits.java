package com.dannykim.dtbetternether.systems;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dannykim.dtbetternether.systems.growthlogic.*;
import com.dtteam.dynamictrees.api.registry.Registry;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;

public final class ModGrowthLogicKits {
    public static final GrowthLogicKit WILLOW = new WillowLogic(DynamicTreesBetterNether.location("willow"));
    public static final GrowthLogicKit OLD_WILLOW = new OldWillowLogic(DynamicTreesBetterNether.location("old_willow"));
    public static final GrowthLogicKit WART = new WartLogic(DynamicTreesBetterNether.location("wart"));
    public static final GrowthLogicKit RUBEUS = new RubeusLogic(DynamicTreesBetterNether.location("rubeus"));
    public static final GrowthLogicKit MUSHROOM_FIR = new MushroomFirLogic(DynamicTreesBetterNether.location("mushroom_fir"));
    public static final GrowthLogicKit NETHER_SAKURA = new NetherSakuraLogic(DynamicTreesBetterNether.location("nether_sakura"));
    public static final GrowthLogicKit ANCHOR_TREE = new AnchorTreeLogic(DynamicTreesBetterNether.location("anchor_tree"));
    public static final GrowthLogicKit BIG_WARPED = new BigWarpedLogic(DynamicTreesBetterNether.location("big_warped"));
    public static final GrowthLogicKit CRIMSON_GLOWING = new CrimsonGlowingLogic(DynamicTreesBetterNether.location("crimson_glowing"));
    public static final GrowthLogicKit CRIMSON_PINE = new CrimsonPineLogic(DynamicTreesBetterNether.location("crimson_pine"));
    public static final GrowthLogicKit NETHER_RED_MUSHROOM = new MushroomStemLogic(DynamicTreesBetterNether.location("nether_red_mushroom"), 14, 3);
    public static final GrowthLogicKit NETHER_BROWN_MUSHROOM = new MushroomStemLogic(DynamicTreesBetterNether.location("nether_brown_mushroom"), 17, 2);

    private ModGrowthLogicKits() {}

    public static void register(final Registry<GrowthLogicKit> registry) {
        registry.registerAll(WILLOW, OLD_WILLOW, WART, RUBEUS, MUSHROOM_FIR, NETHER_SAKURA, ANCHOR_TREE,
                BIG_WARPED, CRIMSON_GLOWING, CRIMSON_PINE, NETHER_RED_MUSHROOM, NETHER_BROWN_MUSHROOM);
    }
}
