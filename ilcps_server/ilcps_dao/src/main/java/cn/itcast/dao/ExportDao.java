package cn.itcast.dao;

import java.util.List;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.Export;

/**
 * 出口报运模块，dao
 * @author Jie.Y
 *
 */
public interface ExportDao extends BaseDao<Export, String>{
	
	List<Export> findByState(Integer state);
}











