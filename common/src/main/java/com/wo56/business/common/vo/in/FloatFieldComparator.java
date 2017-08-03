package com.wo56.business.common.vo.in;

public class FloatFieldComparator implements FieldComparator<Float> {
	@Override
	public boolean compare(Float fieldOldValue, Float fieldNewValue) {
		if (null == fieldOldValue && fieldNewValue == null)
			return true;
		if (null == fieldOldValue || fieldNewValue == null)
			return false;
		return fieldOldValue.floatValue() == fieldNewValue.floatValue();
	}

	@Override
	public String getFileValueClassName() {
		return "java.lang.Float";
	}
}
