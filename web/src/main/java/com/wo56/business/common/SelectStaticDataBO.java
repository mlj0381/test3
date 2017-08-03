
package com.wo56.business.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.components.citys.City;
import com.framework.components.citys.District;
import com.framework.components.citys.Province;
import com.framework.components.citys.Street;
import com.framework.core.SysContexts;
import com.framework.core.cache.CacheFactory;
import com.framework.core.cache.impl.SysStaticDataCache;
import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.util.DataFormat;
import com.framework.core.util.DateUtil;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.SysCfgUtil;
import com.framework.core.util.SysStaticDataUtil;
//import com.wo56.business.ac.vo.AcFeeConfig;
import com.wo56.business.org.vo.Organization;
import com.wo56.common.consts.EnumConsts;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.consts.SysStaticDataEnumYunQi;
import com.wo56.common.sms.utils.SysTenantExtendCacheUtil;
//import com.wo56.common.utils.AcFeeCacheDataUtil;
import com.wo56.common.utils.CommonUtil;
import com.wo56.common.utils.OraganizationCacheDataUtil;

public class SelectStaticDataBO {

	
	private static final Log log = LogFactory.getLog(SelectStaticDataBO.class);

	/**
	 * 通过cityId，反向加载省份和统一省份的其它第十信息
	 * @return
	 * @throws Exception
	 */
	public String revSelectCity() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long cityId = DataFormat.getLongKey(map, "revSelectCity");
		City city = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cityId));
		List<City> allCitylist = SysStaticDataUtil.getCityDataList("SYS_CITY");
		List<Province> provinceList = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE");
		List<City> provinceCityList = new ArrayList<City>();
		for (City c : allCitylist) {
			if (c.getProvId() == city.getProvId()) {
				provinceCityList.add(c);
			}
		}
		JSONObject ret = new JSONObject();
		ret.put("city", city);
		ret.put("provinceList", provinceList);
		ret.put("provinceCityList", provinceCityList);
		return ret.toString();
	}
	
	/**
	 * 反转地址信息
	 * @return
	 * @throws Exception
	 */
	public String revAddressInfo() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long streetId = DataFormat.getLongKey(map, "streetId");
		Street street = null;
		if (streetId > 0) {
			street = SysStaticDataUtil.getStreetDataList("SYS_STREET", String.valueOf(streetId));
		}
		long districtId = DataFormat.getLongKey(map, "districtId");
		if (street != null&&street.getDistrictId()!=null) {// 街道信息
			districtId = street.getDistrictId();// 曲线信息
		}
		District district = null;
		if (districtId > 0) {// 区县信息
			district = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(districtId));
		}
		
		long cityId = DataFormat.getLongKey(map, "cityId");
		if (null != district) {
			cityId = district.getCityId();
		}
		City city = null;
		if (cityId > 0) {// 市
			city = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cityId));
		}
		
		long provinceId = DataFormat.getLongKey(map, "provinceId");
		if (null != city) {
			provinceId = city.getProvId();
		}
		Province province = null;
		if (provinceId > 0) {
			province = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(provinceId));
		}

		Map retMap = new HashMap();
		if (street != null) {
			retMap.put("streetId", street.getId());
			retMap.put("streetName", street.getName());
		}
		if (district != null) {
			retMap.put("districtId", district.getId());
			retMap.put("districtName", district.getName());
		}
		if (city != null) {
			retMap.put("cityId", city.getId());
			retMap.put("cityName", city.getName());
		}
		if (province != null) {
			retMap.put("provinceId", province.getId());
			retMap.put("provinceName", province.getName());
		}
		return JsonHelper.json(retMap);
	}
	
	
	/**
	 * 查询所有城市
	 * @return
	 * @throws Exception
	 */
	public String selectCity()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String isAll = DataFormat.getStringKey(map,"isAll");
		long provinceId = DataFormat.getLongKey(map, "provinceId");
		List<City> cityList = new ArrayList<City>();
		List<City> list = SysStaticDataUtil.getCityDataList("SYS_CITY");
		if (StringUtils.isNotEmpty(isAll) && "Y".equals(isAll)) {
			City city = new City();
			city.setId(-1);
			city.setName("全部");
			cityList.add(city);
		}
		if(provinceId>0){
			for (City city : list) {
				if(city.getProvId()==provinceId){
					cityList.add(city);
				}
			}
		}
//		else{
//			cityList.addAll(list);
//		}
		return JsonHelper.json(cityList);
	}
	
	public String queryCityDetailInfo() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long cityId = DataFormat.getLongKey(map, "cityId");
		if (cityId <= 0) {
			return StringUtils.EMPTY;
		}
		City city = SysStaticDataUtil.getCityDataList("SYS_CITY", String.valueOf(cityId));
		if (null == city) {
			return StringUtils.EMPTY;
		}
		
		Map retMap = new HashMap();
		retMap.put("cityId", city.getId());
		retMap.put("cityName", city.getName());
		Province province = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", String.valueOf(city.getProvId()));
		if (null != province) {
			retMap.put("provinceId", province.getId());
			retMap.put("provinceName", province.getName());
		}
		return JsonHelper.json(retMap);
	}
	
	/**
	 * 查询所有省份
	 * @return
	 * @throws Exception
	 */
	public String selectProvince()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String isAll = DataFormat.getStringKey(map,"isAll");
		List<Province> provinceList = new ArrayList<Province>();
		List<Province> list = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE");
		if (StringUtils.isNotEmpty(isAll) && "Y".equals(isAll)) {
			Province province = new Province();
			province.setId(-1);
			province.setName("全部");
			provinceList.add(province);
		}
		provinceList.addAll(list);
		return JsonHelper.json(provinceList);
	}
	
	/**
	 * 查询所有县区
	 * @return
	 * @throws Exception
	 */
	public String selectDistrict()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String isAll = DataFormat.getStringKey(map,"isAll");
		long cityId = DataFormat.getLongKey(map, "cityId");
		List<District> districtList = new ArrayList<District>();
		List<District> list = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT");
		if (StringUtils.isNotEmpty(isAll) && "Y".equals(isAll)) {
			District district = new District();
			district.setId(-1);
			district.setName("全部");
			districtList.add(district);
		}
		if(cityId>0){
			for (District district : list) {
				if(district.getCityId()==cityId){
					districtList.add(district);
				}
			}
		}
//		else{
//			districtList.addAll(list);
//		}
		return JsonHelper.json(districtList);
	}
	
	/**
	 * 查询所有街道
	 * @return
	 * @throws Exception
	 */
	public String selectStreet()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String isAll = DataFormat.getStringKey(map,"isAll");
		long districtId = DataFormat.getLongKey(map, "districtId");
		List<Street> districtList = new ArrayList<Street>();
		List<Street> list = SysStaticDataUtil.getStreetDataList("SYS_STREET");
				
		if (StringUtils.isNotEmpty(isAll) && "Y".equals(isAll)) {
			Street district = new Street();
			district.setId(-1);
			district.setName("全部");
			districtList.add(district);
		}
		if (districtId > 0) {
			if(list!=null && list.size() > 0){
			for (Street district : list) {
				if (district.getDistrictId() == districtId) {
					districtList.add(district);
				}
			}
			}
		}else{
			districtList.addAll(list);
		}
		return JsonHelper.json(districtList);
	}
	
	
	/**
	 * 查询货物类型
	 * @return
	 * @throws Exception
	 */
	public String selectGoodsType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.GOODS_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 获取包装类型
	 * @return
	 * @throws Exception
	 */
	public String selectPackType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.PACKING_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 获取计费方式
	 * @return
	 * @throws Exception
	 */
	public String selectBillingType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.BILLING_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 获取常见品名
	 * @return
	 * @throws Exception
	 */
	public String loadCommonGoodsName()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.COMMON_TYPE_GOODS_NAME);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 获取注意事项
	 * @return
	 * @throws Exception
	 */
	public String selectNotesName()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.NOTES);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 运输方式
	 * @return
	 * @throws Exception
	 */
	public String selectTransportType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.TRANSPORT_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 交接方式
	 * @return
	 * @throws Exception
	 */
	public String selectDeliveryType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.DELIVERY_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	/**
	 * 查询方式
	 * @return
	 * @throws Exception
	 */
	public String selectQueryType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.QUERY_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 付款方式
	 * @return
	 * @throws Exception
	 */
	public String selectPaymentType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.PAYMENT_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	
	/**
	 * 车辆状态
	 * @return
	 * @throws Exception
	 */
	public String selectVehicleState()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String isAll = DataFormat.getStringKey(map, "isAll");
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.VEHICLE_STATE);
		if(StringUtils.isNotEmpty(isAll)){
			SysStaticData staticData = new SysStaticData();
			staticData.setCodeValue("-1");
			staticData.setCodeName("所有");
			sysStaticData.set(0, staticData);
		}
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 是否需要安装车辆
	 * @return
	 * @throws Exception
	 */
	public String selectInstall()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.IS_INSTALL);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 查询问题件处理状态
	 * @return
	 * @throws Exception
	 */
	public String selectAuditState()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.QUESTION_STATE);
		return JsonHelper.json(sysStaticData);
	}
	/**
	 * 查询问题件状态
	 * @return
	 * @throws Exception
	 */
	public String selectQuestionType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.QUESTION_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	/**
	 * 查询异常状态
	 * @return
	 * @throws Exception
	 */
	public String selectExceptionState()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.EXCEPTION_STATE);
		return JsonHelper.json(sysStaticData);
	}
	/**
	 * 查询异常类型
	 * @return
	 * @throws Exception
	 */
	public String selectExceptionType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.EXCEPTION_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	
	/**
	 * 查询公司所有网点
	 * @return
	 * @throws Exception
	 */
	public String selectOrg()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String hasAll=DataFormat.getStringKey(map, "hasAll");
		Integer isParent=DataFormat.getIntKey(map, "isParent");
		long tenantId = SysContexts.getCurrentOperator().getTenantId(); 
		List<Organization> lists = OraganizationCacheDataUtil.getTenantOrg(tenantId,isParent);
		List<Organization> r = new ArrayList();
		if (StringUtils.isNotEmpty(hasAll) && "true".equals(hasAll.toLowerCase())) {
			Organization org = new Organization();
			org.setOrgId(-1L);
			org.setOrgName("");
			r.add(org);
		}
		//只查询专线下的网点(其他不查询)
		for( Organization o : lists){
			if((SysStaticDataEnum.TENANT_TYPE.TENANT_TYPE2+"").equals(o.getTenantType())){
				r.add(o);
			}
			
		}
		
		if(r!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"orgId","orgName"}));
			return JsonHelper.jsonInclude(r,set);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询总公司
	 * @return
	 * @throws Exception
	 */
	public String selectRootOrg()throws Exception{
		long tenantId = SysContexts.getCurrentOperator().getTenantId(); 
		List<Organization> list = OraganizationCacheDataUtil.selectTenant();
		if(list!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"orgId","orgName"}));
			return JsonHelper.jsonInclude(list,set);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询网点 信息
	 * @return departmentAddress
	 * @throws Exception
	 */
	public String selectOrgInfo()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long orgId = DataFormat.getLongKey(map,"orgId");
		Organization organization = OraganizationCacheDataUtil.getOrganization(orgId);
		if(organization!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"orgId","orgName","departmentAddress","orgType"}));
			return JsonHelper.jsonInclude(organization,set);
		}else{
			return null;
		}
	}
	
	/**
	 * 基础数据查询
	 * @return
	 * @throws Exception
	 */
	public String selectSysStaticDataByCodeType() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String hasAll = DataFormat.getStringKey(map, "hasAll");
		String codeType = DataFormat.getStringKey(map, "codeType");
		List<SysStaticData> r = selectSysStaticDataByCodeTypePri(codeType, hasAll);
		return JsonHelper.json(r);
	}
	/**
	 * 专线拉包公司添加用户用
	 * @param codeType
	 * @param hasAll
	 * @param codeId
	 * @return
	 */
	public String selectSysStaticDataByCodeUserType() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		String hasAll = DataFormat.getStringKey(map, "hasAll");
		String codeType = DataFormat.getStringKey(map, "codeType");
		String codeId=DataFormat.getStringKey(map, "codeId");
		List<SysStaticData> r = selectSysStaticDataByCodeTypePri(codeType, hasAll,codeId);
		return JsonHelper.json(r);
	}
	/**
	 * 专线拉包公司添加用户用
	 * @param codeType
	 * @param hasAll
	 * @param codeId
	 * @return
	 */
	private List<SysStaticData> selectSysStaticDataByCodeTypePri (String codeType,String hasAll,String codeId){
		List<SysStaticData> r = new ArrayList<SysStaticData>();
		List<SysStaticData> sysStaticData = SysStaticDataUtil
				.getSysStaticDataList(codeType);
		if (StringUtils.isNotEmpty(hasAll) && "true".equals(hasAll.toLowerCase())) {
			SysStaticData staticData = new SysStaticData();
			staticData.setCodeValue("-1");
			staticData.setCodeName("所有");
			r.add(staticData);
		}
		r.addAll(sysStaticData);
		return r;
	}
	private List<SysStaticData> selectSysStaticDataByCodeTypePri (String codeType,String hasAll){
        List<SysStaticData> r = new ArrayList<SysStaticData>();
		List<SysStaticData> sysStaticData = SysStaticDataUtil
				.getSysStaticDataList(codeType);
		if (StringUtils.isNotEmpty(hasAll) && "true".equals(hasAll.toLowerCase())) {
			SysStaticData staticData = new SysStaticData();
			staticData.setCodeValue("-1");
			staticData.setCodeName("所有");
			r.add(staticData);
		}
		if (sysStaticData!=null&&sysStaticData.size()>0) {
			r.addAll(sysStaticData);
		}
		return r;
	}
	
	/*******基础数据配置********/
	
	/**
	 * 查询所有组织
	 * @return
	 * @throws Exception
	 */
	public String selectTenant()throws Exception{
		List<Organization> list = OraganizationCacheDataUtil.selectTenant();
		if(list!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"tenantId","orgName"}));
			return JsonHelper.jsonInclude(list,set);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询公司所有网点
	 * @return
	 * @throws Exception
	 */
	public String selectAllOrg()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser user = SysContexts.getCurrentOperator();
//		long tenantId = DataFormat.getLongKey(map,"tenantId");
//		if(tenantId<0){
//			throw new BusinessException("请输入租户信息");
//		}
		Integer isParent=DataFormat.getIntKey(map, "isParent");
		List<Organization> list = OraganizationCacheDataUtil.getTenantOrg(user.getTenantId(),isParent);
		if(list!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"orgId","orgName"}));
			return JsonHelper.jsonInclude(list,set);
		}else{
			return "";
		}
	}
	
	
	/**
	 * 查询公司所有网点
	 * @return
	 * @throws Exception
	 */
	public String selectparentOrg()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser user = SysContexts.getCurrentOperator();
     	long tenantId = DataFormat.getLongKey(map,"tenantId");
//		if(tenantId<0){
//			throw new BusinessException("请输入租户信息");
//		}
		Integer isParent=DataFormat.getIntKey(map, "isParent");
		List<Organization> list = OraganizationCacheDataUtil.getTenantOrg(tenantId,isParent);
		if(list!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"orgId","orgName"}));
			return JsonHelper.jsonInclude(list,set);
		}else{
			return "";
		}
	}
	
	/**
	 * 查询所有网点
	 * @return
	 * @throws Exception
	 */
	public String queryOrganization ()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		List<Organization> orgList = OraganizationCacheDataUtil.getOrganizationDataList();
		if(orgList!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"orgId","orgName"}));
			return JsonHelper.jsonInclude(orgList,set);
		}else{
			return null;
		}
	}
	/**
	 * 查询费用科目类型
	 * @return
	 * @throws Exception
	 */
	public String selectAcFeeConfig()throws Exception{
//		Map<String, String[]> map = SysContexts.getRequestParameterMap();
//		long feeId = DataFormat.getLongKey(map,"feeId");
//		List<AcFeeConfig> list = AcFeeCacheDataUtil.getAcFeeConfigDataList();
//		List<AcFeeConfig> listAfC = new ArrayList();
//		for(int i=0;i<list.size();i++){
//			if(list.get(i).getFeeType()==7){
//				if(feeId>-1){
//					if(feeId==list.get(i).getFeeId()){
//						listAfC.add(list.get(i));
//					}
//				}
//				else
//				{
//					listAfC.add(list.get(i));
//				}
//			}
//		}
//		if(list!=null){
//			Set<String> set = new HashSet<String>();
//			set.addAll(Arrays.asList(new String[]{"feeId","feeName"}));
//			return JsonHelper.jsonInclude(listAfC,set);
//		}else{
//			return "";
//		}
		return null;
	}
	
	/**
	 * 查询费用科目类型
	 * @return
	 * @throws Exception
	 */
	public String queryAcFeeConfig()throws Exception{
//		Map<String, String[]> map = SysContexts.getRequestParameterMap();
//		long feeType = DataFormat.getLongKey(map,"feeId");
//		long tenantId = DataFormat.getLongKey(map,"tenantId");
//		 BaseUser base = SysContexts.getCurrentOperator();
//		if(tenantId<=0){
//			 tenantId = Long.valueOf(base.getTenantId());
//		}
//		long feeId =queryUserFeeId(tenantId);
//		List<AcFeeConfig> list = AcFeeCacheDataUtil.getAcFeeConfigDataList(tenantId);
//		
//		List<AcFeeConfig> listAfC = new ArrayList();
//		for(int i=0;i<list.size();i++){
//			if(list.get(i).getFeeType()==7){
//				if(feeType>-1){
//					if(feeId==list.get(i).getFeeId()){
//						listAfC.add(list.get(i));
//					}
//				}
//				else
//				{
//					if(feeId!=list.get(i).getFeeId()){
//						listAfC.add(list.get(i));
//					}
//					
//				}
//			}
//		}
//		if(list!=null){
//			Set<String> set = new HashSet<String>();
//			set.addAll(Arrays.asList(new String[]{"feeId","feeName"}));
//			return JsonHelper.jsonInclude(listAfC,set);
//		}else{
//			return "";
//		}
		return "";
	}
	private Long queryUserFeeId(long tenantId)throws Exception{
		   /* BaseUser base = SysContexts.getCurrentOperator();
		    long tenantId = Long.valueOf(base.getTenantId());*/
		    
//			List<AcFeeConfig> list = AcFeeCacheDataUtil.getAcFeeConfigDataList(tenantId);
//			for(AcFeeConfig acFeeConfig:list){
//				if(acFeeConfig.getTenantId()==tenantId 
//						&& acFeeConfig.getRouteOrgType()==SysStaticDataEnum.ROUTE_ORG_TYPE.ROUTE_FEE){
//					return acFeeConfig.getFeeId();
//				}
//			}
		return null;
	}
	
	
	/**
	 * 查询网点的电话
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryOrgPhone()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser user = SysContexts.getCurrentOperator();
		long orgId = DataFormat.getLongKey(map,"orgId");
//		if(tenantId<0){
//			throw new BusinessException("请输入租户信息");
//		}
		Map rtnMap = new HashMap();
		if (orgId > 0) {
			Organization org = OraganizationCacheDataUtil.getOrganization(orgId);
			rtnMap.put("descOrgStaffPhone", org.getSupportStaffPhone());// 客服号码1
			rtnMap.put("descOrgStaffPhone2", org.getSupportStaffPhone2());// 客服号码2
			rtnMap.put("destOrgName", org.getOrgName());
		}
//		Organization tenantOrg = OraganizationCacheDataUtil.getOrganizationByTenantId(user.getTenantId());
//		String tenantName = tenantOrg.getOrgName();
//		if (!tenantName.endsWith("物流")) {
//			tenantName += "物流";
//		}
//		rtnMap.put("tenantName", tenantName);
//		rtnMap.put("tenantStaffPhone", tenantOrg.getSupportStaffPhone());
//		
//		Organization orgInfo = OraganizationCacheDataUtil.getOrganization(user.getOrgId());
//		rtnMap.put("beginOrgId", orgInfo.getSupportStaffPhone());// 客服号码
//		rtnMap.put("beginAddress", orgInfo.getProvinceName()+orgInfo.getRegionName()+orgInfo.getCountyName()+orgInfo.getDepartmentAddress());
//		rtnMap.put("beginOrgName", orgInfo.getOrgName());
		
		long openOrgId = DataFormat.getLongKey(map, "openOrgId");// 开单网点
		if (openOrgId > 0) {
			Organization openOrg = OraganizationCacheDataUtil.getOrganization(openOrgId);
			rtnMap.put("openOrgStaffPhone", openOrg.getSupportStaffPhone());// 客服号码1
			rtnMap.put("openOrgStaffPhone2", openOrg.getSupportStaffPhone2());// 客服号码2
			rtnMap.put("openOrgName", openOrg.getOrgName());
			City city = SysStaticDataUtil.getCityDataList("SYS_CITY",openOrg.getRegionId());
			rtnMap.put("openCityName",city != null && city.getName() != null ? city.getName() : "" );
			String tenantName = openOrg.getOrgName();
			rtnMap.put("tenantName",tenantName );
			
		}
		rtnMap.put("userName", user.getUserName());
		rtnMap.put("createDate", DateUtil.getCurrDate());
		
		return JsonHelper.json(rtnMap);
	}
	
	
	
	

	/**
	 *根据用户角色查询对应的网点信息
	 * @return
	 * @throws Exception
	 */
	public String selectOrgByRole()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		long tenantId = SysContexts.getCurrentOperator().getTenantId(); 
		//List<Organization> list = OraganizationCacheDataUtil.getOrganizationDataList();
		List<Organization> r = new ArrayList<Organization>();
		BaseUser base = SysContexts.getCurrentOperator();
		Map<String,Object> attrs= base.getAttrs();
		List<Integer> rolds=(List<Integer>) attrs.get(EnumConsts.Common.SESSION_ROLES);
		if(rolds.size()>0){
			for(Integer roleId:rolds){
				if(roleId==SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR){
					r =OraganizationCacheDataUtil.getOrgByRole(SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR,Long.valueOf(base.getTenantId()));
				   break;
				}else if(roleId==SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER){
					r =OraganizationCacheDataUtil.getOrgByRole(SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER,Long.valueOf(base.getTenantId()));
					   break;
				}else{
					r =OraganizationCacheDataUtil.getOrgByRole(SysStaticDataEnum.ADMIN_ROLE.ORG_ADMIN_UPER,base.getOrgId());
				}
			}
		}
		if(r!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"orgId","orgName"}));
			return JsonHelper.jsonInclude(r,set);
		}else{
			return "";
		}
	}
	
	
	/**根据用户角色查询对应总公司网点信息**/
	
	public String selectRootOrgByRole()throws Exception{
		long tenantId = SysContexts.getCurrentOperator().getTenantId(); 
		List<Organization> list= new ArrayList<Organization>();
		BaseUser base = SysContexts.getCurrentOperator();
		Map<String,Object> attrs= base.getAttrs();
		List<Integer> rolds=(List<Integer>) attrs.get(EnumConsts.Common.SESSION_ROLES);
		for(Integer roleId:rolds){
			if(roleId==SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR){
				list = OraganizationCacheDataUtil.selectTenantByRole(SysStaticDataEnum.ADMIN_ROLE.SUPP_ADMINISTRATOR,Long.valueOf(base.getTenantId()));
			    break;
			}else{
				list = OraganizationCacheDataUtil.selectTenantByRole(SysStaticDataEnum.ADMIN_ROLE.ADMIN_UPER,Long.valueOf(base.getTenantId()));
			}
		}
		if(list!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"orgId","orgName"}));
			return JsonHelper.jsonInclude(list,set);
		}else{
			return null;
		}
	}
	
		
	/**
	 * 获取预约提货类型
	 * @return
	 * @throws Exception
	 */
	public String getOrdSellerNotifyPeriodType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.ORD_SELLER_NOTIFY_PERIODTYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 获取预约提货状态
	 * @return
	 * @throws Exception
	 */
	public String getOrdSellerNotifyState()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.ORD_SELLER_NOTIFY_STATE);
		return JsonHelper.json(sysStaticData);
	}
	
	/**
	 * 服务类型
	 * @return
	 * @throws Exception
	 */
	public String getOrdSellerServeType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.SERVE_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	public String getVisitInfoState()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.VISIT_INFO_STATE);
		return JsonHelper.json(sysStaticData);
	}
	
	public String getComplaintInfoState()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.COMPLAINT_INFO_STATE);
		return JsonHelper.json(sysStaticData);
	}
	
	public String getComplaintInfoSourceType()throws Exception{
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.COMPLAINT_INFO_SOURCE_TYPE);
		return JsonHelper.json(sysStaticData);
	}
	
	public String getTrackingNum()throws Exception{
		return JsonHelper.json(getTrackingNumMap());
	}
	
	private Map getTrackingNumMap()throws Exception{
		Map ret = new HashMap();
		BaseUser baseUser= SysContexts.getCurrentOperator();
		String trackingBeginNum = "";
		trackingBeginNum = SysTenantExtendCacheUtil.getValue(baseUser.getTenantId(), EnumConsts.TRACKING_NUM_REDIS.TANANT_TRACKING_NUM_BEGIN);
		if(trackingBeginNum==null){
			trackingBeginNum = "";
		}
		
		ret.put("trackingNum", CommonUtil.getTrackingNum(baseUser.getTenantId(),baseUser.getUserId()));
		ret.put("trackingNumBegin", trackingBeginNum);
		return ret;
	}
	
	public String getOpenButtonList()throws Exception{
		return JsonHelper.json(getOpenButtonListPri());
	}
	
	private List<SysStaticData> getOpenButtonListPri()throws Exception{
		BaseUser baseUser= SysContexts.getCurrentOperator();
		List<SysStaticData> sysStaticData = SysStaticDataUtil.getSysStaticDataList(baseUser.getTenantId().longValue(), "OPEN_BUTTON_LIST");
		
		List<SysStaticData> listOut = new ArrayList<SysStaticData>();
		if(sysStaticData==null){
			return listOut;
		}
		for(SysStaticData s : sysStaticData){
			if(s.getTenantId() == baseUser.getTenantId()){
				listOut.add(s);
			}
		}
		return listOut;
	}
	
	
	/**
	 * 南哥物流： 需要根据租户，网点进行配载哪些市是需要直送
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDirectSendingInfo()throws Exception{
		return JsonHelper.json(getDirectSendingInfoPri());
	}
	
	private List<SysStaticData> getDirectSendingInfoPri(){
		BaseUser baseUser= SysContexts.getCurrentOperator();
		long tenantId=baseUser.getTenantId();
		long orgId=baseUser.getOrgId();
		List<SysStaticData> staticDataList = (List)CacheFactory.get(SysStaticDataCache.class, tenantId + "_" + EnumConsts.SysStaticData.DIRECT_SENDING);
		
		List<SysStaticData> listOut = new ArrayList<SysStaticData>();
		if(staticDataList==null){
			return listOut;
		}
		for(SysStaticData s : staticDataList){
			//配置的网点
			if(s.getCodeId()==orgId){
				listOut.add(s);
			}
		}
		return listOut;
	}
	
	
	/**
	 * 获取运单录入的静态页面的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryOrderStaticInfo() throws Exception {
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		Boolean isAdd=DataFormat.getBooleanKey(map, "isAdd");//true 表示是新增的功能呢，其他是修改
		
		
		Map<String, Object> ret =new HashMap<String, Object>();
		
		//运单新增需要，修改不需要   运单号  getTrackingNum
		if(isAdd!=null && isAdd.booleanValue()==true){
			Map getTrackingNumMap= getTrackingNumMap();
			ret.put("getTrackingNumMap", getTrackingNumMap);
		}
		
		//selectDeliveryType 交接方式
		List<SysStaticData> selectDeliveryType = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.DELIVERY_TYPE);
		
		//selectPackType 包装类型
		List<SysStaticData> selectPackType = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.PACKING_TYPE);
		
		//selectGoodsType 货品类型
		List<SysStaticData> selectGoodsType = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.GOODS_TYPE);
		
		//获取常见品名 loadCommonGoodsName
		List<SysStaticData> loadCommonGoodsName = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.COMMON_TYPE_GOODS_NAME);
		
		//获取付款方式 
		List<SysStaticData> selectPaymentType = SysStaticDataUtil.getSysStaticDataList(EnumConsts.SysStaticData.PAYMENT_TYPE);
		if(isAdd!=null && isAdd.booleanValue()==true){
			List<SysStaticData> getOpenButtonList= getOpenButtonListPri();
			ret.put("getOpenButtonList", getOpenButtonList);
		}
		
		List<SysStaticData> getDirectSendingInfo= getDirectSendingInfoPri();
		
		List<SysStaticData> selectSysStaticDataByCodeType=selectSysStaticDataByCodeTypePri(SysStaticDataEnum.SCHE_SERVICE_TYPE.SCHE_SERVICE_TYPE, "");
		
		
		ret.put("selectDeliveryType", selectDeliveryType);
		ret.put("selectPackType", selectPackType);
		ret.put("loadCommonGoodsName", loadCommonGoodsName);
		ret.put("selectPaymentType", selectPaymentType);
		ret.put("selectGoodsType", selectGoodsType);
		ret.put("getDirectSendingInfo", getDirectSendingInfo);
		ret.put("selectSysStaticDataByCodeType", selectSysStaticDataByCodeType);
		
		return JsonHelper.json(ret);
	}
	
	/**
	 * 查询公司所有网点
	 * @return
	 * @throws Exception
	 */
	public String selectAllTenantId()throws Exception{
		Map<String, String[]> map = SysContexts.getRequestParameterMap();
		BaseUser user = SysContexts.getCurrentOperator();
//		long tenantId = DataFormat.getLongKey(map,"tenantId");
//		if(tenantId<0){
//			throw new BusinessException("请输入租户信息");
//		}
		Integer isParent=DataFormat.getIntKey(map, "isParent");
		List<Organization> list = OraganizationCacheDataUtil.getTenantOrg(user.getTenantId(),isParent);
		if(list!=null){
			Set<String> set = new HashSet<String>();
			set.addAll(Arrays.asList(new String[]{"tenantId","orgName"}));
			return JsonHelper.jsonInclude(list,set);
		}else{
			return "";
		}
	}
	/**
	 * 查询当前租户公司（云启）
	 * @return
	 */
	public String selectCurTenantId()throws Exception{
		List<Map<String, String>> list = OraganizationCacheDataUtil.getOrganizationByTenantId();
		if(list != null && list.size() > 0){
			return JsonHelper.json(list);
		}
		return "";
	}
	
	/**
	 * 查询公司专线信息
	 * @return
	 * @throws Exception
	 */
	public String queryTenantInfo()throws Exception{
		List<Organization> list = OraganizationCacheDataUtil.getOrganizationDataList();
		BaseUser user = SysContexts.getCurrentOperator();
		List<Map<String,Object>> rtnList = new ArrayList<Map<String,Object>>();
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		rtnMap.put("tenantId", -1);
		rtnMap.put("tenantName", "全部");
		rtnList.add(rtnMap);
		for (Organization organization : list) {
			Map<String,Object> map = new HashMap<String,Object>();
			if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.CHAIN){
				if(organization.getTenantId().longValue()==user.getTenantId().longValue()
						&&organization.getParentOrgId()==-1
						&&organization.getTenantType().equals("2")&&organization.getState()==1){
					map.put("tenantId", organization.getTenantId());
					map.put("tenantName", organization.getOrgName());
					rtnList.add(map);
				}
			}else if(user.getUserType().intValue()==SysStaticDataEnumYunQi.USER_TYPE_YUNQI.PLATFORM
					&&organization.getParentOrgId()==-1
					&&organization.getTenantType().equals("2")&&organization.getState()==1){
				map.put("tenantId", organization.getTenantId());
				map.put("tenantName", organization.getOrgName());
				rtnList.add(map);
			}
		}
		return JsonHelper.json(rtnList);
	}
	/**
	 * 获取城市id
	 * @return
	 */
	public String getIdByName(){
		Map<String, Object> map = SysContexts.getRequestParamFlatMap();
		String cityName = DataFormat.getStringKey(map, "cityName");
		String districtName = DataFormat.getStringKey(map, "districtName");
		Map<String,Object> outMap = new HashMap<String, Object>();
		outMap.put("cityId", CommonUtil.getCityIdByName(cityName));
		outMap.put("districtId", CommonUtil.getDistrictIdByName(districtName));
		return JsonHelper.json(outMap);
	}
	/**
	 * 获取app下载地址
	 * @return
	 */
	public String getAppUrl(){
		Map<String,Object> map = SysContexts.getRequestParamFlatMap();
		int appType = DataFormat.getIntKey(map, "appType");
		String cfg = "";
		if (appType == 1) {
			cfg = "IOS_APP_URL";
		}else {
			cfg = "ANDROID_APP_URL";
		}
		return SysCfgUtil.getSysCfg(cfg).getCfgValue();
	}
	
	/**
	 * 获取租户下所有的网点
	 * @return
	 */
	public String getOrgan(){
		BaseUser user = SysContexts.getCurrentOperator();
		List<Organization> list = OraganizationCacheDataUtil.getOrganizationByTenantIdAndOrgType(user.getTenantId());
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if (list != null && list.size() > 0) {
			for (Organization org : list) {
				if (org.getParentOrgId() != -1) {
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("endOrgName", org.getOrgName());
					map.put("endOrgId", org.getOrgId());
					listMap.add(map);
				}
			}
		}
		return JsonHelper.json(listMap);
	}
	/**
	 * 判断是否是顶级租户
	 * @return
	 */
	public String isTopOrg(){
		BaseUser user = SysContexts.getCurrentOperator();
		return OraganizationCacheDataUtil.getOrganization(user.getOrgId()).getParentOrgId() == -1 ? "Y" : "N";
	}
}

