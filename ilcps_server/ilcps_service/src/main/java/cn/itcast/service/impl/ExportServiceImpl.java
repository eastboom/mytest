package cn.itcast.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.vo.ExportProductResult;
import com.itcast.vo.ExportResult;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ContractProductDao;
import cn.itcast.dao.ExportDao;
import cn.itcast.dao.ExportProductDao;
import cn.itcast.domain.ContractProduct;
import cn.itcast.domain.Export;
import cn.itcast.domain.ExportProduct;
import cn.itcast.domain.ExtCproduct;
import cn.itcast.domain.ExtEproduct;
import cn.itcast.service.ExportProductService;
import cn.itcast.service.ExportService;

@Service  // 加入ioc容器
@Transactional
public class ExportServiceImpl extends BaseServiceImpl<Export, String> implements ExportService{
	
	private ExportDao exportDao;

	// 注入货物dao
	@Resource
	private ContractProductDao contractProductDao;
	// 报运商品dao
	@Resource
	private ExportProductDao exportProductDao;

	@Resource
	protected void setBaseDao(ExportDao exportDao) {
		super.setBaseDao(exportDao);
		this.exportDao = exportDao;
	}
	
	@Override
	public void saveOrUpdate(Export t) {
		// 判断
		if (StringUtils.isBlank(t.getId())) {
			/** 新增报运单 (商品、附件) */
			//1. 获取购销合同ids
			String contractIds = t.getContractIds();
			
			//2. 根据购销合同的ids，查询货物
			// select * from contract_product_c where contract_id in ('11','22');
			List<ContractProduct> cpList = 
					contractProductDao.findContractProductByContractIds(contractIds.split(","));
			
			//3. 商品集合
			Set<ExportProduct> epSet = new HashSet<>();
			
			//4. 遍历货物； ContractProduct
			if (cpList != null && cpList.size() > 0) {
				for (ContractProduct cp : cpList) {
					//5. 创建商品对象，封装数据。 ContractProduct----> ExportProdcut   
					ExportProduct ep = new ExportProduct();
					// cp---->拷贝到ep
					// org.springframework.beans
					BeanUtils.copyProperties(cp, ep);
					// 设置商品ID为NULL
					ep.setId(null);
					// 设置商品关联报运单
					ep.setExport(t);
					
					//6. 商品附件集合、封装商品附件
					Set<ExtEproduct> exteSet = new HashSet<>(); 
					// 遍历货物附件
					if (cp.getExtCproducts() != null && cp.getExtCproducts().size()>0) {
						for(ExtCproduct extc : cp.getExtCproducts()) {
							// 创建商品附件
							ExtEproduct exte = new ExtEproduct();
							// 对象拷贝: extc---->exte
							BeanUtils.copyProperties(extc, exte);
							// 设置商品附件ID为NULL
							exte.setId(null);
							// 商品附件关联商品
							exte.setExportProduct(ep);
							// 商品附件添加到集合
							exteSet.add(exte);
						}
					}
					// 设置附件的集合到商品对象中
					ep.setExtEproducts(exteSet);
					// 商品添加到集合
					epSet.add(ep);
				}
			}
			
			// 商品集合, 设置到报运单对象中
			t.setExportProducts(epSet);
		}
		
		// 保存或更新
		exportDao.save(t);
	}
	
	@Override
	public void updateExport(ExportResult exportResult) {
		
		//1. 修改报运单信息（状态、备注）
		String exportId = exportResult.getExportId();
		// 根据报运单查询
		Export export = exportDao.findOne(exportId);
		// 修改报运单信息
		export.setState(exportResult.getState());
		export.setRemark(exportResult.getRemark());
		// 修改
		exportDao.save(export);
		
		//2. 修改报运商品（交税金额）
		if (exportResult.getProducts() != null && exportResult.getProducts().size()>0) {
			for (ExportProductResult epr : exportResult.getProducts()) {
				// 获取商品id
				String exportProductId = epr.getExportProductId();
				// 查询
				ExportProduct ep = exportProductDao.findOne(exportProductId);
				ep.setTax(epr.getTax());
				// 修改
				exportProductDao.save(ep);
			}
		}
	}

	@Override
	public List<Export> findByState(Integer state) {
		return exportDao.findByState(state);
	}
}





















