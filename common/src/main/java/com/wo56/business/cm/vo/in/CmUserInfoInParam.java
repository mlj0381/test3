package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

/***
 * @author zjy 
 * （1） 表示必传 （0）  非必传
 * 
 * 
 * */
public class CmUserInfoInParam  implements IParamIn {

	// 接口编号
		@Override
		public String getInCode() {
			return InterFacesCodeConsts.CM.QUERY_BUSS_BILLING;
		}
		/**网点id   （1）*/
		private long orgId;
		
		private String orgCode;
		
		/**租户id （1）*/
		private long tenantId;
		
		private String tenantCode;
		
		private int state;

		
		public String getOrgCode() {
			return orgCode;
		}
		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}
		public String getTenantCode() {
			return tenantCode;
		}
		public void setTenantCode(String tenantCode) {
			this.tenantCode = tenantCode;
		}
		public long getOrgId() {
			return orgId;
		}
		public void setOrgId(long orgId) {
			this.orgId = orgId;
		}
		public long getTenantId() {
			return tenantId;
		}
		public void setTenantId(long tenantId) {
			this.tenantId = tenantId;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		
		
		
}

