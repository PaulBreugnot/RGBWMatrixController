package main.core.model.animations.text;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.core.model.panel.LedPanel;

public abstract class ImageGenerator {

	public static Image convertText(String textToConvert, Font font, Color color) {
		Canvas canvas = new Canvas(textToConvert.length() * (int) font.getSize(), LedPanel.MATRIX_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, textToConvert.length() * (int) font.getSize(), LedPanel.MATRIX_HEIGHT);
		gc.setFill(color);
		gc.setFont(font);
		gc.fillText(textToConvert, 2, LedPanel.MATRIX_HEIGHT - 2);

		WritableImage image = new WritableImage(textToConvert.length() * (int) font.getSize(), LedPanel.MATRIX_HEIGHT);
		canvas.snapshot(null, image);
		// Image truncatedImage = TruncateImage(image, backGround);

		return image;
	}

	private static Image TruncateImage(WritableImage image, Color backGround) {
		PixelReader pixelReader = image.getPixelReader();
		int imageWidth = (int) image.getWidth();
		int imageHeight = (int) image.getHeight();
		boolean blackColumn = true;
		int column = imageWidth - 1;
		while (column >= 0 && blackColumn) {
			int line = 0;
			while (line < imageHeight && blackColumn) {
				System.out.println(pixelReader.getColor(column, line));
				System.out.println(backGround);
				System.out.println(pixelReader.getColor(column, line).equals(backGround));
				if (!pixelReader.getColor(column, line).equals(backGround)) {
					blackColumn = false;
				}
				line++;
			}
			column--;
		}
		column += 2;

		column = Math.max(32, column);
		WritableImage truncatedImage = new WritableImage(column + 5, imageHeight);
		for (int x = 0; x < column; x++) {
			for (int y = 0; y < imageHeight; y++) {
				truncatedImage.getPixelWriter().setColor(x, y, image.getPixelReader().getColor(x, y));
			}
		}
		for (int x = column; x < column + 5; x++) {
			for (int y = 0; y < imageHeight; y++) {
				truncatedImage.getPixelWriter().setColor(x, y, backGround);
			}
		}
		return truncatedImage;
	}
}
