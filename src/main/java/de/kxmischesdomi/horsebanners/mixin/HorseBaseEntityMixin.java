package de.kxmischesdomi.horsebanners.mixin;

import de.kxmischesdomi.horsebanners.common.horsebanners.Bannerable;
import net.minecraft.entity.passive.HorseBaseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Mixin(HorseBaseEntity.class)
public abstract class HorseBaseEntityMixin {

	@Inject(method = "getInventorySize", at = @At(value = "HEAD"), cancellable = true)
	private void getInventorySize(CallbackInfoReturnable<Integer> cir) {

		if ((HorseBaseEntity) (Object) this instanceof Bannerable) {
			cir.setReturnValue(((Bannerable) this).getNewInventorySize());
		}

	}

}
