package com.wo56.business.cm.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.sys.vo.SysRole;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;

public class CmUserSV {

	private static final Log log = LogFactory.getLog(CmUserSV.class);

	/**
	 * 新增用户新消息
	 * @param cmUserInfo
	 */
	public void doSaveCmUserInfo(CmUserInfo cmUserInfo)throws Exception{
		SysContexts.getEntityManager().save(cmUserInfo);
	}
	/**
	 * 更新用户新消息
	 * @param cmUserInfo
	 */
	public void doUpdateCmUserInfo(CmUserInfo cmUserInfo)throws Exception{
		SysContexts.getEntityManager().update(cmUserInfo);
	}
	/**
	 * lyh
	 * 根据账号查询用户
	 * @param loginAcct
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo queryUserByAcct(String loginAcct)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if(StringUtils.isEmpty(loginAcct)){
			return null;
		}
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("loginAcct", loginAcct));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		
		List<CmUserInfo> list = ca.list();
		if(list!=null && list.size()>0){
			if(list.size()>1){
				log.error("账号："+loginAcct+"数据异常");
			}
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据微信的openId进行查询用户信息
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo queryUserByOpenId(String openId)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if(StringUtils.isEmpty(openId)){
			return null;
		}
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("openId", openId));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		
		List<CmUserInfo> list = ca.list();
		if(list!=null && list.size()>0){
			if(list.size()>1){
				log.error("微信openId账号："+openId+"数据异常");
			}
			return list.get(0);
		}
		return null;
	}
	
	
	/**
	 * lyh
	 * 根据账号查询用户
	 * @param loginAcct
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo queryUserByAcctType(String loginAcct,Integer userType)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if(StringUtils.isEmpty(loginAcct)){
			return null;
		}
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("loginAcct", loginAcct));
		ca.add(Restrictions.eq("userType", userType));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		
		List<CmUserInfo> list = ca.list();
		if(list!=null && list.size()>0){
			if(list.size()>1){
				log.error("账号："+loginAcct+"数据异常");
			}
			return list.get(0);
		}
		return null;
	}
	
	
	
	/**
	 * lyh
	 * 
	 * 查询用户的账号在一个组织下面是否唯一
	 * 
	 * @param loginAcct
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserAcct(String loginAcct,long tenantId,long userId)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		 
		StringBuffer hql= new StringBuffer("select user  from CmUserInfo user , CmUserOrgRel rel where user.userId =rel.userId  ");
		hql.append(" and rel.tenantId=:tenantId and user.loginAcct=:loginAcct and user.state=:state");
//		if(userId>0){
//			hql.append(" and user.userId=:userId");
//		}
		Query query = session.createQuery(hql.toString());
		query.setParameter("tenantId", tenantId);
		query.setParameter("loginAcct", loginAcct);
		query.setParameter("state", SysStaticDataEnum.STS.VALID);
//		if(userId>0){
//			query.setParameter("userId", userId);
//		}
		List list=query.list();
		if(list!=null && list.size()>0){
			if(userId>0){
				CmUserInfo cmUserInfo=(CmUserInfo)list.get(0);
				if(cmUserInfo.getUserId()==userId){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
			
		}
		return true;
		
	}
	
//	/**
//	 * lyh
//	 * 
//	 * 修改用户除了本用户的账号在一个组织下面是否唯一
//	 * 
//	 * @param loginAcct
//	 * @return
//	 * @throws Exception
//	 */
//	public List<CmUserInfoOutQuery> checkUpdateUser(long tenantId)throws Exception{
//		Session session = SysContexts.getEntityManager(true);
//		 
//		StringBuffer hql= new StringBuffer(" select c.USER_ID,c.LOGIN_ACCT from cm_user_info c,cm_user_org_rel o where c.USER_ID = o.user_id and o.tenant_id = :tenantId and c.STATE = :state ");
//		Query query = session.createSQLQuery(hql.toString());
//		query.setParameter("tenantId", tenantId);
//		query.setParameter("state", SysStaticDataEnum.STS.VALID);
//		
//		List<Object[]> list=query.list();
//		List<CmUserInfoOutQuery> cmList = new ArrayList<CmUserInfoOutQuery>();
//		if(list!=null && list.size()>0){
//			for(Object[] temp : list){
//				CmUserInfoOutQuery cmu = new CmUserInfoOutQuery();
//				cmu.setUserId(Long.parseLong(String.valueOf(temp[0])));
//				cmu.setLoginAcct(temp[1].toString());
//				cmList.add(cmu);
//			}
//		}
//		return cmList;
//		
//	}
	
	
	/**
	 * 根据账号查询用户
	 * @param 
			key:userId
			key:userType 操作员1,业务员2、开单员3、师傅4;	
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo getUserInfo(long userId,int userType)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if(userId<0){
			return null;
		}
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("userId", userId));
		if(userType>0){
			ca.add(Restrictions.eq("userType", userType));
		}
		List<CmUserInfo> list = ca.list();
		if(list!=null && list.size()==1){
			return list.get(0);
		}else if(list.size()>1){
			log.error("存在同一用户ID["+userId+"]的数据");
		}
		return null;
	}
	/**
	 * 根据账号列表查询用户To APP
	 * @param loginAcct[] String
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> queryUserByAcctList(Object[] logAccts )throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if(logAccts==null){
			return null;
		}
		 StringBuffer buffer = new StringBuffer();  
	     for (int i = 0; i < logAccts.length; i++) {  
	            buffer.append("?,");  
	      }  
	     String bf=buffer.toString().substring(0, buffer.toString().length()-1);
		StringBuffer hql= new StringBuffer(" select userInfo,sfUserInfo from CmUserInfo userInfo , CmSfUserInfo sfUserInfo where userInfo.userId =sfUserInfo.sfUserId and userInfo.userType=4 ");
		hql.append(" and sfUserInfo.phone in ("+bf+")");
		Query query = session.createQuery(hql.toString());
		for (int i = 0; i < logAccts.length; i++) {  
			query.setLong(i, Long.valueOf(logAccts[i].toString()));
		}  
		List<Object[]> list = query.list();
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * 根据账号查询用户
	 * @param loginAcct
	 * @return
	 * @throws Exception
	 */
	public IPage<Object[]> queryUserList(Map<String,Object> inParam)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		String cityName = DataFormat.getStringKey(inParam, "cityName");
		String phone = DataFormat.getStringKey(inParam, "phone");
		String userName = DataFormat.getStringKey(inParam, "userName");
		long userId = DataFormat.getLongKey(inParam, "userId");
		StringBuffer sb = new StringBuffer("select userInfo,sfUserInfo from CmUserInfo userInfo, CmSfUserInfo sfUserInfo where userInfo.userId = sfUserInfo.sfUserId and userInfo.userType=4 ");
		
		if(StringUtils.isNotEmpty(userName)){
			sb.append(" and userInfo.userName=:userName");
		}
		if(StringUtils.isNotEmpty(phone)){
			sb.append(" and userInfo.loginAcct=:phone");
		}
		if(StringUtils.isNotEmpty(cityName)){
			sb.append(" and sfUserInfo.cityName=:cityName");
		}
		
		Query ca = session.createQuery(sb.toString());

		if(StringUtils.isNotEmpty(userName)){
			ca.setParameter("userName", userName);
		}
		if(StringUtils.isNotEmpty(phone)){
			ca.setParameter("phone", phone);
		}
		if(StringUtils.isNotEmpty(cityName)){
			ca.setParameter("cityName", cityName);
		}
		IPage p = PageUtil.gridPage(ca);
		return p;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询用户的信息
	 * @return
	 */
	public Pagination doQueryCmUser(String userName,int userType,long orgId,int userRole){
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(CmUserInfo.class);
		BaseUser base = SysContexts.getCurrentOperator();
		if(StringUtils.isNotEmpty(userName)){
			ca.add(Restrictions.eq("userName", userName));
		}
		if(userType>0){
			ca.add(Restrictions.eq("userType", userType));	
		}
		if(orgId>0){
			ca.add(Restrictions.eq("orgId", orgId));	
		}
        if(userRole!=SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR){
        	ca.add(Restrictions.eq("tenantId", Long.valueOf(base.getTenantId())));
        	if(userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
        		ca.add(Restrictions.eq("orgId", base.getOrgId()));
        	}
		}
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		ca.addOrder(Order.desc("createTime"));
		
		//List<CmUserInfo>  userList = ca.list();
		IPage p = PageUtil.gridPage(ca);
		Pagination page = new Pagination(p);
		return page;
		
	}
	
	
	/**
	 * lyh
	 *保存员工的信息
	 * @return
	 * @throws Exception 
	 */
	public CmUserInfo doSaveCmUserInfo(String userName,String loginAcct,String loginPwd,int loginType,long userId,int userType) throws Exception{
		CmUserInfo cmUser = new CmUserInfo(); 
		Session session = SysContexts.getEntityManager();
		
		if(userId>0){
			cmUser=(CmUserInfo)session.get(CmUserInfo.class, userId); 
			if(StringUtils.isEmpty(loginPwd)){
				loginPwd=cmUser.getLoginPwd();
			}
		}
		
		
		
		BaseUser base = SysContexts.getCurrentOperator();
		cmUser.setLoginAcct(loginAcct);
		cmUser.setLoginPwd(loginPwd);
		cmUser.setOpId(base.getOperId());
		cmUser.setState(SysStaticDataEnum.STS.VALID);
		cmUser.setUserName(userName);
		cmUser.setCreateTime(new Date(System.currentTimeMillis()));
		cmUser.setUserType(userType);
		cmUser.setLoginType(loginType);
		session.saveOrUpdate(cmUser);
//		if(id<=0){
//			
//					SysRoleOperRel operRel = new SysRoleOperRel();
//					operRel.setRoleId(roleId);
//					operRel.setCreateDate(new Date(System.currentTimeMillis()));
//					operRel.setLastModifyDate(new Date(System.currentTimeMillis()));
//					operRel.setLastModifyOperatorId(base.getUserId());
//					operRel.setOperatorId(cmUser.getUserId());
//					//operRel.setRoleId(SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER);
//					operRel.setState(SysStaticDataEnum.STS.VALID);
//				    session.saveOrUpdate(operRel);
//				   
//		}
		return cmUser;
	}
	
	
	
	/**
	 * 用户查询(查询公司管理员)
	 * @param roleId
	 * @return
	 */
	public Pagination  getPlateUser (String userName,int userType,long orgId,int roleId){
		Session session  = SysContexts.getEntityManager(true);
		BaseUser base  = SysContexts.getCurrentOperator();
		Criteria roleCa = session.createCriteria(SysRole.class);
		roleCa.add(Restrictions.eq("roleType", 1));
		roleCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		List<SysRole> roleList = new ArrayList<SysRole>();
		List<Integer> intList = new ArrayList<Integer>();
		if(roleCa.list().size()>0){
			roleList=roleCa.list();
			for(SysRole sysRole:roleList){
				intList.add(sysRole.getRoleId());
			}
		}
		Criteria ca  = session.createCriteria(SysRoleOperRel.class);
		//ca.add(Restrictions.eq("roleId",SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER));
		ca.add(Restrictions.in("roleId", intList));
		ca.add(Restrictions.eq("state",SysStaticDataEnum.STS.VALID));
		if(ca.list().size()>0){
			Criteria userCa  = session.createCriteria(CmUserInfo.class);
			List<SysRoleOperRel> operList = ca.list();
			List<Long> objList = new ArrayList<Long>();
			for(SysRoleOperRel sysRel:operList){
				objList.add(sysRel.getOperatorId());
			}
			if(objList.size()>0){
				userCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
				//userCa.add(Restrictions.eq("tenantId", Long.valueOf(base.getTenantId()+"")));
				userCa.add(Restrictions.in("userId", objList));
				if(StringUtils.isNotEmpty(userName)){
					userCa.add(Restrictions.eq("userName", userName));
				}
				if(userType>0){
					userCa.add(Restrictions.eq("userType", userType));	
				}
				if(orgId>0){
					userCa.add(Restrictions.eq("orgId", orgId));	
				}
			}
			  userCa.addOrder(Order.desc("createTime"));
			    IPage p = PageUtil.gridPage(userCa);
		 		Pagination page = new Pagination(p);
			return page;
		}else{
			  IPage p = PageUtil.gridPage(ca);
		 		Pagination page = new Pagination(p);
		 		return  page;
		}
		//
	}
	
	
	/**
	 * 删除用户信息
	 * @param userIds
	 * @return
	 */
	public void doDelCmUser(long userId){
		 Session session  = SysContexts.getEntityManager();
		 Criteria userCa = session.createCriteria(CmUserInfo.class);
		 userCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		 userCa.add(Restrictions.eq("userId", userId));
		 List<CmUserInfo> cmUserList  =userCa.list();
		 if(cmUserList!=null && cmUserList.size()==1){
			 CmUserInfo cmUserInfo=cmUserList.get(0);
			 cmUserInfo.setState(SysStaticDataEnum.STS.NULLITY);
			 session.update(cmUserInfo);
		 }else{
			 throw new BusinessException("找不到该用户信息["+userId+"],请联系管理员！");
		 }
		 
		/* Criteria operCa = session.createCriteria(SysRoleOperRel.class);
		 operCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		 operCa.add(Restrictions.eq("operatorId", userId));
		 List<SysRoleOperRel> operList = operCa.list();
		 if(operList!=null && operList.size()>0){
			 for(SysRoleOperRel operRel : operList){
				 session.delete(operRel);
			 }
		 }*/
	}
	
	
	
	

	/**
	 * lyh
	 * 用户查询(查询公司管理员)
	 * @return
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public Pagination queryPlateUser(String userName,long orgId,int roleId,Long tenantId,String loginAcct,int roleType) throws Exception{
		
		Session session  = SysContexts.getEntityManager();
		BaseUser base  = SysContexts.getCurrentOperator();
		StringBuffer buff = new StringBuffer();
		
		buff.append("select {u.*},{p.*},{s.*}, {o.*}, {r.*}");
		buff.append(" from cm_user_info {u},organization {p},cm_user_org_rel {o},sys_role_oper_rel {r} ,sys_role {s}");
		buff.append(" where {u}.USER_ID = {o}.user_id and {o}.org_id={p}.ORG_ID  and {u}.user_id={r}.OPERATOR_ID and {r}.role_id={s}.ROLE_ID ");
		buff.append(" and {u}.state=:state");
		buff.append(" and {p}.state=:state");
		buff.append(" and {o}.TENANT_ID={p}.TENANT_ID");
		if(roleType>0){
			buff.append(" and {s}.ROLE_TYPE=:roleType");
		}
		if(roleId>0){
			buff.append(" and {s}.ROLE_ID=:roleId");
		}
		if(tenantId!=null && tenantId>0){
			buff.append(" and {p}.TENANT_ID=:tenantId");
		}
		if(StringUtils.isNotEmpty(userName)){
			buff.append(" and {u}.user_name like :userName");
		}
		if(StringUtils.isNotEmpty(loginAcct)){
			buff.append(" and {u}.login_acct like :loginAcct");
		}
		
		if(orgId>0){
			buff.append(" and {p}.org_id=:orgId");
		}
		if(base.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PLATFORM && base.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULLCOM){
			buff.append(" and {u}.user_type in :userType ");
		}
		
		SQLQuery query  = session.createSQLQuery(buff.toString());
		query.addEntity("u", CmUserInfo.class).addEntity("p", Organization.class).addEntity("s", SysRole.class).addEntity("o", CmUserOrgRel.class).addEntity("r", SysRoleOperRel.class);
		query.setParameter("state", SysStaticDataEnum.STS.VALID);
		if(base.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PLATFORM && base.getUserType() != SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULLCOM){
			query.setParameterList("userType", new Integer[]{SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN,SysStaticDataEnumYunQi.USER_TYPE_YUNQI.DISTRIBUTION,SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER,SysStaticDataEnumYunQi.USER_TYPE_YUNQI.MERCHANDISER_DISTRIBUTION});
		}
		Map<String,Integer> map=new HashMap<String, Integer>();
		
		if(roleType>0){
			query.setParameter("roleType", roleType);
		}
		
		if(tenantId!=null && tenantId>0){
			query.setParameter("tenantId", tenantId);
		}
		if(orgId>0){
			query.setParameter("orgId", orgId);
		}
		if(roleId>0){
			query.setParameter("roleId", roleId);
		}
		if(StringUtils.isNotEmpty(userName)){
			query.setParameter("userName", userName+"%");
		}
		if(StringUtils.isNotEmpty(loginAcct)){
			query.setParameter("loginAcct", loginAcct+"%");
		}
		

		
		IPage p = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>)p.getThisPageElements();
		List<Map<String,String>> rtnList = new ArrayList<Map<String,String>>();
		if(list!= null && list.size()>0){
			for(Object[] obj : list){
				CmUserInfo cmuserInfo =(CmUserInfo) obj[0];
				
				Organization cmCompanyInfo = (Organization) obj[1];
				SysRole role = (SysRole)obj[2];
				
				Map<String,String> retMap=new HashMap<String, String>();
				retMap.put("tenantCode", CommonUtil.getTennatCodeById(cmCompanyInfo.getTenantId()));
				retMap.put("tenantName", CommonUtil.getTennatNameById(cmCompanyInfo.getTenantId()));
				retMap.put("tenantId", cmCompanyInfo.getTenantId().toString());
				retMap.put("orgName", cmCompanyInfo.getOrgName());
				retMap.put("orgId", String.valueOf(cmCompanyInfo.getOrgId()));
				retMap.put("roleName", role.getRoleName());
				retMap.put("roleId", String.valueOf(role.getRoleId()));
				retMap.put("roleType", String.valueOf(role.getRoleType()));
				SysStaticData roleTypeData=SysStaticDataUtil.getSysStaticData(EnumConsts.SysStaticData.ROLE_TYPE , String.valueOf(role.getRoleType()));
				retMap.put("roleTypeName", "");
				if(roleTypeData!=null){
					retMap.put("roleTypeName", roleTypeData.getCodeName());
				}
				retMap.put("loginAcct", cmuserInfo.getLoginAcct());
				retMap.put("userId", String.valueOf(cmuserInfo.getUserId()));
				retMap.put("userName", cmuserInfo.getUserName());
				retMap.put("createTime", DateUtil.formatDate(cmuserInfo.getCreateTime(), DateUtil.DATETIME12_FORMAT));
				retMap.put("loginTypeName", SysStaticDataUtil.getSysStaticData("LOGIN_TYPE",String.valueOf(cmuserInfo.getLoginType())).getCodeName());
				retMap.put("loginType", String.valueOf(cmuserInfo.getLoginType()));
				retMap.put("userType", cmuserInfo.getUserType().toString());
				rtnList.add(retMap);
			}
		}
		p.setThisPageElements(rtnList);
		Pagination<Map> page = new Pagination<Map>(p);
		return page;
	}
	
	
	
	/**
	 * yq
	 * 根据账号查询用户
	 * @param loginAcct
	 * @return
	 * @throws Exception
	 */
	public CmUserInfo queryUserByAcctYQ(String loginAcct,Integer [] loginType)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		if(StringUtils.isEmpty(loginAcct)){
			return null;
		}
		Criteria ca = session.createCriteria(CmUserInfo.class);
		ca.add(Restrictions.eq("loginAcct", loginAcct));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		ca.add(Restrictions.in("loginType", loginType));
		List<CmUserInfo> list = ca.list();
		if(list!=null && list.size()>0){
			if(list.size()>1){
				log.error("账号："+loginAcct+"数据异常");
			}
			return list.get(0);
		}
		return null;
	}
	
}
