package com.wo56.business.common.vo.in;

import org.apache.commons.lang.StringUtils;

import com.framework.components.citys.District;
import com.framework.core.util.SysStaticDataUtil;

/**
 * 区县翻译器
 * 
 * @author chenjun
 *
 */
public class CompareFieldValueCountyTranslator implements CompareFieldValueTranslator<Long> {

	@Override
	public String translator(Long value) {
		if (null == value)
			return StringUtils.EMPTY;
		District district = SysStaticDataUtil.getDistrictDataList("SYS_DISTRICT", String.valueOf(value));
		if (null != district)
			return district.getName();
		return String.valueOf(value);
	}
}
