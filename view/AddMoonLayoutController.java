package survey.view;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import survey.MainApp;

public class AddMoonLayoutController {

	MainApp mainApp;
	@FXML
	Button addButton;
	@FXML
	Button closeButton;
	@FXML
	TextField moonNameTextField;
	@FXML
	TextArea surveyResultTextArea;
	
	String moonNameString = new String();
	ObservableList<CharSequence> surveyResultCharSequence;
	
	
	public AddMoonLayoutController() {
	}
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void addActionButton() {
		this.moonNameString = "moon "+moonNameTextField.getText();
		this.surveyResultCharSequence = surveyResultTextArea.getParagraphs();

		ArrayList<String> temp = new ArrayList<String>();
		int nChar = surveyResultCharSequence.size();
		
		for(int i=0; i<nChar; i++) {
			if(surveyResultCharSequence.get(i).toString().isEmpty()) {
				//do nothing
			} else {
				temp.add(surveyResultCharSequence.get(i).toString());
			}
		}
		
		ArrayList<String> mineralAbundanceArray = new ArrayList<String>();
		
		for(int i=0; i<temp.size(); i++) {
			int marcFirstChar = temp.get(i).toString().indexOf("[");
			int marcSecondChar =temp.get(i).toString().indexOf("]");
			
			String mineralName = temp.get(i).toString().substring(0, marcFirstChar-1);
			String value = temp.get(i).toString().substring(marcFirstChar+1, marcSecondChar);
			
			mineralAbundanceArray.add(mineralName);
			mineralAbundanceArray.add(value);
		}
		mainApp.addMoonAction(moonNameString, mineralAbundanceArray);
		
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();	
	}
	
	public void cancelActionButton() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();				
	}
}
