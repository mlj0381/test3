package com.wo56.business.cm.intf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.wo56.business.cm.impl.CmCustomerSV;
import com.wo56.business.cm.impl.ICmDriverInfoIntf;
import com.wo56.business.cm.vo.CmDriverInfo;
import com.wo56.business.cm.vo.CmUserInfoPull;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.CommonUtil;

public class CmDriverInfoTF implements ICmDriverInfoIntf{

	
public Map doQueryCmDriver(Map<String, String> inParam){
	String name=DataFormat.getStringKey(inParam, "name");
	String bill=DataFormat.getStringKey(inParam, "bill");
	Long orgId=DataFormat.getLongKey(inParam, "orgId");
	String telephone="";
	String identityCardNo=DataFormat.getStringKey(inParam, "identityCardNo");
	String bankName="";
	String bankAccount="";
	String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
    if(StringUtils.isNotBlank(inputParamJson)){
    	Map inputParamMap=JsonHelper.parseJSON2Map(inputParamJson);
		String inName=DataFormat.getStringKey(inputParamMap, "name");
		if(StringUtils.isNotEmpty(inName)){
			name=inName;
		}
		String inBill=DataFormat.getStringKey(inputParamMap, "bill");
		if(StringUtils.isNotEmpty(inBill)){
			bill=inBill;
		}
		String inTelephone=DataFormat.getStringKey(inputParamMap, "telephone");
		if(StringUtils.isNotEmpty(inTelephone)){
			telephone=inTelephone;
		}
		String inIdentityCardNo=DataFormat.getStringKey(inputParamMap, "identityCardNo");
		if(StringUtils.isNotEmpty(inIdentityCardNo)){
			identityCardNo=inIdentityCardNo;
		}
		String inBankName=DataFormat.getStringKey(inputParamMap, "bankName");
		if(StringUtils.isNotEmpty(inBankName)){
			bankName=inBankName;
		}
		String inBankAccount=DataFormat.getStringKey(inputParamMap, "bankAccount");
		if(StringUtils.isNotEmpty(inBankAccount)){
			bankAccount=inBankAccount;
		}
    }
	CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
	Pagination page = cmCustomerSV.doQueryCmDriver(name, bill, orgId,telephone,identityCardNo,bankName,bankAccount);
	//Map iparmMap = new HashMap();
	//iparmMap.put("info", "Y");
	return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	 
	
}


@SuppressWarnings("rawtypes")
public Map doSaveCmDriverInfo(Map<String, String> inParam) throws Exception{
	Session session = SysContexts.getEntityManager();
	String name=DataFormat.getStringKey(inParam, "name");
	String bill=DataFormat.getStringKey(inParam, "bill");
	String identityCard=DataFormat.getStringKey(inParam, "identityCard");
	String drivingLicense=DataFormat.getStringKey(inParam, "drivingLicense");
	//String identityCardNo=DataFormat.getStringKey(inParam, "identityCard");
	String identityCardNo=DataFormat.getStringKey(inParam, "identityCardNo");
	String telephone=DataFormat.getStringKey(inParam, "telephone");
	Long id=DataFormat.getLongKey(inParam, "id");
	if(StringUtils.isNotEmpty(telephone)&&!CommonUtil.isCheckMobiPhone(telephone)){
		throw new BusinessException("联系电话格式不正确！");
	}
	if(StringUtils.isEmpty(name)){
		throw new BusinessException("用户名不能为空");
	}
	if(!CommonUtil.isCheckPhone(bill)){
		throw new BusinessException("司机手机号码格式不正确！");
	}
	if(StringUtils.isEmpty(identityCardNo) ){
		throw new BusinessException("司机身份证号不能为空");	
	}
	if(StringUtils.isEmpty(identityCard)){
		throw new BusinessException("身份证正面不能为空");
	}
	if(StringUtils.isEmpty(drivingLicense)){
		throw new BusinessException("驾驶证不能为空");
	}
	BaseUser user = SysContexts.getCurrentOperator();
	CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
	CmDriverInfo cmDriverInfo = new CmDriverInfo();
	if(id!=null && id>0){
		cmDriverInfo = (CmDriverInfo)session.get(CmDriverInfo.class, id);
		if(cmDriverInfo==null){
			throw new BusinessException("找不到该司机的信息");
		}
		/**判断司机是否是空闲的**/
		cmCustomerSV.checkVehicle(id);
	}else{
		
		//如果存在新增失败
		Criteria driverCa = session.createCriteria(CmDriverInfo.class);
		driverCa.add(Restrictions.eq("bill", bill));
		driverCa.add(Restrictions.eq("orgId",user.getOrgId()));
		driverCa.add(Restrictions.eq("tenantId",Long.valueOf(user.getTenantId())));
		driverCa.add(Restrictions.eq("status", SysStaticDataEnum.STS.VALID));
		
		List list = driverCa.list();
		if(list.size() > 0){
			throw new BusinessException("手机号码为["+bill+"]司机用户已存在，新增失败。");
		}

	}
	BeanUtils.populate(cmDriverInfo, inParam);
	
	cmCustomerSV.doSaveCmDriverInfo(cmDriverInfo);
	Map map = new HashMap();
	map.put("info", "Y");
	return map;
	
}



/**
 * 删除司机
 * @param inParam
 * @return
 * @throws Exception
 */
public Map doDelUrl(Map<String, Object> inParam) throws Exception{
	List driverIds = (List)inParam.get("driverIds");
	Map paramMap = new HashMap();
     Session session  = SysContexts.getEntityManager();
	if(driverIds.size()<=0){
		throw new BusinessException("请传入司机编号");	
	}
	CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
	cmCustomerSV.doDelDriver(driverIds);
	paramMap.put("info", "Y");
	return paramMap;
}


/**
 * 查询司机详情
 * @param inParam
 * @return
 * @throws Exception
 */
public Map querySysDriverDtl(Map<String, String> inParam) throws Exception{
	long id=DataFormat.getLongKey(inParam, "id");
	Session session = SysContexts.getEntityManager(true);
	CmDriverInfo cmDriverInfo = (CmDriverInfo)session.get(CmDriverInfo.class, id);
	if(cmDriverInfo==null){
		throw new BusinessException("查询不到该司机的信息");	
	}
	Map iparmMap = new HashMap();
	iparmMap.put("cmDriverInfo", cmDriverInfo);
	return iparmMap;
}
	
	/**
	 *根据车牌号码查询最近合作的司机
	 * 
	 * @param inParam
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map doQueryDriver(Map<String, Object> inParam) throws Exception {
		String plateNumber=DataFormat.getStringKey(inParam, "plateNumber");
		Long orgId=DataFormat.getLongKey(inParam, "orgId");
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		Query query = session.createSQLQuery("SELECT A.DRIVER_ID,A.DRIVER_NAME,A.DRIVER_BILL FROM cm_vehicle_driver_rel AS A,cm_driver_info AS C WHERE A.DRIVER_ID=C.ID AND A.STS=1 AND C.STATUS=1 AND A.PLATE_NUMBER=:plateNumber AND A.ORG_ID=:orgId AND  A.TENANT_ID=:tenantId ORDER BY A.OP_DATE DESC ");
		query.setParameter("plateNumber", plateNumber);
		query.setParameter("orgId", orgId);
		query.setParameter("tenantId", user.getTenantId());
		List list = query.list();
		if(list!=null &&list.size()>0){
			Object[] obj= (Object[]) list.get(0);
			Map map = new HashMap();
			map.put("id", obj[0]);
			map.put("name", obj[1]);
			map.put("bill", obj[2]);
			return map;
		}
		return null;
	}


	public Map getDriverInfo(Map<String, Object> inParam) {
		// TODO Auto-generated method stub
		String  driverName=DataFormat.getStringKey(inParam, "driverName");
		if(StringUtils.isEmpty(driverName)){
			throw new BusinessException("司机名称不能为空");
		}
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		long orgId = user.getOrgId();
		if(orgId<0){
			throw new BusinessException("当前网点不能为空");
		}
		
		Criteria ca = session.createCriteria(CmDriverInfo.class);
		ca.add(Restrictions.eq("orgId", orgId));
		ca.add(Restrictions.like("name", "%"+driverName+"%"));
		List<CmDriverInfo> list = ca.list();
		List<Map<String, String>> listOut = new ArrayList<Map<String, String>>();  
		if(list!=null &&list.size()>0){
			for(CmDriverInfo driver : list){
				Map map = new HashMap();
				map.put("driverId", driver.getId());
				map.put("driverName",driver.getName() );
				map.put("driverBill",driver.getBill());
				listOut.add(map);
			}			
		}	
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}	
}
