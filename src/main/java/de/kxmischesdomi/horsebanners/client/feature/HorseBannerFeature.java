package de.kxmischesdomi.horsebanners.client.feature;

import com.mojang.datafixers.util.Pair;
import de.kxmischesdomi.horsebanners.common.horsebanners.Bannerable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec3f;

import java.util.List;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Environment(EnvType.CLIENT)
public class HorseBannerFeature extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {

	private final ModelPart bannerPart;

	public HorseBannerFeature(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> context) {
		super(context);
		this.bannerPart = MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, HorseEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity instanceof Bannerable) {
			Bannerable bannerable = (Bannerable) entity;
			ItemStack itemStack = bannerable.getBannerItem();
			if (itemStack == null || !(itemStack.getItem() instanceof BannerItem)) return;
			List<Pair<BannerPattern, DyeColor>> bannerPatterns = BannerBlockEntity.getPatternsFromNbt(((BannerItem)itemStack.getItem()).getColor(), BannerBlockEntity.getPatternListTag(itemStack));

			matrices.scale(0.45f, 0.45f, 0.45f);

			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));

			matrices.translate(-0.07, 0.35, 0.9);

			BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, this.bannerPart, ModelLoader.BANNER_BASE, true, bannerPatterns);

			matrices.translate(0, 0, -1.6);

			BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, this.bannerPart, ModelLoader.BANNER_BASE, true, bannerPatterns);

		}
	}

}
