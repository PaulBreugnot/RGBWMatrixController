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

	private String textToDisplay = "Min'Bot 2K18";
	private Color textColor = Color.hsb(0, 1, LedPanel.MAX_INTENSITY);
	private Color backgroundColor = Color.hsb(0, 0, 0.01 * LedPanel.MAX_INTENSITY);
	private Font font = Font.getDefault();
	private int whiteLevel;
	private RGBWPixel[][] textArray;
	private int textImageLength;
	private int speed = 1;

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

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setWhiteLevel(int whiteLevel) {
		this.whiteLevel = whiteLevel;
	}

	public int getWhiteLevel() {
		return whiteLevel;
	}

	public void setTextToDisplay(String textToDisplay) {
		this.textToDisplay = textToDisplay;
	}

	public String getTextToDisplay() {
		return textToDisplay;
	}

	public Font getFont() {
		return font;
	}

	public void setTextArray() {
		if (textToDisplay != null) {
			Image textImage = ImageGenerator.convertText(textToDisplay, font, textColor);
			int imageHeight = (int) textImage.getHeight();
			int imageWidth = (int) textImage.getWidth();
			textImageLength = imageWidth;
			textArray = new RGBWPixel[imageHeight][imageWidth];
			setBackground(imageHeight, imageWidth);
			PixelReader pixelReader = textImage.getPixelReader();
			for (int line = 0; line < imageHeight; line++) {
				for (int column = 0; column < imageWidth; column++) {
					textArray[line][column] = treatedPixel(line, column,
							pixelReader.getColor(column, imageHeight - line - 1));
				}
			}
		}
	}

	public void setBackground(int height, int width) {
		for (int line = 0; line < height; line++) {
			for (int column = 0; column < width; column++) {
				textArray[line][column] = new RGBWPixel(backgroundColor, whiteLevel);
			}
		}
	}

	public RGBWPixel treatedPixel(int line, int column, Color readColor) {
		if (readColor.getBrightness() == 0 && readColor.getSaturation() == 0) {
			return textArray[line][column]; // background color
		} else {
			return new RGBWPixel(textColor, whiteLevel);
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
