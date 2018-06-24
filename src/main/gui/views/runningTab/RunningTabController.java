package main.gui.views.runningTab;

import javafx.fxml.FXML;
import main.core.model.panel.LedPanel;
import main.gui.views.connectionInterface.usb.UsbConnectionModule;
import main.gui.views.ledMatrix.LedMatrix;
import main.gui.views.mainView.MainViewController;
import main.gui.views.playBar.PlayBar;
import main.gui.views.sizeSpinners.SizeSpinners;

public class RunningTabController {
	@FXML
	private LedMatrix ledMatrix;
	
	@FXML
	private SizeSpinners sizeSpinners;
	
	@FXML
	private UsbConnectionModule connectionModule;
	
	@FXML
	private PlayBar playBar;
	
	private LedPanel ledPanel;
	private MainViewController mainViewController;
	
	
	public void setLedPanel(LedPanel ledPanel) {
		this.ledPanel = ledPanel;
		initTilePane();
	}
	
	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
		initSizeSpinners();
	}
	
	public void setSizeSpinners(SizeSpinners sizeSpinners) {
		this.sizeSpinners = sizeSpinners;
	}
	
	public void initRunningTab() {
		initConnectionModule();
		initPlayBar();
	}
	
	private void initConnectionModule() {
		connectionModule.getController().setLedPanel(ledPanel);
		connectionModule.getController().initComPort();
	}
	
	private void initPlayBar() {
		playBar.getController().setLedPanel(ledPanel);
		playBar.getController().setLedMatrix(ledMatrix);
	}

	private void initTilePane() {
		ledMatrix.getController().setLedPanel(ledPanel);
		ledMatrix.getController().initMatrix();
	}

	public void initSizeSpinners() {
		sizeSpinners.getController().setMainViewController(mainViewController);
		sizeSpinners.getController().setLedMatrix(ledMatrix);
		sizeSpinners.getController().initSizeSpinners();
	}
}
