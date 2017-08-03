package com.wo56.business.common.vo.in;

public class LongFieldComparator implements FieldComparator<Long> {
	@Override
	public boolean compare(Long fieldOldValue, Long fieldNewValue) {
		if (null == fieldOldValue && fieldNewValue == null)
			return true;
		if (null == fieldOldValue || fieldNewValue == null)
			return false;
		return fieldOldValue.longValue() == fieldNewValue.longValue();
	}

	@Override
	public String getFileValueClassName() {
		return "java.lang.Long";
	}
}
