package com.wo56.business.sys.intf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;

public class SysConnectTF {

	/**
	 * 交接方式保存与修改
	 * 
	 * */
	public Map<String,Object> doSaveUpdateSysData(Map<String,String> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager();
		Long tenantId = user.getTenantId();
		Integer sortId = DataFormat.getIntKey(inParam,"sortId");
		String codeType = EnumConsts.SysStaticData.DELIVERY_TYPE;
		String codeValue = DataFormat.getStringKey(inParam,"codeValue");
		Criteria ca = doQueryAllSysSF(null,codeValue,null);
		List<SysStaticData> codeList = ca.list();
		String codeName = codeList.get(0).getCodeName();
		long codeId = codeList.get(0).getCodeId();
		long flowId = DataFormat.getLongKey(inParam, "flowId");
		SysStaticData sData = new SysStaticData();
		if(flowId > 0){
			sData = doQuerySysSDById(inParam);
			if(sData.getCodeValue() != codeValue && checkSysSDSFcodeValue(codeValue,tenantId)){
				throw new BusinessException("已存在改交接方式");
			}
			sData.setFlowId(flowId);
			sData.setCodeName(codeName);
			sData.setCodeType(codeType);
			sData.setCodeValue(codeValue);
			sData.setCodeDesc(codeName);
			sData.setCodeTypeAlias(codeName);
			sData.setSortId(sortId);
			sData.setCodeId(codeId);
			sData.setTenantId(user.getTenantId());
			sData.setState(String.valueOf(SysStaticDataEnum.STS.VALID));
			session.update(sData);
		}else{
			if(checkSysSDSFcodeValue(codeValue,tenantId)){
				throw new BusinessException("已存在改交接方式");
			}
			sData.setCodeName(codeName);
			sData.setCodeType(codeType);
			sData.setCodeValue(codeValue);
			sData.setCodeDesc(codeName);
			sData.setCodeId(codeId);
			sData.setCodeTypeAlias(codeName);
			sData.setTenantId(user.getTenantId());
			sData.setSortId(sortId);
			sData.setState(String.valueOf(SysStaticDataEnum.STS.VALID));
			session.save(sData);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 交接方式查询
	 * */
	public Map<String,Object> doQuerySysSD(Map<String,String> inParam){
		String codeName = DataFormat.getStringKey(inParam, "codeName");
		String codeValue = DataFormat.getStringKey(inParam, "codeValue");
		BaseUser user = SysContexts.getCurrentOperator();
		long tenantId = user.getTenantId();
		Criteria ca = doQueryAllSysSF(codeName,codeValue,tenantId);
		ca.addOrder(Order.asc("sortId"));
		IPage page = PageUtil.gridPage(ca);
		Pagination pagination = new Pagination(page);
		List<SysStaticData> list = pagination.getItems();
		pagination.setItems(list);
		return JsonHelper.parseJSON2Map(JsonHelper.json(pagination));
	}
	/**
	 * 验证交接方式codeValue重复问题
	 * @param codeValue
	 * @return true 存在
	 * @return false 不存在
	 */
	public boolean checkSysSDSFcodeValue(String codeValue,Long tenantId){
		return doQueryAllSysSF(null,codeValue,tenantId).list().size() > 0;
	}
	/**
	 * 排序
	 * @return int
	 */
	public int sortSysSDSF(){
		List<SysStaticData> list = doQueryAllSysSF(null,null,null).list();
		List<Integer> sort = new ArrayList<Integer>();
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				sort.add(list.get(i).getSortId());
			}
		}else{
			return 1;
		}
		return Collections.max(sort)+1;
	}
	/**
	 * 交接方式查询
	 * @return Criteria
	 * */
	public Criteria doQueryAllSysSF(String codeName,String codeValue,Long tenantId){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(SysStaticData.class);
		String state = String.valueOf(SysStaticDataEnum.STS.VALID);
		if(StringUtils.isNotEmpty(codeName)){
			ca.add(Restrictions.like("codeName", "%"+codeName+"%"));
		}
		if(StringUtils.isNotEmpty(codeValue)){
			ca.add(Restrictions.eq("codeValue", codeValue));
		}
		if(tenantId != null){
			ca.add(Restrictions.eq("tenantId", tenantId));
		}
		ca.add(Restrictions.eq("state", state));
		ca.add(Restrictions.eq("codeType", EnumConsts.SysStaticData.DELIVERY_TYPE));
		return ca;
	}
	/**
	 * 根据ID查询交接方式
	 * */
	public SysStaticData doQuerySysSDById(Map<String,String> inParam){
		Session session = SysContexts.getEntityManager(true);
		long flowId = DataFormat.getLongKey(inParam,"flowId");
		SysStaticData cc = (SysStaticData) session.get(SysStaticData.class, flowId);
		return cc;
	}
	/**
	 * 常用交接方式（删除）
	 * */
	public Map<String,Object> doDelSysSD(Map<String,String> inParam){
		Session session = SysContexts.getEntityManager();
		long flowId = DataFormat.getLongKey(inParam,"flowId");
		int state = SysStaticDataEnum.STS.NULLITY;
		String sql = "update sys_static_data s set s.STATE = :state where s.FLOW_ID = :flowId";
		session.createSQLQuery(sql).setParameter("flowId", flowId).setParameter("state", state).executeUpdate();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 所有查询交接方式(默认)
	 * */
	public Map<String,Object> doQueryAllSysSD(Map<String,String> inParam){
		BaseUser user = SysContexts.getCurrentOperator();
		Criteria ca = doQueryAllSysSF(null,null,-1L);
		List<SysStaticData> list = (List<SysStaticData>)ca.list();
		return JsonHelper.parseJSON2Map(JsonHelper.json(list));
	}
}
