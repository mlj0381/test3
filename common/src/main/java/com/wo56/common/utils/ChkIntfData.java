package com.wo56.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.framework.components.citys.City;
import com.framework.components.citys.District;
import com.framework.components.citys.Province;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.util.SysStaticDataUtil;

/**
 * 公用检查接口数据类
 * 
 * @author wangbq
 * 
 */
public class ChkIntfData {

	/**
	 * 检查省份信息是否正确
	 * 
	 * @param provinceId
	 * @return
	 * @throws Exception
	 */
	public static boolean chkProvince(int provinceId) throws Exception {
		SysStaticData sysStaticData =SysStaticDataUtil.getSysStaticData("IS_CHK_INTF_DATA", "0");
		if(sysStaticData != null && sysStaticData.getCodeType()!=null && !sysStaticData.getCodeType().equals("")){
			if (provinceId > 0) {
				Province province = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId + "");
				if (province != null && province.getId() >0) {
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	
	/**
	 * 检查地市信息是否正确
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public static boolean chkRegion(int regionId) throws Exception {
		SysStaticData sysStaticData =SysStaticDataUtil.getSysStaticData("IS_CHK_INTF_DATA", "0");
		if(sysStaticData != null && sysStaticData.getCodeType()!=null && !sysStaticData.getCodeType().equals("")){
			if (regionId > 0) {
				City city = SysStaticDataUtil.getCityDataList("SYS_CITY", regionId + "");
				if (city != null && city.getId() >0) {
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	
	/**
	 * 检查县区信息是否正确
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public static boolean chkDistrict(int regionId) throws Exception {
		SysStaticData sysStaticData =SysStaticDataUtil.getSysStaticData("IS_CHK_INTF_DATA", "0");
		if(sysStaticData != null && sysStaticData.getCodeType()!=null && !sysStaticData.getCodeType().equals("")){
			if (regionId > 0) {
				District district = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", regionId + "");
				if (district != null && district.getId() >0) {
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 检查货物类型是否正确
	 * @param goodsType
	 * @return
	 * @throws Exception
	 */
	public static boolean chkGoodsType(int goodsType) throws Exception {
		SysStaticData sysStaticData =SysStaticDataUtil.getSysStaticData("IS_CHK_INTF_DATA", "0");
		if(sysStaticData != null && sysStaticData.getCodeType()!=null && !sysStaticData.getCodeType().equals("")){
			SysStaticData sysStatic = SysStaticDataUtil.getSysStaticData("GOODS_TYPE", goodsType + "");
			if (sysStatic != null && sysStatic.getCodeType() != null) {
				return true;
			}
		}else{
			return true;
		}
		return false;
	}
	
	/**
	 * 检查车辆类型是否正确
	 * @param vehicleStatus
	 * @return
	 * @throws Exception
	 */
	public static boolean chkVehicleStatus(int vehicleStatus) throws Exception {
		SysStaticData sysStaticData =SysStaticDataUtil.getSysStaticData("IS_CHK_INTF_DATA", "0");
		if(sysStaticData != null && sysStaticData.getCodeType()!=null && !sysStaticData.getCodeType().equals("")){
			SysStaticData sysStatic = SysStaticDataUtil.getSysStaticData("VEHICLE_TYPE", vehicleStatus + "");
			if (sysStatic != null && sysStatic.getCodeType() != null) {
				return true;
			}
		}else{
			return true;
		}
		return false;
	}
	
	
	
	
	
	/**
	 * 检查整车吨位是否正确
	 * @param vehicleLoad
	 * @return
	 * @throws Exception
	 */
	public static boolean chkVehicleLoad(int vehicleLoad) throws Exception {
		SysStaticData sysStaticData =SysStaticDataUtil.getSysStaticData("IS_CHK_INTF_DATA", "0");
		if(sysStaticData != null && sysStaticData.getCodeType()!=null && !sysStaticData.getCodeType().equals("")){
			SysStaticData sysStatic = SysStaticDataUtil.getSysStaticData("VEHICLE_LOAD", vehicleLoad + "");
			if (sysStatic != null && sysStatic.getCodeType() != null) {
				return true;
			}
		}else{
			return true;
		}
		return false;
	}
	
	/**
	 * 检查车牌是否正确
	 * @param plateNumber
	 * @return
	 * @throws Exception
	 */
	public static boolean chkPlateNumber(String plateNumber) throws Exception {
		String chk1="^[\u4e00-\u9fa5]{1}[A-Z_0-9]{6}$";
		String chk14="^[\u4e00-\u9fa5]{1}[A-Z_0-9]{5}[\u4e00-\u9fa5]{1}$";
		String chk2="^[A-Z_0-9]{5}[\u4e00-\u9fa5]{1}$";
		String chk3="^[A-Z]{2}[A-Z_0-9]{7}$";
		String chk4="^[A-Z]{2}[-]{1}[0-9]{5}$";
		String chk5="^[A-Z]{2}[0-9]{5}$";
		String chk6="^[A-Z]{2}[A-Z_0-9]{5}$";
		String chk7="^[A-Z]{2}[\u4e00-\u9fa5]{1}[A-Z_0-9]{4}$";
		String chk8="^[A-Z]{2}[0-9]{8}$";
		String chk9="^[A-Z]{2}[\u4e00-\u9fa5]{1}[A-Z_0-9]{5}$";
		String chk10="^[A-Z]{2}[\u4e00-\u9fa5]{1}[0-9]{5}$";
		String chk11="^[A-Z]{2}[0-9]{5}$";
		String chk12="^[A-Z]{2}[0-9]{7}$";
		String chk13="^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$";
		
		String [] chk= new String[]{chk1,chk14,chk2,chk3,chk4,chk5,chk6,chk7,chk8,chk9,chk10,chk11,chk12,chk13};
		for(int i=0;i<chk.length;i++){
			Pattern pat = Pattern.compile(chk[i]);
			Matcher mat = pat.matcher(plateNumber);
			if(mat.matches()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查用户类型
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	public static boolean chkUserType(int userType) throws Exception {
		SysStaticData sysStaticData =SysStaticDataUtil.getSysStaticData("IS_CHK_INTF_DATA", "0");
		if(sysStaticData != null && sysStaticData.getCodeType()!=null && !sysStaticData.getCodeType().equals("")){
			SysStaticData sysStatic = SysStaticDataUtil.getSysStaticData("USER_TYPE", userType + "");
			if (sysStatic != null && sysStatic.getCodeType() != null) {
				return true;
			}
		}else{
			return true;
		}
		return false;
	}
}
