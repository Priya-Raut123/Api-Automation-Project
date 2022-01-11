package ExcelUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class printReport {
	public static void printRowData(int row, String url, String method, String body, String status, int statuscode, long time){
		String excelPath = System.getProperty("user.dir") + "/target/xlsx-report/report.xlsx";
		XSSFWorkbook workbook;
		try {
			FileInputStream file = new FileInputStream(excelPath);
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("Sheet1");
			SimpleDateFormat sdf = new SimpleDateFormat("ss");
			String t = sdf.format(new Date(time)); 
			CellStyle style = workbook.createCellStyle();  
			CellStyle style2 = workbook.createCellStyle(); 
			
			//For ok response format
			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());  
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			//For Error response Format
			style2.setFillForegroundColor(IndexedColors.RED1.getIndex());
			style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			sheet.getRow(row).createCell(3).setCellType(CellType.STRING);
			sheet.getRow(row).createCell(4).setCellType(CellType.STRING);
			sheet.getRow(row).createCell(5).setCellType(CellType.STRING);
			sheet.getRow(row).createCell(6).setCellType(CellType.NUMERIC);
			sheet.getRow(row).createCell(7).setCellType(CellType.STRING);
			sheet.getRow(row).createCell(8).setCellType(CellType.NUMERIC);
			
			sheet.getRow(row).getCell(3).setCellValue(url);
			sheet.getRow(row).getCell(4).setCellValue(method);
			sheet.getRow(row).getCell(5).setCellValue(body);
			sheet.getRow(row).getCell(6).setCellValue(statuscode);
//			sheet.getRow(row).getCell(7).setCellValue(status);
			sheet.getRow(row).getCell(8).setCellValue(t + "secs");
			if(statuscode == 200) {
				sheet.getRow(row).getCell(7).setCellValue("Pass");
				sheet.getRow(row).getCell(7).setCellStyle(style);
		          
			}else {
				sheet.getRow(row).getCell(7).setCellValue("Fail");
				sheet.getRow(row).getCell(7).setCellStyle(style2);
			}
			
			 FileOutputStream outFile = new FileOutputStream(excelPath);
			  workbook.write(outFile);
			  workbook.close();
			  outFile.close();
			  file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
