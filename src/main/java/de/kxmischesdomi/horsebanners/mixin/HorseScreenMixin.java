package de.kxmischesdomi.horsebanners.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import de.kxmischesdomi.horsebanners.HorseBanners;
import de.kxmischesdomi.horsebanners.common.horsebanners.Bannerable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Environment(EnvType.CLIENT)
@Mixin(HorseScreen.class)
public abstract class HorseScreenMixin extends HandledScreen<HorseScreenHandler> {

	private static final Identifier TEXTURE = new Identifier(HorseBanners.MOD_ID, "textures/gui/container/horse.png");

	@Shadow @Final private HorseBaseEntity entity;

	public HorseScreenMixin(HorseScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(method = "drawBackground", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
	public void onHasArmorSlot(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {

		if (this.entity instanceof Bannerable) {

			RenderSystem.setShaderTexture(0, TEXTURE);

			int i = (this.width - this.backgroundWidth) / 2;
			int j = (this.height - this.backgroundHeight) / 2;

			Bannerable entity = (Bannerable) this.entity;
			this.drawTexture(matrices, i + entity.getInvX(), j + 35 + entity.getInvY(), 54, this.backgroundHeight + 54, 18, 18);
		}
	}

}
