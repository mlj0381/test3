package com.wo56.business.route.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

public class RouteFeeConfigParamIn extends PageInParamVO implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return "160009";
	}
	
	private long startOrgid;//起始网点
	
		public long getStartOrgid() {
			return startOrgid;
		}
		public void setStartOrgid(long startOrgid) {
			this.startOrgid = startOrgid;
		}

	private long endOrgid;//到达网点
	
		public long getEndOrgid() {
			return endOrgid;
		}
		public void setEndOrgid(long endOrgid) {
			this.endOrgid = endOrgid;
		}

	private int lfType;//线路费类型
	
		public int getLfType() {
			return lfType;
		}
		public void setLfType(int lfType) {
			this.lfType = lfType;
		}

	}
