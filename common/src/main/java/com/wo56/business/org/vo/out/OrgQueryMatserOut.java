package com.wo56.business.org.vo.out;

import java.util.Date;

import com.framework.core.svcaller.vo.BaseOutParamVO;

public class OrgQueryMatserOut extends BaseOutParamVO{
		// 商家ID
		// 组织id
		private Long orgId;
		// 商家编码
		private String tenantCode;
		// 商家名称
		private String name;
		// 商家联系人
		private String linkMan;
		// 商家联系人电话
		private String linkPhone;
		private String orgName;
		private String sfOrgName;
		private long relId;
		private String provinceName;
		private Date opDate;
		private String opName;
		public Long getTenantId() {
			return tenantId;
		}
		public void setTenantId(Long tenantId) {
			this.tenantId = tenantId;
		}
		public Long getOrgId() {
			return orgId;
		}
		public void setOrgId(Long orgId) {
			this.orgId = orgId;
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
		public String getOrgName() {
			return orgName;
		}
		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}
		public String getSfOrgName() {
			return sfOrgName;
		}
		public void setSfOrgName(String sfOrgName) {
			this.sfOrgName = sfOrgName;
		}
		public long getRelId() {
			return relId;
		}
		public void setRelId(long relId) {
			this.relId = relId;
		}
		public String getProvinceName() {
			return provinceName;
		}
		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}
		public Date getOpDate() {
			return opDate;
		}
		public void setOpDate(Date opDate) {
			this.opDate = opDate;
		}
		public String getOpName() {
			return opName;
		}
		public void setOpName(String opName) {
			this.opName = opName;
		}
		
		
}
