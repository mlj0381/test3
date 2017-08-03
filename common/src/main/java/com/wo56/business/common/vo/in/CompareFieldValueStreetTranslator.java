package com.wo56.business.common.vo.in;

import org.apache.commons.lang.StringUtils;

import com.framework.components.citys.Street;
import com.framework.core.util.SysStaticDataUtil;

/**
 * 接到翻译器
 * 
 * @author chenjun
 *
 */
public class CompareFieldValueStreetTranslator implements CompareFieldValueTranslator<Long> {

	@Override
	public String translator(Long value) {
		if (null == value)
			return StringUtils.EMPTY;
		Street street = SysStaticDataUtil.getStreetDataList("SYS_STREET", String.valueOf(value));
		if (null != street)
			return street.getName();
		return String.valueOf(value);
	}
}
