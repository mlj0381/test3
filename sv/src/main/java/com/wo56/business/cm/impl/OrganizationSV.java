package com.wo56.business.cm.impl;

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
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.components.redis.RemoteCacheUtil;
import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.vo.CmCustomer;
import com.wo56.business.cm.vo.CmOrgRel;
import com.wo56.business.cm.vo.CmUserInfo;
import com.wo56.business.cm.vo.CmUserOrgRel;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.org.vo.in.OrgSaveBussParamIn;
import com.wo56.business.org.vo.in.OrgSavePullPagParamIn;
import com.wo56.business.org.vo.in.OrgSaveZxParamIn;
import com.wo56.business.org.vo.in.OrgUpdatePullPagParamIn;
import com.wo56.business.org.vo.out.OrgQueryAllBusOut;
import com.wo56.business.org.vo.out.OrgQueryBusOut;
import com.wo56.business.org.vo.out.OrgSavePullPagParamOut;
import com.wo56.business.org.vo.out.OrganizationQueryZXOut;
import com.wo56.business.sys.vo.SysRoleOperRel;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.cache.SysTenantDefCache;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.PermissionConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.vo.SysTenantExtend;
import com.wo56.common.utils.OraganizationCacheDataUtil;
import com.wo56.common.utils.SmsUtil;

public class OrganizationSV {
	private static final Log log = LogFactory.getLog(OrganizationSV.class);

	/**
	 * 保存
	 * 
	 * @param org
	 * @throws Exception
	 */
	public void saveOrg(Organization org) throws Exception {
		SysContexts.getEntityManager().save(org);
	}

	/**
	 * 
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	public Organization getOrgByCode(String orgCode) throws Exception {
		Criteria ca = SysContexts.getEntityManager().createCriteria(Organization.class);
		ca.add(Restrictions.eq("orgCode", orgCode));
		List<Organization> list = ca.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询顶级的公司信息
	 * 
	 * @param tenantType
	 *            公司类型 1 平台 2 专线 3商家
	 * @return
	 * @throws Exception
	 */
	public List<Organization> getRootOrg(String tenantType) throws Exception {
		Criteria ca = SysContexts.getEntityManager().createCriteria(Organization.class);
		ca.add(Restrictions.eq("parentOrgId", -1L));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		if (!StringUtils.isEmpty(tenantType)) {
			ca.add(Restrictions.eq("tenantType", tenantType));
		}
		List<Organization> list = ca.list();
		return list;
	}

	/**
	 * lyh 根据ID查看商家信息
	 * 
	 * @param tenantId
	 * 
	 * @return OrgQueryBusOut
	 */
	public OrgQueryBusOut getOrgBusinessByTenantId(long tenantId) {
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS);// 商家类型
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("SELECT o.ORG_CODE,o.ORG_NAME,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE, ");
		sb.append(
				" o.BUSI_NOTES,o.BUSINESS_TYPE,c.ADDRESS,o.SELLER_NOTES,o.PROVINCE_ID,o.REGION_ID,o.COUNTY_ID,o.STREET_ID,d.LINE_ORG_ID FROM ");
		sb.append(
				" organization o LEFT JOIN cm_org_pickup_point c on o.TENANT_ID = c.TENANT_ID LEFT JOIN cm_org_rel d ON o.TENANT_ID = d.BUSI_TENANT_ID ");
		sb.append(" WHERE o.TENANT_TYPE = :zxType AND o.STATE = :state AND o.PARENT_ORG_ID = :parentOrgId ");
		sb.append(" AND o.TENANT_ID = :tenantId ");
		Query query = session.createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(zxType)) {
			query.setParameter("zxType", zxType);
		}
		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		query.setParameter("tenantId", tenantId);
		query.setParameter("parentOrgId", parentOrgId);
		List<Object[]> list = (List<Object[]>) query.list();
		;
		OrgQueryBusOut orgOut = new OrgQueryBusOut();
		orgOut.setTenantId(tenantId);
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				orgOut.setTenantCode(String.valueOf(obj[0]));
				orgOut.setName(String.valueOf(obj[1]));
				orgOut.setLinkMan(String.valueOf(obj[2]));
				orgOut.setLinkPhone(String.valueOf(obj[3]));
				if (obj[4] != null) {
					orgOut.setBusiNotes(obj[4].toString());
				}
				orgOut.setBusinessType(String.valueOf(obj[5]));
				if (obj[6] != null) {
					orgOut.setAddress(obj[6].toString());
				}
				if (obj[7] != null) {
					orgOut.setSellerNotes(obj[7].toString());
				}
				if (obj[8] != null) {
					orgOut.setProvinceId(String.valueOf(obj[8]));
				}
				if (obj[9] != null) {
					orgOut.setRegionId(String.valueOf(obj[9]));
				}
				if (obj[10] != null) {
					orgOut.setCountyId(String.valueOf(obj[10]));
				}
				if (obj[11] != null) {
					orgOut.setStreetId(String.valueOf(obj[11]));
				}
				long lineOrgId = Long.parseLong(String.valueOf(obj[12]));
				orgOut.setLineOrgId(lineOrgId);
				Organization o = (Organization) session.get(Organization.class, lineOrgId);
				orgOut.setLineOrgName(o.getOrgName());
			}
		}
		return orgOut;
	}

	public OrgQueryBusOut getOrgBusinessByRelId(long relId) {
		int zxType = SysStaticDataEnum.USER_TYPE.BUSINESS;// 商家类型
		int state = SysStaticDataEnum.ORG_STATE.COMMON;
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer("SELECT o.ORG_CODE,o.ORG_NAME,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE, ");
		sb.append(" o.BUSI_NOTES,o.BUSINESS_TYPE,d.ADDRESS,o.SELLER_NOTES,o.PROVINCE_ID, ");
		sb.append(" o.REGION_ID,o.COUNTY_ID,o.STREET_ID,c.LINE_ORG_ID,c.BUSI_TENANT_ID ");
		sb.append(" FROM cm_org_rel c LEFT JOIN organization o ON c.BUSI_ORG_ID = o.ORG_ID ");
		sb.append(" LEFT JOIN cm_org_pickup_point d ON d.ORG_ID = c.BUSI_ORG_ID ");
		sb.append(" where c.REL_ID = :relId AND c.STATE = :state AND o.TENANT_TYPE = :zxType ");

		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("zxType", zxType);
		query.setParameter("state", state);

		query.setParameter("relId", relId);
		List<Object[]> list = (List<Object[]>) query.list();
		;
		OrgQueryBusOut orgOut = new OrgQueryBusOut();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				orgOut.setTenantCode(String.valueOf(obj[0]));
				orgOut.setName(String.valueOf(obj[1]));
				orgOut.setLinkMan(String.valueOf(obj[2]));
				orgOut.setLinkPhone(String.valueOf(obj[3]));
				if (obj[4] != null) {
					orgOut.setBusiNotes(obj[4].toString());
				}
				orgOut.setBusinessType(String.valueOf(obj[5]));
				if (obj[6] != null) {
					orgOut.setAddress(obj[6].toString());
				}
				if (obj[7] != null) {
					orgOut.setSellerNotes(obj[7].toString());
				}
				if (obj[8] != null) {
					orgOut.setProvinceId(String.valueOf(obj[8]));
				}
				if (obj[9] != null) {
					orgOut.setRegionId(String.valueOf(obj[9]));
				}
				if (obj[10] != null) {
					orgOut.setCountyId(String.valueOf(obj[10]));
				}
				if (obj[11] != null) {
					orgOut.setStreetId(String.valueOf(obj[11]));
				}
				long lineOrgId = Long.parseLong(String.valueOf(obj[12]));
				orgOut.setLineOrgId(lineOrgId);
				Organization o = OraganizationCacheDataUtil.getOrganization(lineOrgId);
				;
				orgOut.setLineOrgName(o.getOrgName());
				orgOut.setTenantId(Long.parseLong(String.valueOf(obj[13])));
			}
		}
		return orgOut;
	}

	/**
	 * 根据条件查询没有跟本专线建立关系的存在的用户
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 * @return ooList
	 */
	public OrgQueryAllBusOut getOrgBusinessByNo(String tenantCode, String name, String linkPhone, int zxType) {
		Session session = SysContexts.getEntityManager(true);
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		BaseUser user = SysContexts.getCurrentOperator();
		long linkTenantId = user.getTenantId();
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		StringBuffer sb = new StringBuffer("SELECT o.TENANT_ID,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.BUSI_NOTES, ");
		sb.append(
				" o.BUSINESS_TYPE,d.ADDRESS,o.SELLER_NOTES,o.ORG_ID,c.LINE_ORG_ID,s.TENANT_CODE,s.`NAME`,o.PROVINCE_ID,o.REGION_ID,o.DEPARTMENT_ADDRESS,o.PROVINCE_ID,o.REGION_ID,o.COUNTY_ID,o.STREET_ID FROM sys_tenant_def s LEFT JOIN ");
		sb.append(" organization o on s.TENANT_ID = o.TENANT_ID LEFT JOIN cm_org_pickup_point d  ");
		sb.append(
				" on o.TENANT_ID = d.TENANT_ID LEFT JOIN cm_org_rel c ON c.BUSI_TENANT_ID = o.TENANT_ID where o.PARENT_ORG_ID = :parentOrgId AND o.TENANT_TYPE = :zxType ");
		sb.append(" AND s.`STATUS` = :state AND o.STATE = :state ");
		// sb.append(" AND c.LINE_TENANT_ID != :linkTenantId ");

		StringBuffer orSqlBuf = new StringBuffer();

		String orSqlStr = "";
		if (StringUtils.isNotEmpty(tenantCode)) {
			orSqlStr = orSqlStr + " s.TENANT_CODE = :tenantCode ";
		}

		if (StringUtils.isNotEmpty(name)) {
			if (StringUtils.isNotEmpty(orSqlStr)) {
				orSqlStr = orSqlStr + " or s.`NAME` = :name ";
			} else {
				orSqlStr = orSqlStr + "   s.`NAME` = :name ";
			}
		}

		if (StringUtils.isNotEmpty(linkPhone)) {
			if (StringUtils.isNotEmpty(orSqlStr)) {
				orSqlStr = orSqlStr + " or s.LINK_PHONE = :linkPhone ";
			} else {
				orSqlStr = orSqlStr + "   s.LINK_PHONE = :linkPhone ";
			}
		}
		if (StringUtils.isNotEmpty(orSqlStr)) {
			sb.append(" and ( " + orSqlStr + " )");
		}

		Query q = session.createSQLQuery(sb.toString());
		q.setParameter("zxType", zxType);
		q.setParameter("state", state);
		if (StringUtils.isNotEmpty(tenantCode)) {
			q.setParameter("tenantCode", tenantCode);
		}
		if (StringUtils.isNotEmpty(name)) {
			q.setParameter("name", name);
		}
		if (StringUtils.isNotEmpty(linkPhone)) {
			q.setParameter("linkPhone", linkPhone);
		}
		q.setParameter("parentOrgId", parentOrgId);
		// q.setParameter("linkTenantId", linkTenantId);
		List<Object[]> list = (List<Object[]>) q.list();
		OrgQueryAllBusOut oAllBus = new OrgQueryAllBusOut();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				oAllBus.setTenantId(Long.parseLong(String.valueOf(obj[0])));
				oAllBus.setLinkMan(String.valueOf(obj[1]));
				oAllBus.setLinkPhone(String.valueOf(obj[2]));
				if (obj[3] != null) {
					oAllBus.setBusiNotes(String.valueOf(obj[3]));
				}
				oAllBus.setBusinessType(Integer.parseInt(String.valueOf(obj[4])));

				oAllBus.setAddress(String.valueOf(obj[5]));
				if (obj[6] != null) {
					oAllBus.setSellerNotes(String.valueOf(obj[6]));
				}
				oAllBus.setOrgId(Long.parseLong(String.valueOf(obj[7])));
				if (obj[8] != null) {
					long lineOrgId = Long.parseLong(String.valueOf(obj[8]));
					oAllBus.setLineOrgId(lineOrgId);
					Organization o = (Organization) session.get(Organization.class, lineOrgId);
					oAllBus.setLineOrgName(o.getOrgName());
				}

				oAllBus.setTenantCode(String.valueOf(obj[9]));
				oAllBus.setName(String.valueOf(obj[10]));
				oAllBus.setProvinceId(String.valueOf(obj[11]));
				oAllBus.setRegionId(String.valueOf(obj[12]));
				oAllBus.setDepaetmentAdderss(String.valueOf(obj[13]));
				oAllBus.setProvinceId(String.valueOf(obj[14]));
				oAllBus.setRegionId(String.valueOf(obj[15]));
				oAllBus.setCountyId(String.valueOf(obj[16]));
				oAllBus.setStreetId(String.valueOf(obj[17]));
			}
		}
		return oAllBus;
	}
	
	/**
	 * 新增拉包公司
	 * @param paramIn
	 * @return
	 */
	public Map<String, Long> savePullPakCompanyOrganizationAndTeantInfo(OrgSavePullPagParamIn paramIn) {
		List<SysTenantDef> sysTenantDefs = (List<SysTenantDef>) CacheFactory.get(SysTenantDefCache.class.getName(),
				PermissionConsts.GrantConstant.SYS_TENANT_DEF);
		if (sysTenantDefs != null) {
			for (SysTenantDef def : sysTenantDefs) {
				if (def.getTenantCode().equals(paramIn.getTenantCode())) {
					throw new BusinessException("拉包公司的编码已经存在，请重新输入！");
				}
				if (def.getName().equals(paramIn.getName())) {
					throw new BusinessException("拉包公司的名称已经存在，请重新输入！");
				}
			}
		}
		// 1：`organization`新增组织信息，类型为拉包公司
		// 2：`sys_tenant_def`新增租户信息
		BaseUser user = SysContexts.getCurrentOperator();
		SysTenantDef sysTenantDef = new SysTenantDef();
		sysTenantDef.setTenantCode(paramIn.getTenantCode());
		sysTenantDef.setName(paramIn.getName());
		sysTenantDef.setLinkMan(paramIn.getLinkMan());
		sysTenantDef.setLinkPhone(paramIn.getLinkPhone());
		// 客服电话
		sysTenantDef.setCsPhones(paramIn.getCsPhones());
		sysTenantDef.setStatus(SysStaticDataEnum.STS.VALID);
		sysTenantDef.setOpId(user.getOperId());
		sysTenantDef.setCreateDate(new Date());
		Session session = SysContexts.getEntityManager();
		session.save(sysTenantDef);

		/* 初始化redis缓存-end */
		Organization org = new Organization();
		org.setTenantType(String.valueOf(SysStaticDataEnumYunQi.TENANT_TYPE.COMPANY));
		org.setTenantId(sysTenantDef.getTenantId());
		org.setOrgCode(sysTenantDef.getTenantCode());
		org.setOrgName(sysTenantDef.getName());
		org.setCreateDate(new Date());
		org.setParentOrgId(EnumConsts.ROOT_ORG_ID);
		org.setOpId(user.getUserId());
		org.setState(SysStaticDataEnum.STS.VALID);
		org.setSupportStaffPhone(paramIn.getCsPhones());
		org.setOrgPrincipalPhone(paramIn.getLinkPhone());
		org.setOrgPrincipal(paramIn.getLinkMan());
		org.setOrgType(SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE);
		org.setCreatorId(user.getUserId());
		org.setCreateDate(new Date());
		//公司地址
		org.setDepartmentAddress(paramIn.getAddress());
		// 公司简介
		org.setSellerNotes(paramIn.getSellerNotes());
		//拉包公司法人身份证
		org.setCorporateFrontCard(paramIn.getCorporateFrontCardId());
		org.setCorporateBackCard(paramIn.getCorporatebackCardId());
		//拉包公司营业执照
		org.setBusinesslicensePic(paramIn.getBusinesslicensePicId());
		session.save(org);
		Map<String, Long> retMap = new HashMap<String, Long>();
		retMap.put("orgId", org.getOrgId());
		retMap.put("tenantId", sysTenantDef.getTenantId());
		return retMap;

	}

	/**
	 * lyh 保存专线 1.`organization`新增组织信息，类型为专线 2.`sys_tenant_def`新增租户信息
	 * 
	 * @param tenantId
	 * @return orgId、tenantId
	 */
	public Map<String, Long> saveOrganizationAndTeantInfo(OrgSaveZxParamIn paramIn) {

		List<SysTenantDef> sysTenantDefs = (List<SysTenantDef>) CacheFactory.get(SysTenantDefCache.class.getName(),
				PermissionConsts.GrantConstant.SYS_TENANT_DEF);
		if (sysTenantDefs != null) {
			for (SysTenantDef def : sysTenantDefs) {
				if (def.getTenantCode().equals(paramIn.getTenantCode())) {
					throw new BusinessException("专线的编码已经存在，请重新输入！");
				}
				if (def.getName().equals(paramIn.getName())) {
					throw new BusinessException("专线的名称已经存在，请重新输入！");
				}
			}
		}
		// 1：`organization`新增组织信息，类型为专线
		// 2：`sys_tenant_def`新增租户信息
		BaseUser user = SysContexts.getCurrentOperator();
		SysTenantDef sysTenantDef = new SysTenantDef();
		sysTenantDef.setTenantCode(paramIn.getTenantCode());
		sysTenantDef.setName(paramIn.getName());
		sysTenantDef.setLinkMan(paramIn.getLinkMan());
		sysTenantDef.setLinkPhone(paramIn.getLinkPhone());
		// 客服电话
		sysTenantDef.setCsPhones(paramIn.getCsPhones());
		sysTenantDef.setStatus(SysStaticDataEnum.STS.VALID);
		sysTenantDef.setOpId(user.getOperId());
		sysTenantDef.setCreateDate(new Date());
		Session session = SysContexts.getEntityManager();
		session.save(sysTenantDef);
		/* 租户对应运单号前缀-begin */
		Criteria ca = SysContexts.getEntityManager().createCriteria(SysTenantExtend.class);
		ca.add(Restrictions.eq("EKey", EnumConsts.TRACKING_NUM_REDIS.TANANT_TRACKING_NUM_BEGIN));
		List<SysTenantExtend> extendList = ca.list();
		int maxEValue = 0;
		if (extendList != null && extendList.size() > 0) {
			for (SysTenantExtend temp : extendList) {
				if (temp.getEValue() != null && !"".equals(temp.getEValue())) {
					if (Integer.parseInt(temp.getEValue()) > maxEValue) {
						maxEValue = Integer.parseInt(temp.getEValue());
					}
				}
			}
		} else {
			maxEValue = 9;
		}
		maxEValue = maxEValue + 1;
		SysTenantExtend extend = new SysTenantExtend();
		extend.setTenantId(sysTenantDef.getTenantId());
		extend.setStatus(true);
		extend.setEKey(EnumConsts.TRACKING_NUM_REDIS.TANANT_TRACKING_NUM_BEGIN);
		extend.setEValue(maxEValue + "");
		session.save(extend);
		/* 租户对应运单号前缀-end */
		/* 初始化redis缓存-begin */
		RemoteCacheUtil.set(EnumConsts.TRACKING_NUM_REDIS.ORD_SEQ + sysTenantDef.getTenantId() + "",
				"" + maxEValue + "00000001");
		/* 初始化redis缓存-end */
		Organization org = new Organization();
		org.setTenantType(String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN));
		org.setTenantId(sysTenantDef.getTenantId());
		org.setOrgCode(sysTenantDef.getTenantCode());
		org.setOrgName(sysTenantDef.getName());
		org.setCreateDate(new Date());
		org.setParentOrgId(EnumConsts.ROOT_ORG_ID);
		org.setOpId(user.getOperId());
		org.setState(SysStaticDataEnum.STS.VALID);
		org.setOrgName(paramIn.getName());
		org.setSupportStaffPhone(paramIn.getCsPhones());
		org.setOrgPrincipalPhone(paramIn.getLinkPhone());
		org.setOrgPrincipal(paramIn.getLinkMan());
		org.setOrgType(SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE);
		session.save(org);

		Map<String, Long> retMap = new HashMap<String, Long>();
		retMap.put("orgId", org.getOrgId());
		retMap.put("tenantId", sysTenantDef.getTenantId());
		return retMap;
	}

 
	
	
	
	
	
	/**
	 * 专线查询
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOrgLine(String name, String linkMan, String linkPhone, String provinceIds,
			boolean selfTenant) {
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN);// 专线类型
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(
				"SELECT * from (SELECT DISTINCT o.TENANT_ID,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.SUPPORT_STAFF_PHONE, ");
		sb.append(" r.SOURCE_PROVINCE_NAME,(SELECT GROUP_CONCAT(r1.DES_PROVINCE_NAME) FROM route_introduce r1  ");
		sb.append(
				" WHERE r1.TENANT_ID = r.TENANT_ID GROUP BY r1.TENANT_ID) AS DES_PROVINCE_NAME,r.DES_DETAIL,o.ORG_NAME,o.ORG_CODE,o.org_id  ");
		sb.append(" FROM organization o LEFT JOIN route_introduce r ON o.TENANT_ID = r.TENANT_ID ");
		sb.append(" where o.PARENT_ORG_ID = :parentOrgId and o.STATE = :state and o.TENANT_TYPE = :zxType");
		if (StringUtils.isNotEmpty(name)) {
			sb.append(" and o.ORG_NAME like :name ");
		}
		if (StringUtils.isNotEmpty(linkMan)) {
			sb.append(" and o.ORG_PRINCIPAL like :linkMan ");
		}
		if (StringUtils.isNotEmpty(linkPhone)) {
			sb.append(" and o.ORG_PRINCIPAL_PHONE = :linkPhone ");
		}
		if (StringUtils.isNotEmpty(provinceIds) && !provinceIds.equals("-1")) {
			sb.append(" and r.DES_PROVINCE_ID = :provinceIds ");
		}
		// 不查询自己租户下的专线
		if (selfTenant) {
			sb.append(" and o.tenant_id != " + baseUser.getTenantId());
		}
		sb.append(" ORDER BY o.TENANT_ID ASC) as s ");
		Query query = session.createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		if (StringUtils.isNotEmpty(zxType)) {
			query.setParameter("zxType", zxType);
		}
		if (StringUtils.isNotEmpty(name)) {
			query.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotEmpty(linkMan)) {
			query.setParameter("linkMan", "%" + linkMan + "%");
		}
		if (StringUtils.isNotEmpty(linkPhone)) {
			query.setParameter("linkPhone", linkPhone);
		}
		if (StringUtils.isNotEmpty(provinceIds) && !provinceIds.equals("-1")) {
			query.setParameter("provinceIds", provinceIds);
		}
		query.setParameter("parentOrgId", parentOrgId);
		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<OrganizationQueryZXOut> rtnList = new ArrayList<OrganizationQueryZXOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrganizationQueryZXOut orgOut = new OrganizationQueryZXOut();
				if (obj[0] != null) {
					orgOut.setTenantId(Long.parseLong(obj[0].toString()));
				}
				if (obj[5] != null) {
					String s = obj[5].toString();
					String s1 = s.replaceAll(",", " / ");
					orgOut.setProvinceNames(s1);
				}
				if (obj[1] != null) {
					orgOut.setLinkMan((String) obj[1]);
				}
				if (obj[2] != null) {
					orgOut.setLinkPhone((String) obj[2]);
				}
				if (obj[3] != null) {
					orgOut.setCsPhones((String) obj[3]);
				}
				if (obj[6] != null) {
					orgOut.setAddress((String) obj[6]);
				}
				orgOut.setTenantCode(String.valueOf(obj[8]));
				orgOut.setName(String.valueOf(obj[7]));
				orgOut.setOrgId(String.valueOf(obj[9]));
				rtnList.add(orgOut);
			}
		}
		page.setThisPageElements(rtnList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return rtnMap;
	}

	/**
	 * 根据商家查询专线
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 * @return ooList
	 */
	public List<OrgQueryAllBusOut> getOrgBusByLink(long tenantId) {
		Session session = SysContexts.getEntityManager(true);
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN);// 商家类型
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		StringBuffer sb = new StringBuffer(" SELECT o.TENANT_ID,o.ORG_NAME FROM organization o ");
		sb.append("WHERE o.TENANT_TYPE = :zxType AND o.PARENT_ORG_ID = :parentOrgId ");
		sb.append("AND o.STATE = :state AND o.TENANT_ID IN (SELECT c.LINE_TENANT_ID ");
		sb.append(
				"FROM cm_org_rel c WHERE c.BUSI_TENANT_ID = :tenantId and c.STATE = :state) ORDER BY o.TENANT_ID desc ");
		Query q = session.createSQLQuery(sb.toString());
		q.setParameter("zxType", zxType);
		q.setParameter("state", state);
		q.setParameter("tenantId", tenantId);
		q.setParameter("parentOrgId", parentOrgId);
		List<Object[]> list = (List<Object[]>) q.list();
		List<OrgQueryAllBusOut> oAllBusList = new ArrayList<OrgQueryAllBusOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrgQueryAllBusOut oAllBus = new OrgQueryAllBusOut();
				if (obj[0] != null) {
					oAllBus.setTenantId(Long.parseLong(obj[0].toString()));
				}
				if (obj[1] != null) {
					oAllBus.setName(obj[1].toString());
				}
				oAllBusList.add(oAllBus);
			}
		}
		return oAllBusList;
	}

	/**
	 * 根据专线查询商家
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 * @return ooList
	 */
	public List<OrgQueryAllBusOut> getOrgLinkByBus(long tenantId) {
		Session session = SysContexts.getEntityManager(true);
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS);// 商家类型
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		StringBuffer sb = new StringBuffer(" SELECT o.TENANT_ID,o.ORG_NAME FROM organization o ");
		sb.append("WHERE o.TENANT_TYPE = :zxType AND o.PARENT_ORG_ID = :parentOrgId ");
		sb.append("AND o.STATE = :state AND o.TENANT_ID IN (SELECT c.BUSI_TENANT_ID ");
		sb.append(
				"FROM cm_org_rel c WHERE c.LINE_TENANT_ID = :tenantId and c.STATE = :state) ORDER BY o.TENANT_ID desc ");
		Query q = session.createSQLQuery(sb.toString());
		q.setParameter("zxType", zxType);
		q.setParameter("state", state);
		q.setParameter("tenantId", tenantId);
		q.setParameter("parentOrgId", parentOrgId);
		List<Object[]> list = (List<Object[]>) q.list();
		List<OrgQueryAllBusOut> oAllBusList = new ArrayList<OrgQueryAllBusOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrgQueryAllBusOut oAllBus = new OrgQueryAllBusOut();
				if (obj[0] != null) {
					oAllBus.setTenantId(Long.parseLong(obj[0].toString()));
				}
				if (obj[1] != null) {
					oAllBus.setName(obj[1].toString());
				}
				oAllBusList.add(oAllBus);
			}
		}
		return oAllBusList;
	}

	/**
	 * 专线商家分页查询
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getLinkQueryBusiness(String name, String linkMan, String linkPhone, String busiNotes,
			long orgId) {
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS);// 商家类型
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		BaseUser user = SysContexts.getCurrentOperator();
		long lineTenantId = user.getTenantId();
		Session session = SysContexts.getEntityManager(true);

		StringBuffer sb = new StringBuffer(
				"SELECT o.TENANT_ID,o.ORG_CODE,o.ORG_NAME,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.BUSI_NOTES, ");
		sb.append("o.BUSINESS_TYPE,d.ADDRESS,o.SELLER_NOTES,c.LINE_ORG_ID,c.REL_ID FROM organization o ");
		sb.append("LEFT JOIN cm_org_pickup_point d ON o.TENANT_ID = d.TENANT_ID ");
		sb.append("LEFT JOIN cm_org_rel c ON o.TENANT_ID = c.BUSI_TENANT_ID ");
		sb.append(
				"where o.TENANT_TYPE = :zxType and c.STATE = :state and o.STATE = :state and o.PARENT_ORG_ID = :parentOrgId ");
		Organization org = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		long pOrgId = org.getParentOrgId();

		// if(pOrgId == EnumConsts.ROOT_ORG_ID){
		// sb.append(" and c.line_tenant_Id = :lineTenantId");
		// }else{
		// sb.append(" and c.line_org_Id = :lineOrgId");
		// }
		if (orgId > 0) {
			Organization o = OraganizationCacheDataUtil.getOrganization(orgId);
			if (o.getParentOrgId() == EnumConsts.ROOT_ORG_ID) {
				sb.append(" and c.line_tenant_Id = :lineTenantId");
			} else {
				sb.append(" and c.line_org_Id = :lineOrgId");
			}
		} else {
			if (pOrgId == EnumConsts.ROOT_ORG_ID) {
				sb.append(" and c.line_tenant_Id = :lineTenantId");
			} else {
				sb.append(" and c.line_org_Id = :lineOrgId");
			}
		}

		if (StringUtils.isNotEmpty(name)) {
			sb.append(" and o.ORG_NAME like :name ");
		}
		if (StringUtils.isNotEmpty(linkMan)) {
			sb.append(" and o.ORG_PRINCIPAL like :linkMan ");
		}
		if (StringUtils.isNotEmpty(linkPhone)) {
			sb.append(" and o.ORG_PRINCIPAL_PHONE = :linkPhone ");
		}
		if (StringUtils.isNotEmpty(busiNotes)) {
			sb.append(" and o.BUSI_NOTES = :busiNotes ");
		}
		sb.append(" ORDER BY o.TENANT_ID ASC ");
		Query query = session.createSQLQuery(sb.toString());

		if (orgId > 0) {
			Organization o = OraganizationCacheDataUtil.getOrganization(orgId);
			if (o.getParentOrgId() == EnumConsts.ROOT_ORG_ID) {
				query.setParameter("lineTenantId", lineTenantId);
			} else {
				query.setParameter("lineOrgId", orgId);
			}

		} else {
			if (pOrgId == EnumConsts.ROOT_ORG_ID) {
				query.setParameter("lineTenantId", lineTenantId);
			} else {
				query.setParameter("lineOrgId", user.getOrgId());
			}
		}

		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		if (StringUtils.isNotEmpty(zxType)) {
			query.setParameter("zxType", zxType);
		}
		if (StringUtils.isNotEmpty(name)) {
			query.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotEmpty(linkMan)) {
			query.setParameter("linkMan", "%" + linkMan + "%");
		}
		if (StringUtils.isNotEmpty(linkPhone)) {
			query.setParameter("linkPhone", linkPhone);
		}
		if (StringUtils.isNotEmpty(busiNotes)) {
			query.setParameter("busiNotes", busiNotes);
		}
		query.setParameter("parentOrgId", parentOrgId);
		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<OrgQueryBusOut> rtnList = new ArrayList<OrgQueryBusOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrgQueryBusOut orgOut = new OrgQueryBusOut();
				orgOut.setTenantId(Long.parseLong(String.valueOf(obj[0])));
				orgOut.setTenantCode(String.valueOf(obj[1]));
				orgOut.setName(String.valueOf(obj[2]));
				if (obj[3] != null) {
					orgOut.setLinkMan(obj[3].toString());
				}
				if (obj[4] != null) {
					orgOut.setLinkPhone(obj[4].toString());
				}
				if (obj[5] != null) {
					orgOut.setBusiNotes(obj[5].toString());
				}
				if (obj[6] != null) {
					orgOut.setBusinessType(
							SysStaticDataUtil.getSysStaticData("SERVE_TYPE", obj[6].toString()).getCodeName());
				}
				long lineOrgId = Long.parseLong(String.valueOf(obj[9]));
				Organization o = (Organization) session.get(Organization.class, lineOrgId);
				orgOut.setLineOrgName(o.getOrgName());
				orgOut.setRelId(Long.parseLong(String.valueOf(obj[10])));
				rtnList.add(orgOut);
			}
		}
		page.setThisPageElements(rtnList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return rtnMap;
	}

	/**
	 * 专线商家分页查询
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getLinkQueryBusinessOther(String name, String linkMan, String linkPhone,
			String busiNotes, long orgId) {
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS);// 商家类型
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		BaseUser user = SysContexts.getCurrentOperator();
		long lineTenantId = user.getTenantId();
		Session session = SysContexts.getEntityManager(true);

		StringBuffer sb = new StringBuffer(
				"SELECT o.TENANT_ID,o.ORG_CODE,o.ORG_NAME,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.BUSI_NOTES, ");
		sb.append("o.BUSINESS_TYPE,d.ADDRESS,o.SELLER_NOTES,c.LINE_ORG_ID,c.REL_ID FROM organization o ");
		sb.append("LEFT JOIN cm_org_pickup_point d ON o.TENANT_ID = d.TENANT_ID ");
		sb.append("LEFT JOIN cm_org_rel c ON o.TENANT_ID = c.BUSI_TENANT_ID ");
		sb.append(
				"where o.TENANT_TYPE = :zxType and c.STATE = :state and o.STATE = :state and o.PARENT_ORG_ID = :parentOrgId ");
		Organization org = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		long pOrgId = org.getParentOrgId();

		// if(pOrgId == EnumConsts.ROOT_ORG_ID){
		// sb.append(" and c.line_tenant_Id = :lineTenantId");
		// }else{
		// sb.append(" and c.line_org_Id = :lineOrgId");
		// }
		if (orgId > 0) {
			Organization o = OraganizationCacheDataUtil.getOrganization(orgId);
			if (o.getParentOrgId() == EnumConsts.ROOT_ORG_ID) {
				sb.append(" and c.line_tenant_Id = :lineTenantId");
			} else {
				sb.append(" and c.line_org_Id = :lineOrgId");
			}
		} else {
			if (pOrgId == EnumConsts.ROOT_ORG_ID) {
				sb.append(" and c.line_tenant_Id = :lineTenantId");
			} else {
				sb.append(" and c.line_org_Id = :lineOrgId");
			}
		}

		if (StringUtils.isNotEmpty(name)) {
			sb.append(" and o.ORG_NAME like :name ");
		}
		if (StringUtils.isNotEmpty(linkMan)) {
			sb.append(" and o.ORG_PRINCIPAL like :linkMan ");
		}
		if (StringUtils.isNotEmpty(linkPhone)) {
			sb.append(" and o.ORG_PRINCIPAL_PHONE = :linkPhone ");
		}
		if (StringUtils.isNotEmpty(busiNotes)) {
			sb.append(" and o.BUSI_NOTES = :busiNotes ");
		}
		sb.append(" ORDER BY o.TENANT_ID ASC ");
		Query query = session.createSQLQuery(sb.toString());

		if (orgId > 0) {
			Organization o = OraganizationCacheDataUtil.getOrganization(orgId);
			if (o.getParentOrgId() == EnumConsts.ROOT_ORG_ID) {
				query.setParameter("lineTenantId", lineTenantId);
			} else {
				query.setParameter("lineOrgId", orgId);
			}

		} else {
			if (pOrgId == EnumConsts.ROOT_ORG_ID) {
				query.setParameter("lineTenantId", lineTenantId);
			} else {
				query.setParameter("lineOrgId", user.getOrgId());
			}
		}

		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		if (StringUtils.isNotEmpty(zxType)) {
			query.setParameter("zxType", zxType);
		}
		if (StringUtils.isNotEmpty(name)) {
			query.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotEmpty(linkMan)) {
			query.setParameter("linkMan", "%" + linkMan + "%");
		}
		if (StringUtils.isNotEmpty(linkPhone)) {
			query.setParameter("linkPhone", linkPhone);
		}
		if (StringUtils.isNotEmpty(busiNotes)) {
			query.setParameter("busiNotes", busiNotes);
		}
		query.setParameter("parentOrgId", parentOrgId);
		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<OrgQueryBusOut> rtnList = new ArrayList<OrgQueryBusOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrgQueryBusOut orgOut = new OrgQueryBusOut();
				orgOut.setTenantId(Long.parseLong(String.valueOf(obj[0])));
				orgOut.setTenantCode(String.valueOf(obj[1]));
				orgOut.setName(String.valueOf(obj[2]));
				if (obj[3] != null) {
					orgOut.setLinkMan(obj[3].toString());
				}
				if (obj[4] != null) {
					orgOut.setLinkPhone(obj[4].toString());
				}
				if (obj[5] != null) {
					orgOut.setBusiNotes(obj[5].toString());
				}
				if (obj[6] != null) {
					orgOut.setBusinessType(
							SysStaticDataUtil.getSysStaticData("SERVE_TYPE", obj[6].toString()).getCodeName());
				}
				long lineOrgId = Long.parseLong(String.valueOf(obj[9]));
				Organization o = (Organization) session.get(Organization.class, lineOrgId);
				orgOut.setLineOrgName(o.getOrgName());
				orgOut.setRelId(Long.parseLong(String.valueOf(obj[10])));
				rtnList.add(orgOut);
			}
		}
		page.setThisPageElements(rtnList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return rtnMap;
	}

	/**
	 * lyh 新增商家
	 * 
	 * @param tenantId
	 * @return orgId、tenantId
	 * @throws Exception
	 * 
	 */
	public Map<String, Long> saveOrgBusByTenantId(OrgSaveBussParamIn paramIn, int tenantType, int roleId)
			throws Exception {
		Session session = SysContexts.getEntityManager();
		Session query = SysContexts.getEntityManager(true);
		Criteria ca = query.createCriteria(SysTenantDef.class);

		int state = SysStaticDataEnum.STS.VALID;
		ca.add(Restrictions.eq("status", state));
		List<SysTenantDef> sysTenantDefs = (List<SysTenantDef>) ca.list();

		if (sysTenantDefs != null) {
			for (SysTenantDef def : sysTenantDefs) {
				// 等于商家才需要判断
				if (tenantType == SysStaticDataEnum.USER_TYPE.BUSINESS) {
					if (def.getLinkPhone() != null && def.getLinkPhone().equals(paramIn.getLinkPhone())) {
						throw new BusinessException("商家的手机已经存在，请重新输入！");
					}
				}
				if (def.getTenantCode().equals(paramIn.getTenantCode())) {
					throw new BusinessException("商家的编码已经存在，请重新输入！");
				}
				if (def.getName().equals(paramIn.getName())) {
					throw new BusinessException("商家的名称已经存在，请重新输入！");
				}
			}
		}

		// 1：`sys_tenant_def`新增租户信息
		BaseUser user = SysContexts.getCurrentOperator();
		SysTenantDef sysTenantDef = new SysTenantDef();
		sysTenantDef.setTenantCode(paramIn.getTenantCode());
		sysTenantDef.setName(paramIn.getName());
		sysTenantDef.setLinkMan(paramIn.getLinkMan());
		sysTenantDef.setLinkPhone(paramIn.getLinkPhone());
		sysTenantDef.setStatus(SysStaticDataEnum.STS.VALID);
		sysTenantDef.setOpId(user.getOperId());
		sysTenantDef.setCreateDate(new Date());
		session.save(sysTenantDef);
		// 2：`organization`新增组织信息，类型为商家
		Organization org = new Organization();
		org.setTenantType(String.valueOf(tenantType));
		org.setOrgCode(sysTenantDef.getTenantCode());
		org.setTenantId(sysTenantDef.getTenantId());
		org.setOrgName(sysTenantDef.getName());
		org.setCreateDate(new Date());
		org.setParentOrgId(EnumConsts.ROOT_ORG_ID);
		org.setOpId(user.getOperId());
		org.setState(SysStaticDataEnum.STS.VALID);
		org.setOrgPrincipalPhone(paramIn.getLinkPhone());
		org.setOrgPrincipal(paramIn.getLinkMan());
		org.setBusiNotes(paramIn.getBusiNotes());
		org.setSellerNotes(paramIn.getSellerNotes());
		org.setOrgType(SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE);
		org.setProvinceId(paramIn.getProvinceId());
		org.setProvinceName(SysStaticDataUtil
				.getProvinceDataList("SYS_PROVINCE", String.valueOf(paramIn.getProvinceId())).getName());
		org.setRegionId(paramIn.getRegionId());
		org.setRegionName(
				SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(paramIn.getRegionId())).getName());
		org.setCountyId(paramIn.getCountyId());
		org.setCountyName(
				SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(paramIn.getCountyId())).getName());
		org.setStreetId(paramIn.getStreetId());
		org.setStreetName(
				SysStaticDataUtil.getStreetDataList("SYS_STREET", String.valueOf(paramIn.getStreetId())).getName());
		// 提货地址
		org.setDepartmentAddress(paramIn.getDepaetmentAdderss());

		if (paramIn.getBusinessType() <= 0 && tenantType == SysStaticDataEnum.USER_TYPE.BUSINESS) {
			throw new BusinessException("服务类型不能为空!");
		}
		org.setBusinessType(paramIn.getBusinessType());
		session.save(org);

		// 3:CmUserInfoTF.regAppUserInfo 注册用户cm_user_info 用户类型是商家 登录类型是web+app
		// 用户注册
		String loginAcct = paramIn.getLoginAcct();

		if (tenantType == SysStaticDataEnum.USER_TYPE.BUSINESS) {
			loginAcct = paramIn.getLinkPhone();
		}

		// String passWord = String.valueOf(CommonUtil.getRandomNumber(1, 6,
		// 1));
		String passWord = "123456";
		// 解密
		// passWord=EncryPwdUtil.pwdDecryption(passWord);
		// 加密
		String loginPwd = passWord;
		passWord = K.j_s(passWord);
		CmUserInfo cmUserInfo = new CmUserInfo();
		cmUserInfo.setLoginAcct(loginAcct);
		cmUserInfo.setLoginPwd(passWord);
		cmUserInfo.setUserName(paramIn.getLinkMan());
		cmUserInfo.setCreateTime(new Date());
		cmUserInfo.setState(SysStaticDataEnum.USER_STATE.USER_STATE_VAILDATE);
		cmUserInfo.setLoginType(SysStaticDataEnum.LOGIN_TYPE.WEB_AND_APP);
		cmUserInfo.setUserType(tenantType);
		cmUserInfo.setOpId(user.getOperId());
		session.save(cmUserInfo);

		// 5:cm_user_org_rel 用户跟租户id关联起来
		CmUserOrgRel cmUserOrgRel = new CmUserOrgRel();
		cmUserOrgRel.setOpDate(new Date());
		cmUserOrgRel.setOpId(user.getOperId());
		cmUserOrgRel.setTenantId(org.getTenantId());
		cmUserOrgRel.setUserId(cmUserInfo.getUserId());
		cmUserOrgRel.setOrgId(org.getOrgId());
		session.save(cmUserOrgRel);

		// 6:sys_role_oper_rel role_id 通过配载去弄，sys_cfg SysCfgUtil
		SysRoleOperRel operRel = new SysRoleOperRel();
		operRel.setRoleId(roleId);
		operRel.setCreateDate(new Date(System.currentTimeMillis()));
		operRel.setLastModifyDate(new Date(System.currentTimeMillis()));
		operRel.setLastModifyOperatorId(user.getUserId());
		operRel.setOperatorId(cmUserInfo.getUserId());
		operRel.setState(SysStaticDataEnum.STS.VALID);
		session.save(operRel);

		// 7:增加组织关系表cm_org_rel，记录商家和专线之间的关系
		CmOrgRel cor = new CmOrgRel();
		if (tenantType == SysStaticDataEnum.USER_TYPE.BUSINESS) {
			cor.setLineOrgId(paramIn.getLineOrgId());
		} else {
			cor.setLineOrgId(user.getOrgId());
		}
		cor.setLineTenantId(user.getTenantId());
		cor.setBusiOrgId(org.getOrgId());
		cor.setBusiTenantId(sysTenantDef.getTenantId());
		cor.setOpDate(new Date(System.currentTimeMillis()));
		cor.setOpId(user.getOperId());
		cor.setOpName(user.getUserName());
		cor.setState(SysStaticDataEnum.STS.VALID);
		session.save(cor);
		if (tenantType == SysStaticDataEnum.USER_TYPE.BUSINESS) {
			// 8:cm_customer
			CmCustomer cc = new CmCustomer();
			cc.setName(sysTenantDef.getName());
			cc.setLinkmanName(sysTenantDef.getLinkMan());
			cc.setBill(sysTenantDef.getCsPhones());
			cc.setTelephone(sysTenantDef.getLinkPhone());
			cc.setAddress(paramIn.getAddress());
			// 商家归属网点
			cc.setOrgId(paramIn.getLineOrgId());
			cc.setTenantId(sysTenantDef.getTenantId());
			cc.setType(SysStaticDataEnum.CUSTOMER_TYPE.PUB_CUSTOMER);
			// 父级id
			cc.setParentId((long) SysStaticDataEnum.PARENT_ID.PARENT);
			cc.setState(SysStaticDataEnum.STS.VALID);
			cc.setSigningType(SysStaticDataEnum.SIGNING_TYPE.SIGNING_IS);
			cc.setCreateDate(new Date());
			cc.setZxTenantId(user.getTenantId());

			session.save(cc);
		}
		// TODO
		if (tenantType == SysStaticDataEnum.USER_TYPE.BUSINESS) {
			// 4:发送短信给用户：告知账号密码，登录地址，app下载地址
			SmsUtil.sendBusinessVaildCode(user.getTenantId(), paramIn.getLinkPhone(), paramIn.getTenantCode(),
					paramIn.getLinkPhone(), loginPwd);
		}
		// else{
		// SmsUtil.sendSFVaildCode(user.getTenantId(),billId,
		// paramIn.getTenantCode(), paramIn.getLinkPhone(), loginPwd);
		// }
		Map<String, Long> retMap = new HashMap<String, Long>();
		retMap.put("orgId", org.getOrgId());
		retMap.put("tenantId", sysTenantDef.getTenantId());
		return retMap;
	}

	/**
	 * lyh 修改商家
	 * 
	 * @param tenantId
	 * @return orgId 修改项tenantId orgId
	 *         tenantCode、name、linkMan、linkPhone、csPhones 需要判断
	 */
	public Map<String, Long> updateBusInfo(OrgSaveBussParamIn paramIn, Organization org, int tenantType) {
		// 1：`organization`修改组织信息，类型为专线
		// 2：`sys_tenant_def`修改租户信息
		List<SysTenantDef> sysTenantDefs = (List<SysTenantDef>) CacheFactory.get(SysTenantDefCache.class.getName(),
				PermissionConsts.GrantConstant.SYS_TENANT_DEF);
		if (sysTenantDefs != null) {
			for (SysTenantDef def : sysTenantDefs) {
				if (def.getTenantId() != paramIn.getTenantId()) {
					if (def.getTenantCode().equals(paramIn.getTenantCode())
							&& def.getTenantId().longValue() != paramIn.getTenantId()) {
						throw new BusinessException("专线的编码已经存在，请重新输入！");
					}
					if (def.getName().equals(paramIn.getName())
							&& def.getTenantId().longValue() != paramIn.getTenantId()) {
						throw new BusinessException("专线的名称已经存在，请重新输入！");
					}
				}
			}
		}
		// 1：`organization`新增组织信息，类型为专线
		// 2：`sys_tenant_def`新增租户信息
		Session session = SysContexts.getEntityManager();
		SysTenantDef sysTenantDef = (SysTenantDef) session.get(SysTenantDef.class, paramIn.getTenantId());
		if (sysTenantDef == null) {
			new BusinessException("数据有误，改商家不存在租户！");
		}
		BaseUser user = SysContexts.getCurrentOperator();
		sysTenantDef.setTenantCode(paramIn.getTenantCode());
		sysTenantDef.setName(paramIn.getName());
		sysTenantDef.setLinkMan(paramIn.getLinkMan());
		sysTenantDef.setLinkPhone(paramIn.getLinkPhone());
		sysTenantDef.setStatus(SysStaticDataEnum.STS.VALID);
		sysTenantDef.setOpId(user.getOperId());
		// sysTenantDef.setCreateDate(new Date());
		session.update(sysTenantDef);

		org.setTenantType(String.valueOf(tenantType));// 租户类型
		org.setTenantId(sysTenantDef.getTenantId());// 租户ID
		org.setOrgName(paramIn.getName());// 租户名称
		org.setOrgCode(paramIn.getTenantCode());// 租户编码
		org.setCreateDate(new Date());// 创建时间
		org.setParentOrgId(EnumConsts.ROOT_ORG_ID);// 顶级组织
		org.setOpId(user.getOperId());// 当前操作人
		org.setState(SysStaticDataEnum.STS.VALID);// 状态
		org.setOrgPrincipalPhone(paramIn.getLinkPhone());// 联系人电话
		org.setOrgPrincipal(paramIn.getLinkMan());// 联系人姓名
		org.setOrgType(SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE);// 公司类型
		org.setBusinessType(paramIn.getBusinessType());// 服务类型
		org.setBusiNotes(paramIn.getBusiNotes());// 经营业务
		org.setSellerNotes(paramIn.getSellerNotes());// 商家简介
		org.setProvinceId(paramIn.getProvinceId());
		org.setProvinceName(SysStaticDataUtil
				.getProvinceDataList("SYS_PROVINCE", String.valueOf(paramIn.getProvinceId())).getName());
		org.setRegionId(paramIn.getRegionId());
		org.setRegionName(
				SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(paramIn.getRegionId())).getName());
		org.setCountyId(paramIn.getCountyId());
		org.setCountyName(
				SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(paramIn.getCountyId())).getName());
		org.setStreetId(paramIn.getStreetId());
		org.setStreetName(
				SysStaticDataUtil.getStreetDataList("SYS_STREET", String.valueOf(paramIn.getStreetId())).getName());
		org.setDepartmentAddress(paramIn.getDepaetmentAdderss());// 提货地址
		session.update(org);
		if (tenantType == SysStaticDataEnum.USER_TYPE.BUSINESS) {
			CmCustomerSV ccSV = (CmCustomerSV) SysContexts.getBean("customerSV");
			CmCustomer cc = ccSV.getCmCustomer(sysTenantDef.getTenantId());
			cc.setName(paramIn.getName());
			cc.setLinkmanName(sysTenantDef.getLinkMan());
			cc.setBill(sysTenantDef.getCsPhones());
			cc.setTelephone(sysTenantDef.getLinkPhone());
			cc.setAddress(paramIn.getAddress());
			// 商家归属网点
			cc.setOrgId(paramIn.getOrgId());
			cc.setTenantId(sysTenantDef.getTenantId());
			cc.setType(SysStaticDataEnum.CUSTOMER_TYPE.PUB_CUSTOMER);
			// 父级id
			cc.setParentId((long) SysStaticDataEnum.PARENT_ID.PARENT);
			cc.setState(SysStaticDataEnum.STS.VALID);
			cc.setSigningType(SysStaticDataEnum.SIGNING_TYPE.SIGNING_IS);
			cc.setCreateDate(new Date());
			cc.setZxTenantId(user.getTenantId());
			session.update(cc);
		}
		Map<String, Long> retMap = new HashMap<String, Long>();
		retMap.put("orgId", org.getOrgId());
		retMap.put("tenantId", sysTenantDef.getTenantId());

		return retMap;
	}

}
