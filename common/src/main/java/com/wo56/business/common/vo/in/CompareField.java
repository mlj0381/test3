package com.wo56.business.common.vo.in;

import java.io.Serializable;

public class CompareField implements Serializable {

	private String fieldId;
	private String fieldName;
	private CompareFieldValueTranslator translator;

	public CompareField(String fieldId, String fieldName) {
		this.fieldId = fieldId;
		this.fieldName = fieldName;
	}

	public CompareField(String fieldId, String fieldName, CompareFieldValueTranslator translator) {
		super();
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.translator = translator;
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

	public CompareFieldValueTranslator getTranslator() {
		return translator;
	}

	public void setTranslator(CompareFieldValueTranslator translator) {
		this.translator = translator;
	}
}
