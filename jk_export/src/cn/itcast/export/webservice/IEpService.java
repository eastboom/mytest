package cn.itcast.export.webservice;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import cn.itcast.export.vo.ExportResult;
import cn.itcast.export.vo.ExportVo;

@Produces("*/*")   
public interface IEpService {
	// 电子报运： （1） 往海关平台的mysql数据库插入报运信息、报运商品信息
	@POST
	@Path("/ep")
	@Consumes({"application/xml","application/json"})
	public void exportE(ExportVo export) throws Exception;
	
	// 电子报运： （2） 根据报运单的id查询报运结果： 报运单、报运商品、交税金额、报运状态
	@GET
	@Path("/ep/{id}")
	@Consumes({"application/xml","application/json"})
	@Produces({ "application/xml", "application/json" })
	public ExportResult getResult(@PathParam("id") String id) throws Exception;
}
