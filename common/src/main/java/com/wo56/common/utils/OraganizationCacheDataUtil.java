package com.wo56.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.identity.vo.BaseUser;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.cache.OraganizationCache;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;



/**
 * @author zjy 
 * 
 * 网点费用配置缓存工具类
 * */
public class OraganizationCacheDataUtil{


	/**
	 * 获取数据列表 key--> ORGANIZATION
	 * @param 
	 * @return List-》Organization
	 */
	public static List<Organization> getOrganizationDataList(){
			List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
			return organizationDataList;
	}
	
	
	
	
	
	/**
	 * 获取数据列表对象
	 * @param orgId 网点id
 	 * @return Organization
	 */
	public static Organization getOrganization(long ordId){
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		if(organizationDataList!=null&&organizationDataList.size()>0){
			for(Organization organization:organizationDataList){
				if(organization.getOrgId()==ordId){
				  return organization;
				}
			}
		}
		return null;
	}

	
	/**
	 * 获取数据列表对象
	 * @param orgId 网点id
 	 * @return Organization
	 */
	public static Organization getOrganizationByTenantId(long tenantId){
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		if(organizationDataList!=null&&organizationDataList.size()>0){
			for(Organization organization:organizationDataList){
				if(organization.getTenantId().longValue()==tenantId&&organization.getParentOrgId()==EnumConsts.ROOT_ORG_ID){
				  return organization;
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * 获取数据列表对象
	 * @param orgId 网点id
 	 * @return Organization
	 */
	public static List<Organization> selectTenant(){
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		if(organizationDataList!=null&&organizationDataList.size()>0){
			List<Organization> rtnList = new ArrayList<Organization>();
			for(Organization organization:organizationDataList){
				if(null != organization.getOrgType() && organization.getOrgType()==SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE){
					rtnList.add(organization);
				}
			}
			if(rtnList.size()>0){
				return rtnList;
			}
		}
		return null;
	}
	
	
	
	
	
	/**
	 * 获取数据列表对象
	 * @param orgId 网点id
 	 * @return orgName 
	 */
	public static String getOrgName(long ordId){
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		if(organizationDataList!=null&&organizationDataList.size()>0){
			for(Organization organization:organizationDataList){
				if(organization.getOrgId()==ordId){
				  return organization.getOrgName();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 获取数据列表对象
	 * @param orgId 网点id
 	 * @return Organization
	 */
	public static List<Organization> getTenantOrg(long tenantId,Integer isParent){
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		if(organizationDataList!=null&&organizationDataList.size()>0){
			List<Organization> rtnList = new ArrayList<Organization>();
			for(Organization organization:organizationDataList){
				if(isParent==null ||isParent==8){
					if(organization.getTenantId()==tenantId && organization.getOrgType()==1){
						rtnList.add(organization);
					}
				}
				else if(isParent==null ||isParent!=1){
						if(organization.getTenantId()==tenantId && organization.getOrgType()!=3){
							rtnList.add(organization);
						}
					}else{
						if(organization.getTenantId()==tenantId ){
							rtnList.add(organization);
						}
					}
				}
				
			if(organizationDataList.size()>0){
				return rtnList;
			}
		}
		return null;
	}
	
	
	/**
	 * 获取数据列表对象
	 * @param orgId 网点id
 	 * @return Organization
	 */
	public static List<Organization> getOrgByRole(int roleId,Long tenantId ){
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		if(organizationDataList!=null&&organizationDataList.size()>0){
			List<Organization> rtnList = new ArrayList<Organization>();
			if(roleId==SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR){
				for(Organization organization:organizationDataList){
					if(organization.getOrgId()!=organization.getTenantId().longValue()){
						rtnList.add(organization);
				}
				}
			}else if(roleId==SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER){
				for(Organization organization:organizationDataList){
						if(organization.getTenantId()==tenantId.longValue() && organization.getOrgId()!=tenantId.longValue()){
							rtnList.add(organization);
					}
				}
			}else{
				for(Organization organization:organizationDataList){
					if(organization.getOrgId()==tenantId.longValue()){
						rtnList.add(organization);
				}
			 }
			}
			if(organizationDataList.size()>0){
				return rtnList;
			}
		}
		return null;
	}
	
	/**
	 * 获取角色对应的总公司
	 * @param orgId 网点id
 	 * @return Organization
	 */
	public static List<Organization> selectTenantByRole(int roleId,Long tenantId){
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		if(organizationDataList!=null&&organizationDataList.size()>0){
			List<Organization> rtnList = new ArrayList<Organization>();
			if(roleId==SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR){
				for(Organization organization:organizationDataList){
					if(null != organization.getOrgType() && organization.getOrgType()==SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE){
						rtnList.add(organization);
					}
				}
			}else{
				for(Organization organization:organizationDataList){
					if(null != organization.getOrgType() && organization.getOrgType()==SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE &&  organization.getOrgId()==tenantId.longValue()){
						rtnList.add(organization);
					}
				}
			}
			
			if(rtnList.size()>0){
				return rtnList;
			}
		}
		return null;
	}




    /***
     * 获取同级租户下所有的组织集合（不包括承运商）
     * @param tenantId
     */
	public static List<Long> getOrgIdsByTenantId(Long tenantId) {
		List<Long> lists = new ArrayList<Long>();
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		if(organizationDataList!=null&&organizationDataList.size()>0){
			
			
			for(Organization o : organizationDataList){
				if(o.getTenantId() == tenantId.longValue() && o.getOrgType() != SysStaticDataEnum.ORG_TYPE.CARRIER_ORG){
					lists.add(o.getOrgId());
				}
			}
		}
		
		return lists;
	}
	
	/**
	 * 获取登陆专线下的网点列表（如果登陆的是专线，不是父级只查当前）
	 * @param orgId 当前网点ID
	 */
	public static List<Map<String,String>> getOrganizationByLogin(Long orgId){
		    List<Map<String,String>> listOut = new ArrayList<Map<String,String>>();
		    Organization o = OraganizationCacheDataUtil.getOrganization(orgId);
		    if(o.getParentOrgId() != EnumConsts.ROOT_ORG_ID){
		    	Map<String, String> m = new HashMap<String,String>();
	    		m.put("tenantId", o.getTenantId()+"");
				m.put("orgId", o.getOrgId()+"");
				m.put("orgName", o.getOrgName());
				listOut.add(m);
		    }else{
		    	List<Organization> os = getOrganizationDataList();
		    	for(Organization o2 : os){
		    		if(o2.getTenantId().longValue() == o.getTenantId().longValue() && o2.getOrgType() != SysStaticDataEnum.ORG_TYPE.CARRIER_ORG){
			    		 Map<String, String> m = new HashMap<String,String>();
				    	 m.put("tenantId", o2.getTenantId()+"");
						 m.put("orgId", o2.getOrgId()+"");
						 m.put("orgName", o2.getOrgName());
						 listOut.add(m);
			          }
		        }
		    
		    	
		     }
		    
			
			
			
			return listOut;
	}
	
	
	/**
	 * 获取登陆专线下的网点列表
	 * @param orgId 当前网点ID
	 */
	public static List<Map<String,String>> getOrganizationByTenantId(){
		    List<Map<String,String>> listOut = new ArrayList<Map<String,String>>();
		    Organization o = OraganizationCacheDataUtil.getOrganizationByTenantId(SysContexts.getCurrentOperator().getTenantId());
		    if(o.getParentOrgId() == EnumConsts.ROOT_ORG_ID){
		    	Map<String, String> m = new HashMap<String,String>();
	    		m.put("tenantId", o.getTenantId()+"");
				m.put("orgId", o.getOrgId()+"");
				m.put("orgName", o.getOrgName());
				listOut.add(m);
		    }
			return listOut;
	}
	
	/**
	 * 获取当前网点租户下的所有网点（除了当前网点）
	 * @return
	 */
	public static List<Map<String,String>> getOrganizationByCurOrg(){
		BaseUser user = SysContexts.getCurrentOperator();
		long tenantId= user.getTenantId();
		long orgId = user.getOrgId();
		List<Organization> orgList = getOrganizationDataList();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(orgList != null && orgList.size() > 0){
			for (Organization organization : orgList) {
				if(tenantId == organization.getTenantId() && orgId != organization.getOrgId() && organization.getParentOrgId() != -1){
					Map<String,String> map = new HashMap<String, String>();
					map.put("arrivedOrgId", String.valueOf(organization.getOrgId()));
					map.put("arrivedOrgName", organization.getOrgName());
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取租户下所有的网点信息 (网点)
	 * @param tenantId 租户ID
	 */
	@SuppressWarnings("unchecked")
	public static List<Organization> getOrganizationByTenantIdAndOrgType(Long tenantId){
		List<Organization> organizationDataList = (List<Organization>)CacheFactory.get(OraganizationCache.class, "ORGANIZATION");
		List<Organization> lists = new ArrayList<Organization>();
		if(organizationDataList!=null&&organizationDataList.size()>0){
			for(Organization organization:organizationDataList){
				if (organization.getState() == SysStaticDataEnum.STS.VALID) {
					if(organization.getTenantId().longValue() == tenantId 
							&& (organization.getOrgType() == SysStaticDataEnum.ORG_TYPE.TRADING_ORG
								|| organization.getOrgType() == SysStaticDataEnum.ORG_TYPE.FOXTOWN_CENTER
								    || organization.getOrgType() == SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE
								       || organization.getOrgType() == SysStaticDataEnum.ORG_TYPE.TRANSFER_ORG)){
						lists.add(organization);
						
					}
				}
			}
		}
		return lists;
	}
}
