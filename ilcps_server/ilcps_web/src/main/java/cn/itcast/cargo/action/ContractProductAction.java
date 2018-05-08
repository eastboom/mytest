package cn.itcast.cargo.action;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Contract;
import cn.itcast.domain.ContractProduct;
import cn.itcast.domain.Factory;
import cn.itcast.service.ContractProductService;
import cn.itcast.service.FactoryService;
/**
 * 货物action
 * @author Administrator
 *
 */
@Namespace("/cargo")
@Results({
	// 保存成功，跳转到当前页面（jContractProductCreate.jsp）
	@Result(name="alist",params={"contract.id","${contract.id}"},type="redirectAction",location="contractProductAction_contractProduct")
})
public class ContractProductAction extends BaseAction<ContractProduct>{
	private static final long serialVersionUID = 1L;
	
	// 注入service
	@Resource
	private ContractProductService contractProductService;
	@Resource
	private FactoryService factoryService;
	
	
	/**
	 * 1. 进入jContractProductCreate.jsp页面
	 */
	@Action(value="contractProductAction_contractProduct",
			results={@Result(name="contractProduct",location="/WEB-INF/pages/cargo/contract/jContractProductCreate.jsp")})
	public String contractProduct(){
		//1.0请求参数 （从jContractList.jsp列表页面传入的购销合同id，contract.id）
		final String contractId = getModel().getContract().getId();
		
		//1.1 查看货物的厂家
		List<Factory> factoryList = factoryService.findByCtype("货物");
		set("factoryList", factoryList);
		
		
		//1.2查询货物列表（放入值栈，key是results, 页面取出货物列表）
		// 原生SQL： select * from contract_product_c where contract_id='';
		
		// JPA :  需要2表连接查询.  from ContractProduct cp where cp.contract.id=?
		// Specification查询： 需要2表关联查询
		
		// 构造条件表达式（Specification两表关联查询）
		Specification<ContractProduct> spec = new Specification<ContractProduct>() {
			public Predicate toPredicate(Root<ContractProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 查询条件：ContractProduct对象的购销合同id(contract.id)
				
				//root.get(是指ContractProduct对象的属性)
				//root.get(contract.id 这种写法不支持)
				
				// 关联Contract对象
				// 参数1： 关联ContractProduct对象的哪个属性，或者说关联哪个表
				// 参数2： 两表关联的类型：inner join、left join 、 right join
				// 相当于: sql=select * from contract_product_c cp inner join contract_c c on cp.contract_id=c.contract_id
				Join<ContractProduct, Contract> join = root.join("contract",JoinType.INNER);
				// 构造查询的条件
				Expression<String> exp = join.get("id").as(String.class);
				// 设置条件值返回Predicate条件表达式对象.   sql+=" where c.contract_id=?"
				return cb.equal(exp, contractId);
			}
		};
		// 调用service查询货物
		List<ContractProduct> list = contractProductService.findAll(spec);
		// 放入值栈
		set("results", list);
		
		
		return "contractProduct";
	}
	
	/**
	 * 2. 保存货物
	 */
	@Action(value="contractProductAction_insert")
	public String insert(){
		//getModel().getContract().getId();
		
		//3.1 调用service新增保存
		contractProductService.saveOrUpdate(getModel());
		//3.2保存成功，跳转到当前页面
		return "alist";
	}
	
	/**
	 * 3. 进入货物修改页面
	 */
	@Action(value="contractProductAction_toupdate",
			results={@Result(name="toupdate",location="/WEB-INF/pages/cargo/contract/jContractProductUpdate.jsp")})
	public String toupdate(){
		//6.1 根据id查询
		ContractProduct contractProduct =  contractProductService.findOne(getModel().getId());
		//6.2放入值栈
		push(contractProduct);
		
		//6.3查询货物工厂
		List<Factory> factoryList = factoryService.findByCtype("货物");
		set("factoryList", factoryList);
		
		return "toupdate";
	}
	
	/**
	 * 4. 修改
	 */
	@Action(value="contractProductAction_update")
	public String update(){
		//getModel().getContract().getId();  页面传入数据，放入值栈        struts.xml中${contract.id}
		
		//4.1 根据货物id查询
		ContractProduct cp = contractProductService.findOne(getModel().getId());
		
		//4.2 修改持久化对象属性值为用户输入的数据
		cp.setProductNo(getModel().getProductNo());
		cp.setProductImage(getModel().getProductImage());
		cp.setCnumber(getModel().getCnumber());
		cp.setPackingUnit(getModel().getPackingUnit());
		cp.setLoadingRate(getModel().getLoadingRate());
		cp.setBoxNum(getModel().getBoxNum());
		cp.setPrice(getModel().getPrice());
		cp.setProductDesc(getModel().getProductDesc());
		cp.setProductRequest(getModel().getProductRequest());
		
		//4.3修改
		contractProductService.saveOrUpdate(cp);
		return "alist";
	}
	
	/**
	 * 5. 删除
	 */
	@Action(value="contractProductAction_delete")
	public String delete(){
		// 页面提交地址：contractProductAction_delete.action?id=${o.id}&contract.id=${o.contract.id}
		// 调用service删除
		contractProductService.delete(getModel().getId());
		return "alist"; //getModel().getContract().getId();
	}

}





