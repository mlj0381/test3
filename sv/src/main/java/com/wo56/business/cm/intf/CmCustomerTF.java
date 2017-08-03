package com.wo56.business.cm.intf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.CommonUtil;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.business.cm.impl.CmCustomerSV;
import com.wo56.business.cm.vo.CmCustomer;
import com.wo56.business.cm.vo.out.CmContractOut;
import com.wo56.business.cm.vo.out.CmcustomerOut;
import com.wo56.business.cm.vo.out.PRCustomerParamOut;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.ObjectCompareUtils;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class CmCustomerTF {

	/**
	 * 查询收货人信息 关联发货人
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<CmcustomerOut> queryCustomer(Map<String,Object> inParam)throws Exception{
		String name = DataFormat.getStringKey(inParam, "name");
		int state = DataFormat.getIntKey(inParam, "state");
		BaseUser currentOperator = SysContexts.getCurrentOperator();
		long orgId = currentOperator.getOrgId();
		Session session = SysContexts.getEntityManager(true);
		/**
		 * issue : 这种sql写法无法查询出parent_id=0的收货人
		 */
		StringBuilder sb = new StringBuilder("select id from cm_customer where org_id= :orgId and type= :type ");
		if (state >= 0)
			sb.append(" and state = :state ");
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.setParameter("orgId", orgId);
		query.setParameter("type", SysStaticDataEnum.CUSTOMER_TYPE.PUB_CUSTOMER);
		if(state >= 0)
			query.setParameter("state", state);
		
		List queryResult = query.list(); 
		List<Long> parentIds = new ArrayList<Long>();
		parentIds.add(0l);// 需要把parent_id = 0的数据查询出来。
		for (int i = 0; null != queryResult && i < queryResult.size(); i++) {
			parentIds.add(((BigInteger) queryResult.get(i)).longValue());
		}
		
		sb = new StringBuilder("select a.* from cm_customer a where a.org_id =:orgId and a.PARENT_ID in (:parentIds) and a.type = ").append(SysStaticDataEnum.CUSTOMER_TYPE.REC_CUSTOMER);
		
		if (!CommonUtil.isEmpty(name)) {
			sb.append(" and a.name like :name ");
		}
		if(state >= 0)
			sb.append(" and a.state = :state ");
		
		SQLQuery sql = session.createSQLQuery(sb.toString()).addEntity("a",CmCustomer.class);
		if(!CommonUtil.isEmpty(name)){
			sql.setParameter("name", "%"+name+"%");
		}
		if (state >= 0)
			query.setParameter("state", state);
		
		sql.setParameter("orgId", orgId);
		sql.setParameterList("parentIds", parentIds);
		sql.setParameter("state", state);
		List<CmCustomer> queryResultList = sql.list();
		if (queryResultList.size() > 0) {
			List<CmcustomerOut> rtnList = new ArrayList();
			for(CmCustomer rCustomer : queryResultList){
				CmcustomerOut cmcustomerOut = new CmcustomerOut();
				cmcustomerOut.setId(rCustomer.getId());
				cmcustomerOut.setName(rCustomer.getName());
				cmcustomerOut.setTelephone(rCustomer.getTelephone());
				cmcustomerOut.setBill(rCustomer.getBill());
				cmcustomerOut.setLinkmanName(rCustomer.getLinkmanName());
				cmcustomerOut.setAddress(rCustomer.getAddress());
				rtnList.add(cmcustomerOut);
			}
			return rtnList;
		}
		return null;
	}
	
	/**
	 * 查询发货人或收货人
	 * 主要是查询发货人
	 * @param inParam
	 * @return
	 */
	public List<CmcustomerOut> getCustomer(Map<String,Object> inParam){
		String name = DataFormat.getStringKey(inParam, "name");		
		Session session = SysContexts.getEntityManager(true);
		int type = DataFormat.getIntKey(inParam,"type");
		if (type <= 0) {
			throw new BusinessException("请输入查询类型");
		}
		if(type != SysStaticDataEnum.CUSTOMER_TYPE.PUB_CUSTOMER && type != SysStaticDataEnum.CUSTOMER_TYPE.REC_CUSTOMER ){
			throw new BusinessException("查询类型不正确!");
		}
		int state = DataFormat.getIntKey(inParam, "state");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		StringBuffer sb = new StringBuffer("SELECT c.* FROM cm_customer c WHERE 1=1 ");
		if(!StringUtils.isEmpty(name)){
			sb.append(" and c.name like :name ");
		}
		if (state >= 0){
			sb.append(" and c.state= :state ");
		}
		sb.append(" and c.type= :type ");
		sb.append(" AND c.org_id = :orgId order by c.SIGNING_TYPE asc limit 10 ");
		Query query = session.createSQLQuery(sb.toString()).addEntity(CmCustomer.class);
		if(!StringUtils.isEmpty(name)){
			query.setParameter("name", "%"+name+"%");
		}
		if (state >= 0){
			query.setParameter("state", state);
		}
		query.setParameter("type", type);
		query.setParameter("orgId", baseUser.getOrgId());
		List<CmCustomer> list = query.list();
		if(list.size()>0){
			List<CmcustomerOut> rtnList=new ArrayList();
			for(CmCustomer rCustomer : list){
				CmcustomerOut cmcustomerOut = new CmcustomerOut();
				cmcustomerOut.setId(rCustomer.getId());
				if(rCustomer.getSigningType()!=null&&rCustomer.getSigningType().equals(1)){
					cmcustomerOut.setStrSign("签约客户");
				}
				if(rCustomer.getSigningType()!=null){
					cmcustomerOut.setSigningType(rCustomer.getSigningType().intValue());
				}
				cmcustomerOut.setName(rCustomer.getName());
				cmcustomerOut.setTelephone(rCustomer.getTelephone());
				cmcustomerOut.setBill(rCustomer.getBill());
				cmcustomerOut.setLinkmanName(rCustomer.getLinkmanName());
				cmcustomerOut.setTenantId(rCustomer.getTenantId());
				cmcustomerOut.setOrgId(rCustomer.getOrgId());
				cmcustomerOut.setAddress(rCustomer.getAddress());
				rtnList.add(cmcustomerOut);
			}
			return rtnList;
		}
		return null;
	}
	
	/**
	 * 保存发货人、收货人
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doSave(Map<String,Object> inParam)throws Exception{
		BaseUser baseUser = SysContexts.getCurrentOperator();
		long orgId = baseUser.getOrgId();
		long tenantId = baseUser.getTenantId();
		PRCustomerParamOut prCustomerParam = new PRCustomerParamOut();
		BeanUtils.copyProperties(prCustomerParam, inParam);
		if(orgId<0){
			throw new BusinessException("请输入网点Id");
		}
		if(tenantId<0){
			throw new BusinessException("tenantId不能为空");
		}
		ObjectCompareUtils.isNotBlankNames(prCustomerParam, new String[]{"pName","pLinkmanName","pTelephone","pBill","rName","rLinkmanName","rTelephone","rBill","rAddress"});
		if(!CommonUtil.isNumber(prCustomerParam.getpBill())){
			throw new BusinessException("pBill格式不正确");
		}
		if(!CommonUtil.isNumber(prCustomerParam.getrBill())){
			throw new BusinessException("rBill格式不正确");
		}
		//判断手机号码
		CmCustomerSV sv = (CmCustomerSV) SysContexts.getBean("customerBO");
		sv.doSave(prCustomerParam, orgId,tenantId);
		//发货人
		return "Y";
	}
	
	/**
	 * 收货人、发货人列表
	 * @param inParam
	 * @return
	 */
	public Pagination doQueryCustomer(Map<String, String> inParam){
		CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
		CmUserInfoTF cmUserInfoTF = (CmUserInfoTF)SysContexts.getBean("cmUserInfoTF");
		int userRole = cmUserInfoTF.getUserRole();
		inParam.put("userRole", userRole+"");
		Pagination page  =  cmCustomerSV.doQueryCustomer(inParam);
		return page;
	}

	
	/**
	 * 收货人、发货人详情
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doQueryCustomerDtl(Map<String, String> inParam) throws Exception{
		Long id=DataFormat.getLongKey(inParam, "id");
		Session session = SysContexts.getEntityManager(true);
		CmCustomer cmCustomer = (CmCustomer)session.get(CmCustomer.class, id);
		if(cmCustomer==null){
			throw new BusinessException("查询不到该用户的信息");	
		}
		Map iparmMap = new HashMap();
		iparmMap.put("cmCustomer", cmCustomer);
		return iparmMap;
		
	}
	
	
	/**
	 * 删除发货人/收货人
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map delCmCustomer(Map<String, Object> inParam) throws Exception{
		Map paramMap = new HashMap();
		List<Integer> ids = (List<Integer>)inParam.get("ids");
		Integer type= DataFormat.getIntKey(inParam, "type");
		 Session session  = SysContexts.getEntityManager();
		if(ids.size()<=0){
			throw new BusinessException("请传入用户Id");	
		}
		if(type==null || type<=0){
			throw new BusinessException("请传入用户类型");	
		}
		CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
		cmCustomerSV.delCmCustomer(ids,type);
		paramMap.put("info", "Y");
		return paramMap;
	}
	/**
	 * 保存修改发货人、收货人
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map saveOrUpdateCustomer(Map<String, String> inParam) throws Exception{
		Session session  = SysContexts.getEntityManager();
		CmCustomer cmCustomer = new CmCustomer();
		long id = DataFormat.getLongKey(inParam, "id");
		String linkmanName = DataFormat.getStringKey(inParam, "linkmanName");
		String bill = DataFormat.getStringKey(inParam, "bill");
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		if(id>0){
			cmCustomer = (CmCustomer)session.get(CmCustomer.class, id);
		}else{
		     BeanUtils.populate(cmCustomer, inParam);
		}		
		Criteria ca  =  session.createCriteria(CmCustomer.class);
		if(cmCustomer.getId()<=0){
			ca.add(Restrictions.eq("linkmanName", cmCustomer.getLinkmanName()));
			if(!StringUtils.isEmpty(cmCustomer.getBill()) ){
			     ca.add(Restrictions.eq("bill", cmCustomer.getBill()));
			}
			ca.add(Restrictions.eq("orgId", cmCustomer.getOrgId()));
			if(ca.list().size()>0){
				throw new BusinessException("该网点已有["+cmCustomer.getLinkmanName()+"]的信息");	
			}
		}else if(cmCustomer.getId()>0){
			if(((!StringUtils.isEmpty(cmCustomer.getBill()) && !cmCustomer.getBill().equals(bill)) || !cmCustomer.getLinkmanName().equals(linkmanName) ||cmCustomer.getOrgId().longValue()!=orgId)){
				ca.add(Restrictions.eq("linkmanName", linkmanName));
				ca.add(Restrictions.eq("bill", bill));
				ca.add(Restrictions.eq("orgId", orgId));
				if(ca.list().size()>0){
					throw new BusinessException("该网点已有["+cmCustomer.getLinkmanName()+"]的信息");	
				}
			}
			 BeanUtils.populate(cmCustomer, inParam);
		}
		if(StringUtils.isEmpty(cmCustomer.getName())){
			if(cmCustomer.getType()==1){
				throw new BusinessException("请输入发货方");	
			}else{
				throw new BusinessException("请输入收货方");
			}
		}
		if(StringUtils.isEmpty(cmCustomer.getLinkmanName())){
			throw new BusinessException("请输入联系人");	
		}
		if(cmCustomer.getType()==null || cmCustomer.getType()<=0){
			throw new BusinessException("请输入类型");	
		}
		CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
		cmCustomerSV.saveOrUpdateCustomer(cmCustomer);
		Map map = new HashMap();
		map.put("info", "Y");
		return map;
	}
	
	public static void main(String[] args) throws Exception {
//		PRCustomerParamOut prCustomerParam = new PRCustomerParamOut();
//		prCustomerParam.setpName("hh");
//		CmCustomer cmCustomer = new CmCustomer();
//		BeanUtils.copyProperty(cmCustomer,"p", prCustomerParam);
//		System.out.println(cmCustomer.getName());
		CmCustomerTF t = new CmCustomerTF();
		Map map = new HashMap();
		map.put("orgId", 12);
		map.put("tenantId", 0);
		map.put("pId", 1);
		map.put("pName","ha1");
		map.put("pLinkmanName", 2);
		map.put("pTelephone", 2);
		map.put("pBill", 2);
		map.put("rId", 2);
		map.put("rName", "q");
		map.put("rLinkmanName", 3);
		map.put("rTelephone", 3);
		map.put("rBill", 3);
		map.put("rAddress", "大地");
		t.doSave(map);
	}
	
	public List<CmContractOut> getQueryBus(Long orgId){
		Session session = SysContexts.getEntityManager(true);
		BaseUser user = SysContexts.getCurrentOperator();
		int state = SysStaticDataEnum.STS.VALID;
		long parentOrgId = user.getOrgId();
		StringBuffer sb = new StringBuffer("select o.ORG_ID,o.ORG_NAME from organization o where  o.STATE = :state and o.PARENT_ORG_ID = :parentOrgId ");
		if(orgId !=null && orgId > 0){
			sb.append(" and o.ORG_ID = :orgId ");
		}
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("state", state);
		query.setParameter("parentOrgId", parentOrgId);
		if(orgId !=null && orgId > 0){
			query.setParameter("orgId", orgId);
		}
		List<Object[]> list = (List<Object[]>)query.list();
		List<CmContractOut> ccoList = new ArrayList<CmContractOut>();
		if(list != null && list.size() > 0){
			for(Object[] temp : list){
				CmContractOut cco = new CmContractOut();
				cco.setOrgId(Long.parseLong(temp[0].toString()));
				cco.setOrgName(String.valueOf(temp[1].toString()));
				ccoList.add(cco);
			}
		}
		return ccoList;
	}
	
	/**
	 * 查询商家组织
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> doQueryBus(Map<String, String> inParam) throws Exception{
		return JsonHelper.parseJSON2Map(JsonHelper.json(getQueryBus(null)));
	}
	/**
	 * 查询发货人
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	/*public Map<String,Object> doQueryCon(Map<String, String> inParam)throws Exception{
		BaseUser user=SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		int state = SysStaticDataEnum.STS.VALID;
		String name = DataFormat.getStringKey(inParam,"name");
		String bill = DataFormat.getStringKey(inParam,"bill");
		long orgId = DataFormat.getLongKey(inParam,"orgId");
		int type = DataFormat.getIntKey(inParam,"type");
		long tenantId = user.getTenantId();
		Criteria ca = session.createCriteria(CmCustomer.class);
		ca.add(Restrictions.eq("type",type));
		ca.add(Restrictions.eq("state",state));
		if(orgId > 0){
			ca.add(Restrictions.eq("orgId",orgId));
		}
		if(StringUtils.isNotEmpty(bill)){
			ca.add(Restrictions.eq("bill",bill));
		}
		if(StringUtils.isNotEmpty(name)){
			ca.add(Restrictions.eq("name",name));
		}
		Organization orgP = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
		long parentOrgId = orgP.getParentOrgId();
		if(parentOrgId == EnumConsts.ROOT_ORG_ID){
			ca.add(Restrictions.eq("tenantId",tenantId));
		}else{
			ca.add(Restrictions.eq("orgId",user.getOrgId()));
		}
		IPage  page = PageUtil.gridPage(ca);
		List<CmCustomer> cmList = (List<CmCustomer>)page.getThisPageElements();
		
		List<CmContractOut> ccoList = new ArrayList<CmContractOut>();
		if(cmList != null){
			for(CmCustomer temp : cmList){
				CmContractOut cc = new CmContractOut();
				//1.目标2.来源
				BeanUtils.copyProperties(cc, temp);
				Organization org = (Organization) session.get(Organization.class, temp.getOrgId());
				if(org != null){
					cc.setOrgName(org.getOrgName());
				}else{
					cc.setOrgName("");
				}
				ccoList.add(cc);
			}
		}
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		page.setThisPageElements(ccoList);
		rtnMap=JsonHelper.parseJSON2Map(JsonHelper.json(new Pagination<Object[]>(page)));
		return rtnMap;
	}*/
	
	
	/**
	 * 查询发货人
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> doQueryCon(Map<String, String> inParam) throws Exception{
		BaseUser user=SysContexts.getCurrentOperator();
		Session session = SysContexts.getEntityManager(true);
		int state = SysStaticDataEnum.STS.VALID;
		String name = DataFormat.getStringKey(inParam,"name");
		String telephone = DataFormat.getStringKey(inParam,"telephone");
		long orgId = DataFormat.getLongKey(inParam,"orgId");
		int type = DataFormat.getIntKey(inParam,"type");
		StringBuffer sb = new StringBuffer("SELECT ID,`NAME`,LINKMAN_NAME,TELEPHONE,BILL,ORG_ID,ADDRESS,TENANT_ID,ZX_TENANT_ID FROM cm_customer ");
		long curOrgId = user.getOrgId();
		Organization orgP = OraganizationCacheDataUtil.getOrganization(curOrgId);
		long parentOrgId = orgP.getParentOrgId();
		if(parentOrgId == EnumConsts.ROOT_ORG_ID){
			sb.append(" WHERE (org_id IN (SELECT org_id FROM organization WHERE parent_org_id = :curOrgId AND state = :state) OR org_id = :curOrgId) ");
		}else{
			sb.append(" where org_id = :curOrgId ");
		}
		sb.append(" AND state = :state AND TYPE = :type ");
		if(type < 0){
			throw new BusinessException("类型不能为空！");	
		}
		if(StringUtils.isNotEmpty(name)){
			sb.append(" and `NAME` like :name ");
		}
		if(StringUtils.isNotEmpty(telephone)){
			sb.append(" and TELEPHONE = :telephone ");
		}
		if(orgId > 0){
			sb.append(" and ORG_ID = :orgId");
		}
		sb.append(" order by create_date desc");
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("type", type);
		query.setParameter("state", state);
		query.setParameter("curOrgId", curOrgId);
		if(StringUtils.isNotEmpty(name)){
			query.setParameter("name", "%"+name+"%");
		}
		if(StringUtils.isNotEmpty(telephone)){
			query.setParameter("telephone", telephone);
		}
		if(orgId > 0){
			query.setParameter("orgId", orgId);
		}
		IPage  page = PageUtil.gridPage(query);
		List<Object[]> list = (List<Object[]>)page.getThisPageElements();
		List<CmContractOut> ccoList = new ArrayList<CmContractOut>();
		if(list != null && list.size() > 0){
			for(Object[] temp : list){
				CmContractOut cco = new CmContractOut();
				cco.setId(Long.parseLong(temp[0].toString()));
				cco.setName(String.valueOf(temp[1]));
				if(temp[2] != null){
				cco.setLinkmanName(String.valueOf(temp[2]));
				}
				if(temp[3] != null){
				cco.setTelephone(String.valueOf(temp[3]));
				}
				if(temp[4] != null){
				cco.setBill(String.valueOf(temp[4]));
				}
				if(temp[5] != null){
					long newOrgId = Long.parseLong(String.valueOf(temp[5]));
					cco.setOrgId(newOrgId);
					Organization org = (Organization) session.get(Organization.class, newOrgId);
					if(org != null){
						cco.setOrgName(org.getOrgName());
					}else{
						cco.setOrgName("");
					}
				}
				if(temp[6]!=null){
					cco.setAddress(String.valueOf(temp[6]));
				}
				Long tenantId=null;
				if(temp[7]!=null){
					tenantId = Long.parseLong(String.valueOf(temp[7]));
				}
				 
				
				if(tenantId!=null && tenantId > 0){
					Criteria ca = session.createCriteria(Organization.class);
					ca.add(Restrictions.eq("tenantId", tenantId));
					ca.add(Restrictions.eq("parentOrgId", -1L));
					ca.add(Restrictions.eq("state",SysStaticDataEnum.STS.VALID));
					ca.add(Restrictions.eq("tenantType", String.valueOf(SysStaticDataEnum.USER_TYPE.BUSINESS)));
					List<Organization> oo = (List<Organization>)ca.list();
					if(oo !=null && oo.size() == 1){
						cco.setBusiName(oo.get(0).getOrgName());
					}else{
						cco.setBusiName("");
					}
				}
				ccoList.add(cco);
			}
		}
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		page.setThisPageElements(ccoList);
		rtnMap=JsonHelper.parseJSON2Map(JsonHelper.json(new Pagination<Object[]>(page)));
		return rtnMap;
	}
	/**
	 * 新增发货人
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> doSaveCmCu(Map<String, String> inParam) throws Exception{
		/*Session session = SysContexts.getEntityManager();
		String name = DataFormat.getStringKey(inParam, "name");
		String linkmanName = DataFormat.getStringKey(inParam, "linkmanName");
		String bill = DataFormat.getStringKey(inParam, "bill");
		String telephone = DataFormat.getStringKey(inParam, "telephone");
		String address = DataFormat.getStringKey(inParam, "address");
		int state = SysStaticDataEnum.STS.VALID;
		long orgId = DataFormat.getLongKey(inParam,"orgId");
		int type = DataFormat.getIntKey(inParam,"type");
		int signingType = DataFormat.getIntKey(inParam, "signingType");
		long parentId = SysStaticDataEnum.PARENT_ID.PARENT;
		if(StringUtils.isEmpty(name)){
			throw new BusinessException("请输入发货人");
		}
		if(StringUtils.isEmpty(linkmanName)){
			throw new BusinessException("请输入联系人");
		}
		if(orgId == -1){
			throw new BusinessException("请输入类型");
		}
		CmCustomer cc = new CmCustomer();
		cc.setName(name);
		cc.setLinkmanName(linkmanName);
		cc.setBill(bill);
		cc.setTelephone(telephone);
		cc.setOrgId(orgId);
		cc.setType(type);
		cc.setState(state);
		cc.setParentId(parentId);
		cc.setAddress(address);
		if(signingType != 0 ){
			cc.setSigningType(signingType);
		}
		session.save(cc);*/
		Session session  = SysContexts.getEntityManager();
		CmCustomer cmCustomer = new CmCustomer();
		long id = DataFormat.getLongKey(inParam, "id");
		String linkmanName = DataFormat.getStringKey(inParam, "linkmanName");
		String bill = DataFormat.getStringKey(inParam, "bill");
		long orgId = DataFormat.getLongKey(inParam, "orgId");
		String telephone = DataFormat.getStringKey(inParam, "telephone");
		long tenantId = DataFormat.getLongKey(inParam,"tenantId");
		BaseUser baseUser = SysContexts.getCurrentOperator();
		if(id>0L){
			cmCustomer = (CmCustomer)session.get(CmCustomer.class, id);
		}else{
		     BeanUtils.populate(cmCustomer, inParam);
		}	
		cmCustomer.setBill(bill);
		Criteria ca  =  session.createCriteria(CmCustomer.class);
		if(cmCustomer.getId()<=0){
			ca.add(Restrictions.eq("linkmanName", cmCustomer.getLinkmanName()));
			if(!StringUtils.isEmpty(cmCustomer.getTelephone()) ){
			     ca.add(Restrictions.eq("telephone", cmCustomer.getTelephone()));
			}
			ca.add(Restrictions.eq("orgId", cmCustomer.getOrgId()));
			if(ca.list().size()>0){
				throw new BusinessException("该网点已有["+cmCustomer.getLinkmanName()+"]的信息");	
			}
		}else if(cmCustomer.getId()>0){
			if(((!StringUtils.isEmpty(cmCustomer.getTelephone()) && !cmCustomer.getTelephone().equals(telephone)) || !cmCustomer.getLinkmanName().equals(linkmanName) ||cmCustomer.getOrgId().longValue()!=orgId)){
				ca.add(Restrictions.eq("linkmanName", linkmanName));
				ca.add(Restrictions.eq("telephone", telephone));
				ca.add(Restrictions.eq("orgId", orgId));
				if(ca.list().size()>0){
					throw new BusinessException("该网点已有["+cmCustomer.getLinkmanName()+"]的信息");	
				}
			}
			
			 BeanUtils.populate(cmCustomer, inParam);
		}
		if(StringUtils.isEmpty(cmCustomer.getName())){
			if(cmCustomer.getType()==1){
				throw new BusinessException("请输入发货方");	
			}else{
				throw new BusinessException("请输入收货方");
			}
		}
		if(StringUtils.isEmpty(cmCustomer.getLinkmanName())){
			throw new BusinessException("请输入联系人");
		}
		if(cmCustomer.getType()==null || cmCustomer.getType()<=0){
			throw new BusinessException("请输入类型");	
		}
		if(tenantId < 0L){
			cmCustomer.setSigningType(SysStaticDataEnum.SIGNING_TYPE.SIGNING_NO);
			cmCustomer.setTenantId(null);//要不会保存－1
			cmCustomer.setZxTenantId(baseUser.getTenantId());
		}else {
			cmCustomer.setSigningType(SysStaticDataEnum.SIGNING_TYPE.SIGNING_IS);
		}
		CmCustomerSV cmCustomerSV = (CmCustomerSV)SysContexts.getBean("customerSV");
		cmCustomerSV.saveOrUpdateCustomer(cmCustomer);
		Map<String,String> map = new HashMap<String,String>();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 删除发货人/收货人
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> delCmCuById(Map<String, Object> inParam) throws Exception{
		Map<String,String> paramMap = new HashMap<String,String>();
		long id = DataFormat.getLongKey(inParam,"id");
		int state= SysStaticDataEnum.STS.NULLITY;
		if(id <=0){
			throw new BusinessException("请传入用户Id");	
		}
		Session session = SysContexts.getEntityManager();
		String sql = "update cm_customer set state = :state where id = :id";
		session.createSQLQuery(sql).setParameter("state", state).setParameter("id", id).executeUpdate();
		paramMap.put("info", "Y");
		return paramMap;
	}
	public CmCustomer getQueryCuCmById(Map<String, Object> inParam){
		Session session = SysContexts.getEntityManager(true);
		int state = SysStaticDataEnum.STS.VALID;
		long id = DataFormat.getLongKey(inParam,"id");
		CmCustomer cc = (CmCustomer) session.get(CmCustomer.class, id);
		if(cc.getState() != state){
			return null;
		}
		return cc;
	}
	
}
