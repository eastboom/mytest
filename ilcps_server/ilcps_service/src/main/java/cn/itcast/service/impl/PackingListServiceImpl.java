package cn.itcast.service.impl;


import java.util.List;


import java.util.UUID;


import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ExportDao;
import cn.itcast.dao.PackingListDao;
import cn.itcast.domain.Export;
import cn.itcast.domain.PackingList;
import cn.itcast.domain.ShippingOrder;
import cn.itcast.service.PackingListService;

@Service
@Transactional
public class PackingListServiceImpl extends BaseServiceImpl<PackingList, String> implements PackingListService {
	
	private PackingListDao packingListDao;
	
	@Resource
	private ExportDao exportDao;
	
	@Resource
	public void setPackingListDao(PackingListDao packingListDao) {
		this.packingListDao = packingListDao;
		super.setBaseDao(packingListDao);
	}

	
	
	@Override
	public List<PackingList> findbyState(int state) {
		return packingListDao.findbyState(state);
	}

	@Override
	public void saveOrUpdate(PackingList t) {
		//保存
		if(StringUtils.isBlank(t.getId())){
			t.setId(UUID.randomUUID().toString());
			packingListDao.save(t);
			
			String exportIds = t.getExportIds();
			if(StringUtils.isNoneBlank(exportIds)){
				String[] ids = exportIds.split(",");
				for (String id : ids) {
					
					Export export = exportDao.findOne(id);
					if(export!=null){
						export.setState(2);
					}
				}
			}
		
		}else{
			//更新
			packingListDao.save(t);
		}
	}
}





















