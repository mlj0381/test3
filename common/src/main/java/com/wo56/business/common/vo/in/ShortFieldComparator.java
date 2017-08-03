package com.wo56.business.common.vo.in;

public class ShortFieldComparator implements FieldComparator<Short> {
	@Override
	public boolean compare(Short fieldOldValue, Short fieldNewValue) {
		if (null == fieldOldValue && fieldNewValue == null)
			return true;
		if (null == fieldOldValue || fieldNewValue == null)
			return false;
		return fieldOldValue.shortValue() == fieldNewValue.shortValue();
	}

	@Override
	public String getFileValueClassName() {
		return "java.lang.Short";
	}
}
