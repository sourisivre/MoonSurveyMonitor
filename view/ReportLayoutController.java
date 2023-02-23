package survey.view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import survey.MainApp;
import survey.model.Moon;
import survey.model.SurveyResult;
import survey.util.MountReportData;

public class ReportLayoutController {

	MainApp mainApp;
	@FXML
	TextField volumeTextField;
	
	@FXML
	TableView<Moon> moonReportTableView;
	@FXML
	TableColumn<Moon, String> moonNameTableColumn;
	@FXML
	TableColumn<Moon, String> moonExtractionValueTableColumn;
	
	@FXML
	TableView<SurveyResult> detailedReportTableView;
	@FXML
	TableColumn<SurveyResult, String> mineralNameTableColumn;
	@FXML
	TableColumn<SurveyResult, String> quantityTableColumn;
	@FXML
	TableColumn<SurveyResult, String> valueTableColumn;
	
	ObservableList<Moon> moonReportObservableList = FXCollections.observableArrayList();
	ObservableList<SurveyResult> detailedMoonReportObservableList = FXCollections.observableArrayList();
	
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> appraisal = new ArrayList<String>();
	
	String extractionVolume;
	
	
	public ReportLayoutController() {
	}
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void populateMoonReportTableView() {
		moonNameTableColumn.setCellValueFactory(new PropertyValueFactory<Moon, String>("name"));
		moonExtractionValueTableColumn.setCellValueFactory(new PropertyValueFactory<Moon, String>("value"));
		moonExtractionValueTableColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		moonReportTableView.getItems().addAll(moonReportObservableList);
	}
	
	public void populateDetailedMoonReportTableView(String moonName) {
		mineralNameTableColumn.setCellValueFactory(new PropertyValueFactory<SurveyResult, String>("mineralName"));
		quantityTableColumn.setCellValueFactory(new PropertyValueFactory<SurveyResult, String>("quantity"));
		quantityTableColumn.setStyle("-fx-alignment: CENTER-RIGHT");
		valueTableColumn.setCellValueFactory(new PropertyValueFactory<SurveyResult, String>("value"));
		valueTableColumn.setStyle("-fx-alignment: CENTER-RIGHT");
		detailedReportTableView.getItems().addAll(detailedMoonReportObservableList);
	}
	
	@FXML
	public void setVolumeActionButton() {
		moonReportObservableList.clear();
		moonReportTableView.getItems().clear();
		moonReportTableView.refresh();
		this.data = mainApp.getData();
		this.appraisal = mainApp.getAppraisal();
		extractionVolume = volumeTextField.getText().toString();
		MountReportData rd = new MountReportData(extractionVolume, data, appraisal);
		moonReportObservableList = rd.getMoonReportObservableList();
		populateMoonReportTableView();
	}
	
	@FXML
	public void clickMoonReportTableView(MouseEvent event) {
		if(moonReportTableView.getSelectionModel().getSelectedItem()!=null) {
			detailedMoonReportObservableList.clear();
			detailedReportTableView.getItems().clear();
			detailedReportTableView.refresh();
			String moonName = moonReportTableView.getSelectionModel().getSelectedItem().getName();
			MountReportData rd = new MountReportData(extractionVolume, data, appraisal);
			detailedMoonReportObservableList = rd.getDetailedMoonReportObservableList(moonName);
			populateDetailedMoonReportTableView(moonName);
		}
	}
}
