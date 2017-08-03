package com.wo56.business.cm.intf;

import java.util.Map;

public interface ISpecialLineCompanyNexusIntf {
	/****查询专线拉与包公司关系**/
	public Map<String, Object>doQuerySpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception;
	/****新增专线与拉包公司关系**/
	public Map<String, String>doSaveSpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception;
	/****删除专线与拉包公司关系**/
	public Map<String,String>deleteSpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception;
	/****根据id查询专线与拉包公司**/
	public Map<String, String>queryByIdSpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception;
	/****根据id查询专线与拉包公司**/
	public Map<String, String>doUpdateSpecialLineCompanyNexus(Map<String, Object> inParam) throws Exception;

}
