package com.wo56.business.common.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.wo56.common.consts.InterFacesCodeConsts;

public class ExportDataIn implements IParamIn{

	/**
	 * 查询静态数据
	 */
	@Override
	public String getInCode() {
		return InterFacesCodeConsts.COMMON.EXPORT_DATA;
	}

	public String sqlKey; //模板SQL  value
	public String sqlParams; //sql参数
	 
	 

	public String getSqlKey() {
		return sqlKey;
	}
	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}
	public String getSqlParams() {
		return sqlParams;
	}
	public void setSqlParams(String sqlParams) {
		this.sqlParams = sqlParams;
	}
	 
	

	
}
