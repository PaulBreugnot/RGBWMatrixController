package main.core.model.animations.text;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.gui.views.settings.ScrollingTextSettingsController;

public class ScrollingText implements Animation {

	public static final String effectName = "Text Scrolling";

	private String textToDisplay = "Min Bot 2K18";
	private Color textColor = Color.BLUE;
	private Color backgroundColor = Color.YELLOW;
	private Font font = Font.loadFont("file:fonts/8-BITWONDER.TTF", 9);
	private int whiteLevel;
	private RGBWPixel[][] textArray;
	private int textImageLength;
	private int speed;

	private int offset = 0;

	public ScrollingText() {
		setTextArray();
	}

	public ScrollingText(String textToDisplay, Color textColor, Font font, int whiteLevel) {
		this.textToDisplay = textToDisplay;
		this.textColor = textColor;
		this.font = font;
		setTextArray();
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setTextToDisplay(String textToDisplay) {
		this.textToDisplay = textToDisplay;
	}

	public void setTextArray() {
		if (textToDisplay != null) {
			Image textImage = FromTextToImage.convertText(textToDisplay, font, textColor, backgroundColor);
			int imageHeight = (int) textImage.getHeight();
			int imageWidth = (int) textImage.getWidth();
			textImageLength = imageWidth;
			textArray = new RGBWPixel[imageHeight][imageWidth];
			PixelReader pixelReader = textImage.getPixelReader();
			for (int line = 0; line < imageHeight; line++) {
				for (int column = 0; column < imageWidth; column++) {
					textArray[line][column] = new RGBWPixel(pixelReader.getColor(column, imageHeight - line - 1),
							whiteLevel);
				}
			}
		}
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		readTextArray(ledMatrix);
	}

	private void readTextArray(RGBWPixel[][] ledMatrix) {
		for (int line = 0; line < 16; line++) {
			for (int column = 0; column < 32; column++) {
				ledMatrix[line][column] = textArray[line][(column + offset) % textImageLength];
			}
		}
		offset += speed;
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/settings/ScrollingTextSettings.fxml"));
		configAnchorPane.getChildren().add(loader.load());
		ScrollingTextSettingsController scrollingTextSettingsController = loader.getController();
		scrollingTextSettingsController.setScrollingText((ScrollingText) ledPanel.getCurrentAnimation());

	}

	@Override
	public String toString() {
		return effectName;
	}
}
