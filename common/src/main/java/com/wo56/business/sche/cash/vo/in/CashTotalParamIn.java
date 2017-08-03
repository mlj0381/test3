package com.wo56.business.sche.cash.vo.in;

import com.framework.core.svcaller.interfaces.IParamIn;
import com.framework.core.svcaller.vo.PageInParamVO;
import com.wo56.common.consts.InterFacesCodeConsts;

public class CashTotalParamIn extends PageInParamVO implements IParamIn {

	private String doObjName;
	private String doTel;
	private String belongObjName;
	private String belongTel;
	private int state;
	private String signTimeBegin;
	private String signTimeEnd;
	private int belongObjType;
	private long belongObjId;
	private int doObjType;
	private long doObjId;
	private int userType;
	private Integer feeId;
	
	
	
	
	public Integer getFeeId() {
		return feeId;
	}

	public void setFeeId(Integer feeId) {
		this.feeId = feeId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Override
	public String getInCode() {
		// TODO Auto-generated method stub
		return InterFacesCodeConsts.CASH.CASH_TOTAL_QUERY;
	}

	public String getBelongObjName() {
		return belongObjName;
	}

	public void setBelongObjName(String belongObjName) {
		this.belongObjName = belongObjName;
	}

	public String getBelongTel() {
		return belongTel;
	}

	public void setBelongTel(String belongTel) {
		this.belongTel = belongTel;
	}

	public String getDoObjName() {
		return doObjName;
	}

	public void setDoObjName(String doObjName) {
		this.doObjName = doObjName;
	}

	public String getDoTel() {
		return doTel;
	}

	public void setDoTel(String doTel) {
		this.doTel = doTel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getSignTimeBegin() {
		return signTimeBegin;
	}

	public void setSignTimeBegin(String signTimeBegin) {
		this.signTimeBegin = signTimeBegin;
	}

	public String getSignTimeEnd() {
		return signTimeEnd;
	}

	public void setSignTimeEnd(String signTimeEnd) {
		this.signTimeEnd = signTimeEnd;
	}

	public int getBelongObjType() {
		return belongObjType;
	}

	public void setBelongObjType(int belongObjType) {
		this.belongObjType = belongObjType;
	}

	public long getBelongObjId() {
		return belongObjId;
	}

	public void setBelongObjId(long belongObjId) {
		this.belongObjId = belongObjId;
	}

	public int getDoObjType() {
		return doObjType;
	}

	public void setDoObjType(int doObjType) {
		this.doObjType = doObjType;
	}

	public long getDoObjId() {
		return doObjId;
	}

	public void setDoObjId(long doObjId) {
		this.doObjId = doObjId;
	}

}
