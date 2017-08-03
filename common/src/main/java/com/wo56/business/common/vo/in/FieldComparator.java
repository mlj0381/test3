package com.wo56.business.common.vo.in;

public interface FieldComparator<E> {
	/** 字段域值对应的class名字 */
	public String getFileValueClassName();

	/** 比较新旧值是否相等 */
	public boolean compare(E fieldOldValue, E fieldNewValue);
}
