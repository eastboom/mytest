package cn.itcast.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PACKING_LIST_C")
@DynamicInsert(true)
@DynamicUpdate(true)
public class PackingList{
	@Id
	@Column(name="PACKING_LIST_ID")
	@GeneratedValue(generator="my-assigned")
	@GenericGenerator(name="my-assigned",strategy="assigned")
	private String id;
	
	@Column(name="SELLER")
	private String seller;
	
	@Column(name="BUYER")
	private String buyer;
	
	@Column(name="INVOICE_NO")
	private String invoiceNo;
	
	@Column(name="INVOICE_DATE")
	private Date invoiceDate;
	
	@Column(name="MARKS")
	private String marks;
	
	@Column(name="DESCRIPTIONS")
	private String descriptions;
	
	@Column(name="EXPORT_IDS")
	private String exportIds;
	
	@Column(name="EXPORT_NOS")
	private String ExportNos;
	
	@Column(name="STATE")
	private Integer state;
	
	@Column(name="CREATE_BY")
	private String createBy;
	
	@Column(name="CREATE_DEPT")
	private String createDept;
	
	@Column(name="CREATE_TIME")
	protected Date createTime;//创建时间
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getExportIds() {
		return exportIds;
	}
	public void setExportIds(String exportIds) {
		this.exportIds = exportIds;
	}
	public String getExportNos() {
		return ExportNos;
	}
	public void setExportNos(String exportNos) {
		ExportNos = exportNos;
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
	
	
	
	
	
	
}
