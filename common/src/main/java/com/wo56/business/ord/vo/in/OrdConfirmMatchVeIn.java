package com.wo56.business.ord.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
/***
 * @author zjy 
 * (1) 必传 （0）非必传
 * 
 * */
public class OrdConfirmMatchVeIn implements IParamIn{
	private String inCode;
	
	public OrdConfirmMatchVeIn(){
		
	}
	public OrdConfirmMatchVeIn(String inCode){
		this.inCode=inCode;
	}

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return inCode;
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
