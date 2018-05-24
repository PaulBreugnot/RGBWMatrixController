package main.gui.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.core.model.animations.bouncingBalls.BouncingBalls;
import main.core.model.panel.LedPanel;
import main.gui.views.MainViewController;
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
		TabPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MatrixControllerApp.class.getResource("/main/gui/views/MainView.fxml"));
		root = (TabPane) loader.load();
		mainViewController = loader.getController();
		ledPanel = MainTest.getAppConfig();
		mainViewController.setLedPanel(ledPanel);

		ledPanel.setCurrentAnimation(new BouncingBalls(ledPanel.getLedMatrix()));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Min'Bot 2018 - RGBWMatrixController");
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/img/LogoMinBot.jpg")));
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
