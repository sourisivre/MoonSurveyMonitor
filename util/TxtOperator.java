package survey.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtOperator {

	ArrayList<String> content = new ArrayList<String>();
	
	public TxtOperator() {
	}
	
	public ArrayList<String> readFile(String path){
		try {
			File file = new File(path);
			Scanner scanner1 = new Scanner(file);
			scanner1.useDelimiter("\\n|\\r\n|;"); //Conta as linhas do arquivo texto
			int count = 0;
			while(scanner1.hasNext()){
				scanner1.next();
				count++;
			}
			scanner1.close();
			
			Scanner scanner2 = new Scanner(file);
			scanner2.useDelimiter("\\n|\\r\n|;");
			ArrayList<String> temp = new ArrayList<String>();
			for(int i=0; i<count; i++) {
				temp.add(scanner2.next());
			}
			scanner2.close();
			
			//Exclui as linhas vazias
			int nSize = temp.size();
			for(int i=0; i<nSize; i++) {
				if(temp.get(i).trim().isEmpty()) {
					//donothing
				} else {
					this.content.add(temp.get(i).toString());
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.content;
	}
	
	public void writeFile(ArrayList<String> data, String path) {
		try {

			int numberLines = data.size();
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter writer = new PrintWriter(fileWriter);
			
			for (int i=0; i<numberLines; i++) {
				writer.println(data.get(i)+";");
			}
			fileWriter.close();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
