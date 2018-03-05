package main.gui.views.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.core.model.animations.text.ScrollingText;

public class ScrollingTextSettingsController {

	@FXML
	private TextField TextToDisplayField;
	@FXML
	private Slider TextHueSlider;
	@FXML
	private Slider TextSaturationSlider;
	@FXML
	private Slider TextBrightnessSlider;
	@FXML
	private Slider whiteSlider;
	@FXML
	private Slider BackgroundHueSlider;
	@FXML
	private Slider BackgroundSaturationSlider;
	@FXML
	private Slider BackgroundBrightnessSlider;
	@FXML
	private ComboBox<String> DefaultFontsComboBox;
	@FXML
	private ComboBox<String> EmbeddedFontsComboBox;
	@FXML
	private Slider SizeSlider;
	@FXML
	private Slider SpeedSlider;

	private ScrollingText scrollingText;
	private String[] embeddedFonts = { "8-BITWONDER", "04B_30__", "Perfect DOS VGA 437 Win", "Minecraft", "I-pixel-u",
			"pixelart" };

	private ObservableList<String> ListDefaultFonts = FXCollections.observableArrayList();
	private ObservableList<String> ListEmbeddedFonts = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		setTextSliders();
		setBackgroundSliders();
		setOtherSliders();
		setComboBoxes();
	}

	public void setScrollingText(ScrollingText scrollingText) {
		this.scrollingText = scrollingText;
		EmbeddedFontsComboBox.getSelectionModel().selectFirst();
	}

	private void setTextSliders() {
		TextHueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double saturation = scrollingText.getTextColor().getSaturation();
				double brightness = scrollingText.getTextColor().getBrightness();
				scrollingText.setTextColor(Color.hsb((double) new_val, saturation, brightness));
				scrollingText.setTextArray();
			}
		});
		TextSaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double hue = scrollingText.getTextColor().getHue();
				double brightness = scrollingText.getTextColor().getBrightness();
				scrollingText.setTextColor(Color.hsb(hue, (double) new_val, brightness));
				scrollingText.setTextArray();
			}
		});
		TextBrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double hue = scrollingText.getTextColor().getHue();
				double saturation = scrollingText.getTextColor().getSaturation();
				scrollingText.setTextColor(Color.hsb(hue, saturation, (double) new_val));
				scrollingText.setTextArray();
			}
		});
		
		whiteSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				scrollingText.setWhiteLevel((int) Math.floor((double) new_val));
				scrollingText.setTextArray();
			}
		});
	}

	private void setBackgroundSliders() {
		BackgroundHueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double saturation = scrollingText.getTextColor().getSaturation();
				double brightness = scrollingText.getTextColor().getBrightness();
				scrollingText.setBackgroundColor(Color.hsb((double) new_val, saturation, brightness));
				scrollingText.setTextArray();
			}
		});
		BackgroundSaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double hue = scrollingText.getTextColor().getHue();
				double brightness = scrollingText.getTextColor().getBrightness();
				scrollingText.setBackgroundColor(Color.hsb(hue, (double) new_val, brightness));
				scrollingText.setTextArray();
			}
		});
		BackgroundBrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double hue = scrollingText.getTextColor().getHue();
				double saturation = scrollingText.getTextColor().getSaturation();
				scrollingText.setBackgroundColor(Color.hsb(hue, saturation, (double) new_val));
				scrollingText.setTextArray();
			}
		});
	}

	private void setOtherSliders() {

		SpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				scrollingText.setSpeed((int) Math.floor((double) new_val));
			}
		});

		SizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (DefaultFontsComboBox.getSelectionModel().getSelectedItem() != null) {
					scrollingText.setFont(
							new Font(DefaultFontsComboBox.getSelectionModel().getSelectedItem(), (double) new_val));
					scrollingText.setTextArray();
				} else if (EmbeddedFontsComboBox.getSelectionModel().getSelectedItem() != null) {
					scrollingText.setFont(Font.loadFont(
							"file:fonts/" + EmbeddedFontsComboBox.getSelectionModel().getSelectedItem() + ".TTF",
							(double) new_val));
					scrollingText.setTextArray();
				}
			}
		});

	}

	private void setComboBoxes() {
		ListDefaultFonts.addAll(Font.getFontNames());
		DefaultFontsComboBox.setItems(ListDefaultFonts);
		ListEmbeddedFonts.addAll(embeddedFonts);
		EmbeddedFontsComboBox.setItems(ListEmbeddedFonts);

		DefaultFontsComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (t1 != null) {
					EmbeddedFontsComboBox.getSelectionModel().clearSelection();
					scrollingText.setFont(new Font(t1, SizeSlider.getValue()));
					scrollingText.setTextArray();
				}
			}
		});

		EmbeddedFontsComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (t1 != null) {
					DefaultFontsComboBox.getSelectionModel().clearSelection();
					scrollingText.setFont(Font.loadFont("file:fonts/" + t1 + ".TTF", SizeSlider.getValue()));
					scrollingText.setTextArray();
				}
			}
		});

	}

	@FXML
	private void handleGo() {
		scrollingText.setTextToDisplay(TextToDisplayField.getText());
		scrollingText.setTextArray();
	}
}
