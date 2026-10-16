package net.morimori0317.gmrg.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.morimori0317.gmrg.GameMenuRemoveGFARB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void gmrg$hideFeedbackButtons(CallbackInfo ci) {
        if (!GameMenuRemoveGFARB.hideOnly) {
            return;
        }
        for (GuiEventListener child : ((PauseScreen) (Object) this).children()) {
            if (child instanceof AbstractWidget widget && GameMenuRemoveGFARB.isRemoved(widget)) {
                widget.visible = false;
                widget.active = false;
            }
        }
    }
}
