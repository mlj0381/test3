package com.wo56.business.out.intf;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.ord.intf.OrdDepartInfoYqTF;
import com.wo56.business.ord.intf.OrderInfoTF;
import com.wo56.common.consts.LoginConst;
import com.wo56.common.consts.SysStaticDataEnumYunQi;

public class ToAbutmentYunQiTF {
	/**
	 * 科莱对接开单接口（800000）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetOrderInfoKL(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetOrderInfo(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_KL);
	}
	
	/**
	 * 云企对接开单接口（800001）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetOrderInfoYQ(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetOrderInfo(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_YQ);
	}
	
	/**
	 * 中铁对接开单接口（800002）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetOrderInfoZT(Map<String,Object> inParam) throws Exception{
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetOrderInfo(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_ZT);
	}
	
	/**
	 * 科莱对接配送接口（800003）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetdeliveryOrderInfoKL(Map<String,Object> inParam) throws Exception{
		
		setUserInfoToSession(getCmUserInfo());
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetdeliveryOrderInfoKL(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_KL);
	}
	
	
	/**
	 * 科莱对接到货接口（800004）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetgxArriveGoodsKL(Map<String,Object> inParam) throws Exception{
		setUserInfoToSession(getCmUserInfo());
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetgxArriveGoodsKL(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_KL);
	}
	
	
	/**
	 * 科莱对接签收接口（800005）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetsignUpKL(Map<String,Object> inParam) throws Exception{
		//得到user信息
		setUserInfoToSession(getCmUserInfo());
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetsignUpKL(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_KL);
	}
	
	public CmUserInfo getCmUserInfo (){
		Session session = SysContexts.getEntityManager(true);
		 CmUserInfo cmUserInfo = new CmUserInfo();
		 SysStaticData sysStaticData =  SysStaticDataUtil.getSysStaticData("YQ_TENANT_TMS", SysStaticDataEnumYunQi.YQ_TENANT_TMS.TMS_CODE_VALUE_KL);
		 if(sysStaticData == null){
			 throw new BusinessException("在静态表sys_static_data未配置TMS对接信息！"); 
		 }
		 long   userId  = SysStaticDataUtil.getSysStaticData("YQ_TENANT_TMS", SysStaticDataEnumYunQi.YQ_TENANT_TMS.TMS_CODE_VALUE_KL).getCodeId();
         cmUserInfo = (CmUserInfo) session.get(CmUserInfo.class,userId );
         if(cmUserInfo == null){
        	 throw new BusinessException("没有对应的用户信息！");
         }
		 return cmUserInfo;
	}
	
	private String setUserInfoToSession(CmUserInfo userInfo) throws Exception {
		// TODO Auto-generated method stub
		BaseUser user=new BaseUser(); 
		Session session = SysContexts.getEntityManager(true);
		user.setOperId(userInfo.getUserId());
		user.setUserId(userInfo.getUserId());
		user.setUserName(userInfo.getUserName());
		user.setTelphone(userInfo.getLoginAcct());
		user.setUserType(userInfo.getUserType());		
		Criteria ca = session.createCriteria(CmUserOrgRel.class);
		ca.add(Restrictions.eq("userId", userInfo.getUserId()));
		List<CmUserOrgRel> list = ca.list();
		if (list != null && list.size() > 0) {
			user.setOrgId(list.get(0).getOrgId());
			user.setTenantId(list.get(0).getTenantId());
		}
		Map<String, Object> attrs=new HashMap<String, Object>();
		attrs.put(LoginConst.BaseUserAttr.LOGIN_ACCOUNT, userInfo.getLoginAcct());
		user.setAttrs(attrs);
		//从session里面拿组织id和顶级组织id
		return SysContexts.setCurrentOperator(user);		
	}

	/**
	 * 云启对接配送接口（800006）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetdeliveryOrderInfoYQ(Map<String,Object> inParam) throws Exception{
		
		setUserInfoToSession(getCmUserInfo());
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetdeliveryOrderInfoKL(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_YQ);
	}
	
	
	/**
	 * 云启对接到货接口（800007）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetgxArriveGoodsYQ(Map<String,Object> inParam) throws Exception{
		setUserInfoToSession(getCmUserInfo());
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetgxArriveGoodsKL(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_YQ);
	}
	
	
	/**
	 * 云启对接签收接口（800008）
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> abutmentGetsignUpYQ(Map<String,Object> inParam) throws Exception{
		//得到user信息
		setUserInfoToSession(getCmUserInfo());
		return ((OrderInfoTF)SysContexts.getBean("orderInfoTF")).abutmentGetsignUpKL(inParam, SysStaticDataEnumYunQi.TMS_TENANT_YQ.TMS_YQ);
	}
}
