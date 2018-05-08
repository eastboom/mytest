package cn.itcast.cargo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Contract;
import cn.itcast.domain.Export;
import cn.itcast.domain.ExportProduct;
import cn.itcast.domain.PackingList;
import cn.itcast.service.ExportService;
import cn.itcast.service.PackingListService;

@Namespace("/cargo")
@Results({ @Result(name = "alist", type = "redirectAction", location = "packingListAction_list") })
public class PackingListAction extends BaseAction<PackingList> {
	private static final long serialVersionUID = 1L;

	
	private String[] exportId;
	// 注入service
	@Resource
	private PackingListService packingListService;

	@Resource
	private ExportService exportService;

	/**
	 * 1. 进入jPackingList.jsp页面
	 */
	@Action(value = "packingListAction_list", results = { @Result(name = "list", location = "/WEB-INF/pages/cargo/packing/jPackingList.jsp") })
	public String list() {

		return "list";
	}

	/**
	 * 2. jPackingList.jsp datagrid发送的异步请求，返回json格式数据
	 */
	@Action(value = "packingListAction_listResult")
	public void listResult() {

		// 2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage() - 1, getRows());

		// 2.2 调用service,
		Page<PackingList> pageData = packingListService.findAll(pageable);

		List<PackingList> list = pageData.getContent();

		// 2.3构造datagrid需要的json格式数据
		Map<String, Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", list);

		parseObjToJson(results);
	}

	/**
	 * 3. 进入添加页面
	 */
	@Action(value = "packingListAction_tocreate", results = { @Result(name = "tocreate", location = "/WEB-INF/pages/cargo/packing/jPackingCreate.jsp") })
	public String tocreate() {
		List<Export> exports = exportService.findByState(1);
		if(exports!=null&&exports.size()>0){
			
			for (Export export : exports) {
				System.out.println("id=="+export.getId());
				// 附件总数
				int extcNum = 0;
				// 获取报运商品
				Set<ExportProduct> epSet = export.getExportProducts();
				//-->1. 累加货物
				export.setProductNum(epSet.size());
				
				//-->2. 累加附件
				if (epSet != null && epSet.size() > 0) {
					for (ExportProduct ep : epSet) {
						extcNum += ep.getExtEproducts().size();
					}
				}
				// 设置购销合同的附件
				export.setExtcNum(extcNum);
			}
			set("exports", exports);
		}
		return "tocreate";
	}

	/**
	 * 5. 添加保存
	 */
	@Action(value = "packingListAction_insert")
	public String insert() {
		if(exportId!=null&&exportId.length>0){
			String ids="";
			for(String id:exportId){
		
				
				System.out.println("**************"+id);
				ids+=id+",";
			}
			ids = ids.substring(0, ids.length()-1);
			System.out.println("=========="+ids);
			getModel().setExportIds(ids);
			
		}
		// 3.1 调用service新增保存
		packingListService.saveOrUpdate(getModel());
		// 3.2保存成功，跳转到列表
		return "alist";
	}

	/**
	 * 6. 进入修改页面
	 */
	@Action(value = "packingListAction_toupdate", results = { @Result(name = "toupdate", location = "/WEB-INF/pages/cargo/packing/jPackingUpdate.jsp") })
	public String toupdate() {
		// 根据id查询
		PackingList packingList = packingListService
				.findOne(getModel().getId());
		if (packingList != null) {
			// 放入值栈
			push(packingList);
		}
		findExportTovs(packingList);
		return "toupdate";
	}

	/**
	 * 7. 修改保存
	 */
	@Action(value = "packingListAction_update")
	public String update() {
		// 3.1 先根据id查询
		PackingList packingList = packingListService
				.findOne(getModel().getId());
		// 设置持久化对象属性值，为用户输入的数据
		packingList.setBuyer(getModel().getBuyer());
		packingList.setSeller(getModel().getSeller());
		packingList.setMarks(getModel().getMarks());
		packingList.setDescriptions(getModel().getDescriptions());
		packingList.setInvoiceDate(getModel().getInvoiceDate());
		// ...

		// 3.3 保存持久化对象
		System.out.println("创建时间==============="+packingList.getCreateTime());
		packingListService.saveOrUpdate(packingList);
		// 3.2保存成功，跳转到列表
		return "alist";
	}

	// 8. 删除
	@Action(value = "packingListAction_delete")
	public String delete() {
		packingListService.delete(getModel().getId());
		return "alist";
	}



	// 10. 查看
	@Action(value = "packingListAction_toview", results = { @Result(name = "toview", location = "/WEB-INF/pages/cargo/packing/jPackingView.jsp") })
	public String toview() {
		// 根据id查询
		PackingList packingList = packingListService
				.findOne(getModel().getId());
		if (packingList != null) {
			// 放入值栈
			push(packingList);
		}
		findExportTovs(packingList);
		return "toview";
	}

	public void findExportTovs(PackingList packingList) {

		String exportIds = packingList.getExportIds();
		if (StringUtils.isNoneBlank(exportIds)) {
			String[] ids = exportIds.split(",");
			if (ids != null && ids.length > 0) {

				List<Export> exports = new ArrayList<>();
				for (String id : ids) {

					Export export = exportService.findOne(id);
					if (export != null) {

						// 附件总数
						int extcNum = 0;
						// 获取报运商品
						Set<ExportProduct> epSet = export.getExportProducts();
						// -->1. 累加货物
						export.setProductNum(epSet.size());

						// -->2. 累加附件
						if (epSet != null && epSet.size() > 0) {
							for (ExportProduct ep : epSet) {
								extcNum += ep.getExtEproducts().size();
							}
						}
						// 设置购销合同的附件
						export.setExtcNum(extcNum);
						exports.add(export);
					}
				}
				set("exports", exports);
			}
		}
	}
	// 9. 修改购销合同状态
		@Action("packingListAction_updatePackingListState")
		public String updateContractState(){
			//9.1 根据id查询
			PackingList packingList = packingListService.findOne(getModel().getId());
			//9.2修改状态
			packingList.setState(getModel().getState());
			//9.3保存
			packingListService.saveOrUpdate(packingList);
			return "alist";
		}
	public String[] getExportId() {
		return exportId;
	}

	public void setExportId(String[] exportId) {
		this.exportId = exportId;
	}
	
}
