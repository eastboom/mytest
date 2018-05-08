package cn.itcast.poi.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

public class App_03 {

	// 写excel
	@Test
	public void write() throws Exception {
		//1. 创建工作簿
		Workbook workbook = new HSSFWorkbook();
		
		//2. 创建工作表
		Sheet sheet= workbook.createSheet("第一个工作表");
		// 设置宽度
		sheet.setColumnWidth(1, 41*256);
		
		//3. 创建行 (第一行)
		Row row =  sheet.createRow(0);
		
		//4. 创建单元格 (第二列)
		Cell cell = row.createCell(1);
		
		//5. 设置单元格内容
		cell.setCellValue("第一行第二列");
		
		//6. 写到指定目录
		workbook.write(new FileOutputStream("f:/test.xls"));
		
		//7. 关闭
		workbook.close();
	}
	
	// 读excel
	@Test
	public void read() throws Exception {
		//1. 根据文件流，创建工作簿
		Workbook workbook = new HSSFWorkbook(new FileInputStream("f:/test.xls"));
		
		//2. 获取工作表
		Sheet sheet = workbook.getSheetAt(0);
		
		//3. 获取行
		Row row = sheet.getRow(0);
		
		//4. 获取列
		Cell cell = row.getCell(1);
		
		//5. 获取内容
		System.out.println("单元格内容： " + cell.getStringCellValue());
		System.out.println("总行数： " + sheet.getPhysicalNumberOfRows());
		System.out.println("总列数：" + row.getPhysicalNumberOfCells());
		
		//6. 关闭
		workbook.close();
	}
	
	// 合并单元格
	@Test
	public void cellRange() throws Exception {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet= workbook.createSheet("第一个工作表");
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(3, 5, 2, 3));
		
		// 获取第4行第3列
		Cell cell = sheet.createRow(3).createCell(2);
		cell.setCellValue("AAAA");
		
		workbook.write(new FileOutputStream("f:/test2.xls"));
		workbook.close();
	}
	
}
