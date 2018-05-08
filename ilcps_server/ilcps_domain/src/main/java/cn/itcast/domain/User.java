package cn.itcast.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="USER_P")
@DynamicInsert(true) // 生成动态的插入SQL, 默认是false
@DynamicUpdate(true) // 生成动态的更新SQL，  默认是false
public class User extends BaseEntity {
	@Id
	@Column(name="USER_ID")
	// 主键生成策略为：手动指定(assigned)
	@GeneratedValue(generator="system-assigned")
	@GenericGenerator(name="system-assigned",strategy="assigned")
	private String id;//编号
	
	// 多对一，关联部门
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	private Dept dept;//用户与部门   多对一
	
	@JSONField(serialize=false)// 取消set集合转换为json格式
	// 多对多，用户关联中间表
	// name 中间表表名称
	// joinColumns 中间表的哪个外键字段引用当前表主键
	// inverseJoinColumns 中间表的哪个外键字段引用集合中对象所在表的主键
	@ManyToMany
	@JoinTable(name="ROLE_USER_P",joinColumns={@JoinColumn(name="USER_ID",referencedColumnName="USER_ID")},
	           inverseJoinColumns={@JoinColumn(name="ROLE_ID",referencedColumnName="ROLE_ID")}
	)
	@OrderBy("ORDER_NO")
	private Set<Role> roles = new HashSet<Role>();//用户与角色   多对多
	
	// 用户与用户扩展信息，一对一
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	private Userinfo userinfo;//一对一   用户与扩展信息
	
	@Column(name="USER_NAME")
	private String userName;//用户名
	@Column(name="PASSWORD")
	private String password;//密码
	@Column(name="STATE")
	private Integer state;//状态   0停用    1启用

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Userinfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}
	
	
}
