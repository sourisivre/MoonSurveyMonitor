package survey.util;

import java.util.ArrayList;

public class DeleteMoonArraySequence {

	String deleteMoon;
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> actualizedData = new ArrayList<String>();
	
	public DeleteMoonArraySequence (ArrayList<String> data) {
		this.data = data;
	}
	
	public ArrayList<String> getActualizedData(String deleteMoon){
		int dataSize = data.size();
		int marc = 0;
		for(int i=0; i<dataSize; i++) {
			if(data.get(i).toString().contains(deleteMoon)) {
				marc = i;
			}
		}
		
		int counter = 1;
		int numberOfLinesToDelete = 0;
		while(marc+counter<dataSize && data.get(marc+counter).toString().contains("moon")==false) {
			numberOfLinesToDelete++;
			counter++;
		}
		
		if(marc==0) {
			actualizedData.addAll(data.subList(numberOfLinesToDelete, dataSize));
		} else {
			actualizedData.addAll(data.subList(0, marc-1));
			actualizedData.addAll(data.subList(marc+numberOfLinesToDelete, dataSize));
		}
		
		return actualizedData;
	}
	
}
