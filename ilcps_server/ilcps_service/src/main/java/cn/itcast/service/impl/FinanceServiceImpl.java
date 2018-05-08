package cn.itcast.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ExportDao;
import cn.itcast.dao.FinanceDao;
import cn.itcast.dao.PackingListDao;
import cn.itcast.domain.Export;
import cn.itcast.domain.ExportProduct;
import cn.itcast.domain.ExtEproduct;
import cn.itcast.domain.Finance;
import cn.itcast.domain.PackingList;
import cn.itcast.service.FinanceService;

@Service
@Transactional
public class FinanceServiceImpl extends BaseServiceImpl<Finance, String> implements FinanceService {
	
	private FinanceDao financeDao;
	
	@Resource
	private PackingListDao packingListDao;
	
	@Resource
	private ExportDao exportDao;
	
	@Resource
	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
		super.setBaseDao(financeDao);
	}

	@Override
	public List<Map<String, String>> exportFinance(String id) {
		
		PackingList packingList = packingListDao.findOne(id);
		String ids = packingList.getExportIds();
		String[] idStr = ids.split(",");
		List<Map<String,String>> list  = new ArrayList<>();
		Map<String,String> map = new HashMap<String,String>();
		for (String eid : idStr) {
			map.put("eid", eid);
			Export export = exportDao.findOne(eid);
			int pnum = 0;
			int epnum = 0;
			Set<ExportProduct> exportProducts = export.getExportProducts();
			for (ExportProduct exportProduct : exportProducts) {
				pnum+=exportProduct.getCnumber();
				Set<ExtEproduct> extEproducts = exportProduct.getExtEproducts();
				for (ExtEproduct extEproduct : extEproducts) {
					epnum+=extEproduct.getCnumber();
				}
			}
			map.put("number", pnum+"/"+epnum);
			map.put("pid", id);
			map.put("iid", id);
			list.add(map);
		}
		
		return list;
	}

}
















