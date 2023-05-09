package willow.train.kuayue.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import willow.train.kuayue.Entity.CatenaryBaseEntity;
import willow.train.kuayue.Main;

public class CatenaryBaseRenderer extends ArrowRenderer<CatenaryBaseEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID , "textures/entity/catenary_base_texture.png");

    public CatenaryBaseRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(CatenaryBaseEntity arrow) {
        return TEXTURE;
    }
}
