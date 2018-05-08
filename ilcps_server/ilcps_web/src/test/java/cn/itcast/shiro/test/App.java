package cn.itcast.shiro.test;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

public class App {

	@Test
	public void md5() throws Exception {
		//1. 对“1” 进行md5加密，使用shiro api
		// c4ca4238a0b923820dcc509a6f75849b
		Md5Hash md5 = new Md5Hash("1");
		System.out.println(md5.toString());
		
		//2. 加密，加盐
		String key = "1234567AAAAA";
		md5 = new Md5Hash("1", key);
		System.out.println(md5.toString());
		
		//3. 加密，加盐，加迭代次数
		md5 = new Md5Hash("1", key,2);// 对加密后的结果在加密
		System.out.println(md5.toString());
		
	}
	
	@Test
	public void sha256() throws Exception {
		// 随机盐
		SecureRandomNumberGenerator srn = new SecureRandomNumberGenerator();
		String key = srn.nextBytes().toHex();	
		System.out.println("随机盐：" + key);

		Sha256Hash sha = new Sha256Hash("1",key,2);
		System.out.println(sha.toString());
		System.out.println(sha.toString().length());
	}
}
