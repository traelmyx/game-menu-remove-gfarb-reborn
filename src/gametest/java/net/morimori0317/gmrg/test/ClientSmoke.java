package net.morimori0317.gmrg.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.GameType;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.protocol.common.ClientboundServerLinksPacket;
import net.minecraft.server.ServerLinks;
import java.net.URI;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.morimori0317.gmrg.GameMenuRemoveGFARB;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.Files;

// Run with -Dsmoketest=true: creates a flat world, takes a screenshot and quits. Exit code 1 on failure.
public final class ClientSmoke {

    public static final boolean ENABLED = Boolean.getBoolean("smoketest");
    private static final long TIMEOUT_MS = 5 * 60 * 1000;

    private static int stage;
    private static int ticks;

    private ClientSmoke() {
    }

    public static boolean start() {
        if (!ENABLED) {
            return false;
        }
        Thread watchdog = new Thread(() -> {
            try {
                Thread.sleep(TIMEOUT_MS);
                fail("timed out");
            } catch (InterruptedException ignored) {
            }
        }, "smoketest-watchdog");
        watchdog.setDaemon(true);
        watchdog.start();
        return true;
    }

    public static void tick(Minecraft mc) {
        try {
            switch (stage) {
                case 0 -> {
                    Screen screen = SmokeCompat.screen(mc);
                    if (screen != null && SmokeCompat.overlayHidden(mc) && ++ticks >= 20) {
                        if (screen.getClass().getSimpleName().equals("LoadingErrorScreen")) {
                            fail("mod loading errors");
                        }
                        ticks = 0;
                        mc.options.tutorialStep = net.minecraft.client.tutorial.TutorialSteps.NONE;
                        SmokeCompat.createFlatWorld(mc, GameType.SURVIVAL);
                        stage = 1;
                    }
                }
                case 1 -> {
                    if (mc.level != null && mc.player != null && SmokeCompat.screen(mc) == null) {
                        stage = 2;
                    }
                }
                case 2 -> {
                    ticks++;
                    if (ticks == 40) {
                        SmokeCompat.setScreen(mc, new PauseScreen(true));
                    }
                    if (ticks == 80) {
                        SmokeCompat.screenshot(mc, "pause-menu.png");
                    }
                    if (ticks == 100) {
                        List<String> keys = visibleKeys(mc);
                        System.out.println("[clientsmoke] visible pause buttons: " + keys);
                        if (keys.contains("menu.sendFeedback") || keys.contains("menu.reportBugs") || keys.contains("menu.feedback")) {
                            fail("feedback buttons still visible: " + keys);
                        }
                        if (!keys.contains("menu.options") || !keys.contains("menu.returnToGame")) {
                            fail("pause menu lost its other buttons: " + keys);
                        }
                        if (GameMenuRemoveGFARB.hideOnly && keys.stream().noneMatch(k -> k.startsWith("modmenu"))) {
                            fail("Mod Menu's button is missing: " + keys);
                        }
                        // now act like a server that sends links: vanilla swaps the feedback pair for "Feedback..." + "Server Links"
                        var server = mc.getSingleplayerServer();
                        server.execute(() -> server.getPlayerList().getPlayers().get(0).connection.send(new ClientboundServerLinksPacket(
                            new ServerLinks(List.of(ServerLinks.Entry.knownType(ServerLinks.KnownLinkType.BUG_REPORT, URI.create("https://example.com/bugs")))).untrust())));
                    }
                    if (ticks == 130) {
                        SmokeCompat.setScreen(mc, new PauseScreen(true));
                    }
                    if (ticks == 160) {
                        SmokeCompat.screenshot(mc, "pause-menu-server-links.png");
                    }
                    if (ticks >= 170) {
                        List<String> keys = visibleKeys(mc);
                        System.out.println("[clientsmoke] visible pause buttons with server links: " + keys);
                        if (keys.contains("menu.feedback") || !keys.contains("menu.server_links")) {
                            fail("server links layout wrong: " + keys);
                        }
                        for (var child : GameMenuRemoveGFARB.hideOnly ? List.<GuiEventListener>of() : SmokeCompat.screen(mc).children()) {
                            if (child instanceof AbstractWidget w && w.getMessage().getContents() instanceof TranslatableContents t && t.getKey().equals("menu.server_links") && w.getWidth() != 204) {
                                fail("Server Links button is " + w.getWidth() + " wide");
                            }
                        }
                        Files.writeString(mc.gameDirectory.toPath().resolve("smoke-ok"), "ok\n");
                        stage = 3;
                        mc.stop();
                    }
                }
                default -> {
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.toString());
        }
    }

    private static List<String> visibleKeys(Minecraft mc) {
        List<String> keys = new ArrayList<>();
        if (!(SmokeCompat.screen(mc) instanceof PauseScreen pause)) {
            fail("pause screen not open: " + SmokeCompat.screen(mc));
            return keys;
        }
        for (var child : pause.children()) {
            if (child instanceof AbstractWidget w && w.visible && w.getMessage().getContents() instanceof TranslatableContents t) {
                keys.add(t.getKey());
            }
        }
        return keys;
    }

    public static void fail(String why) {
        System.err.println("Smoke test failed: " + why);
        System.out.println("[clientsmoke] FAILED: " + why);
        System.err.flush();
        Runtime.getRuntime().halt(1);
    }
}
