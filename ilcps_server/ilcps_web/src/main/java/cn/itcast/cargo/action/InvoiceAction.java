package cn.itcast.cargo.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Contract;
import cn.itcast.domain.ContractProduct;
import cn.itcast.domain.Export;
import cn.itcast.domain.Invoice;
import cn.itcast.domain.PackingList;
import cn.itcast.domain.ShippingOrder;
import cn.itcast.service.ExportService;
import cn.itcast.service.InvoiceService;
import cn.itcast.service.PackingListService;
import cn.itcast.service.ShippingOrderService;
import cn.itcast.utils.DownloadUtil;
import cn.itcast.utils.UtilFuns;

@Namespace("/cargo")
@Results({ @Result(name = "alist", type = "redirectAction", location = "invoiceAction_list") })
public class InvoiceAction extends BaseAction<Invoice> {

	@Resource
	private InvoiceService invoiceService;

	/**
	 * 1. 进入jInvoiceList.jsp页面
	 */
	@Action(value = "invoiceAction_list", results = {
			@Result(name = "list", location = "/WEB-INF/pages/cargo/invoice/jInvoiceList.jsp") })
	public String list() {
		return "list";
	}

	/**
	 * 2. jInvoiceList.jsp datagrid发送的异步请求，返回json格式数据
	 */
	@Action(value = "invoiceAction_listResult")
	public void listResult() {

		// 2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage() - 1, getRows());

		// 2.2 调用service,
		Page<Invoice> pageData = invoiceService.findAll(pageable);

		List<Invoice> list = pageData.getContent();

		// 2.3构造datagrid需要的json格式数据
		Map<String, Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", list);

		parseObjToJson(results);

	}

	@Resource
	private ShippingOrderService shippingOrderService;

	/**
	 * 3. 进入添加页面
	 */
	@Action(value = "invoiceAction_tocreate", results = {
			@Result(name = "tocreate", location = "/WEB-INF/pages/cargo/invoice/jInvoiceCreate.jsp") })
	public String tocreate() {
		// 查询状态为1的所有委托单,创建发票时用到
		List<ShippingOrder> ShippingOrderList = shippingOrderService.findbyState(1);

		set("ShippingOrderList", ShippingOrderList);
		return "tocreate";
	}

	// 封装委托单的id也就是发票的id
	private String shippingOrderId;

	public void setShippingOrderId(String shippingOrderId) {
		this.shippingOrderId = shippingOrderId;
	}

	@Resource
	private PackingListService packingListService;

	@Resource
	private ExportService exportService;

	/**
	 * 5. 添加保存
	 */
	@Action(value = "invoiceAction_insert")
	public String insert() {
		// 已经封装好了贸易条款和状态
		Invoice invoice = getModel();

		// 设置id跟委托单一样
		invoice.setId(shippingOrderId);

		// 通过委托单的id也就是发票的id也是装箱单的id来找装箱单
		PackingList packList = packingListService.findOne(shippingOrderId);

		// 通过装箱单封装合同数据
		if (packList != null) {
			// 得到所有的报运单的id
			String exportIdsStr = packList.getExportIds();

			// 用于拼接合同编号
			String concatContractId = "";
			// 有报运单
			if (StringUtils.isNotBlank(exportIdsStr)) {

				String[] exportIds = exportIdsStr.split(",");

				// 得到每个报运单的id
				for (String exportId : exportIds) {
					if (StringUtils.isNotBlank(exportId)) {
						// 得到每个报运单
						Export export = exportService.findOne(exportId);

						String contractIds = export.getContractIds();
						concatContractId += contractIds + ",";
					}
				}
				// 除去最后一个逗号
				concatContractId = concatContractId.substring(0, concatContractId.length() - 1);
			}

			// 设置发票的合同
			invoice.setScNo(concatContractId);

		}
		// 设置提单号
		invoice.setBlNo(UUID.randomUUID().toString().replaceAll("-", ""));

		// 保存发票
		invoiceService.saveOrUpdate(invoice);
		
		
		if(packList!=null){
			packList.setInvoiceNo(shippingOrderId);
			packList.setInvoiceDate(new Date());
		}
		
		

		// 修改委托单的状态为2,并且保存
		ShippingOrder shippingOrder = shippingOrderService.findOne(shippingOrderId);
		shippingOrder.setState(2);
		shippingOrderService.saveOrUpdate(shippingOrder);

		// 3.2保存成功，跳转到列表
		return "alist";
	}

	/**
	 * 6. 进入修改页面
	 */
	@Action(value = "invoiceAction_toupdate", results = {
			@Result(name = "toupdate", location = "/WEB-INF/pages/cargo/invoice/jInvoiceUpdate.jsp") })
	public String toupdate() {
		// 6.1 根据id查询
		Invoice invoice = invoiceService.findOne(getModel().getId());
		// 6.2放入值栈
		push(invoice); // ${id}
		return "toupdate";
	}

	/**
	 * 7. 修改保存
	 */
	@Action(value = "invoiceAction_update")
	public String update() {
		// 3.1 先根据id查询
		Invoice invoice = invoiceService.findOne(getModel().getId());
		invoice.setTradeTerms(getModel().getTradeTerms());
		// 3.3 保存持久化对象
		invoiceService.saveOrUpdate(invoice);
		// 3.2保存成功，跳转到列表
		return "alist";
	}

	private String delId;

	public void setDelId(String delId) {
		this.delId = delId;
	}

	// 8. 删除
	@Action(value = "invoiceAction_delete")
	public String delete() {
		invoiceService.delete(delId);
		return "alist";
	}

	// 9. 修改购销合同状态
	@Action("invoiceAction_updateInvoiceState")
	public String updateContractState() {
		// 9.1 根据id查询
		Invoice invoice = invoiceService.findOne(delId);
		// 9.2修改状态,将草稿修改为可新增财务报运单
		invoice.setState(getModel().getState());
		// 9.3保存
		invoiceService.saveOrUpdate(invoice);
		return "alist";
	}

	
	
	@Action(value = "invoiceAction_printInvoice")
	public void printInvoice() throws Exception {

		InputStream in = ServletActionContext.getServletContext()
				.getResourceAsStream("/make/xlsprint/invoice.xlsx");
		
		// 创建工作簿
		Workbook workbook = new XSSFWorkbook(in);
		// B. 获取工作表
		Sheet sheet = workbook.getSheetAt(0);
		
		
		PackingList packingList = packingListService.findOne(delId);
		
		if(packingList != null){
			String buyer = packingList.getBuyer();
			String seller = packingList.getSeller();
			
			
			//C. 获取第二行
			Row row = sheet.getRow(1);
			//D. 获取第一列
			Cell cell = row.getCell(0);
			//设置第一行第二列的seller
			cell.setCellValue(seller);
			
			//C. 获取第七行
			Row row7 = sheet.getRow(6);
			//D. 获取第一列
			Cell cell71 = row.getCell(0);
			//设置第七行第一列的seller
			cell71.setCellValue(buyer);
			
			Invoice invoice = invoiceService.findOne(delId);
			//C. 获取第十三行
			Row row13 = sheet.getRow(12);
			//D. 获取第二列
			Cell cell132 = row.getCell(1);
			//设置第一行第二列的seller
			cell132.setCellValue(invoice.getId());
			
			
			//C. 获取第十三行
			row13 = sheet.getRow(12);
			//D. 获取第四列
			Cell cell134 = row.getCell(3);
			//设置第一行第二列的seller
			cell132.setCellValue(UtilFuns.dateTimeFormat(invoice.getCreateTime()));
			
			
			//C. 获取第十三行
			row13 = sheet.getRow(12);
			//D. 获取第七列
			Cell cell7 = row.getCell(6);
			//设置第一行第二列的seller
			cell132.setCellValue(invoice.getScNo());
			
			
			//C. 获取第十三行
			row13 = sheet.getRow(12);
			//D. 获取第十列
			Cell cell10 = row.getCell(9);
			//设置第一行第二列的seller
			cell132.setCellValue(invoice.getBlNo());
			
			
			ShippingOrder shippingOrder = shippingOrderService.findOne(delId);
			
			
			//C. 获取第二十二行
			row13 = sheet.getRow(21);
			//D. 获取第二列
			Cell cell2 = row.getCell(1);
			//设置第一行第二列的seller
			cell132.setCellValue(shippingOrder.getLcNo());
		}

		
		

		
		
		
		
		
		//(4)下载
		DownloadUtil downloadUtil = new DownloadUtil();
		// 创建字节数组输出流（缓冲流）。  excel文件流------->bos--------->response输出流
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//   excel文件流------->bos
		workbook.write(bos);
		workbook.close();
		//   bos--------->response输出流
		downloadUtil.download(bos, ServletActionContext.getResponse(), "发票.xlsx");
	}
}
