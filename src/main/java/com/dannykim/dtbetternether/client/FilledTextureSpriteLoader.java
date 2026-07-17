package com.dannykim.dtbetternether.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.client.textures.ForgeTextureMetadata;
import net.minecraftforge.client.textures.ITextureAtlasSpriteLoader;
import org.jetbrains.annotations.NotNull;

public final class FilledTextureSpriteLoader implements ITextureAtlasSpriteLoader {
    @Override
    public SpriteContents loadContents(
            final ResourceLocation name,
            final Resource resource,
            final FrameSize frameSize,
            final NativeImage image,
            final AnimationMetadataSection animationMeta,
            final ForgeTextureMetadata forgeMeta
    ) {
        return new SpriteContents(name, frameSize, FilledTextureProcessor.fillTransparentPixels(image), animationMeta, forgeMeta);
    }

    @Override
    public @NotNull TextureAtlasSprite makeSprite(
            final ResourceLocation atlasName,
            final SpriteContents contents,
            final int atlasWidth,
            final int atlasHeight,
            final int spriteX,
            final int spriteY,
            final int mipmapLevel
    ) {
        return new LoadedSprite(atlasName, contents, atlasWidth, atlasHeight, spriteX, spriteY);
    }

    private static final class LoadedSprite extends TextureAtlasSprite {
        private LoadedSprite(
                final ResourceLocation atlasLocation,
                final SpriteContents contents,
                final int atlasWidth,
                final int atlasHeight,
                final int x,
                final int y
        ) {
            super(atlasLocation, contents, atlasWidth, atlasHeight, x, y);
        }
    }
}
