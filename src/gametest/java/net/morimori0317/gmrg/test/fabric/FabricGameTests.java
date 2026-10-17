package net.morimori0317.gmrg.test.fabric;

//? if fabric {
/*import net.morimori0317.gmrg.test.ServerTests;
import net.minecraft.gametest.framework.GameTestHelper;
//? if >=1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/^import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
^///?}

//? if >=1.21.5 {
public class FabricGameTests {
	private static final String EMPTY = "gamemenuremovegfarb_gametest:empty";

	@GameTest(structure = EMPTY, maxTicks = 100)
	public void dedicatedServerStarts(GameTestHelper helper) {
		ServerTests.dedicatedServerStarts(helper);
	}
}
//?} else {
/^public class FabricGameTests implements FabricGameTest {
	private static final String EMPTY = "gamemenuremovegfarb_gametest:empty";

	@GameTest(template = EMPTY, timeoutTicks = 100)
	public void dedicatedServerStarts(GameTestHelper helper) {
		ServerTests.dedicatedServerStarts(helper);
	}
}
^///?}
*///?}
