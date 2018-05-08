package cn.itcast.stat.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Contract;
import cn.itcast.service.StatChartService;

@Namespace("/stat")
public class StatChartAction extends BaseAction<Contract>{
	
	// 注入service
	@Resource
	private StatChartService statChartService;

	/**
	 * 1. 根据生产厂家统计
	 */
	@Action(value = "statChartAction_factorysale",results={@Result(name="factorySale",location="/WEB-INF/pages/stat/1_factorySale.html")})
	public String factorySale(){
		return "factorySale";
	}
	@Action("statChartAction_factorySaleResult")
	public void factorySaleResult(){
		// 调用service查询数据
		List<Map<String, Object>> list = statChartService.factorySale();
		// 对象转json，响应
		parseObjToJson(list);
	}
	
	/**
	 * 2. 统计产品销售排行 
	 */
	@Action(value = "statChartAction_productsale",results={@Result(name="productSale",location="/WEB-INF/pages/stat/2_productSale.html")})
	public String productSale(){
		return "productSale";
	}
	@Action("statChartAction_productSaleResult")
	public void productSaleResult(){
		// 调用service查询数据
		List<Map<String, Object>> list = statChartService.productSale(10);
		// 对象转json，响应
		parseObjToJson(list);
	}
	
	/**
	 * 3. 系统访问压力图
	 */
	@Action(value = "statChartAction_onlineinfo",results={@Result(name="onlineinfo",location="/WEB-INF/pages/stat/3_onlineinfo.html")})
	public String onlineinfo(){
		return "onlineinfo";
	}
	@Action("statChartAction_onlineinfoResult")
	public void onlineinfoResult(){
		// 调用service查询数据
		List<Map<String, Object>> list = statChartService.onlineInfo();
		// 对象转json，响应
		parseObjToJson(list);
	}
}











