package main.core.util.color;

public class ShadedColorMap extends ColorMap {
	
	public ShadedColorMap( double hue, double hueWidth, double saturation, double satWidth, double brightness, double brightWidth) {
		super(Math.max(0, hue - hueWidth / 2), Math.min(360, hue + hueWidth / 2),
				Math.max(0, saturation - satWidth / 2), Math.min(1, saturation + satWidth / 2),
				Math.max(0, brightness - brightWidth / 2), Math.min(1, brightness + brightWidth / 2));
	}
	
}
