package cn.itcast.service;

import java.util.List;

import com.itcast.vo.ExportResult;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.Export;

/**
 * 出口报运模块
 * @author Administrator
 *
 */
public interface ExportService extends BaseService<Export, String>{

	/**
	 * 根据电子报运服务端返回的结果，修改报运单信息
	 * @param exportResult 封装报运结果（报运单、商品）
	 */
	void updateExport(ExportResult exportResult);

	List<Export> findByState(Integer state);

}









