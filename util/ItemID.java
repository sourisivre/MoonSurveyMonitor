package survey.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;

import survey.MainApp;

public class ItemID {

	String name;
	String itemID;
	
	public ItemID(String name) {
		this.name = name;
		searchOnDatabase();
	}
	
	//Apache POI API
	//https://poi.apache.org/components/index.html
	//TODO MIGRAR PARA CSV
	public void searchOnDatabase() {
		try {
			MainApp mainApp = new MainApp();
			InputStream inp = mainApp.getClass().getResourceAsStream("resource/invTypesModified.xls");
			POIFSFileSystem fs = new POIFSFileSystem(inp);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			
			int n = sheet.getPhysicalNumberOfRows();
			
			for(int i=0; i<n; i++) {
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell(2);
				if(cell.getCellType() == CellType.STRING) {
					if(cell.getRichStringCellValue().getString().trim().equals(name)) {
						HSSFCell IDcell = row.getCell(0);
						itemID = String.valueOf((int)IDcell.getNumericCellValue());
					}
				}
			}
			wb.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getItemID() {
		return this.itemID;
	}
}
