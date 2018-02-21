package main.core.util;

import java.util.ArrayList;

import main.core.model.pixel.RGBWPixel;

public abstract class LedArrayToList {

	// Return a 1D list that represent the led strip.
	public static ArrayList<RGBWPixel> convert(RGBWPixel[][] ledMatrix, int width, int height) {
		ArrayList<RGBWPixel> ledStrip = new ArrayList<>();

		// Matrix reading is fit to real led panel architecture.
		boolean leftToRight = true;
		for (int line = 0; line < height; line++) {
			for (int column = 0; column < width; column++) {
				if (leftToRight) {
					ledStrip.add(ledMatrix[line][column]);
				} else {
					ledStrip.add(ledMatrix[line][width - column - 1]);
				}
			}
			leftToRight = !leftToRight;
		}
		return ledStrip;
	}
}
