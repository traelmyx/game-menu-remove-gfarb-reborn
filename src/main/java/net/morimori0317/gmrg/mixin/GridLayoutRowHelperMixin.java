package net.morimori0317.gmrg.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.morimori0317.gmrg.GameMenuRemoveGFARB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GridLayout.RowHelper.class)
public abstract class GridLayoutRowHelperMixin {

    @Shadow
    public abstract <T extends LayoutElement> T addChild(T child, int occupiedColumns, LayoutSettings layoutSettings);

    @Inject(method = "addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;ILnet/minecraft/client/gui/layouts/LayoutSettings;)Lnet/minecraft/client/gui/layouts/LayoutElement;", at = @At("HEAD"), cancellable = true)
    private <T extends LayoutElement> void gmrg$skipFeedbackButtons(T child, int occupiedColumns, LayoutSettings layoutSettings, CallbackInfoReturnable<T> cir) {
        if (GameMenuRemoveGFARB.hideOnly || !(child instanceof Button button)) {
            return;
        }
        if (GameMenuRemoveGFARB.isRemoved(button)) {
            // never added to the grid, so it is neither placed nor rendered
            cir.setReturnValue(child);
        } else if (GameMenuRemoveGFARB.isServerLinks(button) && occupiedColumns == 1) {
            // its neighbour ("Feedback...") is gone, so let it take the whole row
            button.setWidth(204);
            cir.setReturnValue(this.addChild(child, 2, layoutSettings));
        }
    }
}
