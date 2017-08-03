package com.wo56.business.cm.intf;

import java.util.List;
import java.util.Map;

import com.wo56.business.org.vo.out.OrgQueryMatserOut;
import com.wo56.business.org.vo.out.OrgSavePullPagParamOut;

public interface IOrganizationIntf {
	/****左边列表查询**/
	public List<OrgQueryMatserOut> doQueryLeftMaster(Map<String,Object> inParam)throws Exception;
	/****保存授权信息**/
	public Map<String,Object> doSaveAuthOrg(Map<String,Object> inParam)throws Exception;
	/****保存拉包公司**/
	public Map<String, String>doSavePullPagCompany(Map<String, Object> inParam) throws Exception;
	/****查询拉包公司**/
	public  Map<String,Object> doQueryPullPagCompany(Map<String, Object> inParam) throws Exception;
	/****查询所有拉包公司**/
	public Map<String, Object>queryByCompany(Map<String, Object> inParam) throws Exception;
	/****根据id查询拉包公司**/
	public Map<String, Object> queryByIdOrgCompanyList(Map<String, Object> inParam) throws Exception;
	/****根据id修改拉包公司**/
	public Map<String, String>updateByIdOrgCompanyList(Map<String, Object> inParam) throws Exception;
}
