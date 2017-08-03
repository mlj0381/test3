package com.wo56.business.sys.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;

@SuppressWarnings("serial")
public class SysStaticDataPageIn extends PageInParamVO implements IParamIn{
	public SysStaticDataPageIn(String inCode){
		super();
		this.inCode = inCode;
	}
	@Override
	public String getInCode() {
		return this.inCode;
	}
	private String inCode;
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	  private long flowId;
	  private long codeId;
	  private long tenantId;
	  private String codeType;
	  private String codeValue;
	  private String codeName;
	  private String codeDesc;
	  private String codeTypeAlias;
	  private int sortId;
	  private String state;
	public long getFlowId() {
		return flowId;
	}
	public void setFlowId(long flowId) {
		this.flowId = flowId;
	}
	public long getCodeId() {
		return codeId;
	}
	public void setCodeId(long codeId) {
		this.codeId = codeId;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
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
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeDesc() {
		return codeDesc;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
	public String getCodeTypeAlias() {
		return codeTypeAlias;
	}
	public void setCodeTypeAlias(String codeTypeAlias) {
		this.codeTypeAlias = codeTypeAlias;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	  
}
