package main.core.model.animations;

import main.core.model.pixel.RGBWPixel;

public interface Animation {

	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight);
}
