package com.wo56.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.sys.vo.SysEntity;
import com.wo56.business.sys.vo.SysObjectGrant;
import com.wo56.business.sys.vo.SysRole;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.common.cache.SysEntityCache;
import com.wo56.common.cache.SysObjectGrantCache;
import com.wo56.common.cache.SysRoleCache;
import com.wo56.common.cache.SysRoleOperRelCache;
import com.wo56.common.consts.LogBIConstant;
import com.wo56.common.consts.PermissionConsts;

/**
 * @company 增信信息科技#握物流
 * @author chenjun
 * @desc 权限/角色辅助类
 */
public class PrivilegeHelper {
	private static final Log log = LogFactory.getLog(PrivilegeHelper.class);
	/***************************新版权限管理**Start****************************/
	public static void grantOperatorRole(long operatorId, int roleId, long lastModifyOperatorId, String remark) throws Exception {
		if (!hasOwnerSpecialRole(operatorId, roleId, false)) {
			SysRoleOperRel operRel = new SysRoleOperRel();
			operRel.setRoleId(roleId);
			operRel.setOperatorId(operatorId);
			operRel.setState(1);
			Date currentDate = new Date();
			operRel.setCreateDate(currentDate);
			operRel.setLastModifyDate(currentDate);
			operRel.setLastModifyOperatorId(lastModifyOperatorId);
			operRel.setRemark(remark);
			SysContexts.getEntityManager().save(operRel);
		}
	}
	
	
	/**
	 * 获取角色表(sys_role)的所有有效数据
	 * @param isCache ： 是否从缓存中获取
	 * @return aaa
	 * @throws Exception
	 */
	public static List<SysRole> getAllRole(boolean isCache) throws Exception {
		List<SysRole> roles = null;
		if (isCache) {
			roles = (List<SysRole>)CacheFactory.get(SysRoleCache.class, PermissionConsts.GrantConstant.SYS_ROLE_CACHE_KEY);
		} else {
			Session session = SysContexts.getEntityManager(true);
			Criteria ca = session.createCriteria(SysRole.class);
			ca.add(Restrictions.eq(PermissionConsts.GrantConstant.STATE, PermissionConsts.GrantConstant.STATE_1));
			roles = ca.list();
		}
		return roles;
	}
	
	/**
	 * 获取实体表(sys_entity)的所有有效数据
	 * @param isCache ： 是否从缓存中获取
	 * @return
	 * @throws Exception
	 */
	public static List<SysEntity> getAllEntity(boolean isCache) throws Exception {
		List<SysEntity> entities = null;
		if (isCache) {
			entities = (List<SysEntity>)CacheFactory.get(SysEntityCache.class, PermissionConsts.GrantConstant.SYS_ENTITY_CACHE_KEY);
		} else {
			Session session = SysContexts.getEntityManager(true);
			Criteria ca = session.createCriteria(SysEntity.class);
			ca.add(Restrictions.eq(PermissionConsts.GrantConstant.STATE, PermissionConsts.GrantConstant.STATE_1));
			entities = ca.list();
		}
		return entities;
	}
	
	/**
	 * 获取实体表(sys_role_oper_rel)的所有有效数据
	 * @param isCache ： 是否从缓存中获取
	 * @return
	 * @throws Exception
	 */
	public static List<SysRoleOperRel> getAllRoleOperRel(boolean isCache) throws Exception {
		List<SysRoleOperRel> roleOperRels = null;
		if (isCache) {
			roleOperRels = (List<SysRoleOperRel>)CacheFactory.get(SysRoleOperRelCache.class, PermissionConsts.GrantConstant.SYS_ROLE_OPER_REL_CACHE_KEY);
		} else {
			Session session = SysContexts.getEntityManager(true);
			Criteria ca = session.createCriteria(SysRoleOperRel.class);
			ca.add(Restrictions.eq(PermissionConsts.GrantConstant.STATE, PermissionConsts.GrantConstant.STATE_1));
			roleOperRels = ca.list();
		}
		return roleOperRels;
	}

	/**
	 * 获取操作员关联的角色
	 * @param isCache
	 * @return
	 * @throws Exception
	 */
	public static List<SysRole> getOperatorOwnerRole(long operatorId, boolean isCache) throws Exception {
		List<SysRole> effRoles = new ArrayList<SysRole>();
		if (isCache) {
			List<SysRoleOperRel> allRoleOperRels = getAllRoleOperRel(isCache);// 所有关联角色
			if(null == allRoleOperRels || allRoleOperRels.size() == 0)
				return effRoles;
			
			List<SysRole> roles = getAllRole(isCache);// 所有角色
			if (null == roles || roles.size() == 0)
				return effRoles;
			
			//这个是比对操作员的id，从角色对应表中获取特殊的权限  wlpt暂时不存在这种情况
			List<SysRoleOperRel> effRels  = new ArrayList<SysRoleOperRel>();// 操作员关联的角色
			for (SysRoleOperRel rel : allRoleOperRels) {
				if (operatorId == rel.getOperatorId() && rel.getState() == PermissionConsts.GrantConstant.STATE_1)
					effRels.add(rel);
			}
			
			for (SysRoleOperRel rel : effRels) {
				for (SysRole role : roles) {
					if (role.getState() == PermissionConsts.GrantConstant.STATE_1 && rel.getRoleId() == role.getRoleId()) {// 有效角色
						effRoles.add(role);
						break;
					}
				}
			}
		} else {
			String sql = "SELECT * FROM SYS_ROLE A WHERE A.ROLE_ID IN ("
					+ "SELECT ROLE_ID FROM SYS_ROLE_OPER_REL WHERE OPERATOR_ID =:operatorId AND STATE = 1) AND A.STATE = 1";
			Session session = SysContexts.getEntityManager(true);
			Query query = session.createSQLQuery(sql).addEntity(SysRole.class);
			query.setParameter("operatorId", operatorId);
			effRoles = query.list();
		}
		return effRoles;
	}
	
	/**
	 * 判断操作员是否拥有指定角色
	 * 
	 * @param operatorId
	 * @param roleId
	 * @param isCache
	 * @return
	 * @throws Exception
	 */
	public static boolean hasOwnerSpecialRole(long operatorId, int roleId, boolean isCache) throws Exception {
//		if (isCache) {
//			List<SysRoleOperRel> allRoleOperRels = getAllRoleOperRel(isCache);// 所有关联角色
//			if (null == allRoleOperRels || allRoleOperRels.size() == 0)
//				return false;
//			Set<Integer> effRoleIdSet = new HashSet<Integer>();
//			for (int i = 0; null != allRoleOperRels && i < allRoleOperRels.size(); i++) {
//				SysRoleOperRel operRel = allRoleOperRels.get(i);
//				if(operRel.getOperatorId() == operatorId && roleId == operRel.getRoleId())
//					effRoleIdSet.add(operRel.getRoleId());
//			}
//			
//			List<SysRole> roles = getAllRole(isCache);
//			for (int i = 0; null != roles && i < roles.size(); i++) {
//				SysRole role = roles.get(i);
//				if (effRoleIdSet.contains(role.getRoleId()) && PermissionConsts.GrantConstant.STATE_1 != role.getState()) {
//					effRoleIdSet.remove(role.getRoleId());
//				}
//			}
//			return effRoleIdSet.size() > 0;
//		} else {
//			String sql = "SELECT ROLE_ID FROM SYS_ROLE WHERE ROLE_ID IN ( "
//					+ "SELECT ROLE_ID FROM SYS_ROLE_OPER_REL WHERE OPERATOR_ID =:operatorId AND ROLE_ID =:roleId AND STATE = 1) AND STATE = 1";
//			Session session = SysContexts.getEntityManager(true);
//			Query query = session.createSQLQuery(sql);
//			query.setParameter("operatorId", operatorId);
//			query.setParameter("roleId", roleId);
//			List list = query.list();
//			if(null != list && list.size() > 0)
//				return true;
//		}
		return true;
	}
	
	
	public static boolean hasOwnerSpecialEntityWithCache(long operatorId, int entityId) throws Exception {
		return hasOwnerSpecialEntity(operatorId, entityId, true);
	}
	
	/**
	 * 判断操作员是否拥有指定权限
	 * @param operatorId
	 * @param entityId
	 * @param isCache
	 * @return
	 * @throws Exception
	 */
	public static boolean hasOwnerSpecialEntity(long operatorId, int entityId, boolean isCache) throws Exception {
		boolean isXtgly = hasOwnerSpecialRole(operatorId, LogBIConstant.SysRoleConstant.SYS_ROLE_ID_XTGLY, isCache);
		if (isXtgly)
			return isXtgly;
		List<SysEntity> entities = getOperatorOwnerEntity(operatorId, isCache);
		if(null == entities || entities.size() == 0)
			return false;
		for (SysEntity entity : entities) {
			if (entity.getEntityId() == entityId)
				return true;
		}
		return false;
	}
	
	/**
	 * 获取用户拥有的实体信息
	 * 
	 * @param operatorId
	 * @param isCache
	 * @return
	 * @throws Exception
	 */
	public static List<SysEntity> getOperatorOwnerEntity(long operatorId, boolean isCache) throws Exception {
		List<SysEntity> effEntities = new ArrayList<SysEntity>();
		if (isCache) {
			List<SysRole> effRoles = getOperatorOwnerRole(operatorId, isCache);// 用户关联的有效角色
			Set<Long> effRoleIds = new HashSet<Long>();
			for (int i = 0; null != effRoles && i < effRoles.size(); i++) {
				effRoleIds.add(effRoles.get(i).getRoleId().longValue());
			}
			
			Set<Integer> effEntityIdSet = new HashSet<Integer>();
			if (effRoleIds.size() > 0) {// 角色下关联的实体
				List<SysObjectGrant> roleRelGrant = getObjectRelGrant(LogBIConstant.GrantConstant.OBJECT_TYPE_1, effRoleIds, isCache);// 角色关联的权限
				for (int i = 0; null != roleRelGrant && i < roleRelGrant.size(); i++) {
					SysObjectGrant grant = roleRelGrant.get(i);
					if (LogBIConstant.GrantConstant.IS_PERMIT_1 == grant.getIsPermit()) {
						effEntityIdSet.add(grant.getEntityId());
					}
				}
			}
			Set<Integer> needRemoveEntityIdSet = new HashSet<Integer>();

			List<SysObjectGrant> operatorRelGrant = getObjectRelGrant(LogBIConstant.GrantConstant.OBJECT_TYPE_3, operatorId, isCache);// 操作员关联的权限
			for (int i = 0; null != operatorRelGrant && i < operatorRelGrant.size(); i++) {
				SysObjectGrant grant = operatorRelGrant.get(i);
				if (LogBIConstant.GrantConstant.IS_PERMIT_1 == grant.getIsPermit()) {
					effEntityIdSet.add(grant.getEntityId());
				} else {// 其他的话，移除数据
					needRemoveEntityIdSet.add(grant.getEntityId());
				}
			}
			
			if (needRemoveEntityIdSet.size() > 0) {
				Iterator<Integer> it = needRemoveEntityIdSet.iterator();
				while (it.hasNext()) {
					effEntityIdSet.remove(it.next());
				}
			}
			
			List<SysEntity> allEntities = getAllEntity(isCache);
			for (int i = 0; null != allEntities && i < allEntities.size(); i++) {
				SysEntity entity = allEntities.get(i);
				if (effEntityIdSet.contains(entity.getEntityId()) && LogBIConstant.GrantConstant.STATE_1 == entity.getState()) {
					effEntities.add(entity);
				}
			}
		} else {
			String sql = "SELECT ENTITY_ID FROM SYS_OBJECT_GRANT WHERE OBJECT_TYPE = 1 AND OBJECT_ID IN ("
				+ "SELECT A.ROLE_ID FROM SYS_ROLE_OPER_REL A, SYS_ROLE B WHERE A.OPERATOR_ID =:operatorId AND A.STATE = 1 AND A.ROLE_ID = B.ROLE_ID AND B.STATE = 1 "
				+ ") AND IS_PERMIT = 1 AND STATE = 1";
			Session session = SysContexts.getEntityManager();
			Query query = session.createSQLQuery(sql);
			query.setParameter("operatorId", operatorId);
			Set<Integer> effEntityIdSet = new HashSet<Integer>();
			List list = query.list();
			for (int i = 0; i < list.size(); i++) {
				effEntityIdSet.add((Integer) list.get(i));
			}
			
			
			Set<Integer> needRemoveEntityIdSet = new HashSet<Integer>();
			
			// 操作员关联的权限
			String otherSql = "SELECT ENTITY_ID, IS_PERMIT FROM SYS_OBJECT_GRANT WHERE OBJECT_TYPE = 3 AND OBJECT_ID =:objectId AND STATE = 1 ";
			Query otherQuery = session.createSQLQuery(otherSql);
			otherQuery.setParameter("objectId", operatorId);
			list = otherQuery.list();
			for (int i = 0; i < list.size(); i++) {
				Object[] row = (Object[]) list.get(i);
				Integer entityId = (Integer)row[0];
				Short isPermit = (Short)row[1];
				if (isPermit == LogBIConstant.GrantConstant.IS_PERMIT_1) {// 有效数据
					effEntityIdSet.add(entityId);
				} else {
					needRemoveEntityIdSet.add(entityId);
				}
			}

			if (needRemoveEntityIdSet.size() > 0) {
				Iterator<Integer> it = needRemoveEntityIdSet.iterator();
				while (it.hasNext()) {
					effEntityIdSet.remove(it.next());
				}
			}
			
			Criteria ca = session.createCriteria(SysEntity.class);
			ca.add(Restrictions.in("entityId", effEntityIdSet));
			effEntities = ca.list();
		}
		return effEntities;
	}
	
	/**
	 * 获取object关联授权信息
	 * 
	 * @param objectType
	 * @param objectId
	 * @param isCache
	 * @return
	 * @throws Exception
	 */
	public static List<SysObjectGrant> getObjectRelGrant(int objectType, long objectId, boolean isCache) throws Exception {
		// 判断数据是否正确
		SysStaticData staticData = SysStaticDataUtil.getSysStaticData(LogBIConstant.GrantConstant.CODE_TYPE_SYS_OBJECT_GRANT_OBJECT_TYPE, String.valueOf(objectType));
		if (null == staticData || !String.valueOf(objectType).equals(staticData.getCodeValue())) {
			throw new IllegalArgumentException("参数objectType值错误");
		}
		List<SysObjectGrant> effGrants = new ArrayList<SysObjectGrant>();;
		if (isCache) {
			List<SysObjectGrant> objectGrants = getAllObjectGrant(isCache);
			if (null == objectGrants || objectGrants.size() == 0)
				return effGrants;
			for (SysObjectGrant grant : objectGrants) {
				if (objectType == grant.getObjectType() && objectId == grant.getObjectId()) {
					effGrants.add(grant);
				}
			}
		} else {
			Session session = SysContexts.getEntityManager();
			Criteria ca = session.createCriteria(SysObjectGrant.class);
			ca.add(Restrictions.eq(LogBIConstant.GrantConstant.STATE, LogBIConstant.GrantConstant.STATE_1));
			ca.add(Restrictions.eq(LogBIConstant.GrantConstant.OBJECT_TYPE, objectType));
			ca.add(Restrictions.eq(LogBIConstant.GrantConstant.OBJECT_ID, objectId));
			effGrants = ca.list();
		}
		return effGrants;
	}
	
	
	/**
	 * 获取对象授权表(sys_object_grant)的所有有效数据
	 * @param isCache : 是否从缓存中获取
	 * @return
	 * @throws Exception
	 */
	public static List<SysObjectGrant> getAllObjectGrant(boolean isCache) throws Exception {
		List<SysObjectGrant> objectGrants = null;
		if (isCache) {
			objectGrants = (List<SysObjectGrant>)CacheFactory.get(SysObjectGrantCache.class, "sysObjectGrantCache");
		} else {
			Session session = SysContexts.getEntityManager();
			Criteria ca = session.createCriteria(SysObjectGrant.class);
			ca.add(Restrictions.eq(LogBIConstant.GrantConstant.STATE, LogBIConstant.GrantConstant.STATE_1));
			objectGrants = ca.list();
		}
		return objectGrants;
	}
	
	
	public static List<SysObjectGrant> getObjectRelGrant(int objectType, Set<Long> objectIds, boolean isCache) throws Exception {
		SysStaticData staticData = SysStaticDataUtil.getSysStaticData(LogBIConstant.GrantConstant.CODE_TYPE_SYS_OBJECT_GRANT_OBJECT_TYPE, String.valueOf(objectType));
		if (null == staticData || !String.valueOf(objectType).equals(staticData.getCodeValue())) {// 判断参数{objectType}是否正确
			throw new IllegalArgumentException("参数objectType值错误");
		}
		List<SysObjectGrant> effGrants = new ArrayList<SysObjectGrant>();;
		if (isCache) {
			List<SysObjectGrant> objectGrants = getAllObjectGrant(isCache);
			if (null == objectGrants || objectGrants.size() == 0)
				return effGrants;
			for (SysObjectGrant grant : objectGrants) {
				if (objectType == grant.getObjectType() && objectIds.contains(grant.getObjectId())) {
					effGrants.add(grant);
				}
			}
		} else {
			Session session = SysContexts.getEntityManager();
			Criteria ca = session.createCriteria(SysObjectGrant.class);
			ca.add(Restrictions.eq(LogBIConstant.GrantConstant.STATE, LogBIConstant.GrantConstant.STATE_1));
			ca.add(Restrictions.eq(LogBIConstant.GrantConstant.OBJECT_TYPE, objectType));
			ca.add(Restrictions.in(LogBIConstant.GrantConstant.OBJECT_ID, objectIds));
			effGrants = ca.list();
		}
		return effGrants;
	}
	
	
	public static boolean isSystemManagerRole(long operatorId) throws Exception {
		return hasOwnerSpecialRole(operatorId, LogBIConstant.SysRoleConstant.SYS_ROLE_ID_XTGLY, true);
	}
	/***************************新版权限管理**End****************************/
	
	
}
