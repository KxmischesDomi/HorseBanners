package de.kxmischesdomi.horsebanners.mixin;

import de.kxmischesdomi.horsebanners.client.feature.HorseBannerFeature;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.HorseBaseEntityRenderer;
import net.minecraft.client.render.entity.HorseEntityRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Environment(EnvType.CLIENT)
@Mixin(HorseEntityRenderer.class)
public abstract class HorseEntityRendererMixin extends HorseBaseEntityRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {

	public HorseEntityRendererMixin(Context ctx, HorseEntityModel<HorseEntity> model, float scale) {
		super(ctx, model, scale);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void render(Context context, CallbackInfo ci) {
		addFeature(new HorseBannerFeature(this));
	}

}
