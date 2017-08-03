package com.wo56.common.in;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckExcelRowDataResult implements Serializable {
	private int resultCode;
	private String resultMessage;

	public CheckExcelRowDataResult() {
	}

	public CheckExcelRowDataResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public CheckExcelRowDataResult(int resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

}
