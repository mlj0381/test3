package com.wo56.business.cm.intf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import com.framework.core.SysContexts;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.BeanUtils;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.wo56.business.cm.impl.CmAreaSV;
import com.wo56.business.cm.impl.ICmAreaIntf;
import com.wo56.business.cm.vo.CmArea;

public class CmAreaTF implements ICmAreaIntf{
	/**
	 * 保存与修改
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public String doSaveOrUpdateArea(Map<String, Object> inParam) throws Exception{
		CmAreaSV cmAreaSV = (CmAreaSV) SysContexts.getBean("cmAreaSV");
		CmArea cmArea = new CmArea();
		long areaId = DataFormat.getLongKey(inParam,"id");
		if (areaId > 0) {
			cmArea = cmAreaSV.getCmAreaById(areaId);
		}
		BeanUtils.populate(cmArea, inParam);
		cmAreaSV.doSaveOrUpdate(cmArea);
		return "Y";
	}
	/**
	 * 列表查询
	 * @param inParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getCmAreaList(Map<String,Object> inParam){
		CmAreaSV cmAreaSV = (CmAreaSV) SysContexts.getBean("cmAreaSV");
		Criteria ca = cmAreaSV.getCmAreaList(inParam);
		IPage<CmArea> p = PageUtil.gridPage(ca);
		Pagination<CmArea> page = new Pagination<CmArea>(p);
		return JsonHelper.parseJSON2Map(JsonHelper.json(page));
	}
	/**
	 * 通过id查询
	 * @param inParam
	 * @return
	 */
	public Map<String,Object> getCmArea(Map<String,Object> inParam){
		CmAreaSV cmAreaSV = (CmAreaSV) SysContexts.getBean("cmAreaSV");
		long areaId = DataFormat.getLongKey(inParam, "id");
		return JsonHelper.parseJSON2Map(JsonHelper.json(cmAreaSV.getCmAreaById(areaId)));
	}
	/**
	 * 删除
	 * @param inParam
	 * @return
	 */
	public String delCmArea(Map<String,Object> inParam){
		String ids = DataFormat.getStringKey(inParam, "ids");
		String[] id = ids.split(",");
		CmAreaSV cmAreaSV = (CmAreaSV) SysContexts.getBean("cmAreaSV");
		return cmAreaSV.delCmArea(id);
	}
	/**
	 * 下单关键字关联经纬度
	 * @param province
	 * @param city
	 * @param district
	 * @param address
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> getCmAreaByCityOrAddress(long province,long city,long district,String address){
		Map<String,String> map = new HashMap<String, String>();
		if (province < 0) {
			return map;
		}
		if (city < 0) {
			return map;
		}
		if (district < 0) {
			return map;
		}
		CmAreaSV cmAreaSV = (CmAreaSV) SysContexts.getBean("cmAreaSV");
		List<CmArea> list = cmAreaSV.getCmAreaByCityOrAddress(province, city, district).list();
		for (CmArea cmArea : list) {
			if (cmArea.getKeyWords().indexOf(address) != -1) {
				map.put("latitude", cmArea.getLatitude());
				map.put("longitude", cmArea.getLongitude());
			}
		}
		return map;
	} 
}
