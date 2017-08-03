package com.wo56.business.common.vo.in;

public class StringFieldComparator implements FieldComparator<String> {
	@Override
	public boolean compare(String fieldOldValue, String fieldNewValue) {
		if (null == fieldOldValue && fieldNewValue == null)
			return true;
		if (null == fieldOldValue || fieldNewValue == null)
			return false;
		return fieldOldValue.equals(fieldNewValue);
	}

	@Override
	public String getFileValueClassName() {
		return "java.lang.String";
	}
}
