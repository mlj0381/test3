package com.wo56.business.ord;

import java.util.Map;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.svcaller.CallerProxy;
import com.framework.core.util.BeanUtils;
import com.wo56.business.ord.vo.in.OrdOrgCostIn;
/**
 * 
 * @author Administrator
 *
 */
public class OrdOrgCostBO {
	public String queryOrdOrgCost() throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		OrdOrgCostIn ordOrg = new OrdOrgCostIn();
		BeanUtils.populate(ordOrg, map);
		if(ordOrg.getOrderId()!=null || ordOrg.getOrderId()<=0){
			throw new BusinessException("请输入订单编号");
		}
		if(ordOrg.getOrgId()!=null || ordOrg.getOrgId()<=0){
			throw new BusinessException("请输入网点编号");
		}
		String rtn = CallerProxy.call(ordOrg);
		return rtn;
	}
	
	public static void main(String[] args) throws Exception {
		OrdOrgCostBO outBo = new OrdOrgCostBO();
		System.out.println(outBo.queryOrdOrgCost());
	}

}
