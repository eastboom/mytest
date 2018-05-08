package cn.itcast.cargo.action;

import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.ContractProduct;
import cn.itcast.domain.ExtCproduct;
import cn.itcast.domain.Factory;
import cn.itcast.service.ExtCproductService;
import cn.itcast.service.FactoryService;

@Namespace("/cargo")
@Result(name="extCproductList",params={"contractProduct.id","${contractProduct.id}"},type="redirectAction",location="extCproductAction_tocreate")
public class ExtCproductAction extends BaseAction<ExtCproduct> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ExtCproductService extCproductService;
	@Autowired
	private FactoryService factoryService;
	
	@Action(value="extCproductAction_tocreate",results={@Result(name="extCproduct",location="/WEB-INF/pages/cargo/contract/jExtCproductCreate.jsp")})
	public String tocreate(){
		Specification<ExtCproduct> spec = new Specification<ExtCproduct>() {
			@Override
			public Predicate toPredicate(Root<ExtCproduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<ExtCproduct, ContractProduct> join = root.join("contractProduct",JoinType.INNER);
				Expression<String> expression = join.get("id").as(String.class);
				return cb.equal(expression, getModel().getContractProduct().getId());
			}
		};
		
		Iterable<Factory> factoryList = factoryService.findByCtype("附件");
		set("factoryList", factoryList);
		List<ExtCproduct> results  = extCproductService.findAll(spec);
		set("results",results);
		return "extCproduct";
	}
	
	@Action(value="extCproductAction_insert")
	public String insert(){
		extCproductService.saveOrUpdate(getModel());
		return "extCproductList";
	}
	
	@Action(value="extCproductAction_toupdate",results={@Result(name="toUpdate",location="/WEB-INF/pages/cargo/contract/jExtCproductUpdate.jsp")})
	public String toUpdate(){
		Iterable<Factory> factoryList = factoryService.findByCtype("附件");
		set("factoryList", factoryList);
		ExtCproduct extCproduct = extCproductService.findOne(getModel().getId());
		push(extCproduct);
		return "toUpdate";
	}
	@Action(value="extCproductAction_delete")
	public String delete(){
		extCproductService.delete(getModel().getId());
		return "extCproductList";
	}
	@Action(value="extCproductAction_update")
	public String update(){
		ExtCproduct extCproduct = extCproductService.findOne(getModel().getId());
		extCproduct.setCnumber(getModel().getCnumber());
		extCproduct.setFactory(factoryService.findOne(getModel().getFactory().getId()));
		extCproduct.setFactoryName(factoryService.findOne(getModel().getFactory().getId()).getFactoryName());
		extCproduct.setProductNo(getModel().getProductNo());
		extCproduct.setProductImage(getModel().getProductImage());
		extCproduct.setAmount(getModel().getAmount());
		extCproduct.setPackingUnit(getModel().getPackingUnit());
		extCproduct.setPrice(getModel().getPrice());
		extCproduct.setOrderNo(getModel().getOrderNo());
		extCproduct.setProductDesc(getModel().getProductDesc());
		extCproduct.setProductRequest(getModel().getProductRequest());
		extCproductService.saveOrUpdate(extCproduct);
		return "extCproductList";
	}
}
