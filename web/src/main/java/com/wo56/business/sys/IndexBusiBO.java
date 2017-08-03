package com.wo56.business.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.ListOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
//import com.wo56.business.sys.vo.SysShortcut;
//import com.wo56.business.sys.vo.in.AddShortcut;
import com.wo56.business.sys.vo.in.DelShortCut;
import com.wo56.business.sys.vo.in.IndexAmountIn;
import com.wo56.business.sys.vo.in.IndexElementIn;
import com.wo56.business.sys.vo.in.IndexShortcutIn;
import com.wo56.business.sys.vo.in.NewDetailIn;
import com.wo56.business.sys.vo.in.NewsIn;


public class IndexBusiBO {
	
	/**
	 * 查询首页的帐户信息
	 * 
	 * @return
	 *       Map
	 *          prePay 预付款金额，单位分
	 *          amount 代收货款，单位分
	 * @throws Exception
	 */
	public String getAmount() throws Exception{
		BaseUser user= SysContexts.getCurrentOperator();
		Long orgId=user.getOrgId();
		IndexAmountIn indexAmountIn=new IndexAmountIn();
		indexAmountIn.setOrgId(orgId);
		SimpleOutParamVO<Map> simpleOutParamVO = CallerProxy.call(indexAmountIn,new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		Map retMap=simpleOutParamVO.getContent();
		return JsonHelper.json(retMap);
	}
	/**
	 * TODO 从数据库获取数据
	 * 获取快捷方式
	 * @return
	 * @throws Exception 
	 */
//	public String getshortcutList() throws Exception{
//
//		BaseUser user= SysContexts.getCurrentOperator();
//		Long userId=user.getUserId();
//		IndexShortcutIn elementIn=new IndexShortcutIn();
//		elementIn.setUserId(userId);
//		ListOutParamVO<SysShortcut> listOutParamVO = CallerProxy.call(elementIn,new TypeToken<ListOutParamVO<SysShortcut>>(){}.getType());
//		List<SysShortcut> list=listOutParamVO.getContent();
//		if(list==null){
//			return JsonHelper.json(new ArrayList());
//		}
//		return JsonHelper.json(list);
//	}
	/**
	 * 查询首页的配置元素
	 * 
	 * 
	 * @return
	 * 		 List
	 *           label
	 *           params
	 *           width
	 *           title
	 *           
	 * @throws Exception
	 */
	public String getIndexElement() throws Exception{
		BaseUser user= SysContexts.getCurrentOperator();
		Long userId=user.getUserId();
		IndexElementIn elementIn=new IndexElementIn();
		elementIn.setUserId(userId);
		
		ListOutParamVO<Map> listOutParamVO = CallerProxy.call(elementIn,new TypeToken<ListOutParamVO<Map>>(){}.getType());
		List<Map> list=listOutParamVO.getContent();
		if(list==null){
			return "";
		}
		return JsonHelper.json(list);
	}
	
	
	/**查询新闻动态**/
	public String queryNews() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		NewsIn news = new NewsIn(); 
		
		BeanUtils.populate(news, map);
		news.set_GRID_TYPE("jq");
		SimpleOutParamVO<Map> listOutParamVO = CallerProxy.call(news,new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		Map retMap=listOutParamVO.getContent();
		return JsonHelper.json(retMap);
	}
	/**
	 * 查询新闻明细
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryNewsDetail() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		NewDetailIn news = new NewDetailIn(); 
		BeanUtils.populate(news, map);
		SimpleOutParamVO<Map> listOutParamVO = CallerProxy.call(news,new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		Map retMap=listOutParamVO.getContent();
		return JsonHelper.json(retMap);
	}
	
	
	/**
	 * 新增快捷栏
	 * @return
	 * @throws Exception
	 */
//	public String addShortcuts() throws Exception {
//		Map<String, String[]> map = SysContexts.getRequestParameterMap();
//		SysShortcut sysShortcut = new SysShortcut();
//		BaseUser baseUser = SysContexts.getCurrentOperator();
//		sysShortcut.setUserId(baseUser.getUserId());
//		BeanUtils.populate(sysShortcut, map);
//		AddShortcut add = new AddShortcut(); 
//		add.setSysShortcut(sysShortcut);
//		SimpleOutParamVO<Map> listOutParamVO = CallerProxy.call(add,new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
//		Map retMap=listOutParamVO.getContent();
//		return JsonHelper.json(retMap);
//	}
	
	/**
	 * 删除快捷方式
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String delShortcuts()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		DelShortCut del = new DelShortCut(); 
		BeanUtils.populate(del, map);
		BaseUser baseUser = SysContexts.getCurrentOperator();
		del.setUserId(baseUser.getUserId());
		SimpleOutParamVO<Map> listOutParamVO = CallerProxy.call(del,new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		Map retMap=listOutParamVO.getContent();
		return JsonHelper.json(retMap);
	}
	
}
