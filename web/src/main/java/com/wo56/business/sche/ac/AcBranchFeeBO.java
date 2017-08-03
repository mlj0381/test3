package com.wo56.business.sche.ac;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.SysContexts;
import com.framework.core.base.BaseBO;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.svcaller.vo.PageOutParamVO;
import com.framework.core.svcaller.vo.SimpleOutParamVO;
import com.framework.core.util.JsonHelper;
import com.google.gson.reflect.TypeToken;
import com.wo56.business.sche.ac.vo.in.AcBranchDeleteParamIn;
import com.wo56.business.sche.ac.vo.in.AcBranchQueryParamIn;
import com.wo56.business.sche.ac.vo.in.AcBranchSaveParamIn;
import com.wo56.business.sche.ac.vo.in.AcBranchViewParamIn;

/***
 * @author zjy
 * 支线费用管理
 * */
public class AcBranchFeeBO extends BaseBO {
	private static final Log log = LogFactory.getLog(AcBranchFeeBO.class);
	/**
	 * 查询支线费用定义列表
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doQuery()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcBranchQueryParamIn query = new AcBranchQueryParamIn();
		BeanUtils.populate(query, map);
		PageOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<PageOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
	}
	
	/**
	 * 支线费用新增或者修改
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doSave()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcBranchSaveParamIn query = new AcBranchSaveParamIn();
		BeanUtils.populate(query, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
		
	}
	
	
	/**
	 * 支线费用详情定义列表
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String toView()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcBranchViewParamIn query = new AcBranchViewParamIn();
		BeanUtils.populate(query, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
		
	}
	
	/**
	 * 支线费用删除
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doDelete()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		AcBranchDeleteParamIn query = new AcBranchDeleteParamIn();
		BeanUtils.populate(query, map);
		SimpleOutParamVO<Map> out = CallerProxy.call(query, new TypeToken<SimpleOutParamVO<Map>>(){}.getType());
		if(out.getContent() != null){
			return JsonHelper.json(out.getContent());
		}else{
			return null;
		}
	}

}
