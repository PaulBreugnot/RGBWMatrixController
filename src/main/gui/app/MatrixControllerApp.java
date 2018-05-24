package main.gui.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.core.model.animations.bouncingBalls.BouncingBalls;
import main.core.model.panel.LedPanel;
import main.gui.customTabPane.CustomTabPane;
import main.gui.views.mainView.MainViewController;
import test.MainTest;

public class MatrixControllerApp extends Application {

	private LedPanel ledPanel;

	private MainViewController mainViewController;

	public MatrixControllerApp() {
	}

	public void setLedPanel(LedPanel ledPanel) {
		this.ledPanel = ledPanel;
	}

	public void launchApp() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		CustomTabPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MatrixControllerApp.class.getResource("/main/gui/views/mainView/MainView.fxml"));
		root = (CustomTabPane) loader.load();
		mainViewController = loader.getController();
		ledPanel = MainTest.getAppConfig();
		mainViewController.setLedPanel(ledPanel);

		ledPanel.setCurrentAnimation(new BouncingBalls(ledPanel.getLedMatrix()));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Min'Bot 2018 - PixLed Controller");
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/img/LogoMinBot.jpg")));
		primaryStage.setMinWidth(1200);
		primaryStage.setMinHeight(800);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setWidth(primaryStage.getMinWidth());
		primaryStage.setHeight(primaryStage.getMinHeight());
	}

}
