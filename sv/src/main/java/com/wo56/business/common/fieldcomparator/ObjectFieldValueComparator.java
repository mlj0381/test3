package com.wo56.business.common.fieldcomparator;

import java.util.List;

import com.wo56.business.common.vo.in.CompareFieldDiffResult;

public interface ObjectFieldValueComparator<E> {
	public List<CompareFieldDiffResult> compare(E oldObject, E newObject) throws Exception;
}
