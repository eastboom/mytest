package cn.itcast.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="INVOICE_C")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Invoice {
	
	@Id
	@Column(name="INVOICE_ID")
	@GeneratedValue(generator="my-assigned")
	@GenericGenerator(name="my-assigned",strategy="assigned")
	private String id;

//	   SC_NO                varchar2(100),
//	   BL_NO                varchar2(100),
//	   TRADE_TERMS          varchar2(100),
//	   STATE                NUMBER(11),
//	   CREATE_BY            varchar2(40),
//	   CREATE_DEPT          varchar2(40),
//	   CREATE_TIME          TIMESTAMP(6) default systimestamp not null,
	@Column(name="SC_NO")
	private String scNo;
	
	@Column(name="BL_NO")
	private String blNo;
	
	@Column(name="TRADE_TERMS")
	private String tradeTerms;
	
	@Column(name="STATE")
	private Integer state; // 0 草稿 	1还没新增财务报运单  2已结有对应的财务报运单
	
	@Column(name="CREATE_BY")
	protected String createBy;//创建者的id
	
	@Column(name="CREATE_DEPT")
	protected String createDept;//创建者所在部门的id
	
	@Column(name="CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createTime;//创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScNo() {
		return scNo;
	}

	public void setScNo(String scNo) {
		this.scNo = scNo;
	}

	public String getBlNo() {
		return blNo;
	}

	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}

	public String getTradeTerms() {
		return tradeTerms;
	}

	public void setTradeTerms(String tradeTerms) {
		this.tradeTerms = tradeTerms;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", scNo=" + scNo + ", blNo=" + blNo + ", tradeTerms=" + tradeTerms + ", state="
				+ state + ", createBy=" + createBy + ", createDept=" + createDept + ", createTime=" + createTime + "]";
	}
	
	
	
	
	
	
}
