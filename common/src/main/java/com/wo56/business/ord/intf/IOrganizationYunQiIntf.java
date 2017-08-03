package com.wo56.business.ord.intf;

import java.util.List;
import java.util.Map;

public interface IOrganizationYunQiIntf {
	public List<Map<String,String>> selectOrgByLink(Map<String,Object> inParam);
	
	public String delOrganization(Map<String,Object> inParam)  throws Exception;
	
	public String chackOrgByUser(Map<String,Object> inParam);

	public List<Long> selectOrgIdByOrgName(String orgName);
}
