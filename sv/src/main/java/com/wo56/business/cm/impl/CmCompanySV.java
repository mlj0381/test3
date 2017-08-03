package com.wo56.business.cm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.business.cm.vo.CmCompanyInfo;
import com.wo56.common.consts.EnumConsts;


public class CmCompanySV {

	/**
	 * 查询公司信息
	 * @param tenantCode
	 * @return CmCompanyInfo
	 * @throws Exception
	 */
	public CmCompanyInfo getCompanyInfoByTenantCode(String tenantCode)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		List<CmCompanyInfo> companyInfos = session.createCriteria(CmCompanyInfo.class).add(Restrictions.eq("tenantCode", tenantCode)).list();
		if(companyInfos!=null&&companyInfos.size()>0){
			return companyInfos.get(0);
		}
		return null;
	}
	
	public CmCompanyInfo getCompanyInfoByTenantId(long tenantId)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		List<CmCompanyInfo> companyInfos = session.createCriteria(CmCompanyInfo.class).add(Restrictions.eq("tenantId", tenantId)).list();
		if(companyInfos!=null&&companyInfos.size()>0){
			return companyInfos.get(0);
		}
		return null;
	}
	/**
	 * 保存公司信息
	 * @param companyInfo
	 * @throws Exception
	 */
	public void saveCompanyInfo(CmCompanyInfo companyInfo)throws Exception{
		Session session = SysContexts.getEntityManager();
		session.save(companyInfo);
	}
	/**
	 * 更新公司信息
	 * @param companyInfo
	 * @throws Exception
	 */
	public void doUpdateCompanyInfo(CmCompanyInfo companyInfo)throws Exception{
		Session session = SysContexts.getEntityManager();
		session.update(companyInfo);
	}
	/**
	 * 查询公司列表
	 * @param companyInfo
	 * @throws Exception Map
	 */
	public Map doQueryCompanys(Map<String,Object> inParam)throws Exception{
		long provinceId = DataFormat.getLongKey(inParam, "provinceId");
		long cityId = DataFormat.getLongKey(inParam, "cityId");
		String deptName = DataFormat.getStringKey(inParam, "companyName");
		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
		
		Criteria ca =  SysContexts.getEntityManager().createCriteria(CmCompanyInfo.class);
		
		if(provinceId>0){
			ca.add(Restrictions.eq("provinceId", provinceId));
		}
		
		if(cityId>0){
			ca.add(Restrictions.eq("cityId", cityId));
		}
		
		if(!StringUtils.isEmpty(deptName)){
			ca.add(Restrictions.like("deptName", deptName));
		}
		
		if(!StringUtils.isEmpty(tenantCode)){
			ca.add(Restrictions.like("tenantCode", tenantCode));
		}
		IPage p = PageUtil.gridPage(ca);
		Pagination page = new Pagination(p);
		Map rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(page));
		return rtnMap;
	}
	
	/**
	 * 查询公司列表
	 * @param companyInfo
	 * @throws Exception Map
	 */
	public List doGetCompanys(Map<String,Object> inParam)throws Exception{
		int roleType = DataFormat.getIntKey(inParam, "roleType");
		Session session = SysContexts.getEntityManager(true);
		BaseUser baseuser = SysContexts.getCurrentOperator();
		Criteria ca = session.createCriteria(CmCompanyInfo.class);
         if(roleType!=0 && roleType!=1){
        	 ca.add(Restrictions.eq("tenantId",Long.valueOf(baseuser.getTenantId()) ));
	    }
	    List<CmCompanyInfo> companyList = new ArrayList<CmCompanyInfo>();
	    companyList= ca.list();
	    
		return companyList;
	}
	/**
	 * 查找所有公司信息
	 * @param inParam
	 * @return
	 *  	  String companyName;
	 		  String tenantCode; 公司编码
	          String jdAmount; 	 接单能力
		 	  String hasJdAmount; 当前已接单数
	          String serviceType 服务类型;
		      String storeAddr;仓库地址
			  String storeEand;地址经纬度
	 * 
	 * @throws Exception
	 */
	public List doQueryAllCompanys(Map<String,Object> inParam)throws Exception{
		long provinceId = DataFormat.getLongKey(inParam, "provinceId");
		long cityId = DataFormat.getLongKey(inParam, "cityId");
		StringBuffer sb = new StringBuffer("");
		sb.append(" SELECT company2.Province_id ,company2.City_id ,comp.company_name , comp.tenant_code ,comp.tenant_id ,comp.largest_accept_order,comp.store_addr,comp.store_EAND,comp.store_NAND,GROUP_CONCAT(DISTINCT(ser.product_name)) AS product_name ,GROUP_CONCAT(DISTINCT(ser.service_name)) AS service_name FROM cm_contract_company comp LEFT JOIN cm_company_info company2  ON comp.contract_tenant_code =company2.tenant_code LEFT JOIN cm_service_inst ser ON comp.contract_id=ser.service_obj_id WHERE comp.ending_date>CURDATE()  ");
		if(provinceId>0){
			sb.append(" and comp.province_Id =:provinceId");
		}
		if(cityId>0){
			sb.append(" and comp.city_Id =:cityId");
		}
		sb.append(" GROUP BY comp.contract_id ");
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery(sb.toString());
		if(provinceId>0){
			query.setParameter("provinceId", provinceId);
		}
		if(cityId>0){
			query.setParameter("cityId", cityId);
		}
		IPage p = PageUtil.gridPage(query);
		Pagination<Object[]> page = new Pagination<Object[]>(p);
		List colList= page.getItems();
		List mapList = new ArrayList();
		if(colList!=null&&colList.size()>0){
			for(int i=0;i<colList.size();i++){
				Object[] objs = (Object[])colList.get(i);
				Map<String ,Object> objMap = new HashMap();
				objMap.put("name",objs[0]);
				objMap.put("tenantCode",objs[1]);
				objMap.put("jdAmount",objs[2]);
				objMap.put("storeAddr",objs[3]);
				objMap.put("storeEand",objs[4]);
				objMap.put("storeNand",objs[5]);
				objMap.put("productNames",objs[6]);
				objMap.put("serviceName",objs[7]);
				objMap.put("tenantId",objs[8]);

				//剩余接单数
				String leaveOrder = RemoteCacheUtil.get(EnumConsts.REMOTE_CACHE_KEY.COMPANY_JD_CACHE_KEY_LEAVE+"_"+objs[1]);
				if(StringUtils.isEmpty(leaveOrder)){
					leaveOrder="0";
				}
				
				int maxOrders  = DataFormat.getIntKey(objs[2]);
				int leaveOrderNum = Integer.valueOf(leaveOrder);
				objMap.put("hasJdAmount",String.valueOf(maxOrders-leaveOrderNum));
				mapList.add(objMap);
			}
		}
		return mapList;
	}
	
}
