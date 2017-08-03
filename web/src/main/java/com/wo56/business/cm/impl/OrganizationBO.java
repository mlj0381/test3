package com.wo56.business.cm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.MapInParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.intf.IOrganizationIntf;
import com.wo56.business.ord.intf.IOrganizationYunQiIntf;
import com.wo56.business.org.vo.Organization;
import com.wo56.business.org.vo.in.OrgCarriIn;
import com.wo56.business.org.vo.in.OrgQueryAllBusIn;
import com.wo56.business.org.vo.in.OrgQueryBusParamIn;
import com.wo56.business.org.vo.in.OrgQueryParamIn;
import com.wo56.business.org.vo.in.OrgQueryZXParamIn;
import com.wo56.business.org.vo.in.OrgRechargeParamIn;
import com.wo56.business.org.vo.in.OrgSaveBussParamIn;
import com.wo56.business.org.vo.in.OrgSaveParamIn;
import com.wo56.business.org.vo.in.OrgSaveZxParamIn;
import com.wo56.business.org.vo.in.OrgUpdateParamIn;
import com.wo56.business.org.vo.in.OrganizationIn;
import com.wo56.business.org.vo.in.QueryOrgIn;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.route.vo.in.RouteTowardsIn;
import com.wo56.common.consts.InterFacesCodeConsts;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

/**
 * 查询网点，租户信息
 * 
 * @author liyiye
 *
 */
public class OrganizationBO  extends BaseBO {

	/**
	 * lyh
	 * 查询租户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryCompanyList() throws Exception{
		
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryOrgIn  queryOrgIn= new QueryOrgIn(InterFacesCodeConsts.BASE.QUERY_ORG_IN);
		BeanUtils.populate(queryOrgIn, map);
		ListOutParamVO<Organization> out = CallerProxy.call(queryOrgIn, new TypeToken<ListOutParamVO<Organization>>(){}.getType());
	   
		List<Organization> list=out.getContent();
		List<Map<String,String>> retList=new ArrayList<Map<String,String>>(); 
		for(Organization org:list){
			if(StringUtils.isNotEmpty(CommonUtil.getTennatNameById(org.getTenantId()))){
				Map<String,String> orgMap=new HashMap<String, String>();
				orgMap.put("tenantId", org.getTenantId().toString());
				orgMap.put("tenantType", org.getTenantType());
				orgMap.put("orgName", CommonUtil.getTennatNameById(org.getTenantId()));
				orgMap.put("orgCode", CommonUtil.getTennatCodeById(org.getTenantId()));
				retList.add(orgMap);
			}
		}
		
		return JSONArray.fromObject((retList)).toString();
	}
	/**
	 * lyh
	 * 查询租户下面的网点列表
	 * 
	 * 查询当前登录的租户
	 * @return
	 * @throws Exception
	 */
	public String queryOrgList() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryOrgIn  queryOrgIn= new QueryOrgIn(InterFacesCodeConsts.BASE.QUERY_ORG_BY_TENNANTID);
		BaseUser baseUser=SysContexts.getCurrentOperator();
		long tenantId = DataFormat.getLongKey(map,"tenantId");
		if(tenantId < 0){
			queryOrgIn.setTenantId(baseUser.getTenantId().toString());
		}else{
			queryOrgIn.setTenantId(String.valueOf(tenantId));
		}
		ListOutParamVO<Organization> out = CallerProxy.call(queryOrgIn, new TypeToken<ListOutParamVO<Organization>>(){}.getType());
		List<Organization> list=out.getContent();
		Set<String> includeSet=new HashSet<String>();
		includeSet.add("orgId");
		includeSet.add("orgName");
		includeSet.add("tenantId");
		String retStr= JsonHelper.jsonInclude(list, includeSet);
		return retStr;
	}
	/***
	 * 保存专线信息
	 * @author 邱林锋
	 * @param doSaveSpecialLine
	 * */
	public String doSaveSpecialLine()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		//System.out.println("==============="+JsonHelper.json(map));
		OrgSaveZxParamIn paramIn = new OrgSaveZxParamIn();
		BeanUtils.populate(paramIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 专线查询
	 * @return
	 * @throws Exception
	 */
	public String queryOrgListByPage() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryZXParamIn queryOrgIn = new  OrgQueryZXParamIn(InterFacesCodeConsts.BASE.QUERY_ORGZX);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 拉包公司查询
	 * @return
	 * @throws Exception
	 */
	public String doQueryPullPagCompany()throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		IOrganizationIntf orgTF = CallerProxy.getSVBean(IOrganizationIntf.class,"organizationTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.doQueryPullPagCompany(map));
		
	}
	
	/**
	 * 保存拉包公司
	 */
	public String doSavePullPagCompany()throws Exception{
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		IOrganizationIntf orgTF = CallerProxy.getSVBean(IOrganizationIntf.class,"organizationTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.doSavePullPagCompany(map));
	}
	/**
	 * 根据id查询拉包公司信息
	 * @return
	 * @throws Exception
	 */
	public String queryByIdOrgCompanyList()throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		IOrganizationIntf orgTF = CallerProxy.getSVBean(IOrganizationIntf.class,"organizationTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.queryByIdOrgCompanyList(map));
	}
	/**
	 * 根据id修改拉包公司信息
	 * @return
	 * @throws Exception
	 */
	public String updateByIdOrgCompanyList()throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		IOrganizationIntf orgTF = CallerProxy.getSVBean(IOrganizationIntf.class,"organizationTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgTF.updateByIdOrgCompanyList(map));
	}
 
	/**
	 * 根据ID查询专线信息
	 * @return
	 * @throws Exception
	 */
	public String queryByIdOrgList() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgUpdateParamIn queryOrgIn = new  OrgUpdateParamIn(InterFacesCodeConsts.BASE.QUERYID_ORGZX);
		BeanUtils.populate(queryOrgIn, dataMap);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 根据ID修改专线信息
	 * @return
	 * @throws Exception
	 */
	public String updateByIdOrg() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		//System.out.println("==============="+JsonHelper.json(map));
		OrgUpdateParamIn paramIn = new OrgUpdateParamIn(InterFacesCodeConsts.BASE.UPDATA_ORGZX);
		BeanUtils.populate(paramIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 根据ID删除专线信息
	 * @return
	 * @throws Exception
	 */
	public String delByIdOrg() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgUpdateParamIn paramIn = new OrgUpdateParamIn(InterFacesCodeConsts.BASE.DEL_ORGZX);
		BeanUtils.populate(paramIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/***
	 * 保存商家信息
	 * @author 邱林锋
	 * @param doSaveBusiness
	 * */
	public String doSaveBusiness()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.SAVE_ORGBUSS);
		BeanUtils.populate(queryOrgIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 查询专线商家信息
	 * @author 邱林锋
	 * */
	public String doLinkQueryBusiness() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryBusParamIn queryOrgIn = new  OrgQueryBusParamIn(InterFacesCodeConsts.BASE.QUERY_ORGBUSS);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 查询平台商家信息
	 * @author 邱林锋
	 * */
	public String doQueryBusiness() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryBusParamIn queryOrgIn = new  OrgQueryBusParamIn(InterFacesCodeConsts.BASE.QUERY_ALLBUS);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 根据ID查询商家信息
	 * @return
	 * @throws Exception
	 */
	public String queryByIdBus() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new  OrgSaveBussParamIn(InterFacesCodeConsts.BASE.QUERYID_ORGBUSS);
		BeanUtils.populate(queryOrgIn, dataMap);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/**
	 * 根据ID查询商家信息
	 * @return
	 * @throws Exception
	 */
	public String queryByRelIdBus() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new  OrgSaveBussParamIn(InterFacesCodeConsts.BASE.QUERYID_ORGBUS_LINK);
		BeanUtils.populate(queryOrgIn, dataMap);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 修改商家信息
	 * @author 邱林锋
	 * @param doSaveBusiness
	 * */
	public String doUpdateBusiness() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.UPDATE_ORGBUSS);
		BeanUtils.populate(queryOrgIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 根据条件查询商家信息
	 * @return
	 * @throws Exception
	 */
	public String queryByAllBus() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryAllBusIn queryOrgIn = new OrgQueryAllBusIn(InterFacesCodeConsts.BASE.QUERYBYALL_ORGBUSS);
		BeanUtils.populate(queryOrgIn, dataMap);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 删除商家信息
	 * @author 邱林锋
	 * @param doDelBusiness
	 * */
	public String doDelBusiness()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn paramIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.DEL_ORGBUSS);
		BeanUtils.populate(paramIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	/**
	 * 查询所有专线名称
	 * @return
	 * @throws Exception
	 */
	public String queryByAllLink() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryAllBusIn queryOrgIn = new OrgQueryAllBusIn(InterFacesCodeConsts.BASE.QUERY_ALLLINK);
		BeanUtils.populate(queryOrgIn, dataMap);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 查询所有拉包公司
	 * @return
	 * @throws Exception
	 */
	public String queryByCompany() throws Exception{
		Map<String,Object>map=SysContexts.getRequestParamFlatMap();
		IOrganizationIntf orgSV = CallerProxy.getSVBean(IOrganizationIntf.class,"organizationTF" ,new TypeToken<Map<String,Object>>(){}.getType());
		return JsonHelper.json(orgSV.queryByCompany(map));
	}
	
	public String queryOrgan()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrganizationIn organ = new OrganizationIn();
		BeanUtils.populate(organ, map);
		//organ.setOrgId(1l);
		if(organ.getOrgId()==null || organ.getOrgId()<=0){
			throw new BusinessException("请输入网点编号");
		}
		String out = CallerProxy.call(organ);
		return out;
		
	}
	

	/**
	 * 查询网点
	 * @return
	 * @throws Exception
	 */
	public String queryRoateTowards() throws Exception{
		RouteTowardsIn route = new RouteTowardsIn();
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BeanUtils.populate(route, map);
		BaseUser baseUser = SysContexts.getCurrentOperator();
		route.setBeginOrgId(baseUser.getOrgId());
		ListOutParamVO<RouteTowards> out = CallerProxy.call(route, new TypeToken<ListOutParamVO<RouteTowards>>(){}.getType());
		List<RouteTowards> list = out.getContent();
		return JsonHelper.json(list);
		
	}
	
	/***
	 * 保存网点信息
	 * @author 曾建优
	 * @param OrgSaveParamIn
	 * */
	public String doSave()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveParamIn paramIn = new OrgSaveParamIn();
		BeanUtils.populate(paramIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(paramIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	
	/***
	 * 查询网点信息（l列表）
	 * @author 曾建优
	 * @param OrgSaveParamIn
	 * */
	public String doQuery()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgQueryParamIn query = new OrgQueryParamIn();
		BeanUtils.populate(query, map);
		PageOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/***
	 * 充值（l列表）
	 * @author 曾建优
	 * @param OrgSaveParamIn
	 * */
	public String recharge()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String money = DataFormat.getStringKey(map, "money");
		String orgId = DataFormat.getStringKey(map, "orgId");
		if(StringUtils.isEmpty(money)){
			throw new BusinessException("充值金额不能为空!");
		}
		OrgRechargeParamIn query = new OrgRechargeParamIn();
		Double offerD = Double.valueOf(money);
		long offerL=(long) (offerD*100);
		query.setMoney(offerL);
		query.setOrgId(orgId);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json(out.getContent());
	}
	
	/**
	 * 查询网点详情
	 * @return
	 * @throws Exception
	 */
	public String queryOrgDtl() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryOrgIn queryOrgIn = new QueryOrgIn(InterFacesCodeConsts.BASE.QUERY_ORG_IN);
		BeanUtils.populate(queryOrgIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 * 查询网点详情
	 * @return
	 * @throws Exception
	 */
	public String queryOrgDtllyh() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		QueryOrgIn queryOrgIn = new QueryOrgIn(InterFacesCodeConsts.BASE.QUERY_ORG_DEL);
		BeanUtils.populate(queryOrgIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/**
	 * 根据商家查询专线
	 * @return
	 * @throws Exception
	 */
	public String queryBusByLink() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.QUERY_BUSLINK);
		BeanUtils.populate(queryOrgIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
	    return JsonHelper.json(out.getContent());
	}
	/***
	 * 查询师傅合作商
	 * @author 邱林锋
	 * @param doQuerySFPartners
	 * */
	public String doQuerySFPartners() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryBusParamIn queryOrgIn = new  OrgQueryBusParamIn(InterFacesCodeConsts.BASE.QUERY_SFPARTNERS);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 查询师傅合作商
	 * @author 邱林锋
	 * @param doQuerySFPartners
	 * */
	public String doQuerySFPartnersExc() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryBusParamIn queryOrgIn = new  OrgQueryBusParamIn(InterFacesCodeConsts.BASE.QUERY_SFPARTNER_EXC);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 保存师傅合作商
	 * @author 邱林锋
	 * @param doSaveSFPartners
	 * */
	public String doSaveSFPartners()  throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.SVAEUPDATE_SFPARTNERS);
		BeanUtils.populate(queryOrgIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 根据条件查询师傅合作商信息
	 * @return
	 * @throws Exception
	 */
	public String queryBySFPartners() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryAllBusIn queryOrgIn = new OrgQueryAllBusIn(InterFacesCodeConsts.BASE.CHECK_SFPARTNERS);
		BeanUtils.populate(queryOrgIn, dataMap);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/**
	 * 查询所有合作商
	 * @return
	 * @throws Exception
	 */
	public String getCarri()throws Exception{
		OrgCarriIn route = new OrgCarriIn();
		ListOutParamVO<Organization> out = CallerProxy.call(route, new TypeToken<ListOutParamVO<Organization>>(){}.getType());
		List<Organization> list = out.getContent();
		return JsonHelper.json(list);
	}
	/**
	 * 查询专线的所有网点
	 * @return
	 * @throws Exception
	 */
	public String getAllOrg()throws Exception{
		OrgSaveBussParamIn route = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.QUERY_ORGAll);
		ListOutParamVO<Organization> out = CallerProxy.call(route, new TypeToken<ListOutParamVO<Organization>>(){}.getType());
		List<Organization> list = out.getContent();
		return JsonHelper.json(list);
	}
	
	/**
	 * 根据网点ID查询网点信息
	 * @return
	 * @throws Exception
	 */
	public String getOrganization()throws Exception{
		Map<String, String[]> inParam = SysContexts.getRequestParameterMap();
		long orgId = DataFormat.getLongKey(inParam,"orgId");//到达网点
		Organization organization = OraganizationCacheDataUtil.getOrganization(orgId);
		return JsonHelper.json(organization);
	}
	

	/**
	 * 查询租户对应的合作商
	 * @param tenantId 租户id
	 * @param provinceId 省份id
	 * @param cityId 城市id
	 * @return List<Map<String,Object>>
	 * 				Map<String,Object> key=name 服务商名称
	 * 								   key=phone 服务商手机
	 * 								   key=provinceName 省份名称
	 *    							   key=cityName 省份名称
	 *    							   key=sfUserID 商家租户id
	 *     							   key=userType=2 用户类型，商家为2 
	 * */
	public String querySFPartners()throws Exception{
		MapInParamVO inParamVO = new MapInParamVO(InterFacesCodeConsts.BASE.QUERY_COOP_BUSS, new HashMap());
		ListOutParamVO<Map> out = CallerProxy.call(inParamVO, new TypeToken<ListOutParamVO<Map>>(){}.getType());
		List<Map> list = out.getContent();
		return JsonHelper.json(list);
	}
	/**
	 * 保存与修改承运商
	 * @return
	 * @throws Exception
	 */
	public String saveCmC() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.SAVE_CMC);
		BeanUtils.populate(queryOrgIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 查询承运商
	 * @author 邱林锋
	 * @param doQuerySFPartners
	 * */
	public String doQueryCmC() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryBusParamIn queryOrgIn = new  OrgQueryBusParamIn(InterFacesCodeConsts.BASE.QUERY_CMC);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/**
	 * 删除承运商
	 */
	public String delCmC() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.DEL_CMC);
		BeanUtils.populate(queryOrgIn, map);
		//直接将后台的JSON串输出到前台
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	/***
	 * 查询专线合作商
	 * @author 邱林锋
	 * @param doQuerySFLink
	 * */
	public String doQuerySFLink() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		OrgQueryBusParamIn queryOrgIn = new  OrgQueryBusParamIn(InterFacesCodeConsts.BASE.QUERY_SF_LINK);
		BeanUtils.populate(queryOrgIn, dataMap);
		PageOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/***
	 * 查询服务商合作的合同公司
	 * @author zjy
	 * @param queryServiceOrg
	 * */
	public String queryServiceOrg() throws Exception{
		Map<String, String[]> dataMap = SysContexts.getRequestParameterMap();
		BaseUser user = SysContexts.getCurrentOperator();
		long provinceId = DataFormat.getLongKey(dataMap, "provinceId");
		long cityId = DataFormat.getLongKey(dataMap, "cityId");
		long tenantId =  DataFormat.getLongKey(dataMap, "tenantId");
		int isRootOrg =  DataFormat.getIntKey(dataMap, "isRootOrg");
		tenantId=user.getTenantId();
		String phone =  DataFormat.getStringKey(dataMap, "linkPhone");
		String name =  DataFormat.getStringKey(dataMap, "name");
		Map map = new HashMap();
		map.put("provinceId", provinceId);
		map.put("cityId", cityId);
		map.put("tenantId", tenantId);
		map.put("sfUserAcct", phone);
		map.put("sfUserName", name);
		map.put("isRootOrg", isRootOrg);
		MapInParamVO inParamVO = new MapInParamVO(InterFacesCodeConsts.BASE.QUERY_SERVICE_ORG, map);
		PageOutParamVO<Map> out = CallerProxy.call(inParamVO, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	/**
	 * 查询当前网点信息
	 * @return
	 * @throws Exception
	 */
	public String getOrgInfo() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrgSaveBussParamIn queryOrgIn = new OrgSaveBussParamIn(InterFacesCodeConsts.BASE.GET_CURR_ORG);
		BeanUtils.populate(queryOrgIn, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		return JsonHelper.json( out.getContent());
	}
	
	
	
	/**
	 * 获取详情
	 * @return
	 * @throws Exception
	 */
	public String selectOrgByLink() throws Exception {
		Map<String, Object> inParam = SysContexts.getRequestParamFlatMap();
		IOrganizationYunQiIntf intf = CallerProxy.getSVBean(IOrganizationYunQiIntf.class, 
				"organizationYunQiTF", new TypeToken<List<Map<String,Object>>>(){}.getType());
		List<Map<String,String>> list = intf.selectOrgByLink(inParam);
		
		return JsonHelper.json(list);
	}
	
	/**
	 * 删除网点
	 * @return
	 * @throws Exception 
	 */
	public String delOrganization() throws Exception{
		Map<String,Object> map = SysContexts.getRequestParamFlatMap();
		IOrganizationYunQiIntf intf = CallerProxy.getSVBean(IOrganizationYunQiIntf.class, "organizationYunQiTF", new TypeToken<String>(){}.getType());
		return intf.delOrganization(map);
	}
	
	/**
	 * 删除网点
	 * @return
	 */
	public String chackOrgByUser(){
		Map<String,Object> map = SysContexts.getRequestParamFlatMap();
		IOrganizationYunQiIntf intf = CallerProxy.getSVBean(IOrganizationYunQiIntf.class, "organizationYunQiTF", new TypeToken<String>(){}.getType());
		return intf.chackOrgByUser(map);
	} 
}
