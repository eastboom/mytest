package cn.itcast.cargo.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Contract;
import cn.itcast.domain.ContractProduct;
import cn.itcast.service.ContractService;
import cn.itcast.utils.MailUtil;


@Namespace("/cargo")
@Results({
	@Result(name="alist",type="redirectAction",location="contractAction_list")
})
public class ContractAction extends BaseAction<Contract>{
	private static final long serialVersionUID = 1L;
	
	// 注入service
	@Resource
	private ContractService contractService;
	
	
	/**
	 * 1. 进入jContractList.jsp页面
	 */
	@Action(value="contractAction_list",
			results={@Result(name="list",location="/WEB-INF/pages/cargo/contract/jContractList.jsp")})
	public String list(){
//		String str = null;
//		str.length();
		
		return "list";
	}
	
	/**
	 * 2. jContractList.jsp datagrid发送的异步请求，返回json格式数据
	 */
	@Action(value="contractAction_listResult")
	public void listResult(){
		
		//2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		
		//2.2 调用service,
		Page<Contract> pageData = contractService.findAll(pageable);
		
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
	 * 3. 进入添加页面
	 */
	@Action(value="contractAction_tocreate",
			results={@Result(name="tocreate",location="/WEB-INF/pages/cargo/contract/jContractCreate.jsp")})
	public String tocreate(){
		return "tocreate";
	}
	
	/**
	 * 5. 添加保存
	 */
	@Action(value="contractAction_insert")
	public String insert(){
		//3.1 调用service新增保存
		contractService.saveOrUpdate(getModel());
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	/**
	 * 6. 进入修改页面
	 */
	@Action(value="contractAction_toupdate",
			results={@Result(name="toupdate",location="/WEB-INF/pages/cargo/contract/jContractUpdate.jsp")})
	public String toupdate(){
		//6.1 根据id查询
		Contract Contract =  contractService.findOne(getModel().getId());
		//6.2放入值栈
		push(Contract); //${id}
		return "toupdate";
	}
	
	
	/**
	 * 7. 修改保存
	 */
	@Action(value="contractAction_update")
	public String update(){
		//3.1 先根据id查询
		Contract contract = contractService.findOne(getModel().getId());
		// 设置持久化对象属性值，为用户输入的数据
		contract.setRemark(getModel().getRemark());
		contract.setCustomName(getModel().getCustomName());
		//...
		
		// 3.3 保存持久化对象
		contractService.saveOrUpdate(contract);
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	// 8. 删除
	@Action(value="contractAction_delete")
	public String delete(){
		contractService.delete(getModel().getId());
		return "alist";
	}
	
	// 9. 修改购销合同状态
	@Action("contractAction_updateContractState")
	public String updateContractState(){
		//9.1 根据id查询
		Contract contract = contractService.findOne(getModel().getId());
		//9.2修改状态
		contract.setState(getModel().getState());
		//9.3保存
		contractService.saveOrUpdate(contract);
		return "alist";
	}
	
	//10. 查看
	@Action(value="contractAction_toview",
			results={@Result(name="toview",location="/WEB-INF/pages/cargo/contract/jContractView.jsp")})
	public String toview(){
		// 根据id查询
		Contract contract = contractService.findOne(getModel().getId());
		// 放入值栈
		push(contract);
		return "toview";
	}
	

}














