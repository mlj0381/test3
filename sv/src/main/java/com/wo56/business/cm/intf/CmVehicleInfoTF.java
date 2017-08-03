package com.wo56.business.cm.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.impl.CmCustomerSV;
import com.wo56.business.cm.vo.CmDriverInfo;
import com.wo56.business.cm.vo.CmVehicleInfo;
import com.wo56.business.cm.vo.out.CmVehicleInfoParamOut;
import com.wo56.business.cm.vo.out.QueryVehicleDriverOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class CmVehicleInfoTF {

	/**
	 * 接口编码 ： 110002
	 * 查询车辆信息 
	 * @param plateNumber 车牌号码
	 * @return CmVehicleInfoParamOut 
	 * @throws Exception
	 */
	public Map<String,Object> queryVehicle(Map<String,Object> inParam)throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String plateNumber = DataFormat.getStringKey(inParam, "plateNumber");
		int vehicleState = DataFormat.getIntKey(inParam, "vehicleState");
		long orgId = DataFormat.getLongKey(inParam,"orgId");
		long descOrgId = DataFormat.getLongKey(inParam,"descOrgId");
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sql = new StringBuffer("SELECT {v.*} FROM cm_vehicle_info as {v} WHERE 1=1 AND {v}.STATUS=1 ");
		if(vehicleState>0){
			sql.append(" AND {v}.vehicle_state=:vehicleState");
		}
		if(StringUtils.isNotEmpty(plateNumber)){
			sql.append(" AND {v}.plate_number like :plateNumber ");
		}
		if(orgId>0){
			sql.append(" AND {v}.org_id = :orgId ");
		}
		if(descOrgId>0){
			sql.append(" and ({v}.current_org_id=:currentOrgId or {v}.org_id=:orgId) ");
		}
		Query query = session.createSQLQuery(sql.toString()).addEntity("v",CmVehicleInfo.class);
		if(orgId>0){
			query.setParameter("orgId", orgId);
		}
		if(descOrgId>0){
			query.setParameter("currentOrgId", baseUser.getOrgId());
			query.setParameter("orgId", baseUser.getOrgId());
		}
		if(vehicleState>0){
			query.setParameter("vehicleState", vehicleState);
		}
		if(StringUtils.isNotEmpty(plateNumber)){
			query.setParameter("plateNumber", "%"+plateNumber);
		}
		IPage p = PageUtil.gridPage(query);
		Pagination<CmVehicleInfo> page = new Pagination<CmVehicleInfo>(p);
		List<CmVehicleInfo> list = (List<CmVehicleInfo>)page.getItems();
		List<CmVehicleInfoParamOut> rtnList = new ArrayList<CmVehicleInfoParamOut>();
		if(list!= null && list.size()>0){
			for (CmVehicleInfo cmVehicleInfo : list) {
				CmVehicleInfoParamOut paramOut = new CmVehicleInfoParamOut();
				BeanUtils.copyProperties(paramOut, cmVehicleInfo);
				paramOut.setBusinessType(cmVehicleInfo.getBusinessType());
				rtnList.add(paramOut);
				
			}
		}
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		rtnMap=JsonHelper.parseJSON2Map(JsonHelper.json(rtnList));
		rtnMap.put("page", page.getPage());
		rtnMap.put("totalNum", page.getTotalNum());
		rtnMap.put("count", page.getCount());
		rtnMap.put("hasNext", page.isHasNext());
		return rtnMap;
	}
		
	/**
	 * 接口编码 ： 110008
	 * 查询车辆信息 
	 * @param plateNumber 车牌号码
	 * @return CmVehicleInfoParamOut 
	 * @throws Exception
	 */
	public Map<String,Object> queryVehicleInfo(Map<String,Object> inParam)throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		String plateNumber = DataFormat.getStringKey(inParam, "plateNumber");
		int vehicleState = DataFormat.getIntKey(inParam, "vehicleState");
		long orgId = DataFormat.getLongKey(inParam,"orgId");
		int vehicleType = DataFormat.getIntKey(inParam,"vehicleType");
		int businessType=DataFormat.getIntKey(inParam, "businessType");
		Session session = SysContexts.getEntityManager(true);
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		Organization org = OraganizationCacheDataUtil.getOrganization(baseUser.getOrgId());
		if(org.getParentOrgId()!=-1){
			orgId=baseUser.getOrgId();
		}
		int userRole = cmUserInfoTF.getUserRole();
		int actualVolume = -1;
		int actualWeight = -1;
		String engineNo = "";
		String frameNo = "";
		String systemType = "";
		String inputParamJson = StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
		if (StringUtils.isNotBlank(inputParamJson)) {
			Map inputParamMap = JsonHelper.parseJSON2Map(inputParamJson);
			String inPlateNumber = DataFormat.getStringKey(inputParamMap, "plateNumber");
			if (StringUtils.isNotEmpty(inPlateNumber)) {
				plateNumber = inPlateNumber;
			}
			int inVehicleType = DataFormat.getIntKey(inputParamMap, "vehicleTypeName");
			if (inVehicleType > 0) {
				vehicleType = inVehicleType;
			}
			actualVolume = DataFormat.getIntKey(inputParamMap, "actualVolume");
			actualWeight = DataFormat.getIntKey(inputParamMap, "actualWeight");
			engineNo = DataFormat.getStringKey(inputParamMap, "engineNo");
			frameNo = DataFormat.getStringKey(inputParamMap, "frameNo");
			systemType = DataFormat.getStringKey(inputParamMap, "systemType");

		}

		StringBuffer sql = new StringBuffer("SELECT v.* FROM cm_vehicle_info as v WHERE v.STATUS=1 ");
//		if(userRole==SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER || userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
		if(actualVolume>0){
			sql.append(" AND v.ACTUAL_VOLUME=:actualVolume");
		}
		if(actualWeight>0){
			sql.append(" AND v.ACTUAL_WEIGHT=:actualWeight");
		}
		if(StringUtils.isNotEmpty(engineNo)){
			sql.append(" AND v.ENGINE_NO=:engineNo");
		}
		if(StringUtils.isNotEmpty(frameNo)){
			sql.append(" AND v.FRAME_NO=:frameNo");
		}
		if(StringUtils.isNotEmpty(systemType)){
			sql.append(" AND v.SYSTEM_TYPE=:systemType");
		}
		sql.append(" AND v.TENANT_ID=:tenantId");
//		}
		if(vehicleState>0){
			sql.append(" AND v.vehicle_state=:vehicleState");
		}
		if(StringUtils.isNotEmpty(plateNumber)){
			sql.append(" AND v.plate_number like :plateNumber ");
		}
		if(orgId>0 || userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
			sql.append(" AND v.org_id = :orgId ");
		}
		if(vehicleType>0){
			sql.append(" AND  v.vehicle_type = :vehicleType");
		}
		if(businessType>0){
			sql.append(" AND  v.BUSINESS_TYPE = :businessType");	
		}
		sql.append(" order by v.CREATE_DATE desc");
		Query query = session.createSQLQuery(sql.toString()).addEntity(CmVehicleInfo.class);
		if(actualVolume>0){
			query.setParameter("actualVolume", actualVolume);
		}
		if(actualWeight>0){
			query.setParameter("actualWeight", actualWeight);	
		}
		if(StringUtils.isNotEmpty(engineNo)){
			query.setParameter("engineNo", engineNo);	
		}
		if(StringUtils.isNotEmpty(frameNo)){
			query.setParameter("frameNo", frameNo);	
		}
		if(StringUtils.isNotEmpty(systemType)){
			query.setParameter("systemType", systemType);	
		}
		if(orgId>0  || userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
			query.setParameter("orgId", orgId);
		}
		if(vehicleType>0){
			query.setParameter("vehicleType", vehicleType);
		}
		if(vehicleState>0){
			query.setParameter("vehicleState", vehicleState);
		}
		if(businessType>0){
			query.setParameter("businessType", businessType);
		}
		if(StringUtils.isNotEmpty(plateNumber)){
			query.setParameter("plateNumber", "%"+plateNumber);
		}
//		if(userRole==SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER || userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
			query.setParameter("tenantId", Long.valueOf(baseUser.getTenantId()));
			if(userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
				query.setParameter("orgId", baseUser.getOrgId());
				//ca.add(Restrictions.eq("orgId", base.getOrgId()));		
			}
//		}
		IPage p = PageUtil.gridPage(query);
		Pagination page = new Pagination(p);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	/**
	 * 保存、修改车辆信息
	 * 110009
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSaveCmVehicleInfo(Map<String, String> inParam) throws Exception{
		Long vehicleId = DataFormat.getLongKey(inParam, "vehicleId");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		CmVehicleInfo cmVehicleInfo = new CmVehicleInfo();
		if(vehicleId!=null && vehicleId>0){
		  Session session =  SysContexts.getEntityManager();
		  cmVehicleInfo = (CmVehicleInfo)session.get(CmVehicleInfo.class, vehicleId);
		  if(cmVehicleInfo==null){
			  throw new BusinessException("找不到该车辆的信息");
		  }
          if(cmVehicleInfo.getVehicleState()!=1){
        	  throw new BusinessException("车辆工作中，不能修改");
		  }
		}
		BeanUtils.populate(cmVehicleInfo, inParam);
		cmVehicleInfo.setOrgId(baseUser.getOrgId()+"");
		cmVehicleInfo.setCreatorName(baseUser.getUserName());
		cmVehicleInfo.setCreatorId(baseUser.getUserId());
		CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
		String mainDriverName = DataFormat.getStringKey(inParam, "mainDriverName");
		String bill = DataFormat.getStringKey(inParam, "bill");
		cmVehicleInfo.setMainDriverPhone(bill);
		cmCustomerSV.doSaveCmVehicleInfo(cmVehicleInfo,mainDriverName,bill);
		Map map = new HashMap();
		map.put("info", "Y");
		return map;
		
	}
	/**
	 * 根据司机名称查询司机信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List doQueryCmVehicleAndDriver(Map<String, String> inParam) throws Exception{
		String name=DataFormat.getStringKey(inParam, "name");
		CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
		List<QueryVehicleDriverOut> iparmList  = cmCustomerSV.searchUser(name);
		return iparmList;
	}
	
	
	
	
	
	/**
	 * 删除车辆
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doDelVehicle(Map<String, Object> inParam) throws Exception{
		List vehicleIds = (List)inParam.get("vehicleIds");
		Map paramMap = new HashMap();
	     Session session  = SysContexts.getEntityManager();
		if(vehicleIds.size()<=0){
			throw new BusinessException("请传入车辆编号");	
		}
		CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
		cmCustomerSV.doDelVehicle(vehicleIds);
		paramMap.put("info", "Y");
		return paramMap;
	}
	
	
	
	
	/**
	 * 查询车辆详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map querySysVehicleDtl(Map<String, String> inParam) throws Exception{
		long vehicleId=DataFormat.getLongKey(inParam, "vehicleId");
		Session session = SysContexts.getEntityManager(true);
		CmVehicleInfo cmVehicleInfo = (CmVehicleInfo)session.get(CmVehicleInfo.class, vehicleId);
		if(cmVehicleInfo==null){
			throw new BusinessException("查询不到该车辆的信息");	
		}
		Map iparmMap = new HashMap();
		iparmMap.put("cmVehicleInfo", cmVehicleInfo);
		return iparmMap;
	}

	public Map<String, Object> getVehicleInfo(Map<String, Object> inParam) {
		// TODO Auto-generated method stub
		String  vehicleName=DataFormat.getStringKey(inParam, "plateNumber");
		if(StringUtils.isEmpty(vehicleName)){
			 throw new BusinessException("车牌号不能为空");
		}
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		long orgId = user.getOrgId();	
		if(orgId<0){
			throw new BusinessException("当前网点不能为空");
		}
				
		Criteria ca = session.createCriteria(CmVehicleInfo.class);
		ca.add(Restrictions.eq("orgId", orgId+""));
		ca.add(Restrictions.like("plateNumber", "%"+vehicleName+"%"));
		List<CmVehicleInfo> list = ca.list();
		if(list==null && list.size()<=0){
			throw new BusinessException("无此车辆信息！"); 
		}
		List<Map<String, Object>> listOut = new ArrayList<Map<String, Object>>();  
		List<String> codeType = SysStaticDataUtil.getCodeValueListByCodeType("VEHICLE_TYPE");
		if(codeType == null && codeType.size()<=0){
			 throw new BusinessException("在静态表sys_static_data未配置车辆类型信息！"); 
		}
		if(list!=null &&list.size()>0){
			for(CmVehicleInfo vehicle: list){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("vehicleId", vehicle.getVehicleId());
				map.put("plateNumber",vehicle.getPlateNumber());
				map.put("length",vehicle.getLength());
				map.put("vehicleTypeName",SysStaticDataUtil.getSysStaticDataCodeName("VEHICLE_TYPE",String.valueOf(vehicle.getVehicleType())));
				map.put("vehicleType", vehicle.getVehicleType());
				listOut.add(map);
		     }			
	     }	
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}
	/**
	 * app保存与修改
	 * @param vehicleId
	 * @param vehicleType
	 * @param length)
	 * @param plateNumber
	 * @return
	 */
	public CmVehicleInfo doSaveOrUpdateVehicle(long vehicleId,int vehicleType,String length,String plateNumber){
		Date date = new Date();
		BaseUser user = SysContexts.getCurrentOperator();
		CmVehicleInfo vehicleInfo = null;
		Session session = SysContexts.getEntityManager();
		if (vehicleId > 0) {
			vehicleInfo = (CmVehicleInfo) session.get(CmVehicleInfo.class, vehicleId);
			if (vehicleInfo.getVehicleState() == 1) {
				vehicleInfo.setVehicleType(vehicleType);
				vehicleInfo.setLength(StringUtils.isNotEmpty(length) ?  Double.valueOf(length) : 0D);
				vehicleInfo.setPlateNumber(plateNumber);
			}
		}else{
			vehicleInfo = new CmVehicleInfo();
			vehicleInfo.setVehicleType(vehicleType);
			vehicleInfo.setLength(StringUtils.isNotEmpty(length) ?  Double.valueOf(length) : 0D);
			vehicleInfo.setPlateNumber(plateNumber);
			vehicleInfo.setStatus(SysStaticDataEnumYunQi.STS.VALID);
			
			vehicleInfo.setBusinessType(SysStaticDataEnum.BUSINESS_TYPE.DIRECT);
			vehicleInfo.setActualVolume(0D);
			vehicleInfo.setActualWeight(0D);
			vehicleInfo.setCreateDate(date);
			vehicleInfo.setCreatorId(user.getUserId());
			vehicleInfo.setCreatorName(user.getUserName());
			vehicleInfo.setOrgId(String.valueOf(user.getOrgId()));
			vehicleInfo.setTenantId(user.getTenantId());
			vehicleInfo.setCurrentOrgId(user.getOrgId());
			vehicleInfo.setType(0);
		}
		vehicleInfo.setVehicleState(1);
		vehicleInfo.setOpId(user.getUserId());
		vehicleInfo.setOpName(user.getUserName());
		session.saveOrUpdate(vehicleInfo);
		return vehicleInfo;
	}
	/**
	 * 司机保存与修改app
	 * @param driverId
	 * @param driverName
	 * @param driverBill
	 * @return
	 */
	public CmDriverInfo doSaveOrUpdateDriver(long driverId,String driverName,String driverBill){
		Date date = new Date();
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		CmDriverInfo cmDriverInfo = null;
		if (driverId > 0) {
			cmDriverInfo = (CmDriverInfo) session.get(CmDriverInfo.class, driverId);
			cmDriverInfo.setName(driverName);
			cmDriverInfo.setBill(driverBill);
		}else{
			cmDriverInfo = new CmDriverInfo();
			cmDriverInfo.setBill(driverBill);
			cmDriverInfo.setName(driverName);
			cmDriverInfo.setOrgId(user.getOrgId());
			cmDriverInfo.setCurrentOrgId(user.getOrgId());
			cmDriverInfo.setTenantId(user.getTenantId());
			cmDriverInfo.setStatus(SysStaticDataEnumYunQi.STS.VALID);
			cmDriverInfo.setCreateDate(date);
			cmDriverInfo.setOpId(user.getUserId());
		}
		session.saveOrUpdate(cmDriverInfo);
		return cmDriverInfo;
	}
	
	public CmVehicleInfo getCmVehicleInfo(long vehicleId){
		Session session = SysContexts.getEntityManager(true);
		return (CmVehicleInfo) session.get(CmVehicleInfo.class, vehicleId);
	}
}
