package main.core.model.panel;

import main.core.model.pixel.RGBWPixel;

public class LedPanel {

	public static final int MATRIX_WIDTH = 32;
	public static final int MATRIX_HEIGHT = 16;

	private RGBWPixel[][] LedMatrix = new RGBWPixel[MATRIX_HEIGHT][MATRIX_WIDTH];
}
