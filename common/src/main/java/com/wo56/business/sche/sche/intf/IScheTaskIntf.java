package com.wo56.business.sche.sche.intf;

import java.util.Map;

public interface  IScheTaskIntf {

	/***费用更改*/
	public Map doModifyFee(Map<String, Object> inParam) throws Exception;
	/***已签收师傅更改跟费用修改**/
	public Map doModifySfAndFee(Map<String, Object> inParam) throws Exception;
	/****运单详情的请求逻辑***/
	public Map queryOrdBillDetail(Map<String,Object> inParam) throws Exception;
	
	/****运单详情的请求逻辑  日志请求***/
	public Map queryOrdBillDetailOfLog(Map<String,Object> inParam) throws Exception;
	
	/*****运单详情的查询 签收的信息*******/
	public Map queryOrdBillDetailOfSign(Map<String,Object> inParam) throws Exception;
	
	public Map queryOrdBillDetailOfExp(Map<String,Object> inParam) throws Exception;
	
	public Map queryOrdBillDetailOfTask(Map<String,Object> inParam) throws Exception;
	
	public Map<String, Object> queryOrdBusiLog(Map<String,Object> inParam);
	
	/***任务跟踪*/
	public Map taskTrace(Map<String, Object> inParam) throws Exception;
	
	public Map<String,Object> getOrdLogAndOrdBusi(Map<String, Object> inParam) throws Exception;	
}
