package com.dannykim.dtbetternether.client;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.client.event.RegisterSpriteSourceTypesEvent;

import java.util.Optional;

public final class FilledTextureSource implements SpriteSource {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("dtbetternether", "filled_texture");
    public static final MapCodec<FilledTextureSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("resource").forGetter(source -> source.resourceId)
    ).apply(instance, FilledTextureSource::new));

    private static SpriteSourceType TYPE;

    private final ResourceLocation resourceId;

    public FilledTextureSource(final ResourceLocation resourceId) {
        this.resourceId = resourceId;
    }

    public static void register(final RegisterSpriteSourceTypesEvent event) {
        TYPE = new SpriteSourceType(CODEC);
        event.register(ID, TYPE);
    }

    @Override
    public void run(final ResourceManager resourceManager, final Output output) {
        final ResourceLocation textureId = TEXTURE_ID_CONVERTER.idToFile(this.resourceId);
        final Optional<Resource> resource = resourceManager.getResource(textureId);
        resource.ifPresent(value -> output.add(filledId(this.resourceId), loader -> loadFilledSprite(loader, filledId(this.resourceId), value)));
    }

    @Override
    public SpriteSourceType type() {
        return TYPE;
    }

    private static SpriteContents loadFilledSprite(final SpriteResourceLoader loader, final ResourceLocation id, final Resource resource) {
        final SpriteContents original = loader.loadSprite(id, resource);
        if (original == null) {
            return null;
        }
        return new SpriteContents(
                id,
                new FrameSize(original.width(), original.height()),
                FilledTextureProcessor.fillTransparentPixels(original.getOriginalImage()),
                original.metadata()
        );
    }

    private static ResourceLocation filledId(final ResourceLocation resourceId) {
        return ResourceLocation.fromNamespaceAndPath(resourceId.getNamespace(), resourceId.getPath() + "_filled");
    }
}
