package survey.util;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import survey.model.Moon;
import survey.model.SurveyResult;

public class MountReportData {

	//INPUT DATA
	double extractionVolume;
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> appraisal = new ArrayList<String>();
	
	//INTERNAL DATA
	ArrayList<String> itemMarketData = new ArrayList<String>();
	//Neste array, transformatedData, os volumes serao transformados em unidades e serao acrescentados os valores de compra
	//O total das luas tambem sera armazenado neste array
	ArrayList<String> transformatedData = new ArrayList<String>();
	
	
	
	public MountReportData(String extractionVolume, ArrayList<String> data, ArrayList<String> appraisal) {
		this.extractionVolume = Double.parseDouble(extractionVolume);
		this.data = data;
		this.appraisal = appraisal;
		
		itemDataGenerator();
		transformateData();

	}
	
	public ArrayList<String> getItemMarketData(String json){
		ArrayList<String> individualData = new ArrayList<String>();
		ArrayList<String> tempArray = new ArrayList<String>();
		Scanner scanner = new Scanner(json);
		scanner.useDelimiter("\\{|\\}|\\[|\\]|:|,|\"");
		
		while(scanner.hasNext()) {
			String sNext = scanner.next().toString();
			if(sNext.isEmpty()==false) {
				tempArray.add(sNext);
			}
		}
		
		scanner.close();
		
		int tSize = tempArray.size();
		
		for(int i=0; i<tSize; i++) {
				if(tempArray.get(i).toString().equals("name")) {
					individualData.add(tempArray.get(i+1).toString());
				}
				if(tempArray.get(i).toString().equals("volume")) {
					individualData.add(tempArray.get(i+1).toString());
				}
				if(tempArray.get(i).toString().equals("market_name")) {
					individualData.add(tempArray.get(i+1).toString());
				}
				if(tempArray.get(i).toString().equals("max")) {
					individualData.add(tempArray.get(i+1).toString());
				}
				if(tempArray.get(i).toString().equals("min")) {
					individualData.add(tempArray.get(i+1).toString());
				}
			}
		return individualData;
	}
	
	public void itemDataGenerator() {
		int nItem = appraisal.size();
		for(int i=0; i<nItem; i++) {
			itemMarketData.addAll(getItemMarketData(appraisal.get(i)));
		}
	}
	
	public double getBuyPriceJita(String item) {
		double buyPriceJita = 0;
		int itemMarketDataSize = itemMarketData.size();
		for(int i=0; i<itemMarketDataSize; i++) {
			if(itemMarketData.get(i).toString().trim().equals(item.trim().toString())) {
				//ATENCAO: Mudar valor de soma para obter outros indices: name, volume, market, ...
				buyPriceJita = Double.parseDouble(itemMarketData.get(i+6));
			}
		}
		return buyPriceJita;
	}
	
	public double getVolume(String item) {
		double volume = 0;
		int itemMarketDataSize = itemMarketData.size();
		for(int i=0; i<itemMarketDataSize; i++) {
			if(itemMarketData.get(i).toString().equals(item.toString())) {
				//ATENCAO: Mudar valor de soma para obter outros indices: name, volume, market, ...
				volume = Double.parseDouble(itemMarketData.get(i+1));
			}
		}
		return volume;
	}
	
	public void transformateData() {
		int dataSize = data.size();
		for(int i=0; i<dataSize; i++) {
			if(data.get(i).toString().contains("moon")) {
				transformatedData.add(data.get(i));
				int marc = 1;
				double sum = 0;
				double volumeTemp = 0;
				double priceTemp = 0;
				
				ArrayList<String> tempString = new ArrayList<String>();
				
				while((i+marc)<dataSize && data.get(i+marc).toString().contains("moon")==false) {
					if(data.get(i+marc).toString().matches("[A-Z a-z]*")) {
						tempString.add(data.get(i+marc));
						volumeTemp = getVolume(data.get(i+marc).toString());
						priceTemp = getBuyPriceJita(data.get(i+marc).toString());
					} else {
						double quantity = (Double.parseDouble(data.get(i+marc))*extractionVolume)/volumeTemp;
						double value = quantity*priceTemp;
						sum = sum + value;
						tempString.add(String.valueOf(quantity));
						tempString.add(String.valueOf(value));
					}
					marc++;
				}
				transformatedData.add(String.valueOf(sum));
				transformatedData.addAll(tempString);
			}
		}
	}
	
	public ObservableList<Moon> getMoonReportObservableList(){
		ObservableList<Moon> moonReportObservableList = FXCollections.observableArrayList();
		int transformatedDataSize = transformatedData.size();
		for(int i=0; i<transformatedDataSize; i++) {
			if(transformatedData.get(i).toString().contains("moon")) {
				Moon moon = new Moon(transformatedData.get(i).toString().substring(5));
				moon.setValue(transformatedData.get(i+1).toString());
				moonReportObservableList.add(moon);
			}
		}
		return moonReportObservableList;
	}
	
	public ObservableList<SurveyResult> getDetailedMoonReportObservableList(String moonName){
		ObservableList<SurveyResult> detailedMoonReportObservableList = FXCollections.observableArrayList();
		int transformatedDataSize = transformatedData.size();
		for(int i=0; i<transformatedDataSize; i++) {
			if(transformatedData.get(i).toString().contains(moonName)) {
				int marc = 1;
				while((i+marc)<transformatedDataSize && transformatedData.get(i+marc).toString().contains("moon")==false) {
					if(transformatedData.get(i+marc).toString().matches("[A-Z a-z]*")) {
						String mineralName = transformatedData.get(i+marc);
						String quantity = transformatedData.get(i+marc+1);
						String value = transformatedData.get(i+marc+2);

						SurveyResult result = new SurveyResult(mineralName);
						result.setQuantity(quantity);
						result.setValue(value);
	
						detailedMoonReportObservableList.add(result);
					}
					marc++;
				}
			}
		}
		return detailedMoonReportObservableList;
	}
	
	
}
