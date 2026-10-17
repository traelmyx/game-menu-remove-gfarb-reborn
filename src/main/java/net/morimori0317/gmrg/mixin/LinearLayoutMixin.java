package net.morimori0317.gmrg.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.morimori0317.gmrg.GameMenuRemoveGFARB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Since 1.21.11 the feedback buttons are icons in a row under Advancements and Statistics. */
@Mixin(LinearLayout.class)
public abstract class LinearLayoutMixin {
    @Inject(method = "addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;", at = @At("HEAD"), cancellable = true)
    private <T extends LayoutElement> void gmrg$skipFeedbackIcons(T child, CallbackInfoReturnable<T> cir) {
        if (!GameMenuRemoveGFARB.hideOnly && child instanceof AbstractWidget widget && GameMenuRemoveGFARB.isRemoved(widget)) {
            // the rest of the row stays centred
            cir.setReturnValue(child);
        }
    }
}
