package test;

import main.core.model.animations.fan.Fan;
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
		ledPanel.setCurrentAnimation(new Fan());

		return ledPanel;
	}
}
