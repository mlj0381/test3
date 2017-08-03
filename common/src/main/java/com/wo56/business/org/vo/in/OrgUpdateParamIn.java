package com.wo56.business.org.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

/**
 * @author 邱林锋
 * 修改专线信息
 * */
public class OrgUpdateParamIn implements IParamIn{

	@Override
	public String getInCode() {
		return this.inCode;
	}
	
	
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}


	public OrgUpdateParamIn(String inCode) {
		super();
		this.inCode = inCode;
	}
		
		private String inCode;
		/** 租户Id **/
		private long tenantId;
		/** 租户编码 **/
		private String tenantCode;
		/** 租户名称 **/
		private String name;
		/** 租户联系人 **/
		private String linkMan;
		/** 租户联系人电话 **/
		private String linkPhone;
		/** 客服电话 **/
		private String csPhones;
		/** 起始省份ID **/
		private String provinceId;
		/** 目的省份ID **/
		private String provinceIds;
		/** 专线地址 **/
		private String address;
		
		public long getTenantId() {
			return tenantId;
		}


		public void setTenantId(long tenantId) {
			this.tenantId = tenantId;
		}


		public String getTenantCode() {
			return tenantCode;
		}


		public void setTenantCode(String tenantCode) {
			this.tenantCode = tenantCode;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getLinkMan() {
			return linkMan;
		}


		public void setLinkMan(String linkMan) {
			this.linkMan = linkMan;
		}


		public String getLinkPhone() {
			return linkPhone;
		}


		public void setLinkPhone(String linkPhone) {
			this.linkPhone = linkPhone;
		}


		public String getCsPhones() {
			return csPhones;
		}


		public void setCsPhones(String csPhones) {
			this.csPhones = csPhones;
		}


		public String getProvinceId() {
			return provinceId;
		}


		public void setProvinceId(String provinceId) {
			this.provinceId = provinceId;
		}


		public String getProvinceIds() {
			return provinceIds;
		}


		public void setProvinceIds(String provinceIds) {
			this.provinceIds = provinceIds;
		}


		public String getAddress() {
			return address;
		}


		public void setAddress(String address) {
			this.address = address;
		}
		
}
