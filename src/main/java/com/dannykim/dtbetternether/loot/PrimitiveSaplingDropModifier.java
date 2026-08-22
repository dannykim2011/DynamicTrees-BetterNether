package com.dannykim.dtbetternether.loot;

import com.dannykim.dtbetternether.DynamicTreesBetterNether;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class PrimitiveSaplingDropModifier extends LootModifier {
    public static final Codec<PrimitiveSaplingDropModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance).apply(instance, PrimitiveSaplingDropModifier::new));

    private static final Map<ResourceLocation, ResourceLocation> REPLACEMENTS = Map.ofEntries(
            entry("betternether:anchor_tree_sapling", "anchor_tree_seed"),
            entry("betternether:nether_sakura_sapling", "nether_sakura_seed"),
            entry("betternether:rubeus_sapling", "rubeus_seed"),
            entry("betternether:wart_seed", "wart_seed"),
            entry("minecraft:warped_fungus", "big_warped_seed"),
            entry("minecraft:red_mushroom", "nether_red_mushroom_seed"),
            entry("minecraft:brown_mushroom", "nether_brown_mushroom_seed")
    );

    private PrimitiveSaplingDropModifier(final net.minecraft.world.level.storage.loot.predicates.LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(final ObjectArrayList<ItemStack> loot,
                                                           final LootContext context) {
        final BlockState source = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        if (loot.stream().noneMatch(stack -> isPrimitiveSapling(BuiltInRegistries.ITEM.getKey(stack.getItem())))) {
            return loot;
        }

        final boolean hasDynamicSeed = loot.stream().anyMatch(PrimitiveSaplingDropModifier::isDynamicSeed);
        if (hasDynamicSeed) {
            loot.removeIf(stack -> isPrimitiveSapling(BuiltInRegistries.ITEM.getKey(stack.getItem())));
            return loot;
        }

        final String sourcePath = source == null ? "" : BuiltInRegistries.BLOCK.getKey(source.getBlock()).getPath();
        loot.replaceAll(stack -> replace(stack, targetFor(BuiltInRegistries.ITEM.getKey(stack.getItem()), sourcePath)));
        return loot;
    }

    private static ResourceLocation targetFor(final ResourceLocation primitive, final String sourcePath) {
        if (primitive.equals(ResourceLocation.tryParse("betternether:willow_sapling"))) {
            return DynamicTreesBetterNether.location(sourcePath.contains("old_willow") ? "old_willow_seed" : "willow_seed");
        }
        if (primitive.equals(ResourceLocation.tryParse("minecraft:crimson_fungus"))) {
            if (sourcePath.contains("crimson_pine")) return DynamicTreesBetterNether.location("crimson_pine_seed");
            if (sourcePath.contains("crimson_glowing")) return DynamicTreesBetterNether.location("crimson_glowing_seed");
            return null;
        }
        return REPLACEMENTS.get(primitive);
    }

    private static boolean isPrimitiveSapling(final ResourceLocation id) {
        return REPLACEMENTS.containsKey(id)
                || id.equals(ResourceLocation.tryParse("betternether:willow_sapling"))
                || id.equals(ResourceLocation.tryParse("minecraft:crimson_fungus"));
    }

    private static ItemStack replace(final ItemStack original, final ResourceLocation replacementId) {
        if (replacementId == null || !BuiltInRegistries.ITEM.containsKey(replacementId)) return original;
        final Item replacement = BuiltInRegistries.ITEM.get(replacementId);
        return replacement == null ? original : new ItemStack(replacement, original.getCount());
    }

    private static boolean isDynamicSeed(final ItemStack stack) {
        final ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return DynamicTreesBetterNether.MOD_ID.equals(id.getNamespace()) && id.getPath().endsWith("_seed");
    }

    private static Map.Entry<ResourceLocation, ResourceLocation> entry(final String primitive, final String seed) {
        return Map.entry(ResourceLocation.tryParse(primitive), DynamicTreesBetterNether.location(seed));
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
