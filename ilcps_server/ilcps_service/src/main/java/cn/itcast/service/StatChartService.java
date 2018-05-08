package cn.itcast.service;

import java.util.List;
import java.util.Map;

public interface StatChartService {

	/**
	 * 需求1:根据生产厂家统计销售情况
	 * nativeQuery=true 原生sql
	 * @return
	 */                                                                                        
	List<Map<String,Object>> factorySale();
	
	/**
	 * 需求2： 产品销售排行
	 */
	List<Map<String,Object>> productSale(int topNum);
	
	/**
	 * 需求3：系统访问压力图
	 */
	List<Map<String,Object>> onlineInfo();
}

















