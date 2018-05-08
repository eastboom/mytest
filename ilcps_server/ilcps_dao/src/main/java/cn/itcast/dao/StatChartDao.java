package cn.itcast.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.Contract;

/**
 * 统计分析的dao
 * @author Administrator
 *
 */
public interface StatChartDao extends BaseDao<Contract, String>{


	/**
	 * 需求1:根据生产厂家统计销售情况
	 * nativeQuery=true 原生sql
	 * @return
	 */                                                                                        
	@Query(value="select p.factory_name,sum(p.amount) amount from (                            "
		+"  select f.factory_name,sum(cp.amount) amount                                        "
		+"  from contract_product_c cp inner join factory_c f on cp.factory_id=f.factory_id    "
		+"  group by f.factory_name                                                            "
		+"  union all                                                                          "
		+"  select f.factory_name,sum(e.amount) amount                                         "
		+"  from ext_cproduct_c e inner join factory_c f on e.factory_id=f.factory_id          "
		+"  group by f.factory_name                                                            "
		+") p group by p.factory_name",nativeQuery=true)                                       
	List<Object[]> factorySale();
	
	/**
	 * 需求2： 产品销售排行
	 */
	@Query(value="select p.* from("
		+" select cp.product_no,nvl(sum(cp.amount),0) amount from contract_product_c cp "
		+" group by cp.product_no order by nvl(sum(cp.amount),0) desc"
		+" ) p where rownum<=?",nativeQuery=true)
	List<Object[]> productSale(int topNum);
	
	/**
	 * 需求3：系统访问压力图
	 */
	@Query(value="select t.a1 hour,nvl(p.num,0) num from online_info_t t "
			+"left join (                                                "
			+"  select                                                   "
			+"     to_char(g.login_time,'hh24') hour,                    "
			+"     count(login_time) num                                 "
			+"  from login_log_p g group by to_char(g.login_time,'hh24') "
			+") p on t.a1=p.hour order by t.a1",nativeQuery=true)
	List<Object[]> onlineInfo();
}










