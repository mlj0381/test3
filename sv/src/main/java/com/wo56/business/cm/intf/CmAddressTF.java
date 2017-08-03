package com.wo56.business.cm.intf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.framework.core.SysContexts;
import com.framework.core.exception.BusinessException;
import com.framework.core.identity.vo.BaseUser;
import com.framework.core.inter.vo.Pagination;
import com.framework.core.util.DataFormat;
import com.framework.core.util.IPage;
import com.framework.core.util.JsonHelper;
import com.framework.core.util.PageUtil;
import com.framework.core.util.SysStaticDataUtil;
import com.wo56.business.cm.impl.CmAddressSV;
import com.wo56.business.cm.vo.CmAddress;
import com.wo56.business.cm.vo.out.CmAddressOut;
import com.wo56.common.consts.SysStaticDataEnum;
import com.wo56.common.utils.CommonUtil;

public class CmAddressTF {
	private static final int  addressState[] = new int[]{1,2};

	/**
	 * 新增与修改发货地址/收货地址
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doSaveAddress(Map<String, String> inParam) throws Exception {
		CmAddressSV cmAddressSV = (CmAddressSV) SysContexts.getBean("cmAddressSV");
		CmAddress cmAddress = new CmAddress();
		BaseUser base = SysContexts.getCurrentOperator();
		String cityId = DataFormat.getStringKey(inParam, "cityId");
		if (StringUtils.isEmpty(cityId)) {
			throw new BusinessException("城市不能为空");
		}
		String cityName = SysStaticDataUtil.getCityDataList("SYS_CITY", cityId).getName();
		String provinceId = DataFormat.getStringKey(inParam, "provinceId");
		if (StringUtils.isEmpty(provinceId)) {
			throw new BusinessException("省份不能为空");
		}
		String provinceName = SysStaticDataUtil.getProvinceDataList("SYS_PROVINCE", provinceId).getName();
		String districtId = DataFormat.getStringKey(inParam, "districtId");
		if (StringUtils.isEmpty(districtId)) {
			throw new BusinessException("地区不能为空");
		}
		String districtName = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", districtId).getName();
		String address = DataFormat.getStringKey(inParam, "address");
		String bill = DataFormat.getStringKey(inParam, "bill");
		if (StringUtils.isNotEmpty(bill)) {
			if (!CommonUtil.isCheckPhone(bill)&&!CommonUtil.isCheckMobiPhone(bill)) {
				throw new BusinessException("请输入正确的电话号码！");
			}
		}
		String eand = DataFormat.getStringKey(inParam, "eand");
		String linkman = DataFormat.getStringKey(inParam, "linkman");
		String nand = DataFormat.getStringKey(inParam, "nand");
		Integer type = DataFormat.getIntKey(inParam, "type");
		int isCommonAddress = DataFormat.getIntKey(inParam, "isCommonAddress");//是否常用地址
		long userId=base.getUserId();
		long id = DataFormat.getLongKey(inParam, "id");
		if (id > 0) {
			cmAddress = cmAddressSV.getCmAddress(id);
		}
		cmAddress.setLinkman(linkman);
		cmAddress.setBill(bill);
		cmAddress.setProvince(CommonUtil.isLong(provinceId) ? Long.parseLong(provinceId) : -1);
		cmAddress.setProvinceName(provinceName);
		cmAddress.setCity(CommonUtil.isLong(cityId) ? Long.parseLong(cityId) : -1);
		cmAddress.setCityName(cityName);
		cmAddress.setDistrict(CommonUtil.isLong(districtId) ? Long.parseLong(districtId) : -1);
		cmAddress.setDistrictName(districtName);
		cmAddress.setAddress(address);
		cmAddress.setOrgId(base.getOrgId());
		Date date = new Date();
		cmAddress.setOpId(base.getUserId());
		cmAddress.setCreateTime(date);
		cmAddress.setNand(nand);
		cmAddress.setEand(eand);
		//查询是否有默认收/发地址，如果没有，新增为默认地址
		if(id<0){
			Session session = SysContexts.getEntityManager(true);
			Criteria ca = session.createCriteria(CmAddress.class);
			ca.add(Restrictions.eq("merchantAddressType", type));
			ca.add(Restrictions.eq("userId", userId));
			ca.add(Restrictions.eq("adderssDefault", SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_IS_DEFAULT));
			ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
			CmAddress defaultAddress=(CmAddress)ca.uniqueResult();
			if(defaultAddress==null){
				cmAddress.setAdderssDefault(SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_IS_DEFAULT);	
			}else{
				cmAddress.setAdderssDefault(SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_NOT_DEFAULT);
			}
		}
		//cmAddress
		cmAddress.setState(SysStaticDataEnum.STS.VALID);
		cmAddress.setUserId(userId);
		cmAddress.setMerchantAddressType(type);
		cmAddress.setCommonAddress(isCommonAddress);
		Map<String,String> map = new HashMap<String,String>();
		map.put("info", cmAddressSV.doSaveAddress(cmAddress));
		return map;
	}

	/**
	 * 删除发货/收货地址
	 * 
	 * @param Id
	 * @throws Exception
	 */
	public Map deleteAddressById(long addressId) throws Exception {
		CmAddressSV cmAddressSV = (CmAddressSV) SysContexts.getBean("cmAddressSV");
		BaseUser base = SysContexts.getCurrentOperator();
		long userId=base.getUserId();
		if (addressId < 0) {
			throw new BusinessException("未传入地址ID");
		}
		Session session = SysContexts.getEntityManager();
		CmAddress CmAddress = (CmAddress) session.get(CmAddress.class, addressId);
		cmAddressSV.deleteAddressById(addressId,userId);
		Integer type = -1;
		Integer defaultAddress=-1;
		if (CmAddress != null) {
			type = CmAddress.getMerchantAddressType();
			defaultAddress=CmAddress.getAdderssDefault();
			//如果删除的是默认地址，需要查询是否有其它地址，如果有：设置一条默认地址
			if(defaultAddress==SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_IS_DEFAULT){
				Criteria ca = session.createCriteria(CmAddress.getClass());
				ca.add(Restrictions.eq("userId", userId));
				ca.add(Restrictions.eq("merchantAddressType", type));
				ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
				List<CmAddress> list = ca.list();
				if (list != null && list.size() > 0) {
					CmAddress temp = list.get(0);
						temp.setAdderssDefault(SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_IS_DEFAULT);
						session.update(temp);
				}
			}
		}
		Map map = new HashMap();
		map.put("info", "Y");
		return map;
	}

	/**
	 * 查询发货/收货地址列表
	 * 
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map doQueryAddress(Map<String, String> inParam) throws Exception {
		Session session = SysContexts.getEntityManager(true);
		Integer type = DataFormat.getIntKey(inParam, "type");
		if(type<0){
			throw new BusinessException("未传入地址类型");
		}
		BaseUser base = SysContexts.getCurrentOperator();
		long userId=base.getUserId();
		if(userId<0){
			throw new BusinessException("未传入用户Id");
		}
		Criteria ca = session.createCriteria(CmAddress.class);
		ca.add(Restrictions.eq("merchantAddressType", type));
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("commonAddress", 1));
		ca.addOrder(Order.desc("id"));
		
		IPage page = PageUtil.gridPage(ca);
		Pagination pagination = new Pagination(page);
		List<CmAddress> list = pagination.getItems();
		List<CmAddressOut> outList = new ArrayList<CmAddressOut>();
		if (list != null && list.size() > 0) {
			for (CmAddress temp : list) {
				CmAddressOut cmAddressOut = new CmAddressOut();
				cmAddressOut.setBill(temp.getBill());
				cmAddressOut.setAddress(temp.getAddress());
				cmAddressOut.setId(temp.getId() != null ? temp.getId() .toString() : "");
				cmAddressOut.setLinkman(temp.getLinkman());
				cmAddressOut.setType(temp.getMerchantAddressType() != null ? temp.getMerchantAddressType().toString() : "");
				cmAddressOut.setAdderssDefault(temp.getAdderssDefault() != null ? temp.getAdderssDefault().toString() : "");
				cmAddressOut.setCityId(temp.getCity() != null ? temp.getCity().toString() : "");
				cmAddressOut.setCityName(temp.getCityName());
				cmAddressOut.setDistrictId(temp.getDistrict() != null ? temp.getDistrict().toString() : "");
				cmAddressOut.setDistrictName(temp.getDistrictName());
				cmAddressOut.setProvinceId(temp.getProvince() != null ? temp.getProvince().toString() : "");
				cmAddressOut.setProvinceName(temp.getProvinceName());
				cmAddressOut.setNand(temp.getNand());
				cmAddressOut.setEand(temp.getEand());
				if(temp.getAdderssDefault()!=null&&temp.getAdderssDefault()==1){
					outList.add(0,cmAddressOut);
				}else{
					outList.add(cmAddressOut);
				}
			}
		}
		pagination.setItems(outList);
		return JsonHelper.parseJSON2Map(JsonHelper.json(pagination));
	}

	/**
	 * 
	 * 
	 * 设置默认地址
	 * 
	 * @param inParam
	 * @return
	 */
	public Map  doSaveUpdateDefaultAddress(Map<String, Object> inParam) throws Exception{
		Session session = SysContexts.getEntityManager();
		long id = DataFormat.getLongKey(inParam, "id");
		if(id<0){
			throw new BusinessException("未传入地址ID");
		}
		//根据Id查询该条数据
		//用该条数据获取到地址类型、userId
		//根据UserId查询之前的默认地址
		//改掉之前的默认地址
		CmAddress cmAddress=(CmAddress)session.get(CmAddress.class, id);
		Integer type=cmAddress.getMerchantAddressType();
		long userId=cmAddress.getUserId();
		Criteria ca = session.createCriteria(CmAddress.class);
		ca.add(Restrictions.eq("merchantAddressType", type));
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("adderssDefault", SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_IS_DEFAULT));
		List<CmAddress>cmAddressList=ca.list();
		CmAddress cmAddress2=new CmAddress();
		if(cmAddressList.size()==1){
			cmAddress2 = cmAddressList.get(0);
			cmAddress2.setAdderssDefault(SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_NOT_DEFAULT);
			session.update(cmAddress2);
		}
		cmAddress.setAdderssDefault(SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_IS_DEFAULT);
		session.update(cmAddress);
		Map map=new HashMap();
		map.put("info", "Y");
		return map;
	}
	/**
	 * 查询用户的默认地址
	 * @param inParam
	 * @return
	 * @throws Exception
	 */
	public Map getAdderssDefault(Map<String, String> inParam)throws Exception{
		Session session = SysContexts.getEntityManager(true);
		Integer type = DataFormat.getIntKey(inParam, "type");
		if(type<0){
			throw new BusinessException("未传入地址类型");
		}
		if(!CommonUtil.isContains(addressState, type)){
			throw new BusinessException("传入的地址类型不正确");
		}
		BaseUser base = SysContexts.getCurrentOperator();
		long userId=base.getUserId();
		if(userId<0){
			throw new BusinessException("未传入用户Id");
		}
		Criteria ca = session.createCriteria(CmAddress.class);
		ca.add(Restrictions.eq("merchantAddressType", type));
		ca.add(Restrictions.eq("userId", userId));
		ca.add(Restrictions.eq("adderssDefault", SysStaticDataEnum.ADDRESS_TYPE_YQ.ADDRESS_TYPE_IS_DEFAULT));
		ca.add(Restrictions.eq("state", SysStaticDataEnum.STS.VALID));
		CmAddress cmAddress=(CmAddress)ca.uniqueResult();
		CmAddressOut cmAddressOut = new CmAddressOut();
		if(cmAddress==null){
			return JsonHelper.parseJSON2Map(JsonHelper.json(cmAddressOut));
		}
		cmAddressOut.setAdderssDefault(cmAddress.getAdderssDefault() != null ? cmAddress.getAdderssDefault().toString() : "");
		cmAddressOut.setAddress(cmAddress.getAddress());
		cmAddressOut.setBill(cmAddress.getBill());
		cmAddressOut.setId(cmAddress.getId() != null ? cmAddress.getId().toString() : "");
		cmAddressOut.setLinkman(cmAddress.getLinkman());
		cmAddressOut.setType(cmAddress.getMerchantAddressType() != null ? cmAddress.getMerchantAddressType().toString() : "");
		cmAddressOut.setDistrictId(cmAddress.getDistrict() != null ? cmAddress.getDistrict().toString() : "");
		cmAddressOut.setDistrictName(cmAddress.getDistrictName());
		cmAddressOut.setCityId(cmAddress.getCity() != null ? cmAddress.getCity().toString() : "");
		cmAddressOut.setCityName(cmAddress.getCityName());
		cmAddressOut.setProvinceId(cmAddress.getProvince() != null ? cmAddress.getProvince().toString() : "");
		cmAddressOut.setProvinceName(cmAddress.getProvinceName());
		cmAddressOut.setEand(cmAddress.getEand());
		cmAddressOut.setNand(cmAddress.getNand());
		return JsonHelper.parseJSON2Map(JsonHelper.json(cmAddressOut));
	}



}
