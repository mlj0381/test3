package com.wo56.business.cm.intf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.components.citys.Province;
import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.encrypt.K;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysCfgUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.framework.jmx.client.JmxMonitorClient;
import com.wo56.business.ac.vo.AcAccount;
//import com.wo56.business.cm.impl.CmOrgPickupPointSV;
import com.wo56.business.cm.impl.OrganizationSV;
import com.wo56.business.cm.vo.CmCustomer;
//import com.wo56.business.cm.vo.CmOrgPickupPoint;
import com.wo56.business.cm.vo.CmOrgRel;
import com.wo56.business.cm.vo.out.CmCompanyInfoOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.org.vo.in.OrgQueryParamIn;
import com.wo56.business.org.vo.in.OrgSaveBussParamIn;
import com.wo56.business.org.vo.in.OrgSaveParamIn;
import com.wo56.business.org.vo.in.OrgSavePullPagParamIn;
import com.wo56.business.org.vo.in.OrgSaveZxParamIn;
import com.wo56.business.org.vo.in.OrgUpdateParamIn;
import com.wo56.business.org.vo.in.OrgUpdatePullPagParamIn;
import com.wo56.business.org.vo.out.OrgQueryAllBusOut;
import com.wo56.business.org.vo.out.OrgQueryBusOut;
import com.wo56.business.org.vo.out.OrgQueryMatserOut;
import com.wo56.business.org.vo.out.OrgQueryParamOut;
import com.wo56.business.org.vo.out.OrgSavePullPagParamOut;
import com.wo56.business.org.vo.out.OrganizationOut;
import com.wo56.business.org.vo.out.OrganizationParamOut;
import com.wo56.business.org.vo.out.OrganizationQueryZXOut;
import com.wo56.business.org.vo.out.OrganizationTreeOut;
import com.wo56.business.route.vo.RouteIntroduce;
import com.wo56.business.route.vo.RouteRouting;
import com.wo56.business.sys.vo.SysTenantDef;
import com.wo56.common.cache.OraganizationCache;
import com.wo56.common.cache.SysTenantDefCache;
import com.wo56.common.consts.AccountManageConsts;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.consts.PermissionConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class OrganizationTF implements IOrganizationIntf{
	private static final Log log = LogFactory.getLog(OrganizationTF.class);
	private OrganizationSV orgSV;

	public OrganizationSV getOrgSV() {
		return orgSV;
	}

	public void setOrgSV(OrganizationSV orgSV) {
		this.orgSV = orgSV;
	}

	/**
	 * 判断配送的网点是否支持代收货款 OrgId:配送网点编号
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doQueryOrganization(Map<String, String> inParam) throws Exception {
		Long orgId = DataFormat.getLongKey(inParam, "orgId");
		if (orgId == null || orgId <= 0) {
			throw new BusinessException("请输入网点编号");
		}
		Session session = SysContexts.getEntityManager();
		Criteria orgCa = session.createCriteria(Organization.class);
		Organization organ = (Organization) session.get(Organization.class, orgId);
		OrganizationOut organOut = null;
		if (organ != null) {
			organOut = new OrganizationOut();
			BeanUtils.copyProperties(organOut, organ);
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(organOut));
	}

	/*
	 * public String getOrgData(int orgId) throws Exception { List<CustInfo>
	 * custList=null; if(orgId>0){ custList=
	 * SysStaticDataUtil.getCustInfoById(orgId); Session session =
	 * SysContexts.getEntityManager(); Criteria custCa =
	 * session.createCriteria(CustInfo.class);
	 * custCa.add(Restrictions.eq("orgId", orgId));
	 * custCa.add(Restrictions.eq("sts",
	 * SysStaticDataEnum.SYS_STATE_DESC.EFFECTive)); if(custCa.list().size()>0){
	 * custList=custCa.list(); } } List<CustInfo> list=new
	 * ArrayList<CustInfo>(); if(custList!=null && custList.size()>0){ for
	 * (CustInfo cust : custList) { list.add(cust); } CustInfo cusInfo = new
	 * CustInfo(); cusInfo.setCustId(-1l); //cusInfo.setUpOraganizeId(-1);
	 * cusInfo.setCustName("所有客户"); list.add(0, cusInfo); return
	 * JsonHelper.json(list); }else{ return JsonHelper.json(new ArrayList()); }
	 * }
	 */
	/**
	 * 
	 * @param orgId
	 * @param orgType
	 * @return
	 * @throws Exception
	 */
	public List<Organization> getOrganizationByCondition(long orgId, int orgType) throws Exception {

		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(Organization.class);
		if (orgType >= 0) {
			ca.add(Restrictions.eq("orgType", orgType));
		}
		if (orgId >= 0) {
			ca.add(Restrictions.eq("orgId", orgId));
		}
		List<Organization> organizations = ca.list();
		return organizations;
	}

	/**
	 * 保存网点信息
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> doSave(Map<String, Object> inParam) throws Exception {
		OrgSaveParamIn paramIn = new OrgSaveParamIn();
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(false);
		BeanUtils.populate(paramIn, inParam);
		if (StringUtils.isEmpty(paramIn.getOrgName())) {
			throw new BusinessException("网点名称不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getOrgShorter())) {
			throw new BusinessException("网点简称不能为空!");
		}
		// if(paramIn.getParentOrgId()==null||paramIn.getParentOrgId()<0){
		// throw new BusinessException("请选择父级网点");
		// }
		if (StringUtils.isEmpty(paramIn.getProvinceId())) {
			throw new BusinessException("请选择网点所在省份");
		}
		if (StringUtils.isEmpty(paramIn.getRegionId())) {
			throw new BusinessException("请选择网点所在城市");
		}
		if (StringUtils.isEmpty(paramIn.getCountyId())) {
			throw new BusinessException("请选择网点所在县区");
		}
		/*
		 * if(StringUtils.isNotEmpty(paramIn.getCarryLinkPhone())){
		 * if(!CommonUtil.isCheckPhone(paramIn.getCarryLinkPhone())){ throw new
		 * BusinessException("提货人手机号码格式不正确！"); } }
		 * if(StringUtils.isNotEmpty(paramIn.getAcceptLinkPhone())){
		 * if(!CommonUtil.isCheckPhone(paramIn.getAcceptLinkPhone())){ throw new
		 * BusinessException("接货人手机号码格式不正确！"); } }
		 */
		if (StringUtils.isEmpty(paramIn.getDepartmentAddress())) {
			throw new BusinessException("营业部地址不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getOrgManager())) {
			throw new BusinessException("网点经理不能为空!");
		}
		if (paramIn.getOrgType() == null || paramIn.getOrgType() < 0) {
			throw new BusinessException("请选择网点类型!");
		}
		// if(StringUtils.isNotEmpty(paramIn.getOrgPrincipalPhone())){
		// if(!CommonUtil.isCheckPhone(paramIn.getOrgPrincipalPhone())){
		// throw new BusinessException("网点负责人手机号码格式不正确！");
		// }
		// }
		if (paramIn.getBusinessType() == null || paramIn.getBusinessType() < 0) {
			throw new BusinessException("请选择网点类型!");
		}
		/*
		 * if(paramIn.getIsDepartment()==null||paramIn.getIsDepartment()<0){
		 * throw new BusinessException("请选择是否是营业部门!"); }
		 * if(paramIn.getIsPaymentCollection()==null||paramIn.
		 * getIsPaymentCollection()<0){ throw new
		 * BusinessException("请选择是否代收货款!"); }
		 * if(paramIn.getIsPaymentCollection()==1){
		 * if(paramIn.getLimitForCollection()==null||paramIn.
		 * getLimitForCollection()<0){ throw new
		 * BusinessException("代收款限额不能为空!"); } }
		 * if(paramIn.getIsSettleDocket()==null||paramIn.getIsSettleDocket()<0){
		 * throw new BusinessException("请选择是否结算到付单!"); }
		 * if(paramIn.getCurrencyType()==null||paramIn.getCurrencyType()<0){
		 * throw new BusinessException("请选择本位币类型!"); }
		 * if(StringUtils.isEmpty(paramIn.getCasReference())){ throw new
		 * BusinessException("提现基准不能为空!"); }
		 * if(!CommonUtil.isDouble(paramIn.getCasReference())){ throw new
		 * BusinessException("请输入正确提现基准!"); }
		 * if(Double.parseDouble(paramIn.getCasReference())>1D){ throw new
		 * BusinessException("提现基准不能大于1.0!"); }
		 * if(paramIn.getLockLimit()==null||paramIn.getLockLimit()<0){ throw new
		 * BusinessException("锁机额度不能为空!"); } if(paramIn.getLockLimit()/100<0){
		 * throw new BusinessException("锁机额度不能小于0!"); }
		 * if(paramIn.getWarningLimit()==null||paramIn.getWarningLimit()<0){
		 * throw new BusinessException("预警额度不能为空!"); }
		 * if(paramIn.getWarningLimit()/100<0){ throw new
		 * BusinessException("预警额度不能小于0!"); }
		 */
		Organization organization = new Organization();
		if (paramIn.getOrgId() != null && paramIn.getOrgId() > 0) {
			organization = (Organization) session.get(Organization.class, paramIn.getOrgId());
			if (organization == null) {
				throw new BusinessException("查找不到该网点的信息!");
			}
			if (organization.getParentOrgId() == -1 && paramIn.getParentOrgId() != null
					&& paramIn.getParentOrgId() != -1) {
				throw new BusinessException("总公司账号不能选择上级网点!");
			}
		}
		BeanUtils.copyProperties(organization, paramIn);
		organization.setTenantType(user.getUserType().toString());
		organization.setState(SysStaticDataEnum.STS.VALID);

		if (paramIn.getOrgId() != null && paramIn.getOrgId() > 0) {
			organization.setCashReference(paramIn.getCasReference());
			session.saveOrUpdate(organization);
		} else {
			if (organization.getOrgType() == 3) {
				Criteria orgCa = session.createCriteria(Organization.class);
				orgCa.add(Restrictions.eq("orgType", organization.getOrgType()));
				orgCa.add(Restrictions.eq("tenantId", user.getTenantId()));
				if (orgCa.list().size() > 0) {
					throw new BusinessException("同个公司下不能有多个总公司的网点类型");
				}
			}

			organization.setCashReference(paramIn.getCasReference());
			organization.setTenantId(user.getTenantId());
			organization.setCreateDate(new Date(System.currentTimeMillis()));
			session.saveOrUpdate(organization);
			saveArrcount(1, organization.getOrgId(), user.getUserId());
			saveArrcount(2, organization.getOrgId(), user.getUserId());
			saveArrcount(3, organization.getOrgId(), user.getUserId());
		}

		if (paramIn.getOrgId() != null && paramIn.getOrgId() > 0) {
			// 修改刷新组织缓存前必须commit 事务
			SysContexts.commitTransation();
			// 修改需刷新组织缓存
			try {
				JmxMonitorClient jmx = new JmxMonitorClient();
				jmx.refreshAllCache("woyq", new String[] { OraganizationCache.class.getName() });
			} catch (Exception e) {
				log.info("ORGANIZATION==" + DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "组织["
						+ paramIn.getOrgId() + "]修改，缓存刷新失败。");
			}

		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		
		return map;
	}

	private void saveArrcount(int accountType, long orgId, long userId) throws Exception {
		Session session = SysContexts.getEntityManager(false);
		AcAccount acAccount = new AcAccount();
		acAccount.setBalance(0L);
		acAccount.setObjType(1);
		acAccount.setObjId(orgId);
		acAccount.setCreateTime(new Date(System.currentTimeMillis()));
		// 目前写死默认密码888888
		acAccount.setAccountPwd(K.j_s("888888"));
		acAccount.setOpId(userId);
		acAccount.setAccountType(accountType);
		session.saveOrUpdate(acAccount);
	}

	/**
	 * 查询网点信息
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> doQuery(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		OrgQueryParamIn paramIn = new OrgQueryParamIn();
		BaseUser user = SysContexts.getCurrentOperator();
		BeanUtils.populate(paramIn, inParam);
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
		int userRole = cmUserInfoTF.getUserRole();
		Criteria ca = session.createCriteria(Organization.class);
		/*
		 * if(userRole==SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER ||
		 * userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
		 * ca.add(Restrictions.eq("tenantId", user.getTenantId()));
		 * if(userRole==SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER){
		 * ca.add(Restrictions.eq("orgId", user.getOrgId())); } }
		 */
		ca.add(Restrictions.eq("tenantId", user.getTenantId()));
		ca.add(Restrictions.ne("orgType", SysStaticDataEnum.ORG_TYPE.CARRIER_ORG));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		if (paramIn.getBusinessType() != null && paramIn.getBusinessType() > 0) {
			ca.add(Restrictions.eq("businessType", paramIn.getBusinessType()));
		}
		if (paramIn.getOrgType() != null && paramIn.getOrgType() > 0) {
			ca.add(Restrictions.eq("orgType", paramIn.getOrgType()));
		}
		//网点名称
		if (StringUtils.isNotEmpty(paramIn.getOrgName())) {
			String orgNameLike = '%'+paramIn.getOrgName()+'%';
			ca.add(Restrictions.like("orgName", orgNameLike));
			
		}
		//网点负责人
		if (StringUtils.isNotEmpty(paramIn.getOrgPrincipal())) {
			String orgPrincipalLike = '%'+paramIn.getOrgPrincipal()+'%';
			ca.add(Restrictions.like("orgPrincipal", orgPrincipalLike));
		}
		if (StringUtils.isNotEmpty(paramIn.getOrgPrincipalPhone())) {
			ca.add(Restrictions.eq("orgPrincipalPhone", paramIn.getOrgPrincipalPhone()));
		}
		if (StringUtils.isNotEmpty(paramIn.getCountyId()) && Long.parseLong(paramIn.getCountyId()) > 0) {
			ca.add(Restrictions.eq("countyId", paramIn.getCountyId()));
		}
		if (StringUtils.isNotEmpty(paramIn.getProvinceId()) && Long.parseLong(paramIn.getProvinceId()) > 0) {
			ca.add(Restrictions.eq("provinceId", paramIn.getProvinceId()));
		}
		if (StringUtils.isNotEmpty(paramIn.getRegionId()) && Long.parseLong(paramIn.getRegionId()) > 0) {
			ca.add(Restrictions.eq("regionId", paramIn.getRegionId()));
		}
		if (StringUtils.isNotEmpty(paramIn.getStreetId()) && Long.parseLong(paramIn.getStreetId()) > 0) {
			ca.add(Restrictions.eq("streetId", paramIn.getStreetId()));
		}
		ca.addOrder(Order.desc("orgId"));
		IPage page = PageUtil.gridPage(ca);
		Pagination pagination = new Pagination(page);
		List<Organization> list = pagination.getItems();
		List<OrgQueryParamOut> outList = new ArrayList<OrgQueryParamOut>();
		for (Organization organization : list) {
			OrgQueryParamOut out = new OrgQueryParamOut();
			BeanUtils.copyProperties(out, organization);
			out.setOrgManager(organization.getOrgManager());
			out.setSupportStaff(organization.getSupportStaff());
			out.setSupportStaffPhone(organization.getSupportStaffPhone());
			out.setSupportStaff2(organization.getSupportStaff2());
			out.setSupportStaffPhone2(organization.getSupportStaffPhone2());
			out.setBusinessTypeName(SysStaticDataUtil
					.getSysStaticData("BUSINESS_TYPE", String.valueOf(organization.getBusinessType())).getCodeName());
			out.setOrgTypeName(SysStaticDataUtil.getSysStaticData("ORG_TYPE", String.valueOf(organization.getOrgType()))
					.getCodeName());
			out.setParentOrgName(OraganizationCacheDataUtil.getOrgName(organization.getParentOrgId()));
			out.setProvinceId(organization.getProvinceId());
			out.setRegionId(organization.getRegionId());
			out.setCountyId(organization.getCountyId());
			out.setStreetId(organization.getStreetId());
			outList.add(out);
		}
		pagination.setItems(outList);
		return JsonHelper.parseJSON2Map(JsonHelper.json(pagination));
	}

	/***
	 * 充值（l列表）
	 * 
	 * @author 曾建优
	 * @param OrgSaveParamIn
	 */
	public Map<String, String> recharge(Map<String, Object> inParam) throws Exception {
//		long money = DataFormat.getLongKey(inParam, "money");
//		String orgId = DataFormat.getStringKey(inParam, "orgId");
//		AcOrderAccountOperTF aoao = (AcOrderAccountOperTF) SysContexts.getBean("acOrderAccountOperTF");
//		aoao.cashAccountChange(AccountManageConsts.AcAccount.OBJECT_TYPE_POINT, Long.parseLong(orgId),
//				AccountManageConsts.AcAccount.ACCOUNT_TYPE_SYSTEM, money);
		Map map = new HashMap();
		map.put("isOk", "y");
		return map;
	}

	/**
	 * 获取当前登录的用户的访问的组织的权限
	 * 
	 * @return OrganizationTreeOut
	 */
	public OrganizationTreeOut queryOrgPriByUserId(Map<String, Object> inParam) {
		BaseUser user = SysContexts.getCurrentOperator();
		Long orgId = user.getOrgId();
		Long tenantId = Long.valueOf(user.getTenantId());

		Map<String, Object> attrs = user.getAttrs();
		List<Integer> rolds = (List<Integer>) attrs.get(EnumConsts.Common.SESSION_ROLES);

		List<Organization> allOrg = getorgByRootOrgId(tenantId);
		OrganizationTreeOut currOrg = null;
		if (isCompanyType(rolds)) {
			// 公司角色
			currOrg = getCurrUserOrg(tenantId, allOrg);
		} else {
			currOrg = getCurrUserOrg(orgId, allOrg);
		}
		genOrganizationTree(currOrg, allOrg);
		return currOrg;
	}

	/**
	 * 根据网点code查询公司信息
	 * 
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
//	public CmCompanyInfoOut queryCompanyByOrgCode(String orgCode) throws Exception {
//		Organization org = orgSV.getOrgByCode(orgCode);
//
//		if (org == null) {
//			throw new BusinessException("网点编码:[" + orgCode + "]查询不到网点信息");
//		}
//
//		String parentCode = org.getParentOrgCode();
//		CmCompanyTF cmCompanyTF = (CmCompanyTF) SysContexts.getBean("cmCompanyTF");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tenantCode", parentCode);
//		CmCompanyInfoOut cmCompanyInfoOut = cmCompanyTF.getCompanyInfoByTenantCode(map);
//		return cmCompanyInfoOut;
//	}

	/**
	 * 获取当前登录用户的组织信息
	 */
	private OrganizationTreeOut getCurrUserOrg(Long orgId, List<Organization> organizations) {
		for (Organization organization : organizations) {
			if (orgId.longValue() == organization.getOrgId()) {
				OrganizationTreeOut treeOut = new OrganizationTreeOut();
				treeOut.setId(organization.getOrgId());
				treeOut.setName(organization.getOrgName());
				treeOut.setType(organization.getOrgType());
				return treeOut;
			}
		}

		return null;
	}

	/**
	 * 判断用户是否是公司平台管理角色，可以访问各个网点数据
	 * 
	 * @return
	 */
	private Boolean isCompanyType(List<Integer> rolds) {
		for (Integer role : rolds) {
			if (SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER == role.intValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 构建组织树
	 * 
	 * @param treeOut
	 * @param organizations
	 */
	private void genOrganizationTree(OrganizationTreeOut treeOut, List<Organization> organizations) {
		List<OrganizationTreeOut> childList = new ArrayList<OrganizationTreeOut>();
		if (organizations != null && organizations.size() > 0) {
			for (Organization organization : organizations) {
				if (organization.getParentOrgId() == treeOut.getId().longValue()) {
					OrganizationTreeOut out = new OrganizationTreeOut();
					out.setId(organization.getOrgId());
					out.setName(organization.getOrgName());
					out.setType(organization.getOrgType());
					genOrganizationTree(out, organizations);
					childList.add(out);
				}
			}
			treeOut.setChildOrgList(childList);
		}
	}

	/**
	 * 根据租户id查询该租户下面的所有网点
	 * 
	 * @param tenantId
	 * 
	 * @return
	 */
	private List<Organization> getorgByRootOrgId(Long tenantId) {
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(Organization.class);
		if (tenantId != null && tenantId.longValue() > 0) {
			ca.add(Restrictions.eq("tenantId", tenantId));
		}

		// ca.add(Restrictions.eq("parentOrgId", EnumConsts.ROOT_ORG_ID));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		ca.add(Restrictions.ne("orgType", SysStaticDataEnum.ORG_TYPE.CARRIER_ORG));
		List<Organization> organizations = ca.list();

		return organizations;
	}

	/**
	 * 根据租户id查询该租户下面的所有网点
	 * 
	 * @param tenantId
	 * 
	 * @return
	 */
	public List<Organization> getorgByRootOrgId(Map<String, String> inParam) {
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		List<Organization> organizations = getorgByRootOrgId(tenantId);

		return organizations;
	}

	/**
	 * 查询所有的公司租户信息，除了平台的租户
	 * 
	 * @param inParam
	 * @return
	 */
	public List<Organization> getAllTenant(Map<String, String> inParam) {
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(Organization.class);

		ca.add(Restrictions.eq("parentOrgId", EnumConsts.ROOT_ORG_ID));
		List<String> list = new ArrayList<String>();
		list.add(String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN));
		list.add(String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS));
		list.add(String.valueOf(SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS));
		list.add(String.valueOf(SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY));
		ca.add(Restrictions.in("tenantType", list));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		List<Organization> organizations = ca.list();
		return organizations;
	}

	/**
	 * 查询网点详情
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map queryOrgDtl(Map<String, String> inParam) throws Exception {
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		Session session = SysContexts.getEntityManager(true);
		Organization organization = (Organization) session.get(Organization.class, orgId);
		if (organization == null) {
			throw new BusinessException("查询不到该网点的信息");
		}
		Map iparmMap = new HashMap();
		OrganizationParamOut orgOut = new OrganizationParamOut();
		BeanUtils.copyProperties(orgOut, organization);
		orgOut.setCasReference(organization.getCashReference());
		iparmMap.put("organization", orgOut);
		return iparmMap;
	}

	/**
	 * lyh 查询顶级的公司信息
	 * 
	 * @param tenantType
	 *            公司类型 1 平台 2 专线 3商家
	 * @return
	 * @throws Exception
	 */
	public List<Organization> getRootOrg(Map<String, String> inParam) throws Exception {
		OrganizationSV organizationSV = (OrganizationSV) SysContexts.getBean("organizationSV");
		String tenantType = DataFormat.getStringKey(inParam, "tenantType");
		List<Organization> list = organizationSV.getRootOrg(tenantType);
		return list;
	}

	/**
	 * lyh 根据租户id查询该租户的等级组织的信息
	 * 
	 * @param tenantId
	 * 
	 * @return
	 */
	public Organization getRootOrgByTenantId(Long tenantId) {
		Session session = SysContexts.getEntityManager();
		Criteria ca = session.createCriteria(Organization.class);
		ca.add(Restrictions.eq("tenantId", tenantId));
		ca.add(Restrictions.eq("parentOrgId", EnumConsts.ROOT_ORG_ID));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));

		List<Organization> organizations = ca.list();
		if (organizations != null && organizations.size() == 1) {
			return organizations.get(0);
		}
		return null;
	}

	/**
	 * lyh 保存专线
	 * 
	 * @param tenantId
	 * 
	 * @return
	 */
	public Map<String, String> doSaveSpecialLine(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		OrgSaveZxParamIn paramIn = new OrgSaveZxParamIn();
		BaseUser user = SysContexts.getCurrentOperator();
		BeanUtils.populate(paramIn, inParam);
		if (StringUtils.isEmpty(paramIn.getTenantCode())) {
			throw new BusinessException("租户编码不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getName())) {
			throw new BusinessException("租户名称不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getLinkMan())) {
			throw new BusinessException("租户联系人不能为空!");

		}
		if (StringUtils.isEmpty(paramIn.getLinkPhone())) {
			if (!CommonUtil.isCheckPhone(paramIn.getLinkPhone())) {
				throw new BusinessException("租户联系人手机格式不正确！");
			}
		}
		if (StringUtils.isEmpty(paramIn.getProvinceId())) {
			throw new BusinessException("起始省份不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getProvinceIds())) {
			throw new BusinessException("目的省份不能为空!");
		}
		// 1：`organization`新增组织信息，类型为专线
		// 2：`sys_tenant_def`新增 租户信息
		Map<String, Long> ret = orgSV.saveOrganizationAndTeantInfo(paramIn);
		long orgId = DataFormat.getLongKey(ret, "orgId");
		long tenantId = DataFormat.getLongKey(ret, "tenantId");

		// 3：新增的网点线路简介信息route_introduce
		String sourceProvinceId = paramIn.getProvinceId();
		Province p = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", sourceProvinceId);
		String[] ary = paramIn.getProvinceIds().split(",");
		String endProvinceId = "";
		for (String temp : ary) {
			endProvinceId = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", temp).getName();
			RouteIntroduce routeintroduce = new RouteIntroduce();
			routeintroduce.setDesProvinceId(Long.parseLong(temp));
			routeintroduce.setDesProvinceName(endProvinceId);
			routeintroduce.setSourceProvinceId(Long.parseLong(sourceProvinceId));
			routeintroduce.setSourceProvinceName(p.getName());
			routeintroduce.setTenantCode(paramIn.getTenantCode());
			routeintroduce.setOrgId(orgId);
			routeintroduce.setTenantId(tenantId);
			if (StringUtils.isNotEmpty(paramIn.getAddress())) {
				routeintroduce.setDesDetail(paramIn.getAddress());
			}
			session.save(routeintroduce);
		}
		// 4：新增公司类型的账户ac_account
		saveArrcount(1, orgId, user.getUserId());
		saveArrcount(2, orgId, user.getUserId());
		saveArrcount(3, orgId, user.getUserId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}

	/**
	 * 新增拉包公司
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> doSavePullPagCompany(Map<String, Object> inParam) throws Exception {
		OrgSavePullPagParamIn paramIn = new OrgSavePullPagParamIn();
		BaseUser user = SysContexts.getCurrentOperator();
		BeanUtils.populate(paramIn, inParam);
		if (StringUtils.isEmpty(paramIn.getTenantCode())) {
			throw new BusinessException("公司编码不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getName())) {
			throw new BusinessException("公司名称不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getLinkMan())) {
			throw new BusinessException("法人不能为空");
		}
		if (StringUtils.isEmpty(paramIn.getLinkPhone())) {
			throw new BusinessException("联系人电话不能为空");
		}
		// 1：`organization`新增组织信息，类型为拉包公司
		// 2：`sys_tenant_def`新增 租户信息
		Map<String, Long> ret = orgSV.savePullPakCompanyOrganizationAndTeantInfo(paramIn);
		long orgId = DataFormat.getLongKey(ret, "orgId");
		// 4：新增公司类型的账户ac_account
		saveArrcount(1, orgId, user.getUserId());
		saveArrcount(2, orgId, user.getUserId());
		saveArrcount(3, orgId, user.getUserId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}

	/**
	 * 专线查询
	 * 
	 * @param inParam
	 * @param myself
	 *            等于 0 表示不查询自己租户下的专线
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doQuerySpecialLine(Map<String, Object> inParam) throws Exception {
		String name = DataFormat.getStringKey(inParam, "name");// 专线名称
		String linkMan = DataFormat.getStringKey(inParam, "linkMan");// 联系人
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");// 联系人电话
		String provinceIds = DataFormat.getStringKey(inParam, "provinceIds");// 目的省份
		int myself = DataFormat.getIntKey(inParam, "myself");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		if (myself == 0) {
			rtnMap = orgSV.getOrgLine(name, linkMan, linkPhone, provinceIds, true);
		} else {
			rtnMap = orgSV.getOrgLine(name, linkMan, linkPhone, provinceIds, false);
		}
		return rtnMap;
	}
	
	
	/**
	 * 查询所有拉包公司
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object>queryByCompany(Map<String, Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager(true);
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String tenantType = String.valueOf(SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY);
		int state = SysStaticDataEnum.STS.VALID;
		Criteria ca = session.createCriteria(Organization.class);
		ca.add(Restrictions.eq("parentOrgId", parentOrgId));
		ca.add(Restrictions.eq("tenantType", tenantType));
		ca.add(Restrictions.eq("state", state));
		BaseUser user = SysContexts.getCurrentOperator();
		if(user.getUserType() == SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY){
			long tenantId = user.getTenantId();
			ca.add(Restrictions.eq("tenantId", tenantId));
		}
		List<Organization> orgList = (List<Organization>) ca.list();
		return JsonHelper.parseJSON2Map(JsonHelper.json(orgList));
		
	}
	/**
	 * 根据Id查询拉包公司
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryByIdOrgCompanyList(Map<String, Object> inParam) throws Exception{
		String companyType=String.valueOf(SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY);//拉包公司
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT o.ORG_CODE,o.ORG_NAME,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.SUPPORT_STAFF_PHONE,");
		sb.append("o.DEPARTMENT_ADDRESS,o.CORPORATE_FRONT_CARD,o.CORPORATE_BACK_CARD,o.BUSINESSLICENSE_PIC,o.SELLER_NOTES");
		sb.append(",o.ORG_ID,o.TENANT_ID FROM organization AS o ,cm_user_info AS c WHERE  o.CREATOR_ID =c.USER_ID ");
		sb.append(" AND o.STATE=:state AND o.TENANT_TYPE=:companyType AND o.PARENT_ORG_ID=:parentOrgId AND o.TENANT_ID=:tenantId ");
		Query query = session.createSQLQuery(sb.toString());
		long tenantId=DataFormat.getLongKey(inParam,"tenantId");
		if(user.getUserType() == SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY){
			tenantId = user.getTenantId();
		}
		if(tenantId<0){
			throw new BusinessException("公司Id不能为空！");
		}
		query.setParameter("tenantId", tenantId);
		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		if(StringUtils.isNotEmpty(companyType)){
			query.setParameter("companyType", companyType);
		}
		query.setParameter("parentOrgId", parentOrgId);
		List<Object[]> list = (List<Object[]>) query.list();
		Map<String, Object> map = new HashMap<String, Object>();
		OrgSavePullPagParamOut orgOut=new OrgSavePullPagParamOut();
		//sb.append("o.ORG_CODE,o.ORG_NAME,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.SUPPORT_STAFF_PHONE,");
		//sb.append("o.DEPARTMENT_ADDRESS,o.CORPORATE_FRONT_CARD,o.CORPORATE_BACK_CARD,o.BUSINESSLICENSE_PIC,o.SELLER_NOTES");
		//sb.append(",o.ORG_ID,o.TENANT_ID FROM organization AS o ,cm_user_info AS c WHERE  o.CREATOR_ID =c.USER_ID ");
		if(list!=null&&list.size()>0){
			for(Object[]obj:list){
				if(obj[0]!=null){
					orgOut.setTenantCode((String)obj[0]);
				}
				if(obj[1]!=null){
					orgOut.setName((String)obj[1]);
				}
				if(obj[2]!=null){
					orgOut.setLinkMan((String)obj[2]);
				}
				if (obj[3] != null) {
					orgOut.setLinkPhone((String)obj[3]);
				}
				if(obj[4]!=null){
					orgOut.setCsPhones((String) obj[4]);
				}
				if(obj[5]!=null){
					orgOut.setAddress((String)obj[5]);
				}
				if(obj[6]!=null){
					orgOut.setCorporateFrontCard((String)obj[6]);
				}
				if(obj[7]!=null){
					orgOut.setCorporateBackCard((String)obj[7]);
				}
				
				if(obj[8]!=null){
					orgOut.setBusinesslicensePic((String)obj[8]);
				} 
				if(obj[9]!=null){
					orgOut.setSellerNotes(String.valueOf(obj[9]));
				}
				if(obj[10]!=null){
					BigInteger orgId2=(BigInteger)obj[10];
					orgOut.setOrgId(orgId2.toString());
				}
				if(obj[11]!=null){
					BigInteger oTenantId=(BigInteger)obj[11];
					orgOut.setTenantId(oTenantId.longValue());
				}
			}
			map.put("data", orgOut);
		}
		return map;
		
	}
	

	/**
	 * lyh 根据id查询专线信息
	 * 
	 * @return Map
	 */
	public OrganizationQueryZXOut queryByTenantIdOrganizationAndTeantInfo(Map<String, Object> inParam) {
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN);// 专线类型
		String state = String.valueOf(SysStaticDataEnum.STS.VALID);
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(
				"SELECT DISTINCT o.TENANT_ID,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.SUPPORT_STAFF_PHONE, ");
		sb.append("r.SOURCE_PROVINCE_NAME,(SELECT GROUP_CONCAT(r1.DES_PROVINCE_NAME) FROM route_introduce r1 ");
		sb.append(
				"WHERE r1.TENANT_ID = :tenantId) AS DES_PROVINCE_NAME,r.DES_DETAIL,(SELECT GROUP_CONCAT(r2.DES_PROVINCE_ID) ");
		sb.append(
				"FROM route_introduce r2 WHERE r2.TENANT_ID = :tenantId) AS DES_PROVINCE_ID,r.SOURCE_PROVINCE_ID,o.ORG_NAME,o.ORG_CODE,o.DEPARTMENT_ADDRESS FROM ");
		sb.append(
				" organization o LEFT JOIN route_introduce r ON o.TENANT_ID = r.TENANT_ID WHERE o.TENANT_TYPE = :zxType ");
		sb.append("AND o.PARENT_ORG_ID = :parentOrgId AND o.STATE = :state AND o.TENANT_ID = :tenantId ");
		Query query = session.createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(zxType)) {
			query.setParameter("zxType", zxType);
		}
		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");// 专线名称
		if (tenantId <= 0) {
			throw new BusinessException("租户ID不能为空!");
		} else {
			query.setParameter("tenantId", tenantId);
		}
		query.setParameter("parentOrgId", parentOrgId);
		List<Object[]> list = (List<Object[]>) query.list();
		;
		OrganizationQueryZXOut orgOut = new OrganizationQueryZXOut();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				orgOut.setTenantId(Long.parseLong(String.valueOf(obj[0])));
				orgOut.setLinkMan(String.valueOf(obj[1]));
				orgOut.setLinkPhone(String.valueOf(obj[2]));
				if (obj[3] != null) {
					orgOut.setCsPhones(String.valueOf(obj[3]));
				}
				orgOut.setProvinceName(String.valueOf(obj[4]));
				orgOut.setProvinceNames(String.valueOf(obj[5]));
				if (obj[6] != null) {
					orgOut.setAddress(String.valueOf(obj[6]));
				}
				orgOut.setProvinceIds(String.valueOf(obj[7]));
				orgOut.setProvinceId(Long.parseLong(String.valueOf(obj[8])));
				orgOut.setName(String.valueOf(obj[9]));
				orgOut.setTenantCode(String.valueOf(obj[10]));
				if (obj[11] != null) {
					orgOut.setDepartmentAddress(String.valueOf(obj[11]));
				} else {
					orgOut.setDepartmentAddress("");
				}
			}

		}
		return orgOut;
	}
	/**
	 * 修改拉包公司
	 * @param paramIn
	 * @return
	 */
	public Map<String, Long> updatePullPakCompanyOrganizationAndTeantInfo(OrgUpdatePullPagParamIn paramIn) {
		List<SysTenantDef> sysTenantDefs = (List<SysTenantDef>) CacheFactory.get(SysTenantDefCache.class.getName(),
				PermissionConsts.GrantConstant.SYS_TENANT_DEF);
		if (sysTenantDefs != null) {
			for (SysTenantDef def : sysTenantDefs) {
				if(def.getTenantId() != paramIn.getTenantId()){
					 if (def.getTenantCode().equals(paramIn.getTenantCode())
                             && def.getTenantId().longValue() != paramIn.getTenantId()) {
                        throw new BusinessException("拉包公司的编码已经存在，请重新输入！");
                  }
					 if (def.getName().equals(paramIn.getName())
                             && def.getTenantId().longValue() != paramIn.getTenantId()) {
                        throw new BusinessException("拉包公司名称已经存在，请重新输入！");
                  }
				}
			}
		}
		// 1：`organization`新增组织信息，类型为拉包公司
		// 2：`sys_tenant_def`新增租户信息
		Session session = SysContexts.getEntityManager();
		SysTenantDef sysTenantDef = (SysTenantDef)session.get(SysTenantDef.class, paramIn.getTenantId());
		BaseUser user = SysContexts.getCurrentOperator();
		sysTenantDef.setTenantCode(paramIn.getTenantCode());
		sysTenantDef.setName(paramIn.getName());
		sysTenantDef.setLinkMan(paramIn.getLinkMan());
		sysTenantDef.setLinkPhone(paramIn.getLinkPhone());
		// 客服电话
		sysTenantDef.setCsPhones(paramIn.getCsPhones());
		sysTenantDef.setStatus(SysStaticDataEnum.STS.VALID);
		sysTenantDef.setOpId(user.getOperId());
		sysTenantDef.setCreateDate(new Date());
		session.update(sysTenantDef);
		Organization org = getRootOrgByTenantId(paramIn.getTenantId());
		org.setTenantType(String.valueOf(SysStaticDataEnumYunQi.TENANT_TYPE.COMPANY));
		org.setTenantId(sysTenantDef.getTenantId());
		org.setOrgName(sysTenantDef.getName());
		org.setParentOrgId(EnumConsts.ROOT_ORG_ID);
		org.setOpId(user.getUserId());
		org.setOrgCode(paramIn.getTenantCode());
		org.setCreateDate(new Date());
		org.setState(SysStaticDataEnum.STS.VALID);
		org.setSupportStaffPhone(paramIn.getCsPhones());
		org.setOrgPrincipalPhone(paramIn.getLinkPhone());
		org.setOrgPrincipal(paramIn.getLinkMan());
		org.setOrgType(SysStaticDataEnum.ORG_TYPE.HEAD_OFFICE);
		//org.setCreatorId(user.getUserId());
		//公司地址
		org.setDepartmentAddress(paramIn.getAddress());
		// 公司简介
		org.setSellerNotes(paramIn.getSellerNotes());
		//拉包公司法人身份证
		org.setCorporateFrontCard(paramIn.getCorporateFrontCardId());
		org.setCorporateBackCard(paramIn.getCorporatebackCardId());
		//拉包公司营业执照
		org.setBusinesslicensePic(paramIn.getBusinesslicensePicId());
		session.update(org);
		Map<String, Long> retMap = new HashMap<String, Long>();
		retMap.put("orgId", org.getOrgId());
		retMap.put("tenantId", sysTenantDef.getTenantId());
		return retMap;

	}
	/**
	 * lyh 修改专线
	 * 
	 * @param tenantId
	 * 
	 * @return orgId
	 * 
	 *         修改项tenantId orgId tenantCode、name、linkMan、linkPhone、csPhones 需要判断
	 */
	public Map<String, Long> updateOrganizationAndTeantInfo(OrgUpdateParamIn paramIn) {
		// 1：`organization`修改组织信息，类型为专线
		// 2：`sys_tenant_def`修改租户信息

		List<SysTenantDef> sysTenantDefs = (List<SysTenantDef>) CacheFactory.get(SysTenantDefCache.class.getName(),
				PermissionConsts.GrantConstant.SYS_TENANT_DEF);
		if (sysTenantDefs != null) {
			for (SysTenantDef def : sysTenantDefs) {
				if (def.getTenantCode().equals(paramIn.getTenantCode())
						&& def.getTenantId().longValue() != paramIn.getTenantId()) {
					throw new BusinessException("专线的编码已经存在，请重新输入！");
				}
				if (def.getName().equals(paramIn.getName()) && def.getTenantId().longValue() != paramIn.getTenantId()) {
					throw new BusinessException("专线的名称已经存在，请重新输入！");
				}
			}
		}
		// 1：`organization`新增组织信息，类型为专线
		// 2：`sys_tenant_def`新增租户信息
		Session session = SysContexts.getEntityManager();
		SysTenantDef sysTenantDef = (SysTenantDef) session.get(SysTenantDef.class, paramIn.getTenantId());

		BaseUser user = SysContexts.getCurrentOperator();
		sysTenantDef.setTenantCode(paramIn.getTenantCode());
		sysTenantDef.setName(paramIn.getName());
		sysTenantDef.setLinkMan(paramIn.getLinkMan());
		sysTenantDef.setLinkPhone(paramIn.getLinkPhone());
		// 客服电话
		sysTenantDef.setCsPhones(paramIn.getCsPhones());
		sysTenantDef.setStatus(SysStaticDataEnum.STS.VALID);
		sysTenantDef.setOpId(user.getOperId());
		sysTenantDef.setCreateDate(new Date());

		session.update(sysTenantDef);

		Organization org = getRootOrgByTenantId(paramIn.getTenantId());

		org.setTenantType(String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN));
		org.setTenantId(sysTenantDef.getTenantId());
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
		session.update(org);

		Map<String, Long> retMap = new HashMap<String, Long>();
		retMap.put("orgId", org.getOrgId());
		retMap.put("tenantId", sysTenantDef.getTenantId());

		return retMap;
	}
	public Map<String, String>updateByIdOrgCompanyList(Map<String, Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager();
		OrgUpdatePullPagParamIn paramIn=new OrgUpdatePullPagParamIn();
		BeanUtils.populate(paramIn, inParam);
		long orgId=paramIn.getOrgId();
		if(orgId<=0){
			throw new BusinessException("拉包公司ID不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getTenantCode())) {
			throw new BusinessException("公司编码不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getName())) {
			throw new BusinessException("公司名称不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getLinkMan())) {
			throw new BusinessException("法人不能为空!");

		}
		if (StringUtils.isEmpty(paramIn.getLinkPhone())) {
			if (!CommonUtil.isCheckPhone(paramIn.getLinkPhone())) {
				throw new BusinessException("联系联系人手机格式不正确！");
			}
		}
		updatePullPakCompanyOrganizationAndTeantInfo(paramIn);
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}
	/**
	 * lyh 修改专线
	 * 
	 * @param tenantId
	 * @return
	 */
	public Map<String, String> doUpdateSpecialLine(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		OrgUpdateParamIn paramIn = new OrgUpdateParamIn(InterFacesCodeConsts.BASE.UPDATA_ORGZX);
		BeanUtils.populate(paramIn, inParam);
		long tenantId = paramIn.getTenantId();
		if (tenantId <= 0) {
			throw new BusinessException("租户ID不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getTenantCode())) {
			throw new BusinessException("租户编码不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getName())) {
			throw new BusinessException("租户名称不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getLinkMan())) {
			throw new BusinessException("租户联系人不能为空!");

		}
		if (StringUtils.isEmpty(paramIn.getLinkPhone())) {
			if (!CommonUtil.isCheckPhone(paramIn.getLinkPhone())) {
				throw new BusinessException("租户联系人手机格式不正确！");
			}
		}
		if (StringUtils.isEmpty(paramIn.getProvinceId())) {
			throw new BusinessException("起始省份不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getProvinceIds())) {
			throw new BusinessException("目的省份不能为空!");
		}
		// 1：`organization`修改组织信息，类型为专线
		// 2：`sys_tenant_def`修改租户信息
		Map<String, Long> ret = updateOrganizationAndTeantInfo(paramIn);
		long orgId = DataFormat.getLongKey(ret, "orgId");
		if (orgId <= 0) {
			throw new BusinessException("组织ID不能为空!");
		}
		// 3：修改的网点线路简介信息route_introduce
		String[] ary = paramIn.getProvinceIds().split(",");
		String sourceProvinceId = paramIn.getProvinceId();
		String endProvinceId = "";
		Query query = session.createSQLQuery("DELETE from route_introduce where TENANT_ID = :tenantId");
		query.setParameter("tenantId", tenantId);
		query.executeUpdate();
		for (String temp : ary) {
			endProvinceId = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", temp).getName();
			RouteIntroduce routeintroduce = new RouteIntroduce();
			routeintroduce.setDesProvinceId(Long.parseLong(temp));
			routeintroduce.setDesProvinceName(endProvinceId);
			routeintroduce.setSourceProvinceId(Long.parseLong(sourceProvinceId));
			routeintroduce.setSourceProvinceName(
					SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", sourceProvinceId).getName());
			routeintroduce.setTenantCode(paramIn.getTenantCode());
			routeintroduce.setTenantId(paramIn.getTenantId());
			if (StringUtils.isNotEmpty(paramIn.getAddress()) && paramIn.getAddress() != "") {
				routeintroduce.setDesDetail(paramIn.getAddress());
			}
			routeintroduce.setOrgId(orgId);
			session.saveOrUpdate(routeintroduce);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}

	/**
	 * lyh 根据ID删除专线
	 * 
	 * @param tenantId
	 * 
	 * @return
	 */
	public Map<String, String> doDelSpecialLine(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		if (tenantId <= 0) {
			throw new BusinessException("租户ID不能为空!");
		}
		String state = String.valueOf(SysStaticDataEnum.STS.NULLITY);
		if (StringUtils.isEmpty(state)) {
			throw new BusinessException("组织状态不能为空!");
		}
		String sql = " update organization o,sys_tenant_def s set o.STATE = :state,s.STATUS = :state where o.TENANT_ID = :tenantId and s.TENANT_ID = :tenantId and o.PARENT_ORG_ID = :parentOrgId";
		Query q = session.createSQLQuery(sql);
		q.setParameter("state", state);
		q.setParameter("tenantId", tenantId);
		q.setParameter("parentOrgId", parentOrgId);
		q.executeUpdate();
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}

	/**
	 * lyh 新增商家 1 通过商家编码或商家名称或者联系人手机号码 查询是否已经存在商家 SysTenantDef 存在数据 查询
	 * Organization getRootOrgByTenantId CmUserInfoTF.regAppUserInfo
	 * 注册用户cm_user_info 用户类型是商家 登录类型是web+app 发送短信给用户：告知账号密码，登录地址，app下载地址
	 * cm_user_org_rel 用户跟租户id关联起来 sys_role_oper_rel role_id 通过配载去弄，sys_cfg
	 * SysCfgUtil 修改：
	 * 
	 * @param tenantId
	 * 
	 * @return
	 */
//	public Map<String, String> doSaveBusiness(Map<String, Object> inParam) throws Exception {
//		Session session = SysContexts.getEntityManager();
//		Session query = SysContexts.getEntityManager(true);
//		OrgSaveBussParamIn paramIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.SAVE_ORGBUSS);
//		BaseUser user = SysContexts.getCurrentOperator();
//		BeanUtils.populate(paramIn, inParam);
//		user.getOrgId();
//		Organization org = (Organization) query.get(Organization.class, paramIn.getOrgId());
//		if (!getCheckCmOrgRel(paramIn.getLineOrgId(), paramIn.getOrgId())) {
//			throw new BusinessException("已经存在关系，不能重复添加！");
//		}
//		if (paramIn.getTenantId() > 0) {
//			CmOrgRel cor = new CmOrgRel();
//			// 选择归属网点
//			cor.setLineOrgId(paramIn.getLineOrgId());
//			cor.setLineTenantId(user.getTenantId());
//			cor.setBusiOrgId(org.getOrgId());
//			cor.setBusiTenantId(paramIn.getTenantId());
//			cor.setOpDate(new Date(System.currentTimeMillis()));
//			cor.setOpId(user.getOperId());
//			cor.setOpName(user.getUserName());
//			cor.setState(SysStaticDataEnum.STS.VALID);
//			session.save(cor);
//			// 8:cm_customer
//			CmCustomer cc = new CmCustomer();
//			cc.setName(org.getOrgName());
//			cc.setLinkmanName(org.getOrgPrincipal());
//			cc.setTelephone(org.getOrgPrincipalPhone());
//			cc.setAddress(paramIn.getAddress());
//			// 商家归属网点
//			cc.setOrgId(paramIn.getLineOrgId());
//
//			cc.setTenantId(paramIn.getTenantId());
//			cc.setZxTenantId(user.getTenantId());
//			cc.setType(SysStaticDataEnum.CUSTOMER_TYPE.PUB_CUSTOMER);
//			// 父级id
//			cc.setParentId((long) SysStaticDataEnum.PARENT_ID.PARENT);
//			cc.setState(SysStaticDataEnum.STS.VALID);
//			cc.setSigningType(SysStaticDataEnum.SIGNING_TYPE.SIGNING_IS);
//			cc.setCreateDate(new Date());
//			session.save(cc);
//		} else {
//			int tenantType = SysStaticDataEnum.USER_TYPE.BUSINESS;
//			int roleId = Integer.parseInt(SysCfgUtil.getSysCfg(EnumConsts.SysCfg.BUSINESS_APPWEB).getCfgValue());
//			if (StringUtils.isEmpty(paramIn.getTenantCode())) {
//				throw new BusinessException("商家编码不能为空!");
//			}
//			if (StringUtils.isEmpty(paramIn.getName())) {
//				throw new BusinessException("商家名称不能为空!");
//			}
//			if (StringUtils.isEmpty(paramIn.getLinkMan())) {
//				throw new BusinessException("商家联系人不能为空!");
//			}
//			if (StringUtils.isEmpty(paramIn.getAddress())) {
//				throw new BusinessException("提货地址不能为空!");
//			}
//			if (StringUtils.isEmpty(paramIn.getLinkPhone())) {
//				if (!CommonUtil.isCheckPhone(paramIn.getLinkPhone())) {
//					throw new BusinessException("商家联系人手机格式不正确！");
//				}
//			}
//			CmUserInfoTF cmUserInfoTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
//
//			if (cmUserInfoTF.checkUserPhoneExitForApp(paramIn.getLinkPhone())) {
//				throw new BusinessException("该手机[" + paramIn.getLinkPhone() + "]已存在！");
//			}
//			// 1：`organization`新增组织信息，类型为专线
//			// 2：`sys_tenant_def`新增 租户信息
//			Map<String, Long> ret = orgSV.saveOrgBusByTenantId(paramIn, tenantType, roleId);
//			long orgId = DataFormat.getLongKey(ret, "orgId");
//			long tenantId = DataFormat.getLongKey(ret, "tenantId");
//			// 3：新增的提货地址cm_org_pickup_point
//			CmOrgPickupPoint cOrgp = new CmOrgPickupPoint();
//			cOrgp.setTenantId(tenantId);
//			cOrgp.setOrgId(orgId);
//			cOrgp.setAddress(paramIn.getAddress());
//			cOrgp.setTel(paramIn.getLinkPhone());
//			cOrgp.setTownId(Long.parseLong(paramIn.getStreetId()));
//			cOrgp.setIsSelected(SysStaticDataEnum.IS_SELECTED.BUS_IS);
//			cOrgp.setProvinceId(Long.parseLong(paramIn.getProvinceId()));
//			cOrgp.setCountyId(Long.parseLong(paramIn.getCountyId()));
//			cOrgp.setCityId(Long.parseLong(paramIn.getRegionId()));
//			session.saveOrUpdate(cOrgp);
//			// 4：新增公司类型的账户ac_account
//			saveArrcount(1, orgId, user.getUserId());
//			saveArrcount(2, orgId, user.getUserId());
//			saveArrcount(3, orgId, user.getUserId());
//		}
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("isOk", "Y");
//		return map;
//	}

	/**
	 * 专线商家分页查询
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doLinkQueryBusiness(Map<String, Object> inParam) throws Exception {
		String name = DataFormat.getStringKey(inParam, "name");// 专线名称
		String linkMan = DataFormat.getStringKey(inParam, "linkMan");// 联系人
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");// 联系人电话
		String busiNotes = DataFormat.getStringKey(inParam, "busiNotes");// 主营业务
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		return orgSV.getLinkQueryBusiness(name, linkMan, linkPhone, busiNotes, orgId);
	}

	/**
	 * 专线商家分页查询
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doLinkQueryBusinessOther(Map<String, Object> inParam) throws Exception {
		String name = DataFormat.getStringKey(inParam, "name");// 专线名称
		String linkMan = DataFormat.getStringKey(inParam, "linkMan");// 联系人
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");// 联系人电话
		String busiNotes = DataFormat.getStringKey(inParam, "busiNotes");// 主营业务
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		return orgSV.getLinkQueryBusinessOther(name, linkMan, linkPhone, busiNotes, orgId);
	}

//	/**
//	 * lyh 修改商家
//	 * 
//	 * @param tenantId
//	 * @return
//	 */
//	public Map<String, String> updateBus(Map<String, Object> inParam) throws Exception {
//		Session session = SysContexts.getEntityManager();
//		OrgSaveBussParamIn paramIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.UPDATE_ORGBUSS);
//		BaseUser user = SysContexts.getCurrentOperator();
//		BeanUtils.populate(paramIn, inParam);
//		int tenantType = SysStaticDataEnum.USER_TYPE.BUSINESS;
//		long tenantId = paramIn.getTenantId();
//		int state = SysStaticDataEnum.STS.VALID;
//		if (StringUtils.isEmpty(paramIn.getTenantCode())) {
//			throw new BusinessException("商家编码不能为空!");
//		}
//		if (StringUtils.isEmpty(paramIn.getName())) {
//			throw new BusinessException("商家名称不能为空!");
//		}
//		if (StringUtils.isEmpty(paramIn.getLinkMan())) {
//			throw new BusinessException("商家联系人不能为空!");
//		}
//		if (StringUtils.isEmpty(paramIn.getAddress())) {
//			throw new BusinessException("提货地址不能为空!");
//		}
//		if (StringUtils.isEmpty(paramIn.getLinkPhone())) {
//			if (!CommonUtil.isCheckPhone(paramIn.getLinkPhone())) {
//				throw new BusinessException("商家联系人手机格式不正确！");
//			}
//		}
//		// 1：`organization`修改组织信息，类型为专线
//		// 2：`sys_tenant_def`修改租户信息
//		Organization org = getRootOrgByTenantId(paramIn.getTenantId());
//		Map<String, Long> ret = orgSV.updateBusInfo(paramIn, org, tenantType);
//		long orgId = DataFormat.getLongKey(ret, "orgId");
//		if (orgId <= 0) {
//			throw new BusinessException("组织ID不能为空!");
//		}
//		// 3：修改的网点线路简介信息cm_org_pickup_point
//		CmOrgPickupPointSV coppSV = (CmOrgPickupPointSV) SysContexts.getBean("cmOrgPickupPointSV");
//		CmOrgPickupPoint copp = coppSV.getCmOrgPickupPointB(tenantId, orgId);
//		copp.setAddress(paramIn.getAddress());
//		copp.setTel(paramIn.getLinkPhone());
//		copp.setIsSelected(SysStaticDataEnum.IS_SELECTED.BUS_IS);
//		copp.setProvinceId(Long.parseLong(paramIn.getProvinceId()));
//		copp.setCountyId(Long.parseLong(paramIn.getCountyId()));
//		copp.setCityId(Long.parseLong(paramIn.getRegionId()));
//		copp.setTownId(Long.parseLong(paramIn.getStreetId()));
//		session.update(copp);
//
//		CmOrgRel cor = (CmOrgRel) session.get(CmOrgRel.class, paramIn.getRelId());
//		Long lineOrgId = paramIn.getOrgId();
//		// 选择归属网点
//		cor.setLineOrgId(lineOrgId);
//		cor.setLineTenantId(user.getTenantId());
//		cor.setBusiOrgId(org.getOrgId());
//		cor.setBusiTenantId(paramIn.getTenantId());
//		cor.setOpDate(new Date(System.currentTimeMillis()));
//		cor.setOpId(user.getOperId());
//		cor.setOpName(user.getUserName());
//		cor.setState(state);
//		session.update(cor);
//
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("isOk", "Y");
//		return map;
//	}

	/**
	 * lyh 根据ID查看商家信息
	 * 
	 * @param tenantId
	 * @return
	 */
	public OrgQueryBusOut queryByTenantIdBusInfo(Map<String, Object> inParam) {
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");// 专线名称
		if (tenantId <= 0) {
			throw new BusinessException("租户ID不能为空!");
		}
		OrgQueryBusOut orgOut = orgSV.getOrgBusinessByTenantId(tenantId);
		return orgOut;
	}

	/**
	 * lyh 根据ID查看平台商家信息
	 * 
	 * @param tenantId
	 * @return
	 */
	public OrgQueryBusOut queryByRelIdIdBusInfo(Map<String, Object> inParam) {
		long relId = DataFormat.getLongKey(inParam, "relId");// 专线名称
		OrgQueryBusOut orgOut = orgSV.getOrgBusinessByRelId(relId);
		return orgOut;
	}

	/**
	 * 根据条件查询商家信息
	 * 
	 * @param inParam
	 * @throws Exception
	 * @return ooList
	 */
	public OrgQueryAllBusOut doQueryAllBus(Map<String, Object> inParam) throws Exception {
		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
		String name = DataFormat.getStringKey(inParam, "name");
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");
		int zxType = SysStaticDataEnum.USER_TYPE.BUSINESS;
		OrgQueryAllBusOut oAllBus = orgSV.getOrgBusinessByNo(tenantCode, name, linkPhone, zxType);
		return oAllBus;
	}

	/**
	 * 删除商家
	 * 
	 * @param inParam
	 * @throws Exception
	 * @return ooList
	 */
	public Map<String, String> delBusiness(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		BaseUser user = SysContexts.getCurrentOperator();
		long relId = Long.parseLong(DataFormat.getStringKey(inParam, "relId"));
		if (relId <= 0) {
			throw new BusinessException("删除失败!");
		}
		CmOrgRel cmOrgRel = (CmOrgRel) session.get(CmOrgRel.class, relId);
		Query delquery = session
				.createSQLQuery(" select COUNT(1) from scheduler_task where BELONG_OBJ_ID=" + user.getTenantId()
						+ " and DO_OBJ_TYPE=2 and BELONG_OBJ_TYPE=2 and DO_OBJ_ID=" + cmOrgRel.getBusiTenantId()
						+ " and ((task_state in (:taskState)) or (WITHDRAW_STS in (:withDrewSts) and task_State=8) )");
		delquery.setParameterList("taskState",
				new Integer[] { SysStaticDataEnum.TASK_STATE.ACCEPTED_CHECK,
						SysStaticDataEnum.TASK_STATE.DISTRIBUTION_WAIT_ACCEPT,
						SysStaticDataEnum.TASK_STATE.WAIT_PICK_UP, SysStaticDataEnum.TASK_STATE.WAIT_RESERVATION,
						SysStaticDataEnum.TASK_STATE.WAIT_SIGN });
		delquery.setParameterList("withDrewSts",
				new Integer[] { SysStaticDataEnum.WITHDRAW_STS.FREEZEING, SysStaticDataEnum.WITHDRAW_STS.NO_WITHDRAW,
						SysStaticDataEnum.WITHDRAW_STS.WAIT_WITHDRAW, SysStaticDataEnum.WITHDRAW_STS.WITHDRAW_ING });
		BigInteger result = (BigInteger) delquery.uniqueResult();
		if (result.intValue() > 0) {
			throw new BusinessException("该服务商存在交易中数据或者未提现任务,不允许删除!");
		}
		session.delete(cmOrgRel);
		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}

	/**
	 * 查询全部专线名称
	 * 
	 * @param inParam
	 * @throws Exception
	 * @return ooList
	 */
	public Map<String, Object> doQueryByOrgId(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String tenantType = String.valueOf(SysStaticDataEnum.USER_TYPE.CHAIN);
		int state = SysStaticDataEnum.STS.VALID;
		Criteria ca = session.createCriteria(Organization.class);
		ca.add(Restrictions.eq("parentOrgId", parentOrgId));
		ca.add(Restrictions.eq("tenantType", tenantType));
		ca.add(Restrictions.eq("state", state));
		BaseUser user = SysContexts.getCurrentOperator();
		if(user.getUserType() == SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PULLCOM){
			List<CmOrgRel> list = session.createCriteria(CmOrgRel.class).add(Restrictions.eq("busiTenantId", user.getTenantId())).list();
			if(list != null&&list.size() > 0){
				List<Long> lineOrgId = new ArrayList<Long>();
				for(CmOrgRel temp : list){
					//ca.add(Restrictions.eq("orgId", temp.getLineOrgId()));
					lineOrgId.add(temp.getLineOrgId());
				}
				ca.add(Restrictions.in("orgId", lineOrgId));
			}else{
				JsonHelper.parseJSON2Map(JsonHelper.json(new ArrayList<Organization>()));
			}
			
		}
		List<Organization> orgList = (List<Organization>) ca.list();
		return JsonHelper.parseJSON2Map(JsonHelper.json(orgList));
	}

	/**
	 * 平台商家分页查询
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doQueryBusiness(Map<String, Object> inParam) throws Exception {
		String name = DataFormat.getStringKey(inParam, "name");// 专线名称
		String linkMan = DataFormat.getStringKey(inParam, "linkMan");// 联系人
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");// 联系人电话
		String busiNotes = DataFormat.getStringKey(inParam, "busiNotes");// 主营业务
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS);// 商家类型
		long link = DataFormat.getLongKey(inParam, "tenantId");
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String state = String.valueOf(SysStaticDataEnum.STS.VALID);
		Session session = SysContexts.getEntityManager(true);

		// StringBuffer sb = new StringBuffer("SELECT
		// o.TENANT_ID,o.ORG_ID,o.ORG_CODE,o.ORG_NAME,o.BUSINESS_TYPE,");
		// sb.append("o.BUSI_NOTES,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE FROM
		// organization o,sys_tenant_def s ");
		// sb.append("WHERE o.TENANT_ID = s.TENANT_ID AND o.TENANT_TYPE =
		// :zxType AND o.PARENT_ORG_ID = :parentOrgId AND o.STATE = :state ");
		/*
		 * StringBuffer sb = new StringBuffer(
		 * "SELECT o.TENANT_ID,o.ORG_ID,o.ORG_CODE,o.ORG_NAME,o.BUSINESS_TYPE,o.BUSI_NOTES,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,c.line_org_id,c.REL_ID "
		 * ); sb.append(
		 * " FROM organization o LEFT JOIN sys_tenant_def s ON o.TENANT_ID = s.TENANT_ID LEFT JOIN cm_org_rel c ON o.TENANT_ID = c.BUSI_TENANT_ID "
		 * ); sb.append(
		 * " WHERE o.TENANT_ID = s.TENANT_ID AND o.TENANT_TYPE = :zxType AND o.PARENT_ORG_ID = :parentOrgId AND o.STATE = :state AND c.STATE = :state "
		 * );
		 */
		String sql = " o.TENANT_ID,o.ORG_CODE,o.ORG_NAME,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.BUSI_NOTES,o.BUSINESS_TYPE,o.SELLER_NOTES,o.PROVINCE_ID,o.REGION_ID,o.COUNTY_ID,o.STREET_ID ";
		StringBuffer sb = new StringBuffer(" select " + sql
				+ " from organization o where o.TENANT_TYPE = :zxType and o.STATE = :state and o.PARENT_ORG_ID = :parentOrgId");
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
		if (link > 0) {
			sb.append(
					" and o.ORG_ID in (select c.BUSI_ORG_ID from cm_org_rel c where c.LINE_TENANT_ID = :link and c.STATE = :state) ");
		}
		sb.append(" ORDER BY o.TENANT_ID DESC ");
		Query query = session.createSQLQuery(sb.toString());
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
		if (link > 0) {
			query.setParameter("link", link);
		}
		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		query.setParameter("parentOrgId", parentOrgId);
		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<OrgQueryBusOut> rtnList = new ArrayList<OrgQueryBusOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrgQueryBusOut orgOut = new OrgQueryBusOut();
				orgOut.setTenantId(Long.parseLong(obj[0].toString()));
				orgOut.setTenantCode(obj[1].toString());
				orgOut.setName(obj[2].toString());
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
					// 利用枚举获取静态中文
					orgOut.setBusinessType(
							SysStaticDataUtil.getSysStaticData("SERVE_TYPE", obj[6].toString()).getCodeName());
				}
				// long lineOrgId = Long.parseLong(String.valueOf(obj[8]));
				// orgOut.setLineOrgId(lineOrgId);
				// Organization o = (Organization)
				// session.get(Organization.class, lineOrgId);
				// orgOut.setLineOrgName(o.getOrgName());
				// orgOut.setRelId(Long.parseLong(String.valueOf(obj[9])));
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
	 * @throws Exception
	 * @return ooList
	 */
	public Map<String, Object> getOrgBusIdByLinkName(Map<String, Object> inParam) throws Exception {
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		return JsonHelper.parseJSON2Map(JsonHelper.json(orgSV.getOrgBusByLink(tenantId)));
	}

	/**
	 * 根据商家查询专线 或根据专线查询商家
	 * 
	 * @param tenantId
	 *            租户ID
	 * @param type
	 *            1：商家查询专线 2：专线查询商家
	 */
	public List<OrgQueryAllBusOut> getTeantInfoByTenantId(long tenantId, int type) throws Exception {
		if (type == 1) {
			return orgSV.getOrgBusByLink(tenantId);
		} else {
			return orgSV.getOrgLinkByBus(tenantId);
		}

	}

	/**
	 * 师傅合作商家保存与修改
	 * 
	 * @author qlfeng
	 */
	public Map<String, String> doSaveUpdateSFPartners(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		OrgSaveBussParamIn paramIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.SAVE_ORGBUSS);
		BaseUser user = SysContexts.getCurrentOperator();
		BeanUtils.populate(paramIn, inParam);
		long relId = DataFormat.getLongKey(inParam, "relId");
		int tenantType = SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS;
		int roleId = Integer.parseInt(SysCfgUtil.getSysCfg(EnumConsts.SysCfg.SF_PARTNERS).getCfgValue());
		long tenantId = paramIn.getTenantId();
		if (StringUtils.isEmpty(paramIn.getTenantCode())) {
			throw new BusinessException("师傅合作商编码不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getName())) {
			throw new BusinessException("师傅合作商名称不能为空!");
		}
		if (StringUtils.isEmpty(paramIn.getLinkMan())) {
			throw new BusinessException("师傅联系人不能为空!");
		}
		if (!StringUtils.isEmpty(paramIn.getLinkPhone())) {
			if (!CommonUtil.isCheckPhone(paramIn.getLinkPhone())) {
				throw new BusinessException("商家联系人手机格式不正确！");
			}
		}
		if (tenantId <= 0) {
			CmUserInfoTF cmUserInfoTF = (CmUserInfoTF) SysContexts.getBean("cmUserInfoTF");
			// Map<String, String> linkPhoneMap = new HashMap<String, String>();
			// linkPhoneMap.put("loginAcct", paramIn.getLinkPhone());
			// if(cmUserInfoTF.checkUserPhoneIfReg(linkPhoneMap)){
			// throw new BusinessException("师傅联系人手机已存在！");
			// }
			// 1：`organization`新增组织信息，类型为专线
			// 2：`sys_tenant_def`新增 租户信息
			Map<String, Long> ret = orgSV.saveOrgBusByTenantId(paramIn, tenantType, roleId);
			Long orgId = DataFormat.getLongKey(ret, "orgId");
			if (orgId <= 0 && orgId != null) {
				throw new BusinessException("新增师傅失败！");
			}
			// 3：新增公司类型的账户ac_account
			saveArrcount(1, orgId, user.getUserId());
			saveArrcount(2, orgId, user.getUserId());
			saveArrcount(3, orgId, user.getUserId());
		} else if (tenantId > 0 && relId > 0) {
			// 1：`organization`修改组织信息，类型为专线
			// 2：`sys_tenant_def`修改租户信息
			Organization org = getRootOrgByTenantId(paramIn.getTenantId());
			// if(!org.getOrgPrincipalPhone().equals(paramIn.getLinkPhone())){
			// throw new BusinessException("修改师傅失败，师傅联系手机不能修改！");
			// }
			Map<String, Long> ret = orgSV.updateBusInfo(paramIn, org, tenantType);

			Long orgId = DataFormat.getLongKey(ret, "orgId");
			if (orgId <= 0 && orgId != null) {
				throw new BusinessException("修改师傅失败！");
			}
		} else {
			if (!getCheckCmOrgRel(user.getOrgId(), paramIn.getOrgId())) {
				throw new BusinessException("已经存在关系，不能重复添加！");
			}
			Organization org = (Organization) session.get(Organization.class, paramIn.getOrgId());
			CmOrgRel cor = new CmOrgRel();
			cor.setLineOrgId(user.getOrgId());
			cor.setLineTenantId(user.getTenantId());
			cor.setBusiOrgId(org.getOrgId());
			cor.setBusiTenantId(paramIn.getTenantId());
			cor.setOpDate(new Date(System.currentTimeMillis()));
			cor.setOpId(user.getOperId());
			cor.setOpName(user.getUserName());
			cor.setState(SysStaticDataEnum.STS.VALID);
			session.save(cor);
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("isOk", "Y");
		return map;
	}

	/****
	 * false 存在关系 true 不存在关系
	 * 
	 * @param lineOrgId
	 * @param busiOrgId
	 * @return
	 */
	public boolean getCheckCmOrgRel(Long lineOrgId, Long busiOrgId) {
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		int state = SysStaticDataEnum.STS.VALID;
		String sql = "select * from cm_org_rel c where c.LINE_ORG_ID = :lineOrgId and c.BUSI_ORG_ID = :busiOrgId and c.state = :state AND c.LINE_TENANT_ID = :tenantId ";
		SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setParameter("lineOrgId", lineOrgId)
				.setParameter("busiOrgId", busiOrgId).setParameter("state", state)
				.setParameter("tenantId", user.getTenantId());
		List<Object> cor = query.list();
		if (cor != null && cor.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 查询师傅合作商
	 * 
	 */
	public Map<String, Object> doQuerySFPartners(Map<String, Object> inParam) throws Exception {
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS);//
		String state = String.valueOf(SysStaticDataEnum.STS.VALID);
		Long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		String name = DataFormat.getStringKey(inParam, "name");
		String linkMan = DataFormat.getStringKey(inParam, "linkMan");
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");
		String busiNotes = DataFormat.getStringKey(inParam, "busiNotes");
		int provinceId = DataFormat.getIntKey(inParam, "provinceId");
		int regionId = DataFormat.getIntKey(inParam, "regionId");
		long relId = DataFormat.getLongKey(inParam, "relId");
		BaseUser user = SysContexts.getCurrentOperator();
		long lineTenantId = user.getTenantId();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(
				" SELECT o.ORG_ID, o.ORG_CODE, o.ORG_NAME, o.ORG_PRINCIPAL, o.ORG_PRINCIPAL_PHONE, o.TENANT_ID, o.BUSI_NOTES, o.PROVINCE_ID, o.REGION_ID, o.DEPARTMENT_ADDRESS, o.SELLER_NOTES, c.REL_ID, i.login_acct FROM organization o,cm_org_rel c ,cm_user_info i,cm_user_org_rel r "
						+ ",sys_role s,sys_role_oper_rel g " + "WHERE o.ORG_ID = c.BUSI_ORG_ID  ");
		sb.append(" AND o.PARENT_ORG_ID = :parentOrgId AND o.TENANT_TYPE = :zxType AND c.STATE = :state ");
		sb.append(" and i.user_id=r.user_id and r.org_id=o.org_id");
		sb.append(" and s.ROLE_TYPE=2 and s.ROLE_ID=g.ROLE_ID and g.OPERATOR_ID=i.user_id");
		Organization or = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		if (or.getParentOrgId() == -1 && user.getUserType() != SysStaticDataEnum.USER_TYPE.CHAIN) {
			sb.append(" AND c.LINE_TENANT_ID = :lineTenantId  ");
		} else {
			sb.append(" AND c.line_org_id = :lineOrgId  ");
		}
		if (StringUtils.isNotEmpty(name)) {
			sb.append(" and o.ORG_NAME like :name ");
		}
		if (relId > 0) {
			sb.append(" and c.rel_id = :relId ");
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
		if (tenantId != null && tenantId > 0) {
			sb.append(" and o.TENANT_ID = :tenantId ");
		}
		if (provinceId > 0) {
			sb.append(" and o.PROVINCE_ID = :provinceId ");
		}
		if (regionId > 0) {
			sb.append(" and o.REGION_ID = :regionId ");
		}
		sb.append(" ORDER BY o.TENANT_ID ASC ");
		Query query = session.createSQLQuery(sb.toString());

		if (or.getParentOrgId() == -1 && user.getUserType() != SysStaticDataEnum.USER_TYPE.CHAIN) {
			query.setParameter("lineTenantId", lineTenantId);
		} else {
			query.setParameter("lineOrgId", user.getOrgId());
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
		if (tenantId != null && tenantId > 0) {
			query.setParameter("tenantId", tenantId);
		}
		if (provinceId > 0) {
			query.setParameter("provinceId", provinceId);
		}
		if (regionId > 0) {
			query.setParameter("regionId", regionId);
		}
		query.setParameter("parentOrgId", parentOrgId);
		if (relId > 0) {
			query.setParameter("relId", relId);
		}

		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<OrgQueryBusOut> rtnList = new ArrayList<OrgQueryBusOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrgQueryBusOut o = new OrgQueryBusOut();
				o.setOrgId(Long.parseLong(String.valueOf(obj[0])));
				o.setTenantCode(String.valueOf(obj[1]));
				o.setName(String.valueOf(obj[2]));
				o.setLinkMan(String.valueOf(obj[3]));
				o.setLinkPhone(String.valueOf(obj[4]));
				o.setTenantId(Long.parseLong(String.valueOf(obj[5])));
				o.setBusiNotes(String.valueOf(obj[6]));
				String provinceName = "";
				String regionName = "";

				o.setProvinceId(String.valueOf(obj[7]));
				if (obj[7] != null && !obj[7].equals("-1")) {
					provinceName = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(obj[7]))
							.getName();
				}
				o.setRegionId(String.valueOf(obj[8]));
				if (obj[8] != null && !obj[8].equals("-1")) {
					regionName = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(obj[8])).getName();
				}
				if (obj[9] != null) {
					o.setDepaetmentAdderss(obj[9].toString());
				}
				o.setAddress(provinceName + regionName);
				if (obj[10] != null) {
					o.setSellerNotes(obj[10].toString());
				}
				o.setRelId(Integer.parseInt(String.valueOf(obj[11])));
				o.setLoginAcct(String.valueOf(obj[12]));
				/*
				 * if(obj[44]!=null){
				 * o.setIsAuth(Integer.parseInt(String.valueOf(obj[44])));
				 * o.setIsAuthName(Integer.parseInt(String.valueOf(obj[44]))==1?
				 * "是":"否"); }
				 */

				rtnList.add(o);
			}
		}
		page.setThisPageElements(rtnList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return rtnMap;
	}

	/**
	 * 查询师傅合作商
	 * 
	 */
	public Map<String, Object> doQuerySFPartnersExe(Map<String, Object> inParam) throws Exception {
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String zxType = String.valueOf(SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS);//
		String state = String.valueOf(SysStaticDataEnum.STS.VALID);
		Long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		String name = DataFormat.getStringKey(inParam, "name");
		String linkMan = DataFormat.getStringKey(inParam, "linkMan");
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");
		String busiNotes = DataFormat.getStringKey(inParam, "busiNotes");
		int provinceId = DataFormat.getIntKey(inParam, "provinceId");
		int regionId = DataFormat.getIntKey(inParam, "regionId");
		long relId = DataFormat.getLongKey(inParam, "relId");
		BaseUser user = SysContexts.getCurrentOperator();
		long lineTenantId = user.getTenantId();
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(
				" SELECT o.ORG_ID, o.ORG_CODE, o.ORG_NAME, o.ORG_PRINCIPAL, o.ORG_PRINCIPAL_PHONE, o.TENANT_ID, o.BUSI_NOTES, o.PROVINCE_ID, o.REGION_ID, o.DEPARTMENT_ADDRESS, o.SELLER_NOTES, c.REL_ID FROM organization o,cm_org_rel c WHERE o.ORG_ID = c.BUSI_ORG_ID  ");
		sb.append(" AND o.PARENT_ORG_ID = :parentOrgId AND o.TENANT_TYPE = :zxType AND c.STATE = :state ");

		Organization or = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		if (or.getParentOrgId() == -1 && user.getUserType() != SysStaticDataEnum.USER_TYPE.CHAIN) {
			sb.append(" AND c.LINE_TENANT_ID = :lineTenantId  ");
		} else {
			sb.append(" AND c.line_org_id = :lineOrgId  ");
		}
		if (StringUtils.isNotEmpty(name)) {
			sb.append(" and o.ORG_NAME like :name ");
		}
		if (relId > 0) {
			sb.append(" and c.rel_id = :relId ");
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
		if (tenantId != null && tenantId > 0) {
			sb.append(" and o.TENANT_ID = :tenantId ");
		}
		if (provinceId > 0) {
			sb.append(" and o.PROVINCE_ID = :provinceId ");
		}
		if (regionId > 0) {
			sb.append(" and o.REGION_ID = :regionId ");
		}
		sb.append(" ORDER BY o.TENANT_ID ASC ");
		Query query = session.createSQLQuery(sb.toString());

		if (or.getParentOrgId() == -1 && user.getUserType() != SysStaticDataEnum.USER_TYPE.CHAIN) {
			query.setParameter("lineTenantId", lineTenantId);
		} else {
			query.setParameter("lineOrgId", user.getOrgId());
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
		if (tenantId != null && tenantId > 0) {
			query.setParameter("tenantId", tenantId);
		}
		if (provinceId > 0) {
			query.setParameter("provinceId", provinceId);
		}
		if (regionId > 0) {
			query.setParameter("regionId", regionId);
		}
		query.setParameter("parentOrgId", parentOrgId);
		if (relId > 0) {
			query.setParameter("relId", relId);
		}

		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<OrgQueryBusOut> rtnList = new ArrayList<OrgQueryBusOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrgQueryBusOut o = new OrgQueryBusOut();
				o.setOrgId(Long.parseLong(String.valueOf(obj[0])));
				o.setTenantCode(String.valueOf(obj[1]));
				o.setName(String.valueOf(obj[2]));
				o.setLinkMan(String.valueOf(obj[3]));
				o.setLinkPhone(String.valueOf(obj[4]));
				o.setTenantId(Long.parseLong(String.valueOf(obj[5])));
				o.setBusiNotes(String.valueOf(obj[6]));
				String provinceName = "";
				String regionName = "";

				o.setProvinceId(String.valueOf(obj[7]));
				if (obj[7] != null && !obj[7].equals("-1")) {
					provinceName = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(obj[7]))
							.getName();
				}
				o.setRegionId(String.valueOf(obj[8]));
				if (obj[8] != null && !obj[8].equals("-1")) {
					regionName = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(obj[8])).getName();
				}
				if (obj[9] != null) {
					o.setDepaetmentAdderss(obj[9].toString());
				}
				o.setAddress(provinceName + regionName);
				if (obj[10] != null) {
					o.setSellerNotes(obj[10].toString());
				}
				o.setRelId(Integer.parseInt(String.valueOf(obj[11])));
				rtnList.add(o);
			}
		}
		page.setThisPageElements(rtnList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return rtnMap;
	}

	/**
	 * 查询租户对应的合作商
	 * 
	 * @param tenantId
	 *            租户id
	 * @param provinceId
	 *            省份id
	 * @param cityId
	 *            城市id
	 * @return List<Map<String,Object>> Map<String,Object> key=name 服务商名称
	 *         key=phone 服务商手机 key=provinceName 省份名称 key=cityName 省份名称
	 *         key=sfUserID 商家租户id key=userType=2 用户类型，商家为2
	 */
	public List<Map<String, Object>> querySFPartners(Map<String, Object> inParam) throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		long provinceId = DataFormat.getLongKey(inParam, "provinceId");
		long cityId = DataFormat.getLongKey(inParam, "cityId");
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		long lineOrgId = DataFormat.getLongKey(inParam, "lineOrgId");
		tenantId = user.getTenantId();
		String phone = DataFormat.getStringKey(inParam, "sfUserAcct");
		String name = DataFormat.getStringKey(inParam, "sfUserName");
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(" SELECT "
				+ "o.ORG_NAME,o.ORG_ID,o.TENANT_ID,o.ORG_PRINCIPAL_PHONE,o.province_Id,o.region_Id,o.DEPARTMENT_ADDRESS"
				+ " FROM organization o,cm_org_rel c " + "WHERE o.TENANT_ID = c.BUSI_TENANT_ID and o.state=1  ");
		sb.append("  AND o.TENANT_TYPE = 6 AND c.STATE =1 ");
		if (tenantId > 0) {
			sb.append("  AND c.LINE_TENANT_ID=:tenantId ");
		}
		// if(provinceId>0){
		// sb.append(" AND o.province_Id=:provinceId ");
		// }
		if (lineOrgId > 0) {
			sb.append("  AND c.LINE_ORG_ID=:lineOrgId ");
		}
		// if(cityId>0){
		// sb.append(" AND o.region_Id=:cityId ");
		// }
		if (StringUtils.isNotEmpty(name)) {
			sb.append("  AND o.ORG_NAME like :name ");
		}
		if (StringUtils.isNotEmpty(phone)) {
			sb.append("  AND o.ORG_PRINCIPAL_PHONE=:phone ");
		}
		Query query = session.createSQLQuery(sb.toString());
		if (tenantId > 0) {
			query.setParameter("tenantId", tenantId);
		}
		// if(provinceId>0){
		// query.setParameter("provinceId", provinceId);
		// }
		if (lineOrgId > 0) {
			query.setParameter("lineOrgId", lineOrgId);
		}
		// if(cityId>0){
		// query.setParameter("cityId", cityId);
		// }
		if (StringUtils.isNotEmpty(name)) {
			query.setParameter("name", "%" + name + "%");
		}
		if (StringUtils.isNotEmpty(phone)) {
			query.setParameter("phone", phone);
		}
		List list = query.list();
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			Map map = new HashMap();
			map.put("provinceName", SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", obj[4] + "").getName());
			map.put("cityName", SysStaticDataUtil.getCityDataList("SYS_CITY", obj[5] + "").getName());
			map.put("sfUserId", obj[2]);
			map.put("phone", obj[3]);
			map.put("name", obj[0]);
			map.put("userType", 2);
			map.put("address", obj[6]);
			map.put("descOrgId", obj[1]);
			rtnMap.add(map);
		}
		return rtnMap;
	}

	/**
	 * 查询合作商合作的租户信息
	 * 
	 * @param tenantId
	 *            租户id
	 * @param provinceId
	 *            省份id
	 * @param cityId
	 *            城市id
	 * @return List<Map<String,Object>> Map<String,Object> key=name 服务商名称
	 *         key=phone 服务商手机 key=provinceName 省份名称 key=cityName 省份名称
	 *         key=sfUserID 商家租户id key=userType=2 用户类型，商家为2
	 */
	public Map<String, Object> queryServiceOrg(Map<String, Object> inParam) throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		long provinceId = DataFormat.getLongKey(inParam, "provinceId");
		long cityId = DataFormat.getLongKey(inParam, "cityId");
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");
		String phone = DataFormat.getStringKey(inParam, "sfUserAcct");
		String name = DataFormat.getStringKey(inParam, "sfUserName");
		int isRootOrg = DataFormat.getIntKey(inParam, "isRootOrg");
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb = new StringBuffer(
				" SELECT o.ORG_NAME,o.ORG_ID,o.TENANT_ID,o.ORG_PRINCIPAL_PHONE,o.province_Id,o.region_Id FROM organization o,cm_org_rel c WHERE o.TENANT_ID = c.LINe_TENANT_ID and o.state=1  ");
		sb.append("  AND o.tenant_type!=6 and  c.STATE =1 ");
		if (tenantId > 0) {
			sb.append("  AND c.BUSI_TENANT_ID=:tenantId ");
		}
		if (provinceId > 0) {
			sb.append("  AND o.province_Id=:provinceId ");
		}
		if (cityId > 0) {
			sb.append("  AND o.region_Id=:cityId ");
		}
		if (StringUtils.isNotEmpty(name)) {
			sb.append("  AND o.ORG_NAME like :name ");
		}
		if (StringUtils.isNotEmpty(phone)) {
			sb.append("  AND o.ORG_PRINCIPAL_PHONE=:phone ");
		}
		if (isRootOrg == 1) {
			sb.append("  group by o.tenant_id ");
		}
		Query query = session.createSQLQuery(sb.toString());
		if (tenantId > 0) {
			query.setParameter("tenantId", tenantId);
		}
		if (provinceId > 0) {
			query.setParameter("provinceId", provinceId);
		}
		// if(cityId>0){
		// query.setParameter("cityId", cityId);
		// }
		if (StringUtils.isNotEmpty(name)) {
			query.setParameter("name", "%" + name);
		}
		if (StringUtils.isNotEmpty(phone)) {
			query.setParameter("phone", phone);
		}
		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			Map map = new HashMap();
			map.put("provinceName", SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", obj[4] + "").getName());
			map.put("cityName", SysStaticDataUtil.getCityDataList("SYS_CITY", obj[5] + "").getName());
			map.put("tenantId", obj[2]);
			map.put("phone", obj[3]);
			map.put("orgId", obj[1]);
			map.put("name", obj[0]);
			rtnMap.add(map);
		}
		page.setThisPageElements(rtnMap);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return map;
	}

	/**
	 * 根据条件查询师傅合作商信息
	 * 
	 * @param inParam
	 * @throws Exception
	 * @return ooList
	 */
	public OrgQueryAllBusOut doQueryAllSFPartners(Map<String, Object> inParam) throws Exception {
		String tenantCode = DataFormat.getStringKey(inParam, "tenantCode");
		String name = DataFormat.getStringKey(inParam, "name");
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");

		if (StringUtils.isEmpty(tenantCode) && StringUtils.isEmpty(name) && StringUtils.isEmpty(linkPhone)) {
			return new OrgQueryAllBusOut();
		}

		int zxType = SysStaticDataEnum.USER_TYPE.MASTER_PARTNERS;// 类型
		OrgQueryAllBusOut oAllBus = orgSV.getOrgBusinessByNo(tenantCode, name, linkPhone, zxType);

		// 查询一个租户下面的公司管理员的账号

		return oAllBus;
	}

	/**
	 * 查询所有合作商
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<Organization> getCarri(Map<String, Object> inParam) throws Exception {
		Session session = SysContexts.getEntityManager();
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Criteria ca = session.createCriteria(Organization.class);
		ca.add(Restrictions.eq("orgType", SysStaticDataEnum.ORG_TYPE.CARRIER_ORG));
		ca.add(Restrictions.eq("parentOrgId", baseUser.getOrgId()));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		List list = ca.list();
		return list;
	}

	/**
	 * 查询专线的所有网点
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<Organization> getAllOrg(Map<String, Object> inParam) throws Exception {
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(Organization.class);
		int orgType = SysStaticDataEnum.ORG_TYPE.CARRIER_ORG;
		int state = SysStaticDataEnum.STS.VALID;
		ca.add(Restrictions.ne("orgType", orgType));
		if (user.getOrgId() > 0) {
			Organization org = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
			long parentOrgId = org.getParentOrgId();
			if (parentOrgId == EnumConsts.ROOT_ORG_ID) {
				ca.add(Restrictions.eq("parentOrgId", user.getOrgId()));
			} else {
				ca.add(Restrictions.eq("orgId", user.getOrgId()));
			}
			// ca.add(Restrictions.eq("parentOrgId", parentOrgId));
			ca.add(Restrictions.eq("state", state));
		}
		return (List<Organization>) ca.list();
	}

	/**
	 * 查询合作商专线
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSFLinkName(Map<String, Object> inParam) throws Exception {
		BaseUser user = SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);

		long userOrgId = user.getOrgId();
		int state = SysStaticDataEnum.STS.VALID;
		String orgName = DataFormat.getStringKey(inParam, "orgName");
		Organization oo = (Organization) session.get(Organization.class, userOrgId);
		String userOrgName = oo.getOrgName();
		String sql = "select r.LINE_ORG_ID from cm_org_rel r where r.BUSI_ORG_ID = :userOrgId";
		String sql1 = "";
		if (StringUtils.isNotEmpty(orgName)) {
			sql1 = "and o.ORG_NAME like :orgName";
		}
		SQLQuery q = session.createSQLQuery("select o.ORG_ID,o.ORG_NAME from organization o where o.ORG_ID in(" + sql
				+ ") and o.STATE = :state " + sql1);
		if (StringUtils.isNotEmpty(orgName)) {
			q.setParameter("orgName", "%" + orgName + "%");
		}
		q.setParameter("userOrgId", userOrgId);
		q.setParameter("state", state);
		IPage page = PageUtil.gridPage(q);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<OrgQueryBusOut> rtnList = new ArrayList<OrgQueryBusOut>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				OrgQueryBusOut o = new OrgQueryBusOut();
				o.setOrgId(Long.parseLong(String.valueOf(obj[0])));
				o.setOrgName(String.valueOf(obj[1]));
				o.setSfOrgName(userOrgName);
				rtnList.add(o);
			}
		}
		page.setThisPageElements(rtnList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return rtnMap;
	}

	/**
	 * 查询当前网点信息
	 * 
	 * @param inParam
	 * @return
	 */
	public Map getOrgInfo(Map<String, Object> inParam) {
		Session session = SysContexts.getEntityManager(true);
		BaseUser baseUser = SysContexts.getCurrentOperator();
		Organization o = (Organization) session.get(Organization.class, baseUser.getOrgId());
		Map map = new HashMap();
		map.put("organization", o);
		return map;
	}

	/**
	 * 查询该租户下的网点APP
	 * 
	 * @author 邱林锋
	 * @param inParam
	 * @return
	 */
	public Map<String, Object> curOrgReturnOrgNameByorgId(Map<String, Object> inParam) {
		Session session = SysContexts.getEntityManager(true);
		long tenantId = DataFormat.getLongKey(inParam, "tenantId");// （必填）{注意是首页你选的或物流公司租户ID}
		if (tenantId <= 0) {
			throw new BusinessException("请输入首页选择的物流公司租户ID！");
		}
		Criteria ca = session.createCriteria(Organization.class);
		ca.add(Restrictions.eq("tenantId", tenantId));
		List<Organization> org = (List<Organization>) ca.list();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Organization temp : org) {
			if (temp.getOrgType() != SysStaticDataEnum.ORG_TYPE.CARRIER_ORG) {
				map = new HashMap<String, Object>();
				map.put("orgId", temp.getOrgId());
				map.put("orgName", temp.getOrgName());
				list.add(map);
			}
		}

		if (org == null || org.size() < 0) {
			throw new BusinessException("不存在该租户的网点");
		}
		return JsonHelper.parseJSON2Map(JsonHelper.json(list));
	}

	@Override
	public List<OrgQueryMatserOut> doQueryLeftMaster(Map<String, Object> inParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> doSaveAuthOrg(Map<String, Object> inParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询拉包公司
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object>doQueryPullPagCompany(Map<String,Object>inParam)throws Exception{
		Integer orgId = DataFormat.getIntKey(inParam, "orgId");//拉包公司名称
		String tenantCode=DataFormat.getStringKey(inParam, "tenantCode");//公司编码
		String linkPhone = DataFormat.getStringKey(inParam, "linkPhone");// 联系人电话
		String companyType=String.valueOf(SysStaticDataEnum.USER_TYPE.PULL_PAG_COMPANY);//拉包公司
		long parentOrgId = EnumConsts.ROOT_ORG_ID;
		String state = String.valueOf(SysStaticDataEnum.ORG_STATE.COMMON);
		String companyName="";
		String inputParamJson=StringEscapeUtils.unescapeHtml(DataFormat.getStringKey(inParam, "inputParamJson"));
		if(StringUtils.isNotEmpty(inputParamJson)){
			Map inputParamMap=JsonHelper.parseJSON2Map(inputParamJson);
			String inTenantCode=DataFormat.getStringKey(inputParamMap, "tenantCode");//公司编码
			if(StringUtils.isNotEmpty(inTenantCode)){
				tenantCode=inTenantCode;
			}
			companyName=DataFormat.getStringKey(inputParamMap, "name");//拉包公司
			String inLinkPhone = DataFormat.getStringKey(inputParamMap, "linkPhone");// 联系人电话
			if(StringUtils.isNotEmpty(inLinkPhone)){
				linkPhone=inLinkPhone;
			}
		}
		Session session = SysContexts.getEntityManager(true);
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT * FROM (SELECT DISTINCT o.TENANT_ID,o.ORG_CODE,o.ORG_NAME,o.ORG_PRINCIPAL,o.ORG_PRINCIPAL_PHONE,o.SUPPORT_STAFF_PHONE");
		sb.append(" ,o.org_id, o.DEPARTMENT_ADDRESS,o.SELLER_NOTES,o.CREATE_DATE,u.USER_NAME ");
		sb.append("	 FROM organization o LEFT JOIN cm_user_info AS u ON u.USER_ID=o.CREATOR_ID");
		sb.append(" where o.STATE=:state AND o.TENANT_TYPE=:companyType AND o.PARENT_ORG_ID=:parentOrgId ");
		if(orgId>0){
			sb.append(" AND o.ORG_ID=:orgId ");
		}
		if(StringUtils.isNotEmpty(tenantCode)){
			sb.append(" AND o.ORG_CODE=:tenantCode ");
		}
		if(StringUtils.isNotEmpty(linkPhone)){
			sb.append(" AND  o.ORG_PRINCIPAL_PHONE=:linkPhone ");
		}
		if(StringUtils.isNotEmpty(companyName)){
			//sb.append(" AND o.ORG_NAME = :name ");
			sb.append(" AND o.ORG_NAME like :name ");
		}
		sb.append(" ORDER BY o.TENANT_ID ASC) as s ");
		Query query = session.createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(state)) {
			query.setParameter("state", state);
		}
		if(StringUtils.isNotEmpty(companyName)){
			query.setParameter("name", companyName);
		}
		if(StringUtils.isNotEmpty(companyType)){
			query.setParameter("companyType", companyType);
		}
		if(orgId>0){
			query.setParameter("orgId", orgId);
		}
		if(StringUtils.isNotEmpty(tenantCode)){
			query.setParameter("tenantCode", tenantCode);
		}
		if(StringUtils.isNotEmpty(linkPhone)){
			query.setParameter("linkPhone", linkPhone);
		}
		query.setParameter("parentOrgId", parentOrgId);
		IPage page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>) page.getThisPageElements();
		List<OrgSavePullPagParamOut>outList=new ArrayList<OrgSavePullPagParamOut>();
		if(list!=null&&list.size()>0){
			for(Object[]obj:list){
				OrgSavePullPagParamOut orgOut=new OrgSavePullPagParamOut();
				if(obj[0]!=null){
					BigInteger tenantId=(BigInteger)obj[0];
					orgOut.setTenantId(tenantId.longValue());
				}
				if(obj[1]!=null){
					orgOut.setTenantCode((String)obj[1]);
				}
				if(obj[2]!=null){
					orgOut.setName((String)obj[2]);
				}
				if(obj[3]!=null){
					orgOut.setLinkMan((String)obj[3]);
				}
				if (obj[4] != null) {
					orgOut.setLinkPhone((String)obj[4]);
				}
				if(obj[5]!=null){
					orgOut.setCsPhones((String) obj[5]);
				}
				if(obj[6]!=null){
					BigInteger orgId2=(BigInteger)obj[6];
					orgOut.setOrgId(orgId2.toString());
				}
				if(obj[7]!=null){
					orgOut.setAddress((String)obj[7]);
				}
				if(obj[8]!=null){
					orgOut.setSellerNotes((String)obj[8]);
				}
				if(obj[9]!=null){
					orgOut.setCreateDate((Date)obj[9]);
				}if(obj[10]!=null){
					orgOut.setCreateName((String)obj[10]);
				}
				outList.add(orgOut);
			}
		}
		page.setThisPageElements(outList);
		Pagination<Object[]> pages = new Pagination<Object[]>(page);
		Map<String, Object>rtnMap = new HashMap<String, Object>();
		rtnMap = JsonHelper.parseJSON2Map(JsonHelper.json(pages));
		return rtnMap;
	}

	public Map<String, Object> getEndOrgInfo(Map<String, Object> inParam) {
		// TODO Auto-generated method stub
		BaseUser user = SysContexts.getCurrentOperator();
		long orgId = user.getOrgId(); //当前网点
		Session session = SysContexts.getEntityManager(true);
		Criteria ca = session.createCriteria(RouteRouting.class);
		ca.add(Restrictions.eq("beginOrgId", orgId));
		ca.add(Restrictions.eq("status", SysStaticDataEnumYunQi.STS.VALID));
		List<RouteRouting> list = ca.list();
		List<Map<String, String>> listOut = new ArrayList<Map<String, String>>();  
		if(list!=null &&list.size()>0){
			for(RouteRouting route: list){
				Map map = new HashMap();
				map.put("orgId", route.getEndOrgId());
				map.put("codeName",route.getEndOrgName());
				listOut.add(map);
		     }			
	     }	
		return JsonHelper.parseJSON2Map(JsonHelper.json(listOut));
	}
}
