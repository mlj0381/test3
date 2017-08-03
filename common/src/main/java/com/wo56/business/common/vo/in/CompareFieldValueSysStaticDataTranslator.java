package com.wo56.business.common.vo.in;

import org.apache.commons.lang.StringUtils;

import com.framework.core.cache.vo.SysStaticData;
import com.framework.core.util.SysStaticDataUtil;

/**
 * 静态数据翻译器
 * @author chenjun
 *
 */
public class CompareFieldValueSysStaticDataTranslator implements CompareFieldValueTranslator<Object> {
	private String codeType;

	public CompareFieldValueSysStaticDataTranslator(String codeType) {
		this.codeType = codeType;
	}

	@Override
	public String translator(Object value) {
		if (null == value)
			return StringUtils.EMPTY;
		SysStaticData staticData = SysStaticDataUtil.getSysStaticData(codeType, String.valueOf(value));
		return staticData != null ? staticData.getCodeName() == null ? StringUtils.EMPTY : staticData.getCodeName() : StringUtils.EMPTY;
	}
}
