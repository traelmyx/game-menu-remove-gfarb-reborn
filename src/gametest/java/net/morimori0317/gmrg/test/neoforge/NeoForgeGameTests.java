package net.morimori0317.gmrg.test.neoforge;

//? if neoforge {
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.morimori0317.gmrg.test.ClientSmoke;
import net.morimori0317.gmrg.test.ServerTests;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(NeoForgeGameTests.MOD_ID)
@PrefixGameTestTemplate(false)
@Mod(NeoForgeGameTests.MOD_ID)
public class NeoForgeGameTests {
    public static final String MOD_ID = "gamemenuremovegfarb_gametest";

    public NeoForgeGameTests(Dist dist) {
        if (dist.isClient() && ClientSmoke.start()) {
            NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post e) -> ClientSmoke.tick(net.minecraft.client.Minecraft.getInstance()));
        }
    }

    @GameTest(template = "empty")
    public static void dedicatedServerStarts(GameTestHelper helper) {
        ServerTests.dedicatedServerStarts(helper);
    }
}
//?}
