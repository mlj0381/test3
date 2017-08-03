package com.wo56.business.common.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;

public class StaticDataIn implements IParamIn{

	/**
	 * 查询静态数据
	 */
	@Override
	public String getInCode() {
		return "";
	}
	/**静态数据类型必传*/
	public String codeType;
	/**静态数据值*/
	public String codeValue;
	
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	
}
