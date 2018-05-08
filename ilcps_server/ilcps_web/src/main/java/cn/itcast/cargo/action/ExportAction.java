package cn.itcast.cargo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itcast.vo.ExportProductVo;
import com.itcast.vo.ExportResult;
import com.itcast.vo.ExportVo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Contract;
import cn.itcast.domain.ContractProduct;
import cn.itcast.domain.Export;
import cn.itcast.domain.ExportProduct;
import cn.itcast.service.ContractService;
import cn.itcast.service.ExportProductService;
import cn.itcast.service.ExportService;
import cn.itcast.utils.UtilFuns;

@Namespace("/cargo")
@Results({
	@Result(name="alist",type="redirectAction",location="exportAction_list")
})
public class ExportAction extends BaseAction<Export>{
	private static final long serialVersionUID = 1L;
	
	// 注入报运单service
	@Resource
	private ExportService exportService;
	
	@Resource
	private ContractService contractService;
	
	@Resource
	private ExportProductService exportProductService;
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除报运单
	* @return void    返回类型 
	* @throws
	 */
	@Action(value="exportAction_delete",results={
			@Result(name="tolist",type="redirectAction",location="exportAction_list")
	})
	public String delete(){
		String id=getModel().getId();
		if(StringUtils.isNoneBlank(id)){
			
			exportService.delete(id);
		}
		return  "tolist";
	}
	
	/**
	 * 
	* @Title: toview 
	* @Description: 跳转到查看页面
	* @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@Action(value="exportAction_toview",results={
			@Result(name="toview",location="/WEB-INF/pages/cargo/export/jExportView.jsp")
	})
	public String toview(){
		Export export = exportService.findOne(getModel().getId());
		if(export!=null){
			//存在值栈中
			ValueStack vs = ActionContext.getContext().getValueStack();
			vs.push(export);
		}
		return "toview";
	}
	
	/**
	 * 1. 进入合同管理列表
	 */
	@Action(value="exportAction_contractList",
			results={@Result(name="list",location="/WEB-INF/pages/cargo/export/jContractList.jsp")})
	public String contractList(){
		return "list";
	}
	@Action(value="exportAction_contractListResult")
	public void contractListResult(){
		// 条件：只显示已上报待报运的购销合同
		Specification<Contract> spec = new Specification<Contract>() {
			public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("state").as(Long.class), 1);
			}
		};
		
		//2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		
		//2.2 调用service,
		Page<Contract> pageData = contractService.findAll(spec,pageable);
		
		/*
		 * 封装货物数、附件数
		 */
		List<Contract> list = pageData.getContent();
		for (Contract contract : list) {
			// 附件总数
			int extcNum = 0;
			// 获取合同货物集合
			Set<ContractProduct> contractProducts = contract.getContractProducts();
			//-->1. 累加货物
			contract.setProductNum(contractProducts.size());
			
			//-->2. 累加附件
			if (contractProducts != null && contractProducts.size() > 0) {
				for (ContractProduct cp : contractProducts) {
					extcNum += cp.getExtCproducts().size();
				}
			}
			// 设置购销合同的附件
			contract.setExtcNum(extcNum);
		}
		
		//2.3构造datagrid需要的json格式数据
		Map<String,Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", list);
		
		parseObjToJson(results);
	}
	
	
	/**
	 * 2. 合同管理，生成报运单，进入报运单添加页面
	 */
	@Action(value="exportAction_tocreate",
			results={@Result(name="tocreate",location="/WEB-INF/pages/cargo/export/jExportCreate.jsp")})
	public String tocreate(){
//		String contractIds = getModel().getContractIds();
//		System.out.println(contractIds);
		
		return "tocreate";
	}
	
	/**
	 * 3. 生成报运单
	 */
	@Action(value="exportAction_insert",
			results={@Result(name="tocreate",location="/WEB-INF/pages/cargo/export/jExportCreate.jsp")})
	public String insert(){
		
		//生成出口报运单,所包含的合同状态都改为已报运
		String contractIds = getModel().getContractIds();
		if(StringUtils.isNotBlank(contractIds)){
			
			String[] contractIdStr = contractIds.split(",");
			for (String contractId : contractIdStr) {
				//得到每个合同
				Contract contract = contractService.findOne(contractId);
				//设置状态为已经报运
				contract.setState(2);
				contractService.saveOrUpdate(contract);
			}
			
		}
		
		//设置状态为草稿
		getModel().setState(0);
		//3.1调用service，保存
		exportService.saveOrUpdate(getModel());
		//3.2 生成报运单后，进入报运单列表
		return "alist";
	}
	
	
	/**
	 * 4. 报运单列表分页显示
	 */
	@Action(value="exportAction_list",
			results={@Result(name="list",location="/WEB-INF/pages/cargo/export/jExportList.jsp")})
	public String list(){
		return "list";
	}
	@Action(value="exportAction_listResult")
	public void listResult(){
		
		//2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		
		//2.2 调用service,
		Page<Export> pageData = exportService.findAll(pageable);
		
		/*
		 * 封装货物数、附件数
		 */
		List<Export> list = pageData.getContent();
		for (Export export : list) {
			// 附件总数
			int extcNum = 0;
			// 获取报运商品
			Set<ExportProduct> epSet = export.getExportProducts();
			//-->1. 累加货物
			export.setProductNum(epSet.size());
			
			//-->2. 累加附件
			if (epSet != null && epSet.size() > 0) {
				for (ExportProduct ep : epSet) {
					extcNum += ep.getExtEproducts().size();
				}
			}
			// 设置购销合同的附件
			export.setExtcNum(extcNum);
		}
		
		//2.3构造datagrid需要的json格式数据
		Map<String,Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", list);
		
		parseObjToJson(results);
	}
	
	
	/**
	 * 5. 进入报运单修改页面
	 */
	@Action(value="exportAction_toupdate",
			results={@Result(name="toupdate",location="/WEB-INF/pages/cargo/export/jExportUpdate.jsp")})
	public String toupdate(){
		// 根据id查询
		Export export = exportService.findOne(getModel().getId());
		// 放入值栈，页面回显数据
		push(export);
		return "toupdate";
	}
	
	/**
	 * 6. 修改页面ajax请求，返回报运单下的商品的json格式.
	 */
	@Action("exportAction_exportProductsJson")
	public void exportProductsJson(){
		//6.1 根据id查询报运单
		Export export = exportService.findOne(getModel().getId());
		
		//6.2获取报运商品集合
		Set<ExportProduct> epSet = export.getExportProducts();
		
		/*
		 * 6.3封装对象，对象转json，返回json格式
		 * addTRRecord("mRecordTable", "id", "productNo", "cnumber", "grossWeight", "netWeight", "sizeLength", "sizeWidth", "sizeHeight", "exPrice", "tax");
		 * json格式：[{"id":"","productNo":"","cnumber":"5",,,,,},{}]
		 */
		// 创建对象封装json格式
		List<Map<String,Object>> results = new ArrayList<>();
		// 遍历商品集合
		if (epSet != null && epSet.size() > 0) {
			for (ExportProduct ep : epSet) {
				// ep---->epMap
				Map<String,Object> epMap = new HashMap<>();
				epMap.put("id", ep.getId());
				// 对象如果是NULL，转换为"", 使用函数UtilFuns.convertNull(obj)
				epMap.put("productNo", 		UtilFuns.convertNull(ep.getProductNo()));
				epMap.put("cnumber", 		UtilFuns.convertNull(ep.getCnumber()));
				epMap.put("grossWeight", 	UtilFuns.convertNull(ep.getGrossWeight()));
				epMap.put("netWeight", 		UtilFuns.convertNull(ep.getNetWeight()));
				epMap.put("sizeLength", 	UtilFuns.convertNull(ep.getSizeLength()));
				epMap.put("sizeWidth", 		UtilFuns.convertNull(ep.getSizeWidth()));
				epMap.put("sizeHeight", 	UtilFuns.convertNull(ep.getSizeHeight()));
				epMap.put("exPrice", 		UtilFuns.convertNull(ep.getExPrice()));
				epMap.put("tax", 			UtilFuns.convertNull(ep.getTax()));
				// 对象添加到集合
				results.add(epMap);
			}
		}
		// 对象转换为json，响应
		parseObjToJson(results);
	}
	
	/**
	 * 7. 修改保存
	 */
	//7.1封装请求数据： 报运单商品
	private String[] mr_id;
	private Integer[] mr_cnumber;
	private Double[] mr_grossWeight;
	private Double[] mr_netWeight;
	private Double[] mr_sizeLength;
	private Double[] mr_sizeWidth;
	private Double[] mr_sizeHeight;
	private Double[] mr_exPrice;
	private Double[] mr_tax;
	//7.2 修改保存
	@Action(value="exportAction_update")
	public String update(){
		//A. 根据报运单id查询
		Export export = exportService.findOne(getModel().getId());
		
		//B. 修改报运单信息
		export.setInputDate(getModel().getInputDate());
		export.setLcno(getModel().getLcno());
		export.setConsignee(getModel().getConsignee());
		export.setShipmentPort(getModel().getShipmentPort());
		export.setDestinationPort(getModel().getDestinationPort());
		export.setTransportMode(getModel().getTransportMode());
		export.setPriceCondition(getModel().getPriceCondition());
		export.setMarks(getModel().getMarks());
		export.setRemark(getModel().getRemark());
		
		//C. 修改报运单下的商品信息
		//C1 商品集合
		Set<ExportProduct> exportProducts = new HashSet<>();
		//C2遍历页面传入的商品id
		if (mr_id != null && mr_id.length >0){
			for(int i=0; i< mr_id.length; i++){
				// 根据id查询
				ExportProduct ep = exportProductService.findOne(mr_id[i]);
				// 修改商品信息
				ep.setCnumber(mr_cnumber[i]);
				ep.setNetWeight(mr_netWeight[i]);
				ep.setGrossWeight(mr_grossWeight[i]);
				ep.setSizeWidth(mr_sizeWidth[i]);
				ep.setSizeHeight(mr_sizeHeight[i]);
				ep.setSizeLength(mr_sizeLength[i]);
				ep.setTax(mr_tax[i]);
				ep.setExPrice(mr_exPrice[i]);
				
				// 添加到集合
				exportProducts.add(ep);
			}
		}
		
		//D. 设置商品集合到报运单对象中
		export.setExportProducts(exportProducts);
		
		//E. 保存（报运单）
		exportService.saveOrUpdate(export);
		
		//F. 跳转到报运单列表
		return "alist";
	}
	
	/**
	 * 8. 电子报运
	 */
	@Action(value="exportAction_exportE")
	public String exportE(){
		// 1. 根据报运单的id，查询报运单信息
		Export export = exportService.findOne(getModel().getId());
		
		// 2. 创建ExportVo对象，封装webservice请求数据
		ExportVo exportVo = new ExportVo();
		// export---->exportVo  对象数据的拷贝
		// 2.1 封装exportVo报运单信息
		BeanUtils.copyProperties(export, exportVo);
		exportVo.setExportId(export.getId());
		
		// 2.2 封装exportVo报运单中的商品信息
		if (export.getExportProducts() != null && export.getExportProducts().size()>0) {
			for (ExportProduct ep : export.getExportProducts()) {
				// 创建webservice请求的商品对象
				ExportProductVo epVo = new ExportProductVo();
				// 对象拷贝
				BeanUtils.copyProperties(ep, epVo);
				epVo.setExportProductId(ep.getId());
				// 对象添加到集合
				exportVo.getProducts().add(epVo);
			}
		}
		
		// 3. 远程调用服务端方法1： （报运信息插入服务端的mysql数据库）
		// webservice服务端请求地址  (http://localhost:端口/项目/web.xml/spring配置路径/service接口路径)
		String url = "http://localhost:8090/jk_export/ws/export/ep";
		WebClient
			.create(url)						//请求地址
			.type(MediaType.APPLICATION_JSON)	// 请求数据为json格式
			.post(exportVo);
		
		// 4. 远程调用服务端方法2：（根据报运单id，从服务端的mysql数据库查询报运结果）
		ExportResult exportResult = 
				WebClient
					.create(url + "/" + export.getId())
					.accept(MediaType.APPLICATION_JSON)  // 响应数据格式
					.get(ExportResult.class);
		
		// 5. 根据电子报运服务端返回的结果，修改报运单信息
		exportService.updateExport(exportResult);
		
		return "alist";
	}
	
	
	public String[] getMr_id() {
		return mr_id;
	}
	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}
	public Integer[] getMr_cnumber() {
		return mr_cnumber;
	}
	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}
	public Double[] getMr_grossWeight() {
		return mr_grossWeight;
	}
	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}
	public Double[] getMr_netWeight() {
		return mr_netWeight;
	}
	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}
	public Double[] getMr_sizeLength() {
		return mr_sizeLength;
	}
	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}
	public Double[] getMr_sizeWidth() {
		return mr_sizeWidth;
	}
	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}
	public Double[] getMr_sizeHeight() {
		return mr_sizeHeight;
	}
	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}
	public Double[] getMr_exPrice() {
		return mr_exPrice;
	}
	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}
	public Double[] getMr_tax() {
		return mr_tax;
	}
	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}
	
}











