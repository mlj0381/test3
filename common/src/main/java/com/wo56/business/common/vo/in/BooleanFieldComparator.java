package com.wo56.business.common.vo.in;

public class BooleanFieldComparator implements FieldComparator<Boolean> {
	@Override
	public boolean compare(Boolean fieldOldValue, Boolean fieldNewValue) {
		return fieldOldValue == fieldNewValue;
	}

	@Override
	public String getFileValueClassName() {
		return "java.lang.Boolean";
	}
}
