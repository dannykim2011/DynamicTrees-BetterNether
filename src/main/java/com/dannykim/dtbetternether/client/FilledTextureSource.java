package com.dannykim.dtbetternether.client;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.client.event.RegisterSpriteSourcesEvent;

import java.util.Optional;

public final class FilledTextureSource implements SpriteSource {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("dtbetternether", "filled_texture");
    public static final MapCodec<FilledTextureSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("resource").forGetter(source -> source.resourceId)
    ).apply(instance, FilledTextureSource::new));

    private final Identifier resourceId;

    public FilledTextureSource(final Identifier resourceId) {
        this.resourceId = resourceId;
    }

    public static void register(final RegisterSpriteSourcesEvent event) {
        event.register(ID, CODEC);
    }

    @Override
    public void run(final ResourceManager resourceManager, final Output output) {
        final Identifier textureId = TEXTURE_ID_CONVERTER.idToFile(this.resourceId);
        final Optional<Resource> resource = resourceManager.getResource(textureId);
        resource.ifPresent(value -> output.add(filledId(this.resourceId), loader -> loadFilledSprite(loader, filledId(this.resourceId), value)));
    }

    @Override
    public MapCodec<FilledTextureSource> codec() {
        return CODEC;
    }

    private static SpriteContents loadFilledSprite(final SpriteResourceLoader loader, final Identifier id, final Resource resource) {
        final SpriteContents original = loader.loadSprite(id, resource);
        if (original == null) {
            return null;
        }
        return new SpriteContents(
                id,
                new FrameSize(original.width(), original.height()),
                FilledTextureProcessor.fillTransparentPixels(original.getOriginalImage())
        );
    }

    private static Identifier filledId(final Identifier resourceId) {
        return Identifier.fromNamespaceAndPath(resourceId.getNamespace(), resourceId.getPath() + "_filled");
    }
}
