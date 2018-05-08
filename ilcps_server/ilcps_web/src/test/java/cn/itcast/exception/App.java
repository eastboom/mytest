package cn.itcast.exception;

import java.lang.reflect.InvocationTargetException;


import org.junit.Test;


public class App {
	
	// 检查异常：Exception
	@Test
	public void test2() {
		try {
			// 检查异常应用： 如果希望把异常作为方法返回结果时候，可以使用。其他情况，都建议采用运行时期异常。
			org.apache.commons.beanutils.BeanUtils.copyProperties(null, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			// 方法在执行某个节点时候，出现异常。（方法返回信息）
			// 调用者来说，就可以根据需要处理异常。
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	// 运行时期异常:RuntimeException
	@Test
	public void test1() {
		org.springframework.beans.BeanUtils.copyProperties(null, null);
	}
	


}
