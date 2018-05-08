package cn.itcast.fastjson.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.itcast.domain.Dept;

public class App {

	/**
	 * 1. 使用JSON.toJSONString(dept);方法实现转换
	 * 2. 取消对象属性序列化 @JSONField(serialize=false)
	 * 3. 禁止循环引用调用，在转换时候设置参数：
	 *    SerializerFeature.DisableCircularReferenceDetect
	 */
	@Test
	public void toJsonString() throws Exception {
		// 对象
		Dept dept1 = new Dept();
		dept1.setId("100");
		dept1.setDeptName("取经部");
		dept1.setState(1);
		
		// 对象---》json , 
		// {"id":"100"}
		// {"deptName":"取经部","id":"100","state":1}
		String jsonString = JSON.toJSONString(dept1);
		System.out.println(jsonString);
		
		// 创建list集合，集合转换为json  [{}]
		List<Dept> list = new ArrayList<Dept>();
		list.add(dept1);
		list.add(dept1);
		// 转换
		// [{"deptName":"取经部","id":"100"},{"$ref":"$[0]"}]
		// [{"deptName":"取经部","id":"100"},{"deptName":"取经部","id":"100"}]
		jsonString = JSON.toJSONString(list,SerializerFeature.DisableCircularReferenceDetect);
		System.out.println(jsonString);
	}
}











