package survey.view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import survey.MainApp;
import survey.model.Moon;
import survey.model.SurveyResult;
import survey.util.DeleteMoonArraySequence;
import survey.util.RequestApraisal;

public class MainLayoutController {
	
	MainApp mainApp;
	String projectName, projectPath, searchMoon;
	
	@FXML
	private Label projectNameLabel;
	@FXML
	private Label moonNameLabel;
	
	@FXML
	private TableView<Moon> moonListTableView;
	@FXML
	private TableColumn<String, Moon> moonListTableColumn;
	
	@FXML
	private TableView<SurveyResult> detailsTableView;
	@FXML
	private TableColumn<String, SurveyResult> mineralTableColumn;
	@FXML
	private TableColumn<String, SurveyResult> abundanceTableColumn;
	
	ObservableList<Moon> moonObservableList = FXCollections.observableArrayList();
	
	ObservableList<SurveyResult> resultObservationList = FXCollections.observableArrayList();
	
	public MainLayoutController() {
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void newProjectActionButton() {
		mainApp.initNewProjectLayout();
		projectName = mainApp.getProjectName();
		projectPath = mainApp.getProjectPath();
		projectNameLabel.setText(projectName);
	}
	
	public void populateMoonTableView() {
		moonObservableList = mainApp.getMoonObservableList();
		moonListTableColumn.setCellValueFactory(new PropertyValueFactory<String, Moon>("name"));
		moonListTableView.getItems().addAll(moonObservableList);
	}
	
	public void openProjectActionButton() {
		mainApp.initOpenProjectLayout();
		projectName = mainApp.getProjectName();
		projectPath = mainApp.getProjectPath();
		projectNameLabel.setText(projectName);
		populateMoonTableView();
	}
	
	public void saveProjectActionButton() {
		mainApp.saveProjectAction();
	}
	
	public void addMoonActionButton() {
		mainApp.initAddMoonLayout();
	}
	
	public void populateDetailsTableView(String moonName) {
		ArrayList<String> data = mainApp.getData();
		int dataSize = data.size();
		for(int i=0; i<dataSize; i++) {
			if(data.get(i).toString().contains(moonName)) {
				int counter = 0;
				while(i+1+counter<dataSize && data.get(i+1+counter).toString().contains("moon")==false) {
					String mineralName = data.get(i+1+counter).toString();
					String abundance = data.get(i+2+counter).toString();
					SurveyResult result = new SurveyResult(mineralName);
					result.setAbundance(abundance);
					this.resultObservationList.add(result);
					counter = counter+2;
				}
			}
		}
		mineralTableColumn.setCellValueFactory(new PropertyValueFactory<String, SurveyResult>("mineralName"));
		abundanceTableColumn.setCellValueFactory(new PropertyValueFactory<String, SurveyResult>("abundance"));
		detailsTableView.getItems().addAll(resultObservationList);
	}
	
	@FXML
	public void clickMoonListTableView(MouseEvent event) {
		if(moonListTableView.getSelectionModel().getSelectedItem()!=null) {
			resultObservationList.clear();
			detailsTableView.getItems().clear();
			detailsTableView.refresh();
			this.searchMoon = moonListTableView.getSelectionModel().getSelectedItem().getName();
			moonNameLabel.setText(searchMoon);
			populateDetailsTableView(searchMoon);
		}
	}
	
	//Chamado no mainApp
	public void refreshMoonTableView() {
		moonListTableView.getItems().clear();
		populateMoonTableView();
	}
	
	@FXML
	public void deleteActionButton() {
		ArrayList<String> data = mainApp.getData();
		DeleteMoonArraySequence delete = new DeleteMoonArraySequence(data);
		mainApp.setData(delete.getActualizedData(searchMoon));
		moonObservableList = mainApp.getMoonObservableList();
		refreshMoonTableView();
	}	
	
	@FXML
	public void infoActionButton() {
		mainApp.initInfoLayout();
	}
	
	@FXML
	public void requestApraisalActionButton() {
		mainApp.saveProjectAction();
		RequestApraisal ra = new RequestApraisal(mainApp.getData());
		mainApp.setApraisal(ra.getApraisal());
		//ArrayList<String> temp = new ArrayList<String>();
		//temp.add("{\"type\":{\"id\":45493,\"group_id\":1884,\"market_group_id\":2396,\"name\":\"Coesite\",\"volume\":10,\"packaged_volume\":0,\"base_price\":0},\"summaries\":[{\"market_name\":\"jita\",\"market_display_name\":\"Jita\",\"prices\":{\"all\":{\"avg\":1005.8869737762647,\"max\":2000,\"median\":900.39,\"min\":900.39,\"percentile\":1840,\"stddev\":318.56787182592296,\"volume\":658207,\"order_count\":12},\"buy\":{\"avg\":926.0142245206821,\"max\":1015.52,\"median\":900.39,\"min\":900.39,\"percentile\":1015.52,\"stddev\":48.68554683888075,\"volume\":583245,\"order_count\":2},\"sell\":{\"avg\":1914.7670148248203,\"max\":2000,\"median\":1850,\"min\":1839.99,\"percentile\":1839.99,\"stddev\":76.44731972898295,\"volume\":74962,\"order_count\":10},\"updated\":\"2019-07-31T01:27:15.605358826Z\",\"strategy\":\"orders\"},\"totals\":{\"buy\":0,\"sell\":0,\"volume\":0}},{\"market_name\":\"universe\",\"market_display_name\":\"Universe\",\"prices\":{\"all\":{\"avg\":8.880011512111123,\"max\":2332,\"median\":1.06,\"min\":1.06,\"percentile\":900.51,\"stddev\":501.73737378872556,\"volume\":14452524,\"order_count\":49},\"buy\":{\"avg\":7.474292731662305,\"max\":1015.53,\"median\":1.06,\"min\":1.06,\"percentile\":1015.52,\"stddev\":394.53581609015606,\"volume\":14008303,\"order_count\":18},\"sell\":{\"avg\":2035.0084316797002,\"max\":2332,\"median\":2243.91,\"min\":59.57,\"percentile\":1100,\"stddev\":309.62364471507425,\"volume\":444221,\"order_count\":31},\"updated\":\"2019-07-31T01:27:15.605358826Z\",\"strategy\":\"\"},\"totals\":{\"buy\":0,\"sell\":0,\"volume\":0}},{\"market_name\":\"amarr\",\"market_display_name\":\"Amarr\",\"prices\":{\"all\":{\"avg\":1530.9693743181099,\"max\":2243.91,\"median\":2243.91,\"min\":700.09,\"percentile\":2243.91,\"stddev\":713.9113688093605,\"volume\":316270,\"order_count\":7},\"buy\":{\"avg\":700.0914037117133,\"max\":700.12,\"median\":700.09,\"min\":700.09,\"percentile\":700.12,\"stddev\":0.006250257178827609,\"volume\":101080,\"order_count\":3},\"sell\":{\"avg\":2210.9778274194377,\"max\":2243.91,\"median\":2243.91,\"min\":1100,\"percentile\":2000,\"stddev\":122.09893340623957,\"volume\":215190,\"order_count\":4},\"updated\":\"2019-07-31T01:27:15.605358826Z\",\"strategy\":\"orders\"},\"totals\":{\"buy\":0,\"sell\":0,\"volume\":0}},{\"market_name\":\"dodixie\",\"market_display_name\":\"Dodixie\",\"prices\":{\"all\":{\"avg\":232.27814977456484,\"max\":2332,\"median\":2332,\"min\":1.99,\"percentile\":2332,\"stddev\":1074.3948571844126,\"volume\":122668,\"order_count\":5},\"buy\":{\"avg\":3.7738140365585164,\"max\":1000.09,\"median\":1.99,\"min\":1.99,\"percentile\":1000.09,\"stddev\":303.24251679644397,\"volume\":43862,\"order_count\":2},\"sell\":{\"avg\":2300.7019222394215,\"max\":2332,\"median\":2332,\"min\":1600,\"percentile\":1600,\"stddev\":122.50396084299427,\"volume\":78806,\"order_count\":3},\"updated\":\"2019-07-31T01:27:15.605358826Z\",\"strategy\":\"orders\"},\"totals\":{\"buy\":0,\"sell\":0,\"volume\":0}},{\"market_name\":\"hek\",\"market_display_name\":\"Hek\",\"prices\":{\"all\":{\"avg\":8.880011512111123,\"max\":2332,\"median\":1.06,\"min\":1.06,\"percentile\":900.51,\"stddev\":501.73737378872556,\"volume\":14452524,\"order_count\":49},\"buy\":{\"avg\":7.474292731662305,\"max\":1015.53,\"median\":1.06,\"min\":1.06,\"percentile\":1015.52,\"stddev\":394.53581609015606,\"volume\":14008303,\"order_count\":18},\"sell\":{\"avg\":2035.0084316797002,\"max\":2332,\"median\":2243.91,\"min\":59.57,\"percentile\":1100,\"stddev\":309.62364471507425,\"volume\":444221,\"order_count\":31},\"updated\":\"2019-07-31T01:27:15.605358826Z\",\"strategy\":\"orders_universe\"},\"totals\":{\"buy\":0,\"sell\":0,\"volume\":0}},{\"market_name\":\"rens\",\"market_display_name\":\"Rens\",\"prices\":{\"all\":{\"avg\":8.880011512111123,\"max\":2332,\"median\":1.06,\"min\":1.06,\"percentile\":900.51,\"stddev\":501.73737378872556,\"volume\":14452524,\"order_count\":49},\"buy\":{\"avg\":7.474292731662305,\"max\":1015.53,\"median\":1.06,\"min\":1.06,\"percentile\":1015.52,\"stddev\":394.53581609015606,\"volume\":14008303,\"order_count\":18},\"sell\":{\"avg\":2035.0084316797002,\"max\":2332,\"median\":2243.91,\"min\":59.57,\"percentile\":1100,\"stddev\":309.62364471507425,\"volume\":444221,\"order_count\":31},\"updated\":\"2019-07-31T01:27:15.605358826Z\",\"strategy\":\"orders_universe\"},\"totals\":{\"buy\":0,\"sell\":0,\"volume\":0}}]}");
		//mainApp.setApraisal();
		mainApp.initReportLayout();
	}
}
