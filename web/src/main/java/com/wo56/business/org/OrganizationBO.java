package com.wo56.business.org;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.cm.intf.IOrganizationIntf;
import com.wo56.business.org.vo.in.OrgQueryParamIn;
import com.wo56.business.org.vo.in.OrgRechargeParamIn;
import com.wo56.business.org.vo.in.OrgSaveParamIn;
import com.wo56.business.org.vo.in.OrganizationIn;
import com.wo56.business.route.vo.RouteTowards;
import com.wo56.business.route.vo.in.RouteTowardsIn;

@Deprecated
public class OrganizationBO {
	
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
//		Map<String, String[]> map = SysContexts.getRequestParameterMap();
//		QueryOrgIn queryOrgIn = new QueryOrgIn();
//		BeanUtils.populate(queryOrgIn, map);
//		SimpleOutParamVO<Map> out = CallerProxy.call(queryOrgIn, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
//	    return JsonHelper.json(out.getContent());
		return "";
	}
	
	
	
}
