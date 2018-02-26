package test;

import main.core.model.panel.LedPanel;
import main.gui.app.MatrixControllerApp;

public class MainTest {

	public static void main(String[] args) {
		for (int i = 0; i < 256; i++) {
			System.out.println((byte) i);
		}

		MatrixControllerApp app = new MatrixControllerApp();
		app.launchApp();
	}

	public static LedPanel getAppConfig() {
		LedPanel ledPanel = new LedPanel("COM1", 7);
		LedPanel.setBlackPanel(ledPanel.getLedMatrix());
		// ledPanel.setCurrentAnimation(new TestAnimation());
		// ledPanel.setCurrentAnimation(RandomPop.classicalRGBColorPop(512, 1, 0));
		// ledPanel.setCurrentAnimation(RandomPop.fullRangeColorPop(350, 1, 0));
		// ledPanel.setCurrentAnimation(new PixelRain(PixelRain.Source.BOTTOM, 0, 0.1,
		// 1, 0));
		// ledPanel.setCurrentAnimation(new TextDisplay("Test", 0.0,
		// Font.loadFont("/main/core/model/animations/text/fonts/Pixeled.ttf", 9), 0));

		// ledPanel.setCurrentAnimation(
		// new ScrollingText("MIN'PERIUM", 0.0,
		// Font.loadFont("file:fonts/8-BITWONDER.TTF", 14), 0));
		return ledPanel;
	}
}
