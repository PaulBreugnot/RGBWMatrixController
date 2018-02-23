package main.core.model.animations.text;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.core.model.animations.Animation;
import main.core.model.pixel.RGBWPixel;

public class TextDisplay implements Animation {

	private String textToDisplay;
	private double hueTextColor;
	private Color backgroundColor;
	private Font font;
	private int whiteLevel;
	private RGBWPixel[][] textArray;
	private int textImageLength;

	private int offset = 0;

	public TextDisplay(String textToDisplay, double hueTextColor, Font font, int whiteLevel) {
		this.textToDisplay = textToDisplay;
		this.hueTextColor = hueTextColor;
		this.font = font;
		initTextArray();
	}

	private void initTextArray() {
		Image textImage = FromTextToImage.convertText(textToDisplay, font, Color.hsb(hueTextColor, 1, 1), Color.GREEN);
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

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		readTextArray(ledMatrix);
	}

	private void readTextArray(RGBWPixel[][] ledMatrix) {
		for (int line = 0; line < 16; line++) {
			for (int column = 0; column < 32; column++) {
				System.out.println(offset % textImageLength);
				ledMatrix[line][column] = textArray[line][(column + offset) % textImageLength];
			}
		}
		offset++;
	}
}
