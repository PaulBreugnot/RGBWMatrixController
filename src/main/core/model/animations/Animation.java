package main.core.model.animations;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import main.core.model.pixel.RGBWPixel;

public interface Animation {

	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight);

	public void setAnimationSettings(AnchorPane configAnchorPane) throws IOException;
}
