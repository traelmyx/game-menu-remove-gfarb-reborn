package net.morimori0317.gmrg;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.Set;

public class GameMenuRemoveGFARB {
    public static final String MODID = "gamemenuremovegfarb";

    /** "Give Feedback", "Report Bugs", and the "Feedback..." button that replaces them when a server sends links. */
    private static final Set<String> REMOVED = Set.of("menu.sendFeedback", "menu.reportBugs", "menu.feedback");
    private static final String SERVER_LINKS = "menu.server_links";

    /**
     * Set when Mod Menu is installed: it anchors its Mods button to the feedback buttons, so they stay in the layout
     * and are only hidden.
     */
    public static boolean hideOnly;

    public static boolean isRemoved(AbstractWidget widget) {
        return hasKey(widget, REMOVED);
    }

    public static boolean isServerLinks(AbstractWidget widget) {
        return hasKey(widget, Set.of(SERVER_LINKS));
    }

    private static boolean hasKey(AbstractWidget widget, Set<String> keys) {
        return widget.getMessage().getContents() instanceof TranslatableContents translatable && keys.contains(translatable.getKey());
    }
}
