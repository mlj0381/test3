package com.wo56.business.common.vo.in;

public class IntegerFieldComparator implements FieldComparator<Integer> {
	@Override
	public boolean compare(Integer fieldOldValue, Integer fieldNewValue) {
		if (null == fieldOldValue && fieldNewValue == null)
			return true;
		if (null == fieldOldValue || fieldNewValue == null)
			return false;
		return fieldOldValue.intValue() == fieldNewValue.intValue();
	}

	@Override
	public String getFileValueClassName() {
		return "java.lang.Integer";
	}
}
