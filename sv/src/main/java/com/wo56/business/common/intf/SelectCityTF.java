package com.wo56.business.common.intf;

import java.util.List;
import java.util.Map;

import com.framework.components.citys.City;
import com.framework.components.citys.District;
import com.framework.components.citys.Province;
import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.util.DataFormat;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysStaticDataUtil;

public class SelectCityTF {
	
	/**
	 * 查询省份
	 * @return
	 * @throws Exception
	 */
	public String doQueryProvices() throws Exception {
		List<Province> lists = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE");
		return JsonHelper.json(lists);
	}
	
	/**
	 * 根据省份ID获取省份信息
	 * @param provId
	 * @return
	 * @throws Exception
	 */
	public String getProvices() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long provicesId = DataFormat.getLongKey(map, "provicesId");
		if(provicesId<1){
			throw new BusinessException("没有省份ID！");
		}
		List<Province> lists = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE");
		Province province=null;
		if(lists != null && lists.size()>0){
			for(int i=0;i<lists.size();i++){
				province = lists.get(i);
				if(province.getId()==provicesId){
					break;
				}
			}
		}
		return JsonHelper.json(province);
	}
	/**
	 * 查询地市
	 * @return
	 * @throws Exception
	 */
	public String doQueryRegion() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String provicesId = DataFormat.getStringKey(map, "provicesId");
		List<City> lists = SysStaticDataUtil.getCityData("SYS_CITY", provicesId);
		return JsonHelper.json(lists);
	}
	
	/**
	 * 根据地市ID获取地市信息
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public String getRegion() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long provicesId = DataFormat.getLongKey(map, "provicesId");
		long regionId = DataFormat.getLongKey(map, "regionId");
		if(provicesId<1){
			throw new BusinessException("没有省份ID！");
		}
		if(regionId<1){
			throw new BusinessException("没有地市ID！");
		}
		List<City> lists = SysStaticDataUtil.getCityData("SYS_CITY", String.valueOf(provicesId));
		City city=null;
		if(lists != null && lists.size()>0){
			for(int i=0;i<lists.size();i++){
				city = lists.get(i);
				if(city.getId()==regionId){
					break;
				}
			}
		}
		return JsonHelper.json(city);
	}
	
	/**
	 * 查询县区
	 * @return
	 * @throws Exception
	 */
	public String doQueryCounty() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String cityId = DataFormat.getStringKey(map, "cityId");
		List<District> lists = SysStaticDataUtil.getDistrictData("SYS_DISTRICT", cityId);
		return JsonHelper.json(lists);
	}
	
	
	/**
	 * 根据县区ID获取县区信息
	 * @return
	 * @throws Exception
	 */
	public String getCounty() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long cityId = DataFormat.getLongKey(map, "cityId");
		long districtId = DataFormat.getLongKey(map, "districtId");
		if(cityId<1){
			throw new BusinessException("没有地市ID！");
		}
		if(districtId<1){
			throw new BusinessException("没有县区ID！");
		}
		List<District> lists = SysStaticDataUtil.getDistrictData("SYS_DISTRICT", String.valueOf(cityId));
		District district=null;
		if(lists != null && lists.size()>0){
			for(int i=0;i<lists.size();i++){
				district = lists.get(i);
				if(district.getId()==districtId){
					break;
				}
			}
		}
		return JsonHelper.json(district);
	}
	
	public String getProvince() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String codeValue = DataFormat.getStringKey(map, "codeValue");
		Province province=SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", codeValue);
		return JsonHelper.json(province);
	}
	
	public String getDistrict() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String codeValue = DataFormat.getStringKey(map, "codeValue");
		District district=SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", codeValue);
		return JsonHelper.json(district);
	}
	
	public String getCity() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String codeValue = DataFormat.getStringKey(map, "codeValue");
		City city=SysStaticDataUtil.getCityDataList("SYS_CITY", codeValue);
		return JsonHelper.json(city);
	}
	
	/**
	 * 查询全国所有城市
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public List<City> getAllCity(Map<String,Object> inParam)throws Exception{
		List<City> cityDataList = SysStaticDataUtil.getCityDataList("SYS_CITY");
		return cityDataList;
	}
}
