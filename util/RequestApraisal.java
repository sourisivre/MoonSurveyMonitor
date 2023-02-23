package survey.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RequestApraisal {

	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> requestList = new ArrayList<String>();
	ArrayList<String> appraisal = new ArrayList<String>();
	
	ArrayList<String> temp = new ArrayList<String>();
	ArrayList<String> temp2 = new ArrayList<String>();
	
	
	
	public RequestApraisal(ArrayList<String> data) {
		this.data = data;
		
		reduceRequestList();
		requestApraisal();
	}
	
	public void reduceRequestList() {

		//Retira os nomes das luas
		int dataSize = data.size();
		for (int i=0; i<dataSize;i++) {
			if(data.get(i).toString().contains("moon")==false) {
				temp.add(data.get(i));
			}
		}
		//Retira os valores numericos
		int tempSize = temp.size();
		for(int i=0; i<tempSize; i++) {
			if(temp.get(i).matches("[A-Z a-z]*")) {
				temp2.add(temp.get(i));
			}
		}
		//Retira os elementos duplicados e monta requestList
		int temp2Size = temp2.size();
		for(int i=0; i<temp2Size; i++) {
			if(requestList.contains(temp2.get(i))==false) {
				requestList.add(temp2.get(i));
			}
		}
	}
	
	//EveApraisal API
	//https://evepraisal.com/api-docs
	public void requestApraisal() {
		int nRequest = requestList.size();
		//Get itemIDList
		ArrayList<String> itemIDList = new ArrayList<String>();
		for (int i=0; i<nRequest; i++) {
			ItemID itemID = new ItemID(requestList.get(i).toString());
			itemIDList.add(itemID.getItemID());
		}
		
		//Get EveApraisal JSON strings
		int nItens = itemIDList.size();
		for(int i=0; i<nItens; i++) {
			try {
				String urlString = new String("https://evepraisal.com/item/"+itemIDList.get(i)+".json");
				URL url = new URL(urlString);
				URLConnection con = url.openConnection();
				con.setRequestProperty("User-Agent", "TestTool - sourisivre@yahoo.com.br");
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer json = new StringBuffer();
				String line = new String();
				while((line = reader.readLine())!= null) {
					json.append(line);
				}
				appraisal.add(json.toString());
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<String> getApraisal(){
		return this.appraisal;
	}
}
