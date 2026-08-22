package com.dannykim.dtbetternether.loot;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.dtteam.dynamictrees.tree.TreeHelper;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class PrimitiveSaplingDropModifier extends LootModifier {
    public static final MapCodec<PrimitiveSaplingDropModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            codecStart(instance).apply(instance, PrimitiveSaplingDropModifier::new));

    private static final Map<Identifier, Identifier> REPLACEMENTS = Map.ofEntries(
            entry("betternether:anchor_tree_sapling", "anchor_tree_seed"),
            entry("betternether:nether_sakura_sapling", "nether_sakura_seed"),
            entry("betternether:rubeus_sapling", "rubeus_seed"),
            entry("betternether:wart_seed", "wart_seed"),
            entry("minecraft:warped_fungus", "big_warped_seed"),
            entry("minecraft:red_mushroom", "nether_red_mushroom_seed"),
            entry("minecraft:brown_mushroom", "nether_brown_mushroom_seed")
    );

    private PrimitiveSaplingDropModifier(final LootItemCondition[] conditions, final int priority) {
        super(conditions, priority);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(final ObjectArrayList<ItemStack> loot,
                                                           final LootContext context) {
        final BlockState source = context.getOptionalParameter(LootContextParams.BLOCK_STATE);
        if (source != null && TreeHelper.isBranch(source)) return loot;
        if (loot.stream().noneMatch(stack -> isPrimitiveSapling(BuiltInRegistries.ITEM.getKey(stack.getItem())))) {
            return loot;
        }
        if (loot.stream().anyMatch(PrimitiveSaplingDropModifier::isDynamicSeed)) {
            loot.removeIf(stack -> isPrimitiveSapling(BuiltInRegistries.ITEM.getKey(stack.getItem())));
            return loot;
        }
        final String sourcePath = source == null ? "" : BuiltInRegistries.BLOCK.getKey(source.getBlock()).getPath();
        loot.replaceAll(stack -> replace(stack, targetFor(BuiltInRegistries.ITEM.getKey(stack.getItem()), sourcePath)));
        return loot;
    }

    private static Identifier targetFor(final Identifier primitive, final String sourcePath) {
        if (primitive.equals(Identifier.parse("betternether:willow_sapling"))) {
            return DynamicTreesBetterNether.location(sourcePath.contains("old_willow") ? "old_willow_seed" : "willow_seed");
        }
        if (primitive.equals(Identifier.parse("minecraft:crimson_fungus"))) {
            if (sourcePath.contains("crimson_pine")) return DynamicTreesBetterNether.location("crimson_pine_seed");
            if (sourcePath.contains("crimson_glowing")) return DynamicTreesBetterNether.location("crimson_glowing_seed");
            return null;
        }
        return REPLACEMENTS.get(primitive);
    }

    private static boolean isPrimitiveSapling(final Identifier id) {
        return REPLACEMENTS.containsKey(id)
                || id.equals(Identifier.parse("betternether:willow_sapling"))
                || id.equals(Identifier.parse("minecraft:crimson_fungus"));
    }

    private static ItemStack replace(final ItemStack original, final Identifier replacementId) {
        if (replacementId == null || !BuiltInRegistries.ITEM.containsKey(replacementId)) return original;
        final Item replacement = BuiltInRegistries.ITEM.getValue(replacementId);
        return replacement == null ? original : new ItemStack(replacement, original.getCount());
    }

    private static boolean isDynamicSeed(final ItemStack stack) {
        final Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return DynamicTreesBetterNether.MOD_ID.equals(id.getNamespace()) && id.getPath().endsWith("_seed");
    }

    private static Map.Entry<Identifier, Identifier> entry(final String primitive, final String seed) {
        return Map.entry(Identifier.parse(primitive), DynamicTreesBetterNether.location(seed));
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
