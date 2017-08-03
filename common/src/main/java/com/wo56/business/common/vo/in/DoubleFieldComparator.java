package com.wo56.business.common.vo.in;

public class DoubleFieldComparator implements FieldComparator<Double> {
	@Override
	public boolean compare(Double fieldOldValue, Double fieldNewValue) {
		if (null == fieldOldValue && fieldNewValue == null)
			return true;
		if (null == fieldOldValue || fieldNewValue == null)
			return false;
		return fieldOldValue.doubleValue() == fieldNewValue.doubleValue();
	}

	@Override
	public String getFileValueClassName() {
		return "java.lang.Double";
	}
}
