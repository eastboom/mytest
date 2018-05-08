package cn.itcast.service;


import java.util.List;
import java.util.Map;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.Finance;

/**
 * 财务报运service接口
 */
public interface FinanceService extends BaseService<Finance, String> {

	List<Map<String, String>> exportFinance(String id);

	
}













