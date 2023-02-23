package survey.model;

public class SurveyResult {

	String mineralName;
	String abundance;
	String value;
	String quantity;
	
	public SurveyResult(String mineralName) {
		this.mineralName = mineralName;
	}

	public String getMineralName() {
		return mineralName;
	}

	public void setMineralName(String mineralName) {
		this.mineralName = mineralName;
	}

	public String getAbundance() {
		return abundance;
	}

	public void setAbundance(String abundance) {
		this.abundance = abundance;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		int val = (int) Double.parseDouble(value);
		this.value = String.valueOf(val);
	}
	
	public void setQuantity(String quantity) {
		int val = (int) Double.parseDouble(quantity);
		this.quantity = String.valueOf(val);
	}
	
	public String getQuantity() {
		return this.quantity;
	}
	
}
