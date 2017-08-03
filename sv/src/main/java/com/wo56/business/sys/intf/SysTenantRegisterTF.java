package com.wo56.business.sys.intf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.business.sys.impl.SysRoleSV;
import com.wo56.business.sys.impl.SysTenantRegisterSV;
import com.wo56.business.sys.vo.SysTenantRegister;

public class SysTenantRegisterTF {
	/**
	 * 登记物流公司信息
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> doSaveRegister(Map<String,Object> inParam) throws Exception{
		SysTenantRegisterSV sysTenantRegisterSV = (SysTenantRegisterSV) SysContexts.getBean("sysTenantRegisterSV");
		SysTenantRegister register = new SysTenantRegister();
		BeanUtils.populate(register, inParam);
		if(StringUtils.isEmpty(register.getLogisticsName())){
			throw new BusinessException("请输入物流公司名称！");
		}
		if(StringUtils.isEmpty(register.getLinkName())){
			throw new BusinessException("请输入联系人名称！");
		}
		if (StringUtils.isEmpty(register.getTelephone())) {
			throw new BusinessException("请输入联系人电话！");
		}
		if (StringUtils.isEmpty(register.getAddress())) {
			throw new BusinessException("请输入公司详细地址！");
		}
		sysTenantRegisterSV.doSave(register);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("info", "Y");
		return map;
	}
	
	/**
	 * 列表查询
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> querySysTenantRegister(Map<String,Object> inParam){
		SysTenantRegisterSV sysTenantRegisterSV = (SysTenantRegisterSV) SysContexts.getBean("sysTenantRegisterSV");
		Criteria ca = sysTenantRegisterSV.querySysTenantRegister(inParam);
		IPage<SysTenantRegister> p = PageUtil.gridPage(ca);
		Pagination<SysTenantRegister> page = new Pagination<SysTenantRegister>(p);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	
	/**
	 * 新增物流公司信息
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> addSysTenantRegister(Map<String,Object> inParam){
		SysTenantRegisterSV sysTenantRegisterSV = (SysTenantRegisterSV)SysContexts.getBean("sysTenantRegisterSV");
		long id = DataFormat.getLongKey(inParam,"id");
		Map paramMap = new HashMap();
		if(id >0){
			//调更新数据接口
			sysTenantRegisterSV.updateSysTenantRegister(inParam);
		}else{
			//新增
			sysTenantRegisterSV.addSysTenantRegister(inParam);
		}
		paramMap.put("info", "Y");
		return paramMap;
	}
	/**
	 * 删除物流公司信息
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> delSysTenantRegister(Map<String,Object> inParam){
		long id = DataFormat.getLongKey(inParam,"id");
		Map paramMap = new HashMap();
		if(id <=0){
			throw new BusinessException("请传入ID");	
		}
		SysTenantRegisterSV sysTenantRegisterSV = (SysTenantRegisterSV)SysContexts.getBean("sysTenantRegisterSV");
		sysTenantRegisterSV.delSysTenantRegister(id);
		paramMap.put("info", "Y");
		return paramMap;	
	}
	
	/**
	 * 按主键查询物流公司登记信息
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryById(Map<String,Object> inParam){
		SysTenantRegisterSV sysTenantRegisterSV = (SysTenantRegisterSV)SysContexts.getBean("sysTenantRegisterSV");
		long id = DataFormat.getLongKey(inParam,"id");
		Criteria ca = sysTenantRegisterSV.queryById(id);
		Map paramMap = new HashMap();
		List<SysTenantRegister> list = ca.list();
		if(list!=null && list.size()>0){
			SysTenantRegister register = new SysTenantRegister();
			register= list.get(0);
			paramMap.put("data",register);
		}	
		return paramMap;
	}
	
	
	
	
}
