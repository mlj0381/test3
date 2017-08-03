package com.wo56.business.ord.fieldcomparator;

import java.util.List;

import com.wo56.business.common.vo.in.CompareFieldDiffResult;

/**
 * 业务比较
 * @author chenjun
 *
 * @param <E>
 */
public interface BusinessComparator<E> {
	/**
	 * 比较方法，获取修改的字段
	 * 
	 * @param oldOjbect
	 * @param newObject
	 * @return
	 * @throws Exception
	 */
	List<CompareFieldDiffResult> compare(E oldObject, E newObject) throws Exception;
	
	/**
	 * 业务类型
	 * @return
	 */
	int busiType();
	
	/**
	 * 业务对象主键
	 * @param object
	 * @return
	 */
	long getBusiObjectId(E object);
	
	/**
	 * 获取业务对象主表主键，如果没有，则与getBusiObjectId方法返回一样的值
	 * @param object
	 * @return
	 */
	long getParentBusiObjectId(E object);
}
