package survey.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import survey.MainApp;

public class InfoLayoutController {

	MainApp mainApp;

	@FXML
	Button closeButton;
	
	public InfoLayoutController() {
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void closeActionButton() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
