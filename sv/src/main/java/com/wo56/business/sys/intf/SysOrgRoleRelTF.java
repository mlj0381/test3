package com.wo56.business.sys.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.sys.impl.ISysOrgRoleRelIntf;
import com.wo56.business.sys.impl.SysOrgRoleRelSV;
import com.wo56.business.sys.vo.SysOrgRoleRel;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.common.consts.SysStaticDataEnum;


public class SysOrgRoleRelTF implements ISysOrgRoleRelIntf{
	
	public void doSave(Long orgId,Long roleId){
		
		SysOrgRoleRelSV sysOrgRoleRelSV = (SysOrgRoleRelSV)SysContexts.getBean("sysOrgRoleRelSV");
		SysOrgRoleRel orgRoleRel=new SysOrgRoleRel();
		BaseUser user=SysContexts.getCurrentOperator();
		
		orgRoleRel.setOrgId(orgId);
		orgRoleRel.setRoleId(roleId);
		orgRoleRel.setOpId(user.getUserId());
		orgRoleRel.setCreateDate(new Date());
		orgRoleRel.setTenantId(user.getTenantId());
		sysOrgRoleRelSV.doSave(orgRoleRel);
		
	}

	@Override
	public List<SysOrgRoleRel> querySysOrgRoleRelList(Map<String, Object> params)
			throws Exception {
		Long tenantId=DataFormat.getLongKey(params, "tenantId");
		Long orgId=DataFormat.getLongKey(params, "orgId");
		List<SysOrgRoleRel> orgRoleRels=new ArrayList<SysOrgRoleRel>();

		Session session = SysContexts.getEntityManager(true);
		StringBuffer hql =new StringBuffer();
		hql.append("select u,r from SysOrgRoleRel r,CmUserInfo u where u.userId=r.opId and r.tenantId=:tenantId");
		
		if(orgId!=null && orgId>0){
			hql.append(" and r.orgId=:orgId");
		}
		
		Query query=session.createQuery(hql.toString());
		query.setParameter("tenantId", tenantId);
		if(orgId!=null && orgId>0){
			query.setParameter("orgId", orgId);
		}
		
		
		List<Object[]> list=query.list();
		if(list!=null && list.size()>0){
			session.evict(list);
			
			for(Object[] obj:list){
				CmUserInfo cmUserInfo=(CmUserInfo)obj[0];
				SysOrgRoleRel orgRoleRel=(SysOrgRoleRel)obj[1];
				orgRoleRel.setOpName(cmUserInfo.getUserName());
				orgRoleRels.add(orgRoleRel);
			}
			
		}
		return orgRoleRels;
	}
	/**
	 * 删除网点角色
	 * 
	 * 如果这个网点的角色已经赋权给到下面的员工了，提示不能删除
	 * @param id
	 * @throws Exception
	 */
	public void delSysOrgRoleRel(Map<String,Object> params) throws Exception{
		Long id=DataFormat.getLongKey(params, "id");
		if(id==null || id<0){
			throw new BusinessException("传入id的数据为空!");
		}
		
		
		Session session = SysContexts.getEntityManager(true);
		SysOrgRoleRel orgRoleRel= (SysOrgRoleRel)session.get(SysOrgRoleRel.class, id);
		if(orgRoleRel==null) {
			throw new BusinessException("删除的数据不存在！");
		}
		
		Long orgId=orgRoleRel.getOrgId();
		Long roleId=orgRoleRel.getRoleId();
		
		if(this.isHasUserInOrgAndRole(orgId, roleId)){
			throw new BusinessException("该网点下面的员工有用到这个角色，要先解除下面的员工的角色才能删除！");
		}
		
		session.delete(orgRoleRel);
		
	}
	/**
	 * 查询网点下面的用户是否有传入的角色
	 * 
	 * @param orgId
	 * @param roleId
	 * @return
	 */
	public Boolean isHasUserInOrgAndRole(Long orgId,Long roleId){
		Session session = SysContexts.getEntityManager(true);
		StringBuffer hql =new StringBuffer();
		hql.append("select u from CmUserOrgRel r,CmUserInfo u,SysRoleOperRel s where u.userId=r.userId and u.userId=s.operatorId");
		hql.append(" and r.orgId=:orgId and s.roleId=:roleId");
		Query query=session.createQuery(hql.toString());
		query.setParameter("orgId", orgId);
		query.setParameter("roleId", roleId.intValue());
		List list=query.list();
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 批量保存数据
	 * 如果网点下面已经有这个角色，不进行任何处理
	 * 
	 */
	public void saveSysOrgRoleRelList(Map<String, Object> params)
			throws Exception {
		JSONArray list= JSONArray.fromObject(StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(params,"list")));
		
		Iterator<Map<String,Object>> iterator= list.iterator();
		while (iterator.hasNext()) {
			Map<String,Object> map= iterator.next();
			this.saveSysOrgRoleRelList(Long.valueOf(map.get("orgId").toString()), Long.valueOf(map.get("roleId").toString()));
		}
		
	}
	
	public void saveSysOrgRoleRelList(Long orgId,Long roleId){
		SysOrgRoleRelSV sysOrgRoleRelSV = (SysOrgRoleRelSV)SysContexts.getBean("sysOrgRoleRelSV");
		SysOrgRoleRel orgRoleRel=  sysOrgRoleRelSV.querySysOrgRoleRelList(orgId, roleId);
		if(orgRoleRel==null){
			this.doSave(orgId, roleId);
		}
	}

	@Override
	public void updateUserOrgRole(Map<String, Object> params) throws Exception {
		JSONArray list= JSONArray.fromObject(StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(params,"list")));
		
		Iterator<Map<String,Object>> iterator= list.iterator();
		while (iterator.hasNext()) {
			Map<String,Object> map= iterator.next();
			Long userId=Long.valueOf(map.get("userId").toString());
			String userName=map.get("userName").toString();
			Long orgId=Long.valueOf(map.get("orgId").toString());
			Long roleId=Long.valueOf(map.get("roleId").toString());
			
			if(userId!=null && userId>0 && orgId!=null && orgId>0 && roleId!=null && roleId>0){
				this.updateUserOrgRel(userId, orgId, userName);
				this.updateUserRoleRel(userId, roleId, userName);
			}
			
			
		}
		
	}
	/**
	 * 更新用户的网点数据
	 * 
	 * @param userId
	 * @param orgId
	 */
	public void updateUserOrgRel(Long userId,Long orgId,String userName){
		Session session = SysContexts.getEntityManager();
		BaseUser user=SysContexts.getCurrentOperator();
		Criteria  roleCa = session.createCriteria(CmUserOrgRel.class);
		roleCa.add(Restrictions.eq("tenantId", user.getTenantId()));
		roleCa.add(Restrictions.eq("userId", userId));
		
		List<CmUserOrgRel> cmUserOrgRels= roleCa.list();
		
		if(cmUserOrgRels!=null && cmUserOrgRels.size()==1){
			CmUserOrgRel cmUserOrgRel=cmUserOrgRels.get(0);
			cmUserOrgRel.setOrgId(orgId);
			cmUserOrgRel.setOpId(user.getUserId());
			cmUserOrgRel.setOpDate(new Date());
			session.update(cmUserOrgRel);
			
		}else if(cmUserOrgRels!=null && cmUserOrgRels.size()>1){
			throw new BusinessException("员工["+userName+"]的网点数据有问题");
		}
		
	}
	
	/**
	 * 更新用户的角色数据
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void updateUserRoleRel(Long userId,Long roleId,String userName){
		Session session = SysContexts.getEntityManager();
		BaseUser user=SysContexts.getCurrentOperator();
		Criteria  roleCa = session.createCriteria(SysRoleOperRel.class);
		roleCa.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		roleCa.add(Restrictions.eq("operatorId", userId));
		
		List<SysRoleOperRel> sysRoleOperRels= roleCa.list();
		
		if(sysRoleOperRels!=null && sysRoleOperRels.size()==1){
			SysRoleOperRel sysRoleOperRel=sysRoleOperRels.get(0);
			
			sysRoleOperRel.setRoleId(roleId.intValue());
			sysRoleOperRel.setLastModifyDate(new Date());
			sysRoleOperRel.setLastModifyOperatorId(user.getUserId());
			
			session.update(sysRoleOperRel);
			
		}else if(sysRoleOperRels!=null && sysRoleOperRels.size()>1){
			throw new BusinessException("员工["+userName+"]的角色数据有问题");
		}
	}
	
}
