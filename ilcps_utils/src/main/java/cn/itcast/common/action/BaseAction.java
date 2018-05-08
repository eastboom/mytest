package cn.itcast.common.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	// 模型对象，封装请求数据
	private T model;
	// 模型对象的类型
	private Class<T> clazz;  //如： Dept.class
	
	// 在创建子类对象时候，先执行父类的构造函数
	public BaseAction() {

//		System.out.println("当前运行类：" + this);//DeptAction
//		System.out.println("当前运行类字节码:" + this.getClass());//DeptAction.class
//		System.out.println("当前运行类的父类：" + this.getClass().getSuperclass()); //BaseAction.class
//		System.out.println("当前运行类的泛型父类："+this.getClass().getGenericSuperclass());// BaseAction<Dept>
		
		// 1. 获取当前运行类的泛型父类，BaseAction<Dept>
		//    java.lang.reflect.Type; 反射包下的API
		Type type = this.getClass().getGenericSuperclass();
		// 2. 转换为“参数化类型”
		ParameterizedType pt = (ParameterizedType) type;
		// 3. 通过参数化类型对象，获取参数化类型中实际类型定义， BaseAction<Dept>中的Dept.class
		Type[] typeArgs = pt.getActualTypeArguments();
		// 4. 获取数组第一个元素，就是Dept.class
		clazz = (Class<T>) typeArgs[0];
		
		// 5. 创建对象
		try {
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public T getModel() {
		return model;
	}
	
	
	// 封装分页参数
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public int getPage() {
		return page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getRows() {
		return rows;
	}
	
	
	/**
	 * 对象转换为json并输出
	 */
	public void parseObjToJson(Object obj){
		// 对象转换为json
		String jsonString = 
				JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
		try {
			//2.4响应,返回json
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 对象放入值栈
	 */
	public void push(Object obj){
		ActionContext.getContext().getValueStack().push(obj);
	}
	public void set(String key,Object obj){
		ActionContext.getContext().getValueStack().set(key, obj);;
	}
	
	

}
