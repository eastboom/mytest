package cn.itcast.cargo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import cn.itcast.domain.PackingList;
import cn.itcast.domain.ShippingOrder;
import cn.itcast.service.PackingListService;
import cn.itcast.service.ShippingOrderService;

@Namespace("/cargo")
@Results({
	@Result(name="alist",type="redirectAction",location="shippingOrderAction_list")
})
public class ShippingOrderAction extends BaseAction<ShippingOrder> {
	
	// 注入service
		@Resource
		private ShippingOrderService shippingOrderService;

		@Resource
		private PackingListService packingListService;
		
	/**
	 * 1. 进入jShippingOrderList.jsp页面
	 */
	@Action(value="shippingOrderAction_list",
			results={@Result(name="list",location="/WEB-INF/pages/cargo/shippingOrder/jShippingOrderList.jsp")})
	public String list(){
		return "list";
	}
	
	/**
	 * 2. jContractList.jsp datagrid发送的异步请求，返回json格式数据
	 */
	@Action(value="shippingOrderAction_listResult")
	public void listResult(){
		
		//2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		
		//2.2 调用service,
		Page<ShippingOrder> pageData = shippingOrderService.findAll(pageable);
		
		//2.3构造datagrid需要的json格式数据
		Map<String,Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", pageData.getContent());
		
		System.out.println(results);
		
		parseObjToJson(results);
	 }
	
	/**
	 * 3.查看
	 */
	@Action(value="shippingOrderAction_toview",
			results={@Result(name="toview",location="/WEB-INF/pages/cargo/shippingOrder/jShippingOrderView.jsp")})
	public String toview(){
		// 根据id查询
		ShippingOrder shippingOrder = shippingOrderService.findOne(getModel().getId());
		// 放入值栈
		push(shippingOrder);
		return "toview";
	}
	
	
	/**
	 * 4. 进入添加页面
	 */
	@Action(value="shippingOrderAction_tocreate",
			results={@Result(name="tocreate",location="/WEB-INF/pages/cargo/shippingOrder/jShippingOrderCreate.jsp")})
	public String tocreate(){
		
		List<PackingList> PackingList = packingListService.findbyState(1);
		
		set("PackingList", PackingList);
		return "tocreate";
	}
	
	/**
	 * 4.新增保存
	 */
	@Action(value="shippingOrderAction_insert")
	public String insert(){
		// 调用service新增保存
		shippingOrderService.saveOrUpdate(getModel());
		
		//修改装箱单的状态
		PackingList packingList = packingListService.findOne(getModel().getId());
		packingList.setState(2);
		
		//保存修改
		packingListService.saveOrUpdate(packingList);
		
		//保存成功，跳转到列表
		return "alist";
	}
	
	/**
	 * 5. 进入修改页面
	 */
	@Action(value="shippingOrderAction_toupdate",
			results={@Result(name="toupdate",location="/WEB-INF/pages/cargo/shippingOrder/jShippingOrderUpdate.jsp")})
	public String toupdate(){
		//6.1 根据id查询
		ShippingOrder shippingOrder =  shippingOrderService.findOne(getModel().getId());
		//6.2放入值栈
		push(shippingOrder); //${id}
		return "toupdate";
	}
	
	/**
	 * 7. 修改保存
	 */
	@Action(value="shippingOrderAction_update")
	public String update(){
	
		ShippingOrder shippingOrder = shippingOrderService.findOne(getModel().getId());
		// 设置持久化对象属性值，为用户输入的数据
		shippingOrder.setShipper(getModel().getShipper());
		shippingOrder.setOrderType(getModel().getOrderType());
		shippingOrder.setConsignee(getModel().getConsignee());
		shippingOrder.setNotifyParty(getModel().getNotifyParty());
		shippingOrder.setLcNo(getModel().getLcNo());
		shippingOrder.setPortOfLoading(getModel().getPortOfLoading());
		shippingOrder.setPortOfTrans(getModel().getPortOfTrans());
		shippingOrder.setPortOfDischarge(getModel().getPortOfDischarge());
		shippingOrder.setLoadingDate(getModel().getLoadingDate());
		shippingOrder.setLimitDate(getModel().getLimitDate());
		shippingOrder.setIsBatch(getModel().getIsBatch());
		shippingOrder.setIsTrans(getModel().getIsTrans());
		shippingOrder.setCopyNum(getModel().getCopyNum());
		shippingOrder.setCreateBy(getModel().getCreateBy());
		shippingOrder.setCheckBy(getModel().getCheckBy());
		shippingOrder.setRemark(getModel().getRemark());
		shippingOrder.setSpecialCondition(getModel().getSpecialCondition());
		shippingOrder.setFreight(getModel().getFreight());
	
		// 3.3 保存持久化对象
		shippingOrderService.saveOrUpdate(shippingOrder);
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	/**
	 * 8.删除
	 */
	@Action(value="shippingOrderAction_delete")
	public String delete(){
		shippingOrderService.delete(getModel().getId());
		return "alist";
	}
	
	
	// 9. 修改委托单状态
	@Action("shippingOrderAction_updateContractState")
	public String updateContractState(){
		//9.1 根据id查询
		ShippingOrder shippingOrder = shippingOrderService.findOne(getModel().getId());
		//9.2修改状态
		shippingOrder.setState(getModel().getState());
		//9.3保存
		shippingOrderService.saveOrUpdate(shippingOrder);
		return "alist";
	}
	
}
