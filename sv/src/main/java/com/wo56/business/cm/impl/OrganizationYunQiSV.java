package com.wo56.business.cm.impl;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.org.vo.out.OrgQueryByWeChatOut;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class OrganizationYunQiSV {
	/**
	 * 拉包工获取专线或公司 -- 云启
	 * @param tenantType
	 * @return
	 */
	public Query getTenantByOrg(long tenantType){
		Session session = SysContexts.getEntityManager(true);
		String[] type = null;
		StringBuffer sb = new StringBuffer(" select s.tenant_id,s.NAME,o.TENANT_TYPE from sys_tenant_def s LEFT JOIN organization o ON s.TENANT_ID = o.TENANT_ID WHERE s.STATUS = 1 AND o.PARENT_ORG_ID = -1 ");
		sb.append(" and o.tenant_Type in (:tenantType)");
		Query query = session.createSQLQuery(sb.toString());
		if(tenantType < 0){
			type = new String[]{String.valueOf(SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULLCOM),String.valueOf(SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN)};
			query.setParameterList("tenantType", type);
		}else{
			type = new String[]{String.valueOf(tenantType)};
			query.setParameterList("tenantType", type);
		}
		return query;
	}
	
	public Query selectOrgByLink(){
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("select tenant_id,org_Name from organization where PARENT_ORG_ID = -1 AND TENANT_TYPE = 2 and state=1 ");
		return query;
	}
	
	public Query selectOrgIdByOrgName(String orgName){
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		Long tanentId=user.getTenantId();
		Query query = session.createSQLQuery("select org_id from organization where TENANT_ID=:tenantId and  org_name  like :orgName  ");
		query.setParameter("tenantId",tanentId);
		query.setParameter("orgName", "%"+orgName+"%");	
		return query;
	}
	/**
	 * 微信端查询网点
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map queryOrg(Map<String,String>inParam)throws Exception{
    	Session session = SysContexts.getEntityManager(true);
    	String orgName=DataFormat.getStringKey(inParam, "orgName");
    	if(StringUtils.isEmpty(orgName)){
    		 throw new BusinessException ("请输入网点关键字！");
    	}
    	StringBuffer sb=new StringBuffer();
    	sb.append("SELECT ORG_ID,PROVINCE_ID,REGION_ID, DEPARTMENT_ADDRESS, ORG_PRINCIPAL, ORG_PRINCIPAL_PHONE");
    	sb.append(" FROM organization ");
    	sb.append(" WHERE (DEPARTMENT_ADDRESS LIKE :orgName OR org_name LIKE :orgName)");
    	sb.append(" and STATE=:state");
    	sb.append(" and PARENT_ORG_ID!=-1");
    	Query query=session.createSQLQuery(sb.toString());
    	query.setParameter("orgName", "%"+orgName+"%");
    	query.setParameter("state", SysStaticDataEnum.STS.VALID);
    	List<Object[]> list = query.list();
    	List<OrgQueryByWeChatOut>listOut=new ArrayList<OrgQueryByWeChatOut>();
    	if(list!=null && list.size()>0){
    		for(Object[] obj :list){
    			OrgQueryByWeChatOut out=new OrgQueryByWeChatOut();
    			if(obj[0]!=null){
    				BigInteger orgId = (BigInteger) obj[0];
    				out.setOrgId(orgId.longValue());
    			}
    			if(obj[1]!=null){
    				String provinceName = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(obj[1])).getName();
    				out.setProvinceName(provinceName);
    			}
    			if(obj[2]!=null){
    				String regionName=SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(obj[2])).getName();
    				out.setRegionName(regionName);
    			}
    			if(obj[3]!=null){
    				out.setDepartmentAddress(String.valueOf(obj[3]));
    			}
    			if(obj[4]!=null){
    				out.setOrgPrincipal(String.valueOf(obj[4]));
    			}
    			if(obj[5]!=null){
    				out.setOrgPrincipalPhone(String.valueOf(obj[5]));
    			}
    			listOut.add(out);
    		}
    	}
    	Map map=new HashMap();
    	map.put("items", listOut);
    	return map;
    	
    }
	
	
	
}
