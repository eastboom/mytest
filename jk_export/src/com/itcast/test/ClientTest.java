package com.itcast.test;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.itcast.export.vo.ExportProductVo;
import cn.itcast.export.vo.ExportResult;
import cn.itcast.export.vo.ExportVo;

public class ClientTest {
	@Test
	public void testClient(){
		ExportVo evo = new ExportVo();
		String id = UUID.randomUUID().toString();
		evo.setId(id);
		evo.setExportId("454534");
		evo.setBoxNums(1000);
		evo.setDestinationPort("上海");
		evo.setShipmentPort("北京");
		evo.setTransportMode("陆运");
		evo.setExportDate(new Date());
		evo.setExportId("1");
		evo.setGrossWeights(45.0);
		evo.setPriceCondition("L/C");
		evo.setGrossWeights(200d);
		
		
		ExportProductVo vo = new ExportProductVo();
		vo.setId(UUID.randomUUID().toString());
		
		vo.setExportProductId("ep001");
		vo.setEid(id);
		vo.setFactoryId("111");
		vo.setProductNo("222");
		vo.setPackingUnit("333");
		vo.setBoxNum(1);vo.setCnumber(1);
		vo.setExportProductId("1");
		vo.setExPrice(200d);
		vo.setExportId("1");
		
		
		evo.getProducts().add(vo);
		WebClient client = WebClient.create("http://localhost:8082/jk_export/ws/export/user")
				.type(MediaType.APPLICATION_XML);
		client.put(evo);
		
		
	   
	}
	
	@Test
	public void testClientResult(){
		String id = "75c2c49c-a975-4de3-8a7f-daf5a7195719";
		ExportResult exportResult = WebClient.create("http://localhost:8080/jk_export/ws/export/user/"+id).accept(MediaType.APPLICATION_XML)
		.get(ExportResult.class);
		System.out.println(JSON.toJSONString(exportResult));
	}
}
