package cn.itcast.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.dao.StatChartDao;
import cn.itcast.service.StatChartService;

@Service
//@Transactional(readOnly=true) // 默认事务只读
public class StatChartServiceImpl implements StatChartService {
	
	// 注入dao
	@Resource
	private StatChartDao statChartDao;

	@Override
	public List<Map<String, Object>> factorySale() {
		// 返回结果
		List<Map<String, Object>> results = new ArrayList<>();
		// 统计查询
		List<Object[]> list = statChartDao.factorySale();
		// 遍历查询结果
		if (list != null && list.size()>0) {
			for (Object[] rowObj : list) {
				// rowObj----》rowMap
				Map<String,Object> rowMap = new HashMap<>();
				rowMap.put("factoryName", rowObj[0]);
				rowMap.put("amount", rowObj[1]);
				// 添加每一行到结果集
				results.add(rowMap);
			}
		}
		return results;
	}
	
	@Override
	public List<Map<String, Object>> productSale(int topNum) {
		// 返回结果
		List<Map<String, Object>> results = new ArrayList<>();
		
		// 调用dao，根据产品号统计销售
		List<Object[]> list = statChartDao.productSale(topNum);
		
		// 柱状图有一个颜色的循环显示
		String[] colors = {"#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01"};
		
		// 遍历查询结果
		if (list != null && list.size()>0) {
			int i = 0;
			for (Object[] rowObj : list) {
				// rowObj----》rowMap
				Map<String,Object> rowMap = new HashMap<>();
				rowMap.put("product_no", rowObj[0]);
				rowMap.put("amount", rowObj[1]);
				rowMap.put("color", colors[i++]);
				// 添加每一行到结果集
				results.add(rowMap);
				
				// 判断
				if (i % 5 == 0) {
					i = 0;
				}
			}
		}
		return results;
	}
	
	@Override
	public List<Map<String, Object>> onlineInfo() {
		// 返回结果
		List<Map<String, Object>> results = new ArrayList<>();
		// 统计查询
		List<Object[]> list = statChartDao.onlineInfo();
		// 遍历查询结果
		if (list != null && list.size()>0) {
			for (Object[] rowObj : list) {
				// rowObj----》rowMap
				Map<String,Object> rowMap = new HashMap<>();
				rowMap.put("label", rowObj[0]);
				rowMap.put("value", rowObj[1]);
				// 添加每一行到结果集
				results.add(rowMap);
			}
		}
		return results;
	}
}
