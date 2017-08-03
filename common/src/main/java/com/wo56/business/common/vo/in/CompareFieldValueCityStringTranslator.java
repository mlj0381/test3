package com.wo56.business.common.vo.in;

import org.apache.commons.lang.StringUtils;

import com.framework.components.citys.City;
import com.framework.core.util.SysStaticDataUtil;

/**
 * 地市翻译器
 * 
 * @author chenjun
 *
 */
public class CompareFieldValueCityStringTranslator implements CompareFieldValueTranslator<String> {

	@Override
	public String translator(String value) {
		if (null == value)
			return StringUtils.EMPTY;
		City city = SysStaticDataUtil.getCityDataList("SYS_CITY", value);
		if (null != city)
			return city.getName();
		return String.valueOf(value);
	}
}
