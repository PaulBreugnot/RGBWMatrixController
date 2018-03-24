package test;

import main.core.model.animations.circularWave.CircularWave;
import main.core.model.animations.diamondWave.DiamondWave;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.panel.LedPanel;
import main.gui.app.MatrixControllerApp;

public class MainTest {

	public static void main(String[] args) {
		MatrixControllerApp app = new MatrixControllerApp();
		app.launchApp();
	}

	public static LedPanel getAppConfig() {
		LedPanel ledPanel = new LedPanel(25);
		LedPanel.setBlackPanel(ledPanel.getLedMatrix());

		LoopingAnimations loop = new LoopingAnimations();
		loop.addAnimation(0, new CircularWave());
		loop.addAnimation(500, new DiamondWave());
		loop.setEnd(1000);
		ledPanel.setCurrentAnimation(loop);

		return ledPanel;
	}
}
