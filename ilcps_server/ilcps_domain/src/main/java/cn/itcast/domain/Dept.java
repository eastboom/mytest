package cn.itcast.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity  // 标识一个持久化对象
@Table(name="dept_p")
@DynamicInsert(true) 
public class Dept {

	// 主键, 部门编号
	@Id
	// 主键生成策略： uuid作为主键
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name="dept_id")
	private String id;
	
	// 部门名称
	@Column(name="dept_name")
	private String deptName;
	
	// 需求： 把parent对象，映射到数据库，对用一个外键字段。
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Dept parent;
	
	// 在fastjson中，取消对象属性序列化，不转换json格式
	// @JSONField(serialize=false)
	// 部门状态0暂停； 1正常
	@Column(name="state")
	private Integer state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Dept [id=" + id + ", deptName=" + deptName + ", state=" + state + "]";
	}

}





