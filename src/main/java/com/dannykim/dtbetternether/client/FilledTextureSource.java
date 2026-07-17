package com.dannykim.dtbetternether.client;

import com.google.common.collect.BiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraftforge.client.textures.ForgeTextureMetadata;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

public final class FilledTextureSource implements SpriteSource {
    public static final ResourceLocation ID = new ResourceLocation("dtbetternether", "filled_texture");
    public static final Codec<FilledTextureSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("resource").forGetter(source -> source.resourceId)
    ).apply(instance, FilledTextureSource::new));

    private static final FilledTextureSpriteLoader LOADER = new FilledTextureSpriteLoader();
    private static SpriteSourceType TYPE;

    private final ResourceLocation resourceId;

    public FilledTextureSource(final ResourceLocation resourceId) {
        this.resourceId = resourceId;
    }

    public static synchronized void register() {
        if (TYPE != null) {
            return;
        }
        try {
            final Field field = findSpriteSourceTypesField();
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            final BiMap<ResourceLocation, SpriteSourceType> types =
                    (BiMap<ResourceLocation, SpriteSourceType>) field.get(null);
            TYPE = types.get(ID);
            if (TYPE == null) {
                TYPE = new SpriteSourceType(CODEC);
                types.put(ID, TYPE);
            }
        } catch (final ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to register Dynamic Trees BetterNether filled texture source.", exception);
        }
    }

    private static Field findSpriteSourceTypesField() throws NoSuchFieldException {
        for (final Field field : SpriteSources.class.getDeclaredFields()) {
            if (BiMap.class.isAssignableFrom(field.getType())) {
                return field;
            }
        }
        throw new NoSuchFieldException("SpriteSources BiMap");
    }

    @Override
    public void run(final ResourceManager resourceManager, final Output output) {
        final ResourceLocation textureId = TEXTURE_ID_CONVERTER.idToFile(this.resourceId);
        final Optional<Resource> resource = resourceManager.getResource(textureId);
        resource.ifPresent(value -> output.add(filledId(this.resourceId), wrap(value)));
    }

    @Override
    public SpriteSourceType type() {
        register();
        return TYPE;
    }

    private static ResourceLocation filledId(final ResourceLocation resourceId) {
        return new ResourceLocation(resourceId.getNamespace(), resourceId.getPath() + "_filled");
    }

    private static Resource wrap(final Resource original) {
        return new Resource(original.source(), original::open, () -> new FilledTextureMetadata(original));
    }

    private static final class FilledTextureMetadata implements ResourceMetadata {
        private final Resource original;

        private FilledTextureMetadata(final Resource original) {
            this.original = original;
        }

        @Override
        public <T> Optional<T> getSection(final MetadataSectionSerializer<T> serializer) {
            if (serializer == ForgeTextureMetadata.SERIALIZER) {
                return Optional.of((T) new ForgeTextureMetadata(LOADER));
            }
            try {
                return this.original.metadata().getSection(serializer);
            } catch (final IOException exception) {
                return Optional.empty();
            }
        }
    }
}
