package survey.model;

import java.util.ArrayList;

public class Moon {
	String name;
	ArrayList<String> mineralAbundanceArray = new ArrayList<String>();
	String value;
	
	public Moon(String name) {
		this.name = name;
	}
	
	public void setMineralAbundance(ArrayList<String> mineralAbundanceArray) {
		this.mineralAbundanceArray = mineralAbundanceArray;
	}
	
	public ArrayList<String> getMineralAbundanceArray(){
		return this.mineralAbundanceArray;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setValue(String value) {
		int val = (int) Double.parseDouble(value);
		this.value = String.valueOf(val);
	}
	
	public String getValue() {
		return this.value;
	}
}
