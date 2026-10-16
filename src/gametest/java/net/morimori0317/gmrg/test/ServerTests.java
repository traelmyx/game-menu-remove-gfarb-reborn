package net.morimori0317.gmrg.test;

import net.minecraft.gametest.framework.GameTestHelper;

public final class ServerTests {
    private ServerTests() {
    }

    // The mod is client only. Reaching a GameTest at all means a server (no client classes) with the jar installed started cleanly.
    public static void dedicatedServerStarts(GameTestHelper helper) {
        helper.assertFalse(helper.getLevel().getServer().getClass().getSimpleName().equals("IntegratedServer"), "running inside a client");
        helper.succeed();
    }
}
