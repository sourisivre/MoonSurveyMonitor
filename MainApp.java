package survey;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import survey.model.Moon;
import survey.util.TxtOperator;
import survey.view.AddMoonLayoutController;
import survey.view.InfoLayoutController;
import survey.view.MainLayoutController;
import survey.view.ReportLayoutController;

public class MainApp extends Application{
	
	String projectName, projectPath;
	ArrayList<String> data = new ArrayList<String>();
	Stage mainStage;
	MainLayoutController mainLayoutController = new MainLayoutController();
	AddMoonLayoutController addMoonLayoutController = new AddMoonLayoutController();
	
	ObservableList<Moon> moonObservableList = FXCollections.observableArrayList();
	
	ArrayList<String> appraisal = new ArrayList<String>();
	
	public MainApp() {
	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/MainLayout.fxml"));
		AnchorPane mainLayout = (AnchorPane) loader.load();
		
		this.mainLayoutController = loader.getController();
		this.mainLayoutController.setMainApp(this);
		
		Scene scene = new Scene(mainLayout);
		mainStage.setScene(scene);
		mainStage.setTitle("Moon Survey");
		mainStage.show();
	}
	
	public void initNewProjectLayout() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text file (*.txt)", "txt"));
		fileChooser.setTitle("New Survey");
		File newFile = fileChooser.showSaveDialog(mainStage);
		projectName = newFile.getName();
		projectPath = newFile.getAbsolutePath();
		TxtOperator op = new TxtOperator();
		op.writeFile(data, projectPath);
	}
	
	public void initOpenProjectLayout() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Survey");
		File newFile = fileChooser.showOpenDialog(mainStage);
		projectName = newFile.getName();
		projectPath = newFile.getAbsolutePath();
		//Importa dados do arquivo texto
		TxtOperator op = new TxtOperator();
		data = op.readFile(projectPath);
		//FXObservableList
		//ObservableListUtil obsvList = new ObservableListUtil(data);
		//moonObservableList = obsvList.getObservableList();
		int dataSize = data.size();
		for(int i=0; i<dataSize; i++) {
			if(data.get(i).toString().contains("moon")) {
				moonObservableList.add(new Moon(data.get(i).toString().substring(5)));
			}
		}
	}
	
	public void saveProjectAction() {
		//Escreve dados no arquivo texto
		TxtOperator op = new TxtOperator();
		op.writeFile(data, projectPath);
	}
	
	public void initAddMoonLayout() {
		try {
			FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("view/AddMoonLayout.fxml"));
			AnchorPane addMoonLayout = (AnchorPane) loader2.load();
			
			this.addMoonLayoutController = loader2.getController();
			this.addMoonLayoutController.setMainApp(this);
			
			Scene scene = new Scene(addMoonLayout);
			Stage stage = new Stage();
			stage.setTitle("Add Moon");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addMoonAction(String moonName, ArrayList<String> mineralAbundanceArray) {
		this.data.add(moonName);
		int n = mineralAbundanceArray.size();
		for (int i=0; i<n; i++) {
			this.data.add(mineralAbundanceArray.get(i));
		}
		
		//FXObservableList
		String moonName2 = moonName.substring(5); //descontar prefixo "moon"
		Moon moon = new Moon(moonName2);
		moon.setMineralAbundance(mineralAbundanceArray);
		this.moonObservableList.add(moon);
		
		//Refresh TableView in controller
		this.mainLayoutController.refreshMoonTableView();
	}
	
	public void initInfoLayout() {
		try {
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/InfoLayout.fxml"));
				AnchorPane infoLayout = (AnchorPane) loader.load();
				
				InfoLayoutController controller = loader.getController();
				controller.setMainApp(this);
				
				Stage stage = new Stage();
				stage.setTitle("Info");
				Scene scene = new Scene(infoLayout);
				stage.setScene(scene);
				stage.show();
				
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void initReportLayout() {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/ReportLayout.fxml"));
			AnchorPane reportLayout =(AnchorPane) loader.load();
			
			ReportLayoutController reportLayoutController = loader.getController();
			reportLayoutController.setMainApp(this);
			
			Stage stage = new Stage();
			stage.setTitle("Appraisal Report");
			Scene scene = new Scene(reportLayout);
			stage.setScene(scene);
			stage.show();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	//SETTERS AND GETTERS
	public ObservableList<Moon> getMoonObservableList(){
		return this.moonObservableList;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public String getProjectPath() {
		return this.projectPath;
	}
	
	public ArrayList<String> getData(){
		return this.data;
	}
	
	public void setData(ArrayList<String> data) {
		this.data = data;
		//FXObservableList
		moonObservableList.clear();
		int dataSize = data.size();
		for(int i=0; i<dataSize; i++) {
			if(data.get(i).toString().contains("moon")) {
				moonObservableList.add(new Moon(data.get(i).toString().substring(5)));
			}
		}
	}
	
	public void setApraisal(ArrayList<String> appraisal) {
		this.appraisal = appraisal;
	}
	
	public ArrayList<String> getAppraisal(){
		return this.appraisal;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
