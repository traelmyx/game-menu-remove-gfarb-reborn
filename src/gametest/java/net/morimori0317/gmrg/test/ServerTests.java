package net.morimori0317.gmrg.test;

import net.minecraft.gametest.framework.GameTestHelper;

public final class ServerTests {
    private ServerTests() {
    }

    // The mod is client only. Reaching a GameTest at all means a dedicated server with the jar installed started cleanly.
    public static void dedicatedServerStarts(GameTestHelper helper) {
        helper.assertTrue(helper.getLevel().getServer().isDedicatedServer(), "not a dedicated server");
        helper.succeed();
    }
}
