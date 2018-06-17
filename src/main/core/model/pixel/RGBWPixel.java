package main.core.model.pixel;

import javafx.scene.paint.Color;
import main.core.model.panel.LedPanel;

public class RGBWPixel {

	public enum ColorType {
		RGB, HSB
	};

	public static final RGBWPixel BLACK_PIXEL = new RGBWPixel(0, 0, 0, 0);
	private Color color;
	private int red;
	private int green;
	private int blue;
	private int white;
	
	public RGBWPixel(Color color) {
		this(color, 0);
	}

	public RGBWPixel(Color color, int white) {
		this.color = color;
		this.red = (int) (color.getRed() * 255);
		this.green = (int) (color.getGreen() * 255);
		this.blue = (int) (color.getBlue() * 255);
		this.white = white;
	}

	private RGBWPixel(int red, int green, int blue, int white) {
		color = Color.rgb(red, green, blue);
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.white = white;
	}

	private RGBWPixel(double hue, double saturation, double brightness, int white) {
		color = Color.hsb(hue, saturation, brightness);
		this.white = white;
		this.red = (int) (color.getRed() * 255);
		this.green = (int) (color.getGreen() * 255);
		this.blue = (int) (color.getBlue() * 255);
	}

	public static RGBWPixel rgbwPixel(int red, int green, int blue, int white) {
		return new RGBWPixel(red, green, blue, white);
	}

	public static RGBWPixel hsbwPixel(double hue, double saturation, double brightness, int white) {
		return new RGBWPixel(hue, saturation, brightness, white);
	}

	public static RGBWPixel rgbPixel(int red, int green, int blue) {
		return rgbwPixel(red, green, blue, 0);
	}

	public static RGBWPixel hsbPixel(double hue, double saturation, double brightness) {
		return hsbwPixel(hue, saturation, brightness, 0);
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
		color = Color.rgb(red, green, blue);
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
		color = Color.rgb(red, green, blue);
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
		color = Color.rgb(red, green, blue);
	}

	public double getHue() {
		return color.getHue();
	}

	public void setHue(double hue) {
		color = Color.hsb(hue, color.getSaturation(), color.getBrightness());
	}

	public double getSaturation() {
		return color.getSaturation();
	}

	public void setSaturation(double saturation) {
		color = Color.hsb(color.getHue(), saturation, color.getBrightness());
	}

	public double getBrightness() {
		return color.getBrightness();
	}

	public void setBrightness(double brightness) {
		color = Color.hsb(color.getHue(), color.getSaturation(), brightness);
	}

	public int getWhite() {
		return white;
	}

	public void setWhite(int white) {
		this.white = white;
	}

	public Color getColor() {
		return color;
	}

	public Color getDisplayColor() {
		Color displayColor = Color.hsb(color.getHue(), color.getSaturation(),
				Math.min(1.0, color.getBrightness() / LedPanel.MAX_INTENSITY));
		return displayColor;
	}
}
