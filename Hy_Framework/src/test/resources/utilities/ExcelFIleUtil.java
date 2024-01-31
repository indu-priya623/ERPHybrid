package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFIleUtil
{
	Workbook wb;
	public ExcelFIleUtil(String Excelpath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = WorkbookFactory.create(fi);
	}
	public int rowCount(String sheet)
	{
		return wb.getSheet(sheet).getLastRowNum();
	}
	public String getCellData(String sheet,int row,int cell)
	{
		String data =" ";
		if (wb.getSheet(sheet).getRow(row).getCell(cell).getCellType()==CellType.NUMERIC)
		{
			int celldata = (int)wb.getSheet(sheet).getRow(row).getCell(cell).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(sheet).getRow(row).getCell(cell).getStringCellValue();
		}
		return data;
	}
	public void setCellData(String sheet,int row,int cell,String status,String writepath) throws Throwable
	{
		Sheet ws = wb.getSheet(sheet);
		Row  wr = ws.getRow(row);
		Cell wc =wr.createCell(cell);
		wc.setCellValue(status);
		if (status.equalsIgnoreCase("pass"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(cell).setCellStyle(style);
		}
		else if (status.equalsIgnoreCase("fail")) 
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(cell).setCellStyle(style);
		}
		else if (status.equalsIgnoreCase("blocked"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(cell).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(writepath);
		wb.write(fo);

	}
}



