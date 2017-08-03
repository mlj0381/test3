package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;
/***
 * @author zjy 
 * (1) 必传 （0）非必传
 * 
 * */
public class OrdMatchDeatilOtherIn implements IParamIn{

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.ORD.MATCH_DETAIL_OTHER;
	}
	
	/**批次号 （1）**/
	private String batchNum;

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	
	
	

}
