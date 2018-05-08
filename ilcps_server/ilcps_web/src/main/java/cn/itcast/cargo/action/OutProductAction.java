package cn.itcast.cargo.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Contract;
import cn.itcast.domain.ContractProduct;
import cn.itcast.service.ContractProductService;
import cn.itcast.utils.DownloadUtil;
import cn.itcast.utils.UtilFuns;

@Namespace("/cargo")
public class OutProductAction extends BaseAction<Contract> {
	
	// 封装船期字段
	private String inputDate;
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	
	// 注入货物service
	@Resource
	private ContractProductService contractProductService;
	
	//1. 转发到出货表页面
	@Action(value="outProductAction_toedit",
			results={@Result(name="toedit",location="/WEB-INF/pages/cargo/outproduct/jOutProduct.jsp")})
	public String toedit(){
		return "toedit";
	}
	
	//2. 导出
	@Action("outProductAction_print_old")
	public void print() throws Exception{
		// 创建工作簿
		Workbook workbook = new HSSFWorkbook();
		// 创建工作表
		Sheet sheet = workbook.createSheet();
		// 设置宽度
		sheet.setColumnWidth(1, 26 * 256);
		sheet.setColumnWidth(2, 11 * 256);
		sheet.setColumnWidth(3, 29 * 256);
		sheet.setColumnWidth(4, 11 * 256);
		sheet.setColumnWidth(5, 15 * 256);
		sheet.setColumnWidth(6, 10 * 256);
		sheet.setColumnWidth(7, 10 * 256);
		sheet.setColumnWidth(8, 10 * 256);
		
		//(1)导出第一行
		//1.1 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 8));
		//1.2创建第一行
		Row row = sheet.createRow(0);
		//1.3设置行高
		row.setHeightInPoints(36f);
		//1.4创建第二列
		Cell cell = row.createCell(1);
		//1.5设置单元格内容。2017-09,---》2017年9月份出货表
		String headValue = inputDate.replace("-0", "-").replace("-", "年") + "月份出货表";
		cell.setCellValue(headValue);
		//1.6设置单元格样式（居中加粗）
		cell.setCellStyle(this.bigTitle(workbook));
		
		
		//(2)导出第二行
		String[] titles = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
		//2.1 创建行
		row = sheet.createRow(1);
		// 设置行高
		row.setHeightInPoints(26);
		//2.2创建单元格
		for(int i=0; i<titles.length; i++) {
			cell = row.createCell(i+1);
			//2.3 设置内容
			cell.setCellValue(titles[i]);
			//2.6 设置样式
			cell.setCellStyle(this.title(workbook));
		}
		
		
		//(3)导出数据库中数据。 根据船期，查询货物表
		List<ContractProduct> list = 
				contractProductService.findContractProductByShipTime(inputDate);
		// 判断
		if (list != null && list.size() > 0) {
			// 数据行从第3行开始创建
			int rowNum = 2;
			for (ContractProduct cp : list) {
				row = sheet.createRow(rowNum++);
				// 设置行高
				row.setHeightInPoints(24);
				
				// 创建单元格、设置内容
				cell = row.createCell(1);
				cell.setCellValue(cp.getContract().getCustomName());
				cell.setCellStyle(this.text(workbook));
				
				cell = row.createCell(2);
				cell.setCellValue(cp.getContract().getContractNo());
				cell.setCellStyle(this.text(workbook));				
				
				cell = row.createCell(3);
				cell.setCellValue(cp.getProductNo());
				cell.setCellStyle(this.text(workbook));				
				
				cell = row.createCell(4);
				cell.setCellValue(cp.getCnumber());
				cell.setCellStyle(this.text(workbook));				
				
				cell = row.createCell(5);
				cell.setCellValue(cp.getFactory().getFactoryName());
				cell.setCellStyle(this.text(workbook));				
				
				cell = row.createCell(6);
				
				cell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
				cell.setCellStyle(this.text(workbook));				
				
				cell = row.createCell(7);
				cell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
				cell.setCellStyle(this.text(workbook));				
				
				cell = row.createCell(8);
				cell.setCellValue(cp.getContract().getTradeTerms());
				cell.setCellStyle(this.text(workbook));				
			}
		}
		
		//(4)下载
		DownloadUtil downloadUtil = new DownloadUtil();
		// 创建字节数组输出流（缓冲流）。  excel文件流------->bos--------->response输出流
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//   excel文件流------->bos
		workbook.write(bos);
		workbook.close();
		//   bos--------->response输出流
		downloadUtil.download(bos, ServletActionContext.getResponse(), "出货表.xls");
	}
	
	
	
	
	//3. 模板导出
	@Action("outProductAction_print")
	public void printWithTemplate() throws Exception{
		/*
		 * 改为使用模板导出实现：
		 * 整体实现流程：
		 * 1. 读取excel模板文件
		 * 2. 获取工作表、获取行、获取单元格样式
		 * 3. 设置单元格内容(数据)
		 */
		//A. 加载excel模板, 获取文件流
		// "/"  相对于项目资源路径，如：G:\apache-tomcat-7.0.42\webapps\ilcps_web\
		InputStream in = 
				ServletActionContext
					.getServletContext().getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
		
		// 创建工作簿
		Workbook workbook = new XSSFWorkbook(in);
		// B. 获取工作表
		Sheet sheet = workbook.getSheetAt(0);
		
		//(1)导出第一行
		//C. 获取第一行
		Row row = sheet.getRow(0);
		//D. 获取第二列
		Cell cell = row.getCell(1);
		
		//1.5设置单元格内容。2017-09,---》2017年9月份出货表
		String headValue = inputDate.replace("-0", "-").replace("-", "年") + "月份出货表";
		cell.setCellValue(headValue);
		
		
		//E 获取第三行每个单元格的样式
		row = sheet.getRow(2);
		CellStyle cellStyle_1 = row.getCell(1).getCellStyle();
		CellStyle cellStyle_2 = row.getCell(2).getCellStyle();
		CellStyle cellStyle_3 = row.getCell(3).getCellStyle();
		CellStyle cellStyle_4 = row.getCell(4).getCellStyle();
		CellStyle cellStyle_5 = row.getCell(5).getCellStyle();
		CellStyle cellStyle_6 = row.getCell(6).getCellStyle();
		CellStyle cellStyle_7 = row.getCell(7).getCellStyle();
		CellStyle cellStyle_8 = row.getCell(8).getCellStyle();
		
		
		//(3)导出数据库中数据。 根据船期，查询货物表
		List<ContractProduct> list = 
				contractProductService.findContractProductByShipTime(inputDate);
		// 判断
		if (list != null && list.size() > 0) {
			// 数据行从第3行开始创建
			int rowNum = 2;
			for (ContractProduct cp : list) {
				row = sheet.createRow(rowNum++);
				
				// 创建单元格、设置内容
				cell = row.createCell(1);
				cell.setCellValue(cp.getContract().getCustomName());
				cell.setCellStyle(cellStyle_1);
				
				cell = row.createCell(2);
				cell.setCellValue(cp.getContract().getContractNo());
				cell.setCellStyle(cellStyle_2);				
				
				cell = row.createCell(3);
				cell.setCellValue(cp.getProductNo());
				cell.setCellStyle(cellStyle_3);				
				
				cell = row.createCell(4);
				cell.setCellValue(cp.getCnumber());
				cell.setCellStyle(cellStyle_4);				
				
				cell = row.createCell(5);
				cell.setCellValue(cp.getFactory().getFactoryName());
				cell.setCellStyle(cellStyle_5);				
				
				cell = row.createCell(6);
				
				cell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
				cell.setCellStyle(cellStyle_6);				
				
				cell = row.createCell(7);
				cell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
				cell.setCellStyle(cellStyle_7);				
				
				cell = row.createCell(8);
				cell.setCellValue(cp.getContract().getTradeTerms());
				cell.setCellStyle(cellStyle_8);				
			}
		}
		
		//(4)下载
		DownloadUtil downloadUtil = new DownloadUtil();
		// 创建字节数组输出流（缓冲流）。  excel文件流------->bos--------->response输出流
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//   excel文件流------->bos
		workbook.write(bos);
		workbook.close();
		//   bos--------->response输出流
		downloadUtil.download(bos, ServletActionContext.getResponse(), "出货表.xlsx");
	}
	
	
	
	//大标题的样式
	public CellStyle bigTitle(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)16);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//字体加粗
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		return style;
	}
	//小标题的样式
	public CellStyle title(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short)12);
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		return style;
	}
	
	//文字样式
	public CellStyle text(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short)10);
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_LEFT);					//横向居左
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		return style;
	}
}







