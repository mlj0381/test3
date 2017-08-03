package com.wo56.business.cm.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

/***
 * @author zjy 
 * （1） 表示必传 （0）  非必传
 * 
 * 
 * */
public class CmCompanyIn  implements IParamIn {
		private String tenantCode;
		private String inCode;
		private String tenantType;
		
		public String getTenantCode() {
			return tenantCode;
		}
		public void setTenantCode(String tenantCode) {
			this.tenantCode = tenantCode;
		}
		public String getInCode() {
			return inCode;
		}
		public void setInCode(String inCode) {
			this.inCode = inCode;
		}
		public CmCompanyIn(String inCode) {
			super();
			this.inCode = inCode;
		}
		public String getTenantType() {
			return tenantType;
		}
		public void setTenantType(String tenantType) {
			this.tenantType = tenantType;
		}
		
}

