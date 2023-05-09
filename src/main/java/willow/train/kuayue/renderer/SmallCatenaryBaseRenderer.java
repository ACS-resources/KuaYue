package willow.train.kuayue.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import willow.train.kuayue.Entity.CatenaryBaseEntity;
import willow.train.kuayue.Entity.SmallCatenaryBaseEntity;
import willow.train.kuayue.Main;

public class SmallCatenaryBaseRenderer extends ArrowRenderer<SmallCatenaryBaseEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID , "textures/entity/catenary_base_texture_small.png");

    public SmallCatenaryBaseRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(SmallCatenaryBaseEntity arrow) {
        return TEXTURE;
    }
}
