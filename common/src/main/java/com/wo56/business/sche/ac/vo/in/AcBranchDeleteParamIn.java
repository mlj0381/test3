package com.wo56.business.sche.ac.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.IntfCodeConsts;

/***
 * @author zjy
 * 支线费用管理
 * */
public class AcBranchDeleteParamIn  implements IParamIn {

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return IntfCodeConsts.AC.BRANCH_DELETE;
	}
	/**主键 修改时需要回传 */
	private String branchId;
	
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}
