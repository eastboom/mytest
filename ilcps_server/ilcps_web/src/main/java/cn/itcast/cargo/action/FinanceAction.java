package cn.itcast.cargo.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Export;
import cn.itcast.domain.ExportProduct;
import cn.itcast.domain.Finance;
import cn.itcast.domain.Invoice;
import cn.itcast.service.ExportService;
import cn.itcast.service.FinanceService;
import cn.itcast.service.InvoiceService;
import cn.itcast.utils.DownloadUtil;

@Namespace("/cargo")
@Results({
	@Result(name="alist",type="redirectAction",location="financeAction_list")
})
/**
 * 1.根据发票生成对应的财务报运单,查看发票-委托单-装箱单中的所有产品和产品金额
 * 2.财务报运单只是提供一个报运的过程结果
 * */
public class FinanceAction extends BaseAction<Finance>{
	private static final long serialVersionUID = 1L;
	
	@Resource
	private FinanceService financeService;
	@Resource
	private InvoiceService invoiceService;
	@Resource
	private ExportService exportService;
	
	private String eid;
	private String invoiceId;
	
	/**
	 * 需求: 显示所有的财务报运单的列表清单
	 * */
	@Action(value="financeAction_list",results={@Result(name="list",location="/WEB-INF/pages/cargo/finance/jFinanceList.jsp")})
	public String list(){
		return "list";
	}
	
	@Action(value="financeAction_listResult")
	public void listResult(){
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		Page<Finance> pageData = financeService.findAll(pageable);
		Map<String,Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", pageData.getContent());
		parseObjToJson(results);
	}
	
	/**
	 * 需求:新增财务报运单
	 * 1.财务报运单是根据发票来制定的,发票是根据委托单制定的,委托单是根据装箱单制定的
	 * 2.一个财务报运单----一张发票-----一个装箱单
	 * 3.新增财务报运单就是通过选定发票(发票状态为1,未生成财务报运),来确定已委托的装箱单
	 * 4.新增了财务报运单,修改发票状态为2,已生成财务报运单
	 * */
	@Action(value="financeAction_tocreate",results={@Result(name="tocreate",location="/WEB-INF/pages/cargo/finance/jFinanceCreate.jsp")})
	public String toCreate(){
		return "tocreate";
	}
	
	@Action(value="financeAction_insert")
	public String insert(){
		Finance  finance = new Finance();
		finance.setId(invoiceId);
		finance.setState(getModel().getState());
		finance.setInputBy(getModel().getInputBy());
		finance.setInputDate(getModel().getInputDate());
		financeService.saveOrUpdate(finance);
		Invoice invoice = invoiceService.findOne(invoiceId);
		invoice.setState(2);
		invoiceService.saveOrUpdate(invoice);
		return "alist";
	}
	
	
	/**
	 * 需求:更新财务报运单
	 * 1.财务报运单是根据发票来制定的,发票是根据委托单制定的,委托单是根据装箱单制定的
	 * 2.一个财务报运单----一张发票-----一个装箱单
	 * 3.修改财务报运单就是通过修改发票,来修改财务报运单中(id)的对应的装箱单
	 * */
	@Action(value="financeAction_toupdate",results={@Result(name="toupdate",location="/WEB-INF/pages/cargo/finance/jFinanceUpdate.jsp")})
	public String toUpdate(){
		if(getModel().getId()!=null){
			Finance finance = financeService.findOne(getModel().getId());
			push(finance);
		}
		return "toupdate";
	}
	@Action(value="financeAction_update")
	public String update(){
			Finance finance = financeService.findOne(getModel().getId());
			finance.setInputDate(getModel().getInputDate());
			finance.setInputBy(getModel().getInputBy());
			Invoice invoice2 = invoiceService.findOne(invoiceId);
			if(invoice2!=null){
				financeService.delete(getModel().getId());
				Invoice invoice = invoiceService.findOne(getModel().getId());
				invoice.setState(1);
				invoiceService.saveOrUpdate(invoice);
				invoice2.setState(2);
				invoiceService.saveOrUpdate(invoice2);
				finance.setId(invoiceId);
			}
			financeService.saveOrUpdate(finance);
		return "alist";
	}
	
	
	
	
	@Action(value="financeAction_ajaxInvoice")
	public void ajaxInvoice(){
		List<Invoice> list = invoiceService.findAll(new Specification<Invoice>() {
			@Override
			public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Expression<Integer> expression = root.get("state").as(Integer.class);
				Predicate predicate = cb.equal(expression, 1);
				return predicate;
			}
		});
		parseObjToJson(list);
	}
	/**
	 * 需求:删除财务报运单
	 * 1.删除财务报运单中为草稿的,同时将发票的状态修改为1(开具发票,未生成财务报运单)
	 * 2.已报运的财务报运单不能删除
	 * */
	@Action(value="financeAction_delete")
	public String delete(){
		if(getModel().getId()!=null){
			financeService.delete(getModel().getId());
			Invoice invoice = invoiceService.findOne(getModel().getId());
			invoice.setState(1);
			invoiceService.saveOrUpdate(invoice);
		}
		return "alist";
	}
	
	/**
	 * 需求:出口商品报运单列表
	 * */
	@Action(value="financeAction_toExportFinance",results={@Result(name="toExportFinance",location="/WEB-INF/pages/cargo/finance/jExportFinance.jsp")})
	public String toExportFinance(){
		return "toExportFinance";
	}
	@Action(value="financeAction_exportFinance")
	public void exportFinance(){
		String  id = getModel().getId();
		List<Map<String, String>> list = financeService.exportFinance(id);
		Map<String,Object> results = new HashMap<>();
		results.put("total", list.size());
		results.put("rows", list);
		parseObjToJson(results);
	}
	
	
	@Action(value="financeAction_commit")
	public String commit(){
		String  id = getModel().getId();
		if(id!=null){
			Finance finance = financeService.findOne(id);
			finance.setState(getModel().getState());
			financeService.saveOrUpdate(finance);
		}
		return "alist";
	}
	
	@Action(value="financeAction_concel")
	public String concel(){
		String  id = getModel().getId();
		if(id!=null){
			Finance finance = financeService.findOne(id);
			finance.setState(getModel().getState());
			financeService.saveOrUpdate(finance);
		}
		return "alist";
	}
	
	
	@Action(value="financeAction_printExport")
	public void printExport(){
		Export export = exportService.findOne(eid);
		System.out.println(export);
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook(ServletActionContext.getServletContext().getResourceAsStream("/make/xlsprint/tFINANCE.xls"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = workbook.getSheetAt(0);
		
		Row row2 = sheet.getRow(1);
		CellStyle cellStyle1 = row2.getCell(1).getCellStyle();
		row2.getCell(3).setCellValue(export.getInputDate());
		row2.getCell(15).setCellValue(invoiceId);
		row2.getCell(3).setCellStyle(cellStyle1);
		row2.getCell(15).setCellStyle(cellStyle1);
		Row row3 = sheet.getRow(2);
		row3.getCell(4).setCellValue(export.getCustomerContract());;
		row3.getCell(11).setCellValue(export.getLcno());
		
		Row row4 = sheet.getRow(3);
		row4.getCell(4).setCellValue(export.getConsignee());;
		row4.getCell(13).setCellValue(export.getRemark());
		
		
		Row row5 = sheet.getRow(4);
		row5.getCell(2).setCellValue(export.getShipmentPort());;
		row5.getCell(5).setCellValue(export.getDestinationPort());
		row5.getCell(10).setCellValue(export.getPriceCondition());
		
		Row row8 = sheet.getRow(8);
		CellStyle cellStyle2 = row8.getCell(2).getCellStyle();
		CellStyle cellStyle4 = row8.getCell(3).getCellStyle();
		
		Row row9 = sheet.getRow(9);
		CellStyle rowStyle = row9.getRowStyle();
		Set<ExportProduct> exportProducts = export.getExportProducts();
		if(exportProducts!=null&&exportProducts.size()>0){
			int num = 7;
			double sumCnumber= 0;
			double sumBoxNum = 0;
			double sumGrossWeight = 0;
			double sumNetWeight = 0;
			double sumExMoney = 0;
			double sumMoney = 0;
			double sumTaxMoney = 0;
			for (ExportProduct exportProduct : exportProducts) {
				Row row = sheet.createRow(num);
				sheet.addMergedRegion(new CellRangeAddress(num, num, 1, 2));
				row.createCell(1).setCellValue(exportProduct.getId());
				row.createCell(2).setCellValue(exportProduct.getId());
				row.createCell(4).setCellValue(exportProduct.getFactory().getFactoryName());
				row.createCell(5).setCellValue(exportProduct.getPackingUnit());
				sumCnumber+=exportProduct.getCnumber();
				row.createCell(6).setCellValue(exportProduct.getCnumber());
				sumBoxNum+= exportProduct.getBoxNum();
				row.createCell(7).setCellValue(exportProduct.getBoxNum());
				sumGrossWeight+=exportProduct.getGrossWeight();
				row.createCell(8).setCellValue(exportProduct.getGrossWeight());
				sumNetWeight+=exportProduct.getNetWeight();
				row.createCell(9).setCellValue(exportProduct.getNetWeight());
				row.createCell(10).setCellValue(exportProduct.getSizeLength());
				row.createCell(11).setCellValue(exportProduct.getSizeWidth());
				row.createCell(12).setCellValue(exportProduct.getSizeHeight());
				row.createCell(13).setCellValue(exportProduct.getExPrice());
				sumExMoney+=exportProduct.getExPrice()*exportProduct.getCnumber();
				row.createCell(14).setCellValue(exportProduct.getExPrice()*exportProduct.getCnumber());
				row.createCell(15).setCellValue(exportProduct.getPrice());
				double taxMoney = exportProduct.getPrice()*exportProduct.getTax()*exportProduct.getCnumber()*0.01;
				sumMoney=sumMoney+exportProduct.getPrice()*exportProduct.getCnumber()-taxMoney;
				row.createCell(16).setCellValue(exportProduct.getPrice()*exportProduct.getCnumber()-taxMoney);
				sumTaxMoney+=taxMoney;
				row.createCell(17).setCellValue(taxMoney);
				row.getCell(1).setCellStyle(cellStyle2);
				row.getCell(2).setCellStyle(cellStyle2);
				row.getCell(4).setCellStyle(cellStyle4);
				row.getCell(5).setCellStyle(cellStyle4);
				row.getCell(6).setCellStyle(cellStyle4);
				row.getCell(7).setCellStyle(cellStyle4);
				row.getCell(8).setCellStyle(cellStyle4);
				row.getCell(9).setCellStyle(cellStyle4);
				row.getCell(10).setCellStyle(cellStyle4);
				row.getCell(11).setCellStyle(cellStyle4);
				row.getCell(12).setCellStyle(cellStyle4);
				row.getCell(13).setCellStyle(cellStyle4);
				row.getCell(14).setCellStyle(cellStyle4);
				row.getCell(15).setCellStyle(cellStyle4);
				row.getCell(16).setCellStyle(cellStyle4);
				row.getCell(17).setCellStyle(cellStyle4);
				num++;
			}
			Row row = sheet.createRow(num);
			row.setHeightInPoints(24);
			row.setRowStyle(rowStyle);
			row.createCell(1);
			row.createCell(2);
			row.createCell(3);
			sheet.addMergedRegion(new CellRangeAddress(num, num, 1, 2));
			row.getCell(1).setCellValue("合计");
			row.createCell(4);
			row.createCell(5);
			row.createCell(6).setCellValue(sumCnumber);
			row.createCell(7).setCellValue(sumBoxNum);
			row.createCell(8).setCellValue(sumGrossWeight);
			row.createCell(9).setCellValue(sumNetWeight);
			row.createCell(10).setCellValue(export.getMeasurements());
			row.createCell(11);
			row.createCell(12);
			row.createCell(13);
			row.createCell(14).setCellValue(sumExMoney);
			row.createCell(15);
			row.createCell(16).setCellValue(sumMoney);
			row.createCell(17).setCellValue(sumTaxMoney);
			row.getCell(1).setCellStyle(cellStyle4);
			row.createCell(2).setCellStyle(cellStyle4);
			row.getCell(4).setCellStyle(cellStyle4);
			row.getCell(5).setCellStyle(cellStyle4);
			row.getCell(6).setCellStyle(cellStyle4);
			row.getCell(7).setCellStyle(cellStyle4);
			row.getCell(8).setCellStyle(cellStyle4);
			row.getCell(9).setCellStyle(cellStyle4);
			row.getCell(10).setCellStyle(cellStyle4);
			row.getCell(11).setCellStyle(cellStyle4);
			row.getCell(12).setCellStyle(cellStyle4);
			row.getCell(13).setCellStyle(cellStyle4);
			row.getCell(14).setCellStyle(cellStyle4);
			row.getCell(15).setCellStyle(cellStyle4);
			row.getCell(16).setCellStyle(cellStyle4);
			row.getCell(17).setCellStyle(cellStyle4);
			sheet.addMergedRegion(new CellRangeAddress(num, num, 10, 12));
		}
		
		DownloadUtil downloadUtil = new DownloadUtil();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			downloadUtil.download(bos, ServletActionContext.getResponse(), "出口商品报运表.xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
}














