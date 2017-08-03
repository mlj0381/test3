package com.wo56.business.common.vo.in;

import java.io.Serializable;

public class CompareFieldDiffResult implements Serializable {
	private String fieldId;
	private String fieldName;
	private String oldValue;
	private String oldOrgiValue;
	private String newValue;
	private String newOrgiValue;

	public CompareFieldDiffResult(String fieldId, String fieldName, String oldValue, String oldOrgiValue, String newValue, String newOrgiValue) {
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.oldValue = oldValue;
		this.oldOrgiValue = oldOrgiValue;
		this.newValue = newValue;
		this.newOrgiValue = newOrgiValue;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getOldOrgiValue() {
		return oldOrgiValue;
	}

	public void setOldOrgiValue(String oldOrgiValue) {
		this.oldOrgiValue = oldOrgiValue;
	}

	public String getNewOrgiValue() {
		return newOrgiValue;
	}

	public void setNewOrgiValue(String newOrgiValue) {
		this.newOrgiValue = newOrgiValue;
	}

	@Override
	public String toString() {
		return "CompareFieldDiffResult [fieldId=" + fieldId + ", fieldName="
				+ fieldName + ", oldValue=" + oldValue + ", newValue="
				+ newValue + "]";
	}
}
